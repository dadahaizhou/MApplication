package com.example.zhou;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.zhou.keepalive.MAliveService;

/**
 * Created by Administrator on 2017/6/28.
 */

public class MApp extends Application {

    final String TAG=getClass().getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"MApp  onCreate MAliveService>>");
//        startService(new Intent(this,MAliveService.class));
        schedulerJobService(5000);
    }

    void schedulerJobService(int time){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(getPackageName(), MAliveService.class.getName()))
                    .setPeriodic(time)
                    .setPersisted(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build();
            jobScheduler.schedule(jobInfo);
        }
    }

    @Override
    public void onLowMemory() {
        Log.i(TAG,"MApp  onLowMemory MAliveService>>");
        schedulerJobService(5000);
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        Log.i(TAG,"MApp  onTerminate MAliveService>>");
        schedulerJobService(5000);
        super.onTerminate();
    }
}
