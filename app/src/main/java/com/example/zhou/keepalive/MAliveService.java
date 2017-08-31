package com.example.zhou.keepalive;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.zhou.myapplication.MainActivity;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MAliveService extends JobService {
    public MAliveService() {
    }

    final String TAG = getClass().getSimpleName();

    public static void setIsServiceScheler(boolean isServiceScheler) {
        MAliveService.isServiceScheler = isServiceScheler;
    }

    public static boolean isServiceScheler;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Toast.makeText(MAliveService.this, "MAliveService", Toast.LENGTH_SHORT).show();
            JobParameters param = (JobParameters) msg.obj;
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            jobFinished(param, true);
            return true;
        }
    });

    private void schedulerJobService(boolean isCancel, long time) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            jobScheduler.cancelAll();
            if (!isCancel) {
                JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(getPackageName(), MAliveService.class.getName()))
                        .setPeriodic(time)
                        .setPersisted(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .build();
                jobScheduler.schedule(jobInfo);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate>>>>>>>>>>>");
//        schedulerJobService(false,1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand    " + startId);
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob  isForeGround:" + MainActivity.isForeGround);
        if (!MainActivity.isForeGround) {
            setIsServiceScheler(true);
            Message m = Message.obtain();
            m.obj = params;
            handler.sendMessage(m);
            return true;
        }
//        else {
//            schedulerJobService(false,0);
//        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob  " + params);
        setIsServiceScheler(false);
        handler.removeCallbacksAndMessages(null);
        return false;
    }

    @Override
    public void onLowMemory() {
        Log.i(TAG, "onLowMemory>>>>>>>>>>>>>>");
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        setIsServiceScheler(false);
        Log.i(TAG, "onDestroy>>>>>>>>>>>>>>");
        super.onDestroy();
    }
}
