package com.nurbk.ps.urlconnection.Utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AppExecutor {

    private static AppExecutor instance;

    private static Executor networkExecutor;
    private static Executor diskIoExecutor;
    private static Executor mainExecutor;

    private AppExecutor() {
        networkExecutor = Executors.newFixedThreadPool(3);
        diskIoExecutor = Executors.newSingleThreadExecutor();
        mainExecutor = new MainThreadExecutor();
    }

    public static Executor getNetworkExecutor() {
        return networkExecutor;
    }

    public static Executor getDiskIoExecutor() {
        return diskIoExecutor;
    }

    public static Executor getMainExecutor() {
        return mainExecutor;
    }

    public static AppExecutor getInstance() {
        if (instance == null)
            instance = new AppExecutor();
        return instance;
    }

    private static class MainThreadExecutor implements Executor {
        private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }

}
