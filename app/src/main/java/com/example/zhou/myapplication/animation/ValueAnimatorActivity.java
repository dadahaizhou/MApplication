package com.example.zhou.myapplication.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhou.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ValueAnimatorActivity extends AppCompatActivity {
    protected static final String TAG = "ValueAnimatorActivity";
    @BindView(R.id.id_ball)
    ImageView mBlueBall;
    private float mScreenHeight,mScreenWidth,mBallWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_animator);
        ButterKnife.bind(this);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        mScreenWidth=outMetrics.widthPixels;
        mBallWidth=mBlueBall.getWidth();
    }

    /**
     * 自由落体
     *
     * @param view
     */
    public void verticalRun(View view)
    {
        ValueAnimator animator = ValueAnimator.ofFloat(0, mScreenHeight
                - mBlueBall.getHeight());
        animator.setTarget(mBlueBall);
        animator.setDuration(1000).start();
        // animator.setInterpolator(value)
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                mBlueBall.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    /**
     * 抛物线
     *
     * @param view
     */
    public void paowuxian(final View view)
    {

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>()
        {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue)
            {
                Log.e(TAG, fraction * 3 + "");
                // x方向200px/s ，则y方向0.5 * g * t (g = 100px / s*s)
                PointF point = new PointF();
                point.x = 1.5f * 200 * fraction * 6;
                if(mBallWidth==0) mBallWidth=mBlueBall.getWidth();
                Log.i(TAG,"mBallWidth:"+mBallWidth+" point.x:"+point.x);
                if(point.x+mBallWidth>=mScreenWidth){
                    point.x=mScreenWidth-(point.x+mBallWidth-mScreenWidth);
                }
                point.y = 1.5f * 100 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                PointF point = (PointF) animation.getAnimatedValue();
                mBlueBall.setX(point.x);
                mBlueBall.setY(point.y);

            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
               verticalRun(view);
            }
        });
    }

    public void fadeOut(View view)
    {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mBlueBall, "alpha", 0.5f);

        anim.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                Log.e(TAG, "onAnimationEnd");
                ViewGroup parent = (ViewGroup) mBlueBall.getParent();
                if (parent != null)
                    parent.removeView(mBlueBall);
            }
        });

        anim.addListener(new Animator.AnimatorListener()
        {

            @Override
            public void onAnimationStart(Animator animation)
            {
                Log.e(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {
                // TODO Auto-generated method stub
                Log.e(TAG, "onAnimationRepeat");
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                Log.e(TAG, "onAnimationEnd");
                ViewGroup parent = (ViewGroup) mBlueBall.getParent();
                if (parent != null)
                    parent.removeView(mBlueBall);
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {
                // TODO Auto-generated method stub
                Log.e(TAG, "onAnimationCancel");
            }
        });
        anim.start();
    }

}
