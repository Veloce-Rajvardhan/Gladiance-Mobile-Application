package com.gladiance.local_control;

import android.text.TextUtils;
import android.util.Log;

import com.gladiance.AppConstants;
import com.gladiance.provisioning.listeners.ResponseListener;
import com.gladiance.provisioning.security.Security;
import com.gladiance.provisioning.transport.Transport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EspLocalTransport implements Transport {
    private String TAG = EspLocalTransport.class.getSimpleName();

    private static final String SET_COOKIE_HEADER = "Set-Cookie";
    private static final String COOKIE_HEADER = "Cookie";

    private String baseUrl;
    private ExecutorService workerThreadPool;
    private CookieManager cookieManager;

    public EspLocalTransport(String baseUrl) {
        this.baseUrl = baseUrl;
        this.workerThreadPool = Executors.newSingleThreadExecutor();
        if (cookieManager == null) {
            cookieManager = new CookieManager();
        }
    }

    private byte[] sendPostRequest(String path, byte[] data) throws IOException {
        byte[] responseBytes = null;
        URL url = new URL(baseUrl + "/" + path);
        Log.e(TAG, "URL : " + url.toString());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);

        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Accept", "text/plain");
        urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(5000);

        if (cookieManager.getCookieStore().getCookies().size() > 0) {

            Log.d(TAG, "Cookie - Name : " + cookieManager.getCookieStore().getCookies().get(0).getName());
            Log.d(TAG, "Cookie - Value : " + cookieManager.getCookieStore().getCookies().get(0).getValue());
            // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
            urlConnection.setRequestProperty(COOKIE_HEADER,
                    TextUtils.join(";", cookieManager.getCookieStore().getCookies()));
        }

        OutputStream os = urlConnection.getOutputStream();
        os.write(data);
        os.close();

        int responseCode = urlConnection.getResponseCode();
        Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
        List<String> cookiesHeader = headerFields.get(SET_COOKIE_HEADER);

        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                HttpCookie httpCookie = HttpCookie.parse(cookie).get(0);
                // Default version of HttpCookie is 1. In version 1, quotes will be added.
                // So set version 0 so that quotes will not be added.
                httpCookie.setVersion(0);
                cookieManager.getCookieStore().add(null, httpCookie);
            }
        }

        if (responseCode == HttpURLConnection.HTTP_OK) {
            int n;
            byte[] byteChunk = new byte[4096];
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream is = urlConnection.getInputStream();
            while ((n = is.read(byteChunk)) > 0) {
                outputStream.write(byteChunk, 0, n);
            }
            responseBytes = outputStream.toByteArray();
        }

        return responseBytes;
    }

    /***
     * This method is used to send data on given path using this transport.
     * @param path path of the config endpoint.
     * @param data config data to be sent
     * @param listener listener implementation which receives events when response is received.
     */
    @Override
    public void sendConfigData(final String path, final byte[] data, final ResponseListener listener) {
        this.workerThreadPool
                .submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            byte[] returnData = sendPostRequest(path, data);

                            if (returnData == null) {
                                listener.onFailure(new RuntimeException("Response not received."));
                            } else {
                                listener.onSuccess(returnData);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            listener.onFailure(e);
                        }
                    }
                });
    }
}
