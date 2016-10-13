package com.android.msahakyan.ottonovaclient.application;

import android.app.Application;

import com.android.msahakyan.ottonovaclient.BuildConfig;
import com.android.msahakyan.ottonovaclient.network.Endpoint;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import timber.log.Timber;

/**
 * @author msahakyan
 */
public class SocketIOApp extends Application {

    private static SocketIOApp sInstance;
    private Socket mSocket;

    public static synchronized SocketIOApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initSocket();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initSocket() {
        try {
            mSocket = IO.socket(Endpoint.SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
