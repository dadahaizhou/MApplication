package com.lzy.app;

import android.app.Application;

import com.lzy.app.utils.OkGoUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

/**
 * ================================================
 * 作    者：廖子尧   github 地址  https://github.com/jeasonlzy0216/
 * 版    本：1.0
 * 创建日期：2015/9/23
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkGoUtil.getInstatnce().initOkGo(this);

    }
}
