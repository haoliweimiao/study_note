package com.hlw.android.messengerserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;

public class MessengerService extends Service {

    private static final int MSG_SUM = 0x110;

    private Messenger mServerMessenger;
    private HandlerThread mHandlerThread = new HandlerThread("SERVER_HANDLER_THREAD");

    @Override
    public void onCreate() {
        Log.d(Constants.LOG_TAG, "MessengerService onCreate");
        mHandlerThread.start();
        Handler handler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msgfromClient) {
                Message msgToClient = Message.obtain(msgfromClient);//返回给客户端的消息
                switch (msgfromClient.what) {
                    //msg 客户端传来的消息
                    case MSG_SUM:
                        Log.d(Constants.LOG_TAG, "MessengerService Messenger receiver what -> " + msgfromClient.what);
                        msgToClient.what = MSG_SUM;
                        try {
                            Log.d(Constants.LOG_TAG, "MessengerService Messenger receiver arg -> " + msgfromClient.arg1 + " " + msgfromClient.arg2);
                            msgToClient.arg2 = msgfromClient.arg1 + msgfromClient.arg2;
                            msgfromClient.replyTo.send(msgToClient);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        mServerMessenger = new Messenger(handler);
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Constants.LOG_TAG, "MessengerService onBind");
        return mServerMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandlerThread != null) {
            mHandlerThread.quitSafely();
        }
    }
}
