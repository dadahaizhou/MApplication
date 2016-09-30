package com.example.zhou.myapplication.animation;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhou.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimationActivity extends AppCompatActivity {
    @BindView(R.id.id_ball)
    ImageView img_ball;
    @BindView(R.id.btn_anmation)
    Button btn_animation;
    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
        img_ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertyValuesHolder(view);
            }
        });
        btn_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertyValuesHolder(view);

            }
        });
    }

    public void rotateyAnimRun(final View view) {
        ObjectAnimator anim = ObjectAnimator//
                .ofFloat(view, "zhy", 1.0F, 0.0F)//
                .setDuration(500);
        anim.start();

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //  view.postInvalidate();
//                view.invalidate();
                float cVal = (Float) valueAnimator.getAnimatedValue();
                view.setAlpha(cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
    }

    public void propertyValuesHolder(final View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                0, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                0, 1f);

        PropertyValuesHolder pvtransX = PropertyValuesHolder.ofFloat("translationX", 1f,
                0, 200f);
        PropertyValuesHolder pvtransY = PropertyValuesHolder.ofFloat("translationY", 1f,
                0, 300f);
        PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(0f, 360)
        );

        ObjectAnimator anim= ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ,pvtransX,pvhRotate).setDuration(1000);
        anim .start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if( view instanceof Button)
                startActivity(new Intent(AnimationActivity.this,ValueAnimatorActivity.class));

                view.requestLayout();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }
}
