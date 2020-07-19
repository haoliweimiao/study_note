package com.hlw.android.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AIDLTestBeanService extends Service {

    private List<TestBean> mDatas = new ArrayList<>();

    private final TestBeanManager.Stub testBeanManager = new TestBeanManager.Stub() {
        @Override
        public List<TestBean> getDatas() throws RemoteException {
            synchronized (this) {
                if (mDatas == null) {
                    mDatas = new ArrayList<>();
                }
                return mDatas;
            }
        }

        @Override
        public void addBeanIn(TestBean data) throws RemoteException {
            synchronized (this) {
                if (mDatas == null) {
                    mDatas = new ArrayList<>();
                }

                mDatas.add(data);
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return testBeanManager;
    }
}
