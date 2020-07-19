package com.hlw.android.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hlw.android.aidlserver.TestBean;
import com.hlw.android.aidlserver.TestBeanManager;

import java.util.List;

/**
 * 客户端的AIDLActivity.java
 * 由于测试机的无用debug信息太多，故log都是用的e
 * <p/>
 * Created by lypeer on 2016/7/17.
 */
public class TestBeanAIDLActivity extends AppCompatActivity {

    private TestBeanManager testBeanManager = null;

    /**
     * 标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
     */
    private boolean isConnected = false;

    private List<TestBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bean_aidl);
    }

    /**
     * 按钮的点击事件，点击之后调用服务端的addBookIn方法
     */
    public void addTestBean() {
        //如果与服务端的连接处于未连接状态，则尝试连接
        if (!isConnected) {
            bindService();
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (testBeanManager == null) return;

        TestBean bean = new TestBean();
        bean.setId((int) (Math.random() * 100000));
        bean.setContent("time: " + System.currentTimeMillis());
        try {
            testBeanManager.addBeanIn(bean);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 尝试与服务端建立连接
     */
    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("com.hlw.aidldemo.test_bean_service");
        intent.setPackage(getPackageName());
        intent.setComponent(new ComponentName("com.hlw.android.aidlserver", "com.hlw.android.aidlserver.AIDLTestBeanService"));
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isConnected) {
            bindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isConnected) {
            unbindService(mServiceConnection);
            isConnected = false;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(Constants.LOG_TAG, "service connected");
            testBeanManager = TestBeanManager.Stub.asInterface(service);
            isConnected = true;

            if (testBeanManager != null) {
                try {
                    mDatas = testBeanManager.getDatas();
                    Log.e(Constants.LOG_TAG, mDatas.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(Constants.LOG_TAG, "service disconnected");
            isConnected = false;
        }
    };

    public void addBean(View view) throws Exception {
        addTestBean();
    }

    public void queryBean(View view) throws Exception {
        queryTestBean();
    }

    private void queryTestBean() {
        if (!isConnected || testBeanManager == null) {
            return;
        }
        try {
            mDatas = testBeanManager.getDatas();
            for (TestBean bean :
                    mDatas) {
                Log.d(Constants.LOG_TAG, bean.getId() + " " + bean.getContent());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}