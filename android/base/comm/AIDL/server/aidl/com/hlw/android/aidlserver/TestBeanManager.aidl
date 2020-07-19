// TestBeanManager.aidl
package com.hlw.android.aidlserver;

// Declare any non-default types here with import statements

//导入所需要使用的非默认支持数据类型的包
import com.hlw.android.aidlserver.TestBean;

interface TestBeanManager {
        //所有的返回值前都不需要加任何东西，不管是什么数据类型
        List<TestBean> getDatas();
//        TestBean getBean();
//        int getDatasCount();

        //传参时除了Java基本类型以及String，CharSequence之外的类型
        //都需要在前面加上定向tag，具体加什么量需而定
        void addBeanIn(in TestBean data);
//        void addBeanOut(out TestBean data);
//        void addBeanInout(inout TestBean data);
}
