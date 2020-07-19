package com.hlw.android.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class AIDLService extends Service {

    public void onCreate() {
        Log.e(Constants.LOG_TAG, "onCreate");
    }

    public IBinder onBind(Intent t) {
        Log.e(Constants.LOG_TAG, "onBind");
        return mBinder;
    }

    public void onDestroy() {
        Log.e(Constants.LOG_TAG, "onDestroy");
        super.onDestroy();
    }

    public boolean onUnbind(Intent intent) {
        Log.e(Constants.LOG_TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent) {
        Log.e(Constants.LOG_TAG, "onRebind");
        super.onRebind(intent);
    }

    private final TestAidlInterface.Stub mBinder = new TestAidlInterface.Stub() {

        @Override
        public int add(int x, int y) throws RemoteException {
            return x + y;
        }

        @Override
        public int min(int x, int y) throws RemoteException {
            return x - y;
        }

    };
}
