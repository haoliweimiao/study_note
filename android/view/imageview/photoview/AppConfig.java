package com.goldfish.stocks.config;

import com.goldfish.stocks.BuildConfig;

/**
 * Created by Administrator on 2017-07-05.
 */

public interface AppConfig {

    String DOMAIN = BuildConfig.ENV_DEBUG ? "http://www.snlan.top:7270" : "http://www.snlan.top:7270";
    String WEBSOCKE_URL = BuildConfig.ENV_DEBUG ? "ws://www.snlan.top:7272" : "ws://www.snlan.top:7272";
//    String WEBSOCKE_URL = BuildConfig.ENV_DEBUG ? "ws://118.31.18.180:7272" : "ws://47.97.29.93:7272";
    String API_IP = DOMAIN;
//    String API_IP = "http://118.31.18.180:80";
    //    String API_IP = "https://api." + DOMAIN;
    String FILE_IP = API_IP + "/index/Upload/upload_img.html";

    String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/goldfishapp android " + BuildConfig.VERSION_NAME;
    String AESKEY = "a*jyxga#+wdfa%nd";
    String DEVICE_TYPE = "and17#$ro*id";
    String WX_APP_KEY = BuildConfig.ENV_DEBUG ?"wx430e0f5fa3b306e9" : "wx430e0f5fa3b306e9";// 微信
    String WX_APP_SECRET = BuildConfig.ENV_DEBUG ?"b28d7e14e327ec9630a9fa12fd16b3e1" : "b28d7e14e327ec9630a9fa12fd16b3e1";// 微信
    String SINA_APP_KEY = BuildConfig.ENV_DEBUG ?"2172781593" : "2720148550";// 微博
    String SINA_APP_SECRET = BuildConfig.ENV_DEBUG ?"aa03db7edd3bf6d080357232bb6a58b5" : "c5f42fcd4c7072ac3b538e863b2da65c";// 微博
    String SINA_APP_URL = BuildConfig.ENV_DEBUG ?"http://sns.whalecloud.com" : "http://sns.whalecloud.com";// 微博

    /**
     * androidManifest 中配置的authorities
     */
    String AUTHROES = "com.goldfish.stocks.fileprovider";
}
