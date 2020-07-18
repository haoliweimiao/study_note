package com.hlw.android.messengerclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessengerClientActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final int MSG_SUM = 0x110;

    private Button mBtnSendMsgToServer, mBtnToConnectServer;
    private LinearLayout mLogContent;
    private NestedScrollView mNsvContent;
    private TextView mTvState;

    private Messenger mServerMessenger;
    private int mSendMsgCount;

    private boolean isConnectServerSuccess;

    private HandlerThread mHandlerThread = new HandlerThread("CLIENT_HANDLER_THREAD");
    private Messenger mClientMessenger;

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (!isFinishing()) {
                mServerMessenger = new Messenger(service);
                isConnectServerSuccess = true;
                mBtnToConnectServer.setVisibility(View.GONE);
                mTvState.setText("connected!");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (!isFinishing()) {
                mServerMessenger = null;
                isConnectServerSuccess = false;
                mBtnToConnectServer.setVisibility(View.VISIBLE);
                mTvState.setText("disconnected!");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_client);

        mTvState = findViewById(R.id.id_tv_callback);
        mBtnSendMsgToServer = findViewById(R.id.btn_send_message);
        mBtnToConnectServer = findViewById(R.id.btn_connect_server);
        mLogContent = findViewById(R.id.ll_container);
        mNsvContent = findViewById(R.id.nsv_content);

        mBtnSendMsgToServer.setOnClickListener(this);
        mBtnToConnectServer.setOnClickListener(this);

        initData();
    }

    private void initData() {

        mHandlerThread.start();
        Handler handler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msgFromServer) {
                switch (msgFromServer.what) {
                    case MSG_SUM: {
                        if (!isFinishing()) {
                            TextView tv = mLogContent.findViewById(msgFromServer.arg1);
                            String showText = String.format("%s=>%s", tv.getText(), msgFromServer.arg2);
                            tv.setText(showText);
                        }
                    }
                    break;
                }
                super.handleMessage(msgFromServer);
            }
        };
        mClientMessenger = new Messenger(handler);

    }

    private void addShowMessage(int tvID, String text) {
        if (isFinishing() || TextUtils.isEmpty(text) || mLogContent == null) {
            return;
        }
        TextView tv = new TextView(MessengerClientActivity.this);
        tv.setText(text);
        tv.setId(tvID);
        mLogContent.addView(tv);
    }

    private void bindServiceInvoked() {
        try {
            Intent intent = new Intent();
            intent.setAction("com.hlw.android.message");
            intent.setPackage(getPackageName());
            intent.setComponent(new ComponentName("com.hlw.android.messengerserver", "com.hlw.android.messengerserver.MessengerService"));
            boolean result = bindService(intent, mConn, Context.BIND_AUTO_CREATE);
            Log.e(Constants.LOG_TAG, "bindService invoked ! result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandlerThread != null) {
            mHandlerThread.quitSafely();
        }
        if (mConn != null) {
            unbindService(mConn);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_message: {
                sendMessageToServer();
            }
            break;
            case R.id.btn_connect_server: {
                bindServiceInvoked();
            }
            break;
            default:
                break;
        }
    }

    private void sendMessageToServer() {
        try {
            int a = mSendMsgCount++;
            if (!isConnectServerSuccess) {
                addShowMessage(a, "cannot connect server!!!!!!");
                return;
            }
            int b = (int) (Math.random() * 100);

            String showText = String.format("%s + %s = caculating ...", a, b);

            addShowMessage(a, showText);

            Message msgFromClient = Message.obtain(null, MSG_SUM, a, b);
            msgFromClient.replyTo = mClientMessenger;
            if (isConnectServerSuccess && mServerMessenger != null) {
                //往服务端发送消息
                mServerMessenger.send(msgFromClient);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
