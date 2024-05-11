package com.gladiance.local_control;

import com.gladiance.AppConstants;
import com.gladiance.provisioning.listeners.ResponseListener;
import com.gladiance.provisioning.security.Security;
import com.gladiance.provisioning.transport.Transport;

public class EspLocalSession {
    private static final String TAG = EspLocalSession.class.getSimpleName();
    private Transport transport;
    private Security security;
    private boolean isSessionEstablished;

    /**
     * Initialize Session object with Transport and Security interface implementations
     *
     * @param transport
     * @param security
     */
    public EspLocalSession(Transport transport, Security security) {
        this.transport = transport;
        this.security = security;
    }

    /**
     * Get the Security implementation object
     *
     * @return
     */
    public Security getSecurity() {
        return security;
    }

    /**
     * Get the Transport implementation object.
     *
     * @return
     */
    public Transport getTransport() {
        return transport;
    }

    /**
     * Get whether a secure Session has been established.
     *
     * @return
     */
    public boolean isEstablished() {
        return isSessionEstablished;
    }

    /**
     * Establish the session by performing handshake with the device
     * based on the Security implementation.
     * Communication with the device will happen over the Transport interface.
     *
     * @throws RuntimeException
     */
    public void init(byte[] response, final SessionListener sessionListener) throws RuntimeException {

        try {

            byte[] request = security.getNextRequestInSession(response);

            if (request == null) {

                isSessionEstablished = true;
                if (sessionListener != null) {
                    sessionListener.OnSessionEstablished();
                }
            } else {

                transport.sendConfigData(AppConstants.LOCAL_SESSION_ENDPOINT, request, new ResponseListener() {

                    @Override
                    public void onSuccess(byte[] returnData) {
                        if (returnData == null) {
                            if (sessionListener != null) {
                                sessionListener.OnSessionEstablishFailed(new RuntimeException("Session could not be established"));
                            }
                        } else {
                            init(returnData, sessionListener);
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        if (sessionListener != null) {
                            sessionListener.OnSessionEstablishFailed(e);
                        }
                    }
                });
            }
        } catch (RuntimeException e) {
            if (response == null && sessionListener != null) {
                sessionListener.OnSessionEstablishFailed(new RuntimeException("Session could not be established"));
            }
        }
    }

    public void sendDataToDevice(final String path, byte[] data, final ResponseListener listener) {

        final byte[] encryptedData = security.encrypt(data);

        if (isSessionEstablished) {

            transport.sendConfigData(path, encryptedData, new ResponseListener() {

                @Override
                public void onSuccess(byte[] returnData) {

                    byte[] decryptedData = security.decrypt(returnData);
                    if (listener != null) {
                        listener.onSuccess(decryptedData);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    isSessionEstablished = false;
                    if (listener != null) {
                        listener.onFailure(e);
                    }
                }
            });

        } else {

            init(null, new SessionListener() {

                @Override
                public void OnSessionEstablished() {

                    transport.sendConfigData(path, encryptedData, new ResponseListener() {

                        @Override
                        public void onSuccess(byte[] returnData) {
                            if (listener != null) {
                                listener.onSuccess(returnData);
                            }
                        }

                        @Override
                        public void onFailure(Exception e) {
                            isSessionEstablished = false;
                            if (listener != null) {
                                listener.onFailure(e);
                            }
                        }
                    });
                }

                @Override
                public void OnSessionEstablishFailed(Exception e) {
                    if (listener != null) {
                        listener.onFailure(e);
                    }
                }
            });
        }
    }

    /**
     * Callback interface for listening to Session
     * establish events.
     */
    public interface SessionListener {

        /**
         * Called when session is established.
         * Further communication with the device can only
         * occur after this callback is called.
         */
        void OnSessionEstablished();

        /**
         * Called when session establish fails.
         *
         * @param e Exception
         */
        void OnSessionEstablishFailed(Exception e);
    }
}
