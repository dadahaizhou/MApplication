package com.example.zhou;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.example.zhou.keepalive.MAliveService;
import com.example.zhou.myapplication.BuildConfig;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tinkerpatch.sdk.TinkerPatch;

/**
 * Created by Administrator on 2017/6/28.
 */
//@SuppressWarnings("unused")
//@DefaultLifeCycle(application = "com.example.zhou.MApp",
//        flags = ShareConstants.TINKER_ENABLE_ALL,
//        loadVerifyFlag = false)
public class MAppLike extends DefaultApplicationLike {

    final String TAG=getClass().getSimpleName();

    public MAppLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }
    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
    }

    /**
     * 我们需要确保至少对主进程跟patch进程初始化 TinkerPatch
     */
    @Override
    public void onCreate() {
        super.onCreate();
        initTinker();
    }

    private void initTinker() {
        if (BuildConfig.TINKER_ENABLE) {
            //开始检查是否有补丁，这里配置的是每隔访问3小时服务器是否有更新。
            TinkerPatch.init(this)
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true)
                    .setFetchPatchIntervalByHours(3);

            // 获取当前的补丁版本
            Log.d(TAG, "current patch version is " + TinkerPatch.with().getPatchVersion());

            //每隔3个小时去访问后台时候有更新,通过handler实现轮训的效果
            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
        }
    }

    @Override
    public void onLowMemory() {
        Log.i(TAG,"MAppLike  onLowMemory MAliveService>>");
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        Log.i(TAG,"MAppLike  onTerminate MAliveService>>");
        super.onTerminate();
    }
}
