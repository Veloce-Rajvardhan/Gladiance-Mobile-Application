package com.gladiance.local_control;

import android.util.Log;

import com.gladiance.AppConstants;
import com.gladiance.provisioning.listeners.ResponseListener;
import com.gladiance.provisioning.security.Security;
import com.gladiance.provisioning.security.Security0;
import com.gladiance.provisioning.security.Security1;
import com.gladiance.provisioning.security.Security2;

import java.util.HashMap;

public class EspLocalDevice {
    public static final String TAG = EspLocalDevice.class.getSimpleName();

    private String nodeId;
    private String serviceName;
    private String ipAddr;
    private int port;
    private HashMap<String, String> endpointList;
    private String pop = "";
    private int securityType;
    private int propertyCount;

    private EspLocalSession session;
    private EspLocalTransport transport;
    private SessionState sessionState = SessionState.NOT_CREATED;

    enum SessionState {
        NOT_CREATED,
        CREATING,
        CREATED,
        FAILED
    }

    public EspLocalDevice(String nodeId, String ipAddr, int port) {
        this.nodeId = nodeId;
        this.ipAddr = ipAddr;
        this.port = port;
        this.propertyCount = -1;
    }

    private void initSession(final ResponseListener listener) {

        if (!sessionState.equals(SessionState.CREATING)) {

            sessionState = SessionState.CREATING;
            Log.d(TAG, "========= Init Session for local device =========");

            final String url = "http://" + getIpAddr() + ":" + getPort();
            Security security = null;
            if (securityType == 2) {
                security = new Security2(AppConstants.DEFAULT_SEC2_USER_NAME, pop);
                Log.d(TAG, "Created security 2 with pop : " + pop);
            } else if (securityType == 1) {
                security = new Security1(pop);
                Log.d(TAG, "Created security 1 with pop : " + pop);
            } else if (securityType == 0) {
                security = new Security0();
            } else {
                // Consider Sec0 for other
                security = new Security0();
//            listener.onFailure(new RuntimeException("Security type " + securityType + " not supported"));
            }
            Log.d(TAG, "POP : " + pop);
            Log.d(TAG, "Type : " + securityType);
            transport = new EspLocalTransport(url);
            session = new EspLocalSession(transport, security);

            session.init(null, new EspLocalSession.SessionListener() {

                @Override
                public void OnSessionEstablished() {
                    sessionState = SessionState.CREATED;
                    Log.d(TAG, "========= Session established on local network");
                    listener.onSuccess(null);
                }

                @Override
                public void OnSessionEstablishFailed(Exception e) {
                    sessionState = SessionState.FAILED;
                    listener.onFailure(e);
                }
            });
        } else {
            Log.e(TAG, "Incorrect session initialisation for local device.");
        }
    }

    public void sendData(final String path, final byte[] data, final ResponseListener listener) {

        Log.d(TAG, "Send data to device on path : " + path);

        if (session == null || !session.isEstablished()) {

            initSession(new ResponseListener() {

                @Override
                public void onSuccess(byte[] returnData) {
                    sendData(path, data, listener);
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onFailure(new RuntimeException("Failed to create session."));
                    }
                }
            });
        } else {
            Log.d(TAG, "Session is already created");
            session.sendDataToDevice(path, data, new ResponseListener() {
                @Override
                public void onSuccess(byte[] returnData) {
                    listener.onSuccess(returnData);
                }

                @Override
                public void onFailure(Exception e) {
                    initSession(new ResponseListener() {

                        @Override
                        public void onSuccess(byte[] returnData) {
                            Log.d(TAG, "======== Session established again");
                            sendData(path, data, listener);
                        }

                        @Override
                        public void onFailure(Exception e) {
                            e.printStackTrace();
                            if (listener != null) {
                                listener.onFailure(new RuntimeException("Failed to create session."));
                            }
                        }
                    });
                }
            });
        }
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public int getPort() {
        return port;
    }

    public HashMap<String, String> getEndpointList() {
        return endpointList;
    }

    public void setEndpointList(HashMap<String, String> endpointList) {
        this.endpointList = endpointList;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        Log.d(TAG, "========= Set POP : " + pop);
        this.pop = pop;
    }

    public int getSecurityType() {
        return securityType;
    }

    public void setSecurityType(int securityType) {
        Log.d(TAG, "========= Set Security Type : " + securityType);
        this.securityType = securityType;
    }

    public int getPropertyCount() {
        return propertyCount;
    }

    public void setPropertyCount(int propertyCount) {
        this.propertyCount = propertyCount;
    }
}