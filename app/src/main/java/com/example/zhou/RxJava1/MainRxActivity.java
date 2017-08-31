package com.example.zhou.RxJava1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhou.myapplication.R;
import com.example.zhou.tool.GlideUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainRxActivity extends AppCompatActivity {
//    @BindView(R.id.tv_content)
      TextView tv_content;
//    @BindView(R.id.tv_content1)
      ImageView tv_content1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rx);
//        ButterKnife.bind(this);
        initView();
        initObserve();
    }

    void initView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content1 = (ImageView) findViewById(R.id.tv_content1);
    }

    void initObserve() {
        initTextViewObserve();
        initPhotoViewObserve();
    }

    void initTextViewObserve() {
        observable
                // Run on a background thread
                .subscribeOn(Schedulers.newThread())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

        ;
    }

    void initPhotoViewObserve() {
        String url="http://uploads.jy135.com/allimg/201705/13-1F526104519.jpg";

        GlideUtil.loadImage(this,url,tv_content1);
//        Observable<String> observ = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                HttpsTools hhtp=new HttpsTools(MainRxActivity.this);
////                String url="http://www.jy135.com/sheying/110904.html" ;
//                String str= hhtp.HttpOper1("GET",url,null,"");
//                e.onNext(str);
//                e.onComplete();
//            }
//        });
//        observ.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String str) throws Exception {
//
//                    }
//                })
//
//        ;
    }

    Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {

        @Override
        public void subscribe(ObservableEmitter<String> e) throws Exception {
            int val = 10;
            e.onNext("begin>>>>>"+Thread.currentThread().getName());
            for (int i = 1; i < val; i++) {
                e.onNext(String.valueOf(i));
            }
            e.onNext("<<<<<<<<end");
            e.onComplete();
        }
    });

    Observer<String> observer = new Observer<String>() {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String value) {
            tv_content.append("\n" + value);

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            tv_content.append("\n onComplete" +Thread.currentThread().getName());
        }
    };
    private void swap(List<Object> list, int i, int j){
        Object o = list.get(i);
        list.set(j,o);
    }
}
