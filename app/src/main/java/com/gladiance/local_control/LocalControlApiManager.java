package com.gladiance.local_control;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.gladiance.AppConstants;
import com.gladiance.EspLocalCtrl;
import com.gladiance.JsonDataParser;
import com.gladiance.cloudapi.ApiResponseListener;
import com.gladiance.provisioning.listeners.ResponseListener;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.activities.EspNode;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import espressif.Constants;

public class LocalControlApiManager {
    private final String TAG = LocalControlApiManager.class.getSimpleName();

    private EspApplication espApp;

    public LocalControlApiManager(Context context, EspApplication eApp) {
        espApp = eApp; // new EspApplication(context.getApplicationContext());
    }

    public void getNodeDetails(final String nodeId, final ApiResponseListener listener) {

        Log.d(TAG, "Get Node details on local network for id : " + nodeId);

        if (espApp.localDeviceMap.containsKey(nodeId)) {

            final EspLocalDevice localDevice = espApp.localDeviceMap.get(nodeId);

            if (localDevice.getPropertyCount() != 0) {

                getPropertyValues(AppConstants.LOCAL_CONTROL_ENDPOINT, localDevice, new ApiResponseListener() {

                    @Override
                    public void onSuccess(Bundle data) {

                        Log.d(TAG, "Get node detail - Success");

                        if (data != null) {

                            String configData = data.getString(AppConstants.KEY_CONFIG);
                            String paramsData = data.getString(AppConstants.KEY_PARAMS);

                            Log.d(TAG, "Config data : " + configData);
                            Log.d(TAG, "Params data : " + paramsData);

                            if (!TextUtils.isEmpty(configData)) {

                                JSONObject configJson = null;
                                try {
                                    configJson = new JSONObject(configData);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                EspNode node = espApp.nodeMap.get(nodeId);
                                boolean isDeviceFound = false;
                                if (node != null) {
                                    isDeviceFound = true;
                                }
                                EspNode localNode = JsonDataParser.setNodeConfig(node, configJson);

                                if (!TextUtils.isEmpty(paramsData) && isDeviceFound) {

                                    JSONObject paramsJson = null;
                                    try {
                                        paramsJson = new JSONObject(paramsData);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JsonDataParser.setAllParams(espApp, localNode, paramsJson);
                                    espApp.nodeMap.put(node.getNodeId(), localNode);
                                    listener.onSuccess(null);
                                }
                            }
                        }
                    }

                    @Override
                    public void onResponseFailure(Exception exception) {
                        listener.onResponseFailure(exception);
                    }

                    @Override
                    public void onNetworkFailure(Exception exception) {
                        listener.onNetworkFailure(exception);
                    }
                });

            } else {

                getPropertyCount(AppConstants.LOCAL_CONTROL_ENDPOINT, localDevice, new ApiResponseListener() {

                    @Override
                    public void onSuccess(Bundle data) {

                        if (data != null) {

                            int count = data.getInt(AppConstants.KEY_PROPERTY_COUNT, 0);
                            localDevice.setPropertyCount(count);
                            getPropertyValues(AppConstants.LOCAL_CONTROL_ENDPOINT, localDevice, listener);
                        }
                    }

                    @Override
                    public void onResponseFailure(Exception exception) {
                        listener.onResponseFailure(exception);
                    }

                    @Override
                    public void onNetworkFailure(Exception exception) {
                        listener.onNetworkFailure(exception);
                    }
                });
            }
        } else {
            listener.onResponseFailure(new RuntimeException("Device not available locally."));
        }
    }

    public void getParamsValues(final String nodeId, final ApiResponseListener listener) {

        Log.d(TAG, "Get param values on local network for node : " + nodeId);

        if (espApp.localDeviceMap.containsKey(nodeId)) {

            final EspLocalDevice localDevice = espApp.localDeviceMap.get(nodeId);

            if (localDevice.getPropertyCount() != 0) {

                getPropertyValues(AppConstants.LOCAL_CONTROL_ENDPOINT, localDevice, new ApiResponseListener() {

                    @Override
                    public void onSuccess(Bundle data) {

                        Log.d(TAG, "Get param values - Success");

                        if (data != null) {

                            String configData = data.getString(AppConstants.KEY_CONFIG);
                            String paramsData = data.getString(AppConstants.KEY_PARAMS);
                            Log.e(TAG, "Params data : " + paramsData);

                            if (!TextUtils.isEmpty(configData)) {

                                JSONObject configJson = null;
                                try {
                                    configJson = new JSONObject(configData);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                EspNode node = espApp.nodeMap.get(nodeId);
                                boolean isDeviceFound = false;
                                if (node != null) {
                                    isDeviceFound = true;
                                }
                                EspNode localNode = JsonDataParser.setNodeConfig(node, configJson);

                                if (!TextUtils.isEmpty(paramsData) && isDeviceFound) {

                                    JSONObject paramsJson = null;
                                    try {
                                        paramsJson = new JSONObject(paramsData);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JsonDataParser.setAllParams(espApp, localNode, paramsJson);
                                    listener.onSuccess(null);
                                }
                            }
                        }
                    }

                    @Override
                    public void onResponseFailure(Exception exception) {
                        listener.onResponseFailure(exception);
                    }

                    @Override
                    public void onNetworkFailure(Exception exception) {
                        listener.onNetworkFailure(exception);
                    }
                });

            } else {

                getPropertyCount(AppConstants.LOCAL_CONTROL_ENDPOINT, localDevice, new ApiResponseListener() {

                    @Override
                    public void onSuccess(Bundle data) {

                        if (data != null) {

                            int count = data.getInt(AppConstants.KEY_PROPERTY_COUNT, 0);
                            localDevice.setPropertyCount(count);
                            getPropertyValues(AppConstants.LOCAL_CONTROL_ENDPOINT, localDevice, listener);
                        }
                    }

                    @Override
                    public void onResponseFailure(Exception exception) {
                        listener.onResponseFailure(exception);
                    }

                    @Override
                    public void onNetworkFailure(Exception exception) {
                        listener.onNetworkFailure(exception);
                    }
                });
            }
        } else {
            listener.onResponseFailure(new RuntimeException("Device not available locally."));
        }
    }

//    public void updateParamValue(final String nodeId, JsonObject body, final ApiResponseListener listener) {
    public void updateParamValue(final String nodeId, String commandString, final ApiResponseListener listener) {

        Log.d(TAG, "Update param values on local network");

        if (espApp.localDeviceMap.containsKey(nodeId)) {

            EspLocalDevice localDevice = espApp.localDeviceMap.get(nodeId);

//            String jsonData = body.toString();
//            byte[] data = createSetPropertyInfoRequest(jsonData);

            byte[] data = createSetPropertyInfoRequest(commandString);

            localDevice.sendData(AppConstants.LOCAL_CONTROL_ENDPOINT, data, new ResponseListener() {

                @Override
                public void onSuccess(byte[] returnData) {

                    Log.d(TAG, "Update param values - Success");

                    if (returnData != null) {

                        // Set Property Response
                        try {
                            EspLocalCtrl.LocalCtrlMessage response = EspLocalCtrl.LocalCtrlMessage.parseFrom(returnData);
                            Constants.Status status = response.getRespSetPropVals().getStatus();

                            if (status.equals(Constants.Status.Success)) {
                                listener.onSuccess(null);
                            } else {
                                listener.onResponseFailure(new RuntimeException("Failed to update param."));
                            }

                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Log.e(TAG, "returnData is null");
                        listener.onResponseFailure(new RuntimeException("Response not received."));
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    listener.onResponseFailure(e);
                }
            });

        } else {
            listener.onResponseFailure(new RuntimeException("Device not available locally."));
        }
    }

    public void getPropertyCount(final String path, EspLocalDevice localDevice, final ApiResponseListener listener) {

        byte[] data = createGetPropertyCountRequest();

        localDevice.sendData(path, data, new ResponseListener() {

            @Override
            public void onSuccess(byte[] returnData) {
                if (returnData != null) {
                    int count = processGetPropertyCount(returnData);
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppConstants.KEY_PROPERTY_COUNT, count);
                    listener.onSuccess(bundle);
                } else {
                    Log.e("TAG", "returnData is null");
                }
            }

            @Override
            public void onFailure(Exception e) {
                listener.onResponseFailure(e);
            }
        });
    }

    public void getPropertyValues(String path, EspLocalDevice localDevice, final ApiResponseListener listener) {

        byte[] data = createGetAllPropertyValuesRequest(localDevice.getPropertyCount());

        localDevice.sendData(path, data, new ResponseListener() {

            @Override
            public void onSuccess(byte[] returnData) {
                if (returnData != null) {
                    processGetPropertyValue(returnData, listener);
                } else {
                    listener.onResponseFailure(new RuntimeException("Response not received."));
                }
            }

            @Override
            public void onFailure(Exception e) {
                listener.onResponseFailure(e);
            }
        });
    }

    private byte[] createGetPropertyCountRequest() {

        EspLocalCtrl.LocalCtrlMsgType msgType = EspLocalCtrl.LocalCtrlMsgType.TypeCmdGetPropertyCount;
        EspLocalCtrl.LocalCtrlMessage msg = EspLocalCtrl.LocalCtrlMessage.newBuilder()
                .setMsg(msgType)
                .setCmdGetPropCount(EspLocalCtrl.CmdGetPropertyCount.newBuilder())
                .build();

        return msg.toByteArray();
    }

    private byte[] createSetPropertyInfoRequest(String jsonData) {

        EspLocalCtrl.LocalCtrlMsgType msgType = EspLocalCtrl.LocalCtrlMsgType.TypeCmdSetPropertyValues;
        EspLocalCtrl.PropertyValue prop = EspLocalCtrl.PropertyValue.newBuilder()
                .setIndex(1)
                .setValue(ByteString.copyFrom(jsonData, Charset.forName("UTF-8")))
                .build();

        EspLocalCtrl.CmdSetPropertyValues payload = EspLocalCtrl.CmdSetPropertyValues.newBuilder()
                .addProps(prop).build();

        EspLocalCtrl.LocalCtrlMessage msg = EspLocalCtrl.LocalCtrlMessage.newBuilder()
                .setMsg(msgType)
                .setCmdSetPropVals(payload)
                .build();

        return msg.toByteArray();
    }

    private byte[] createGetAllPropertyValuesRequest(int count) {

        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            indices.add(i);
        }
        EspLocalCtrl.LocalCtrlMsgType msgType = EspLocalCtrl.LocalCtrlMsgType.TypeCmdGetPropertyValues;
        EspLocalCtrl.CmdGetPropertyValues payload = EspLocalCtrl.CmdGetPropertyValues.newBuilder()
                .addAllIndices(indices).build();

        EspLocalCtrl.LocalCtrlMessage msg = EspLocalCtrl.LocalCtrlMessage.newBuilder()
                .setMsg(msgType)
                .setCmdGetPropVals(payload)
                .build();

        return msg.toByteArray();
    }

    private int processGetPropertyCount(byte[] returnData) {

        int count = 0;

        try {
            EspLocalCtrl.LocalCtrlMessage response = EspLocalCtrl.LocalCtrlMessage.parseFrom(returnData);

            if (response.getRespGetPropCount().getStatus().equals(Constants.Status.Success)) {
                count = response.getRespGetPropCount().getCount();
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return count;
    }

    private void processGetPropertyValue(byte[] returnData, final ApiResponseListener listener) {

        try {
            EspLocalCtrl.LocalCtrlMessage response = EspLocalCtrl.LocalCtrlMessage.parseFrom(returnData);

            if (response.getRespGetPropVals().getStatus().equals(Constants.Status.Success)) {

                List<EspLocalCtrl.PropertyInfo> propertyInfoList = response.getRespGetPropVals().getPropsList();
                Bundle bundle = new Bundle();

                for (int i = 0; i < propertyInfoList.size(); i++) {
                    EspLocalCtrl.PropertyInfo propertyInfo = propertyInfoList.get(i);
                    bundle.putString(propertyInfo.getName(), propertyInfo.getValue().toStringUtf8());
                }
                listener.onSuccess(bundle);
            } else {
                listener.onResponseFailure(new RuntimeException("Failed to get data from device"));
            }

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            listener.onResponseFailure(e);
        }
    }
}
