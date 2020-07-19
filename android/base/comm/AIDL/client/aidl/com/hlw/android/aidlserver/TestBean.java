package com.hlw.android.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;

public class TestBean implements Parcelable {
    private int id;
    private String content;

    public TestBean() {
    }

    protected TestBean(Parcel in) {
        id = in.readInt();
        content = in.readString();
    }

    public static final Creator<TestBean> CREATOR = new Creator<TestBean>() {
        @Override
        public TestBean createFromParcel(Parcel in) {
            return new TestBean(in);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(content);
    }

    /**
     * 参数是一个Parcel,用它来存储与传输数据
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        id = dest.readInt();
        content = dest.readString();
    }
}
