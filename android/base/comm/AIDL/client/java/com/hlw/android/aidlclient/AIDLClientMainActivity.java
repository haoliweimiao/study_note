package com.hlw.android.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hlw.android.aidlserver.TestAidlInterface;

public class AIDLClientMainActivity extends AppCompatActivity {

    private TestAidlInterface mServerAIDL;

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(Constants.LOG_TAG, "onServiceDisconnected");
            mServerAIDL = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(Constants.LOG_TAG, "onServiceConnected");
            mServerAIDL = TestAidlInterface.Stub.asInterface(service);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_client_main);
    }

    /**
     * 点击BindService按钮时调用
     *
     * @param view
     */
    public void bindService(View view) {
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        intent.setAction("com.hlw.aidldemo.service");
        intent.setComponent(new ComponentName("com.hlw.android.aidlserver", "com.hlw.android.aidlserver.AIDLService"));
        boolean result = bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
        Log.i(Constants.LOG_TAG, "connect server result: " + result);
    }

    /**
     * 点击unBindService按钮时调用
     *
     * @param view
     */
    public void unbindService(View view) {
        unbindService(mServiceConn);
    }

    /**
     * 点击12+12按钮时调用
     *
     * @param view
     */
    public void addInvoked(View view) throws Exception {

        if (mServerAIDL != null) {
            int addRes = mServerAIDL.add(12, 12);
            Toast.makeText(this, addRes + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务器被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();

        }

    }

    /**
     * 点击50-12按钮时调用
     *
     * @param view
     */
    public void minInvoked(View view) throws Exception {
        if (mServerAIDL != null) {
            int addRes = mServerAIDL.min(58, 12);
            Toast.makeText(this, addRes + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务端未绑定或被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    public void toTestBeanPage(View view) throws Exception {
        startActivity(new Intent(this, TestBeanAIDLActivity.class));
    }

}
