package com.example.zhou.myapplication.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhou.myapplication.R;
import com.example.zhou.myapplication.retrofit.module.Contributor;
import com.example.zhou.myapplication.retrofit.movie.MovieLoader;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitAct extends AppCompatActivity {
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    String TAG=getClass().getSimpleName();
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    String url="https://api.github.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
    }
        MovieLoader mMovieLoader = new MovieLoader(url);
    @Override
    protected void onResume() {
        super.onResume();
//        executorService.execute(new StringRetroFit());
//        executorService.execute(new QueryGETRotrofit());
//        executorService.execute(new PostRetrofit());
        observable
                // Run on a background thread
                .subscribeOn(Schedulers.newThread())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Contributor>>() {
                    @Override
                    public void accept(List<Contributor> list) throws Exception {
                        Log.i("RetrofitAct","accept>>>>>>>>>>>>>");
                        for (Contributor c:list) {
                            sb.append(c);
                            sb.append("\n");
                            Log.i("RetrofitAct","Contributor:"+c);
                        }
                        setShowViewContent(true);
                    }
                })

        ;
    }



    StringBuffer sb=new StringBuffer();
    void setShowViewContent(boolean isSuc){
        mProgressBar.setVisibility(View.GONE);
        if(isSuc)
        mTvContent.setText(sb.toString());
        else{
            Toast.makeText(RetrofitAct.this,"failure",Toast.LENGTH_SHORT).show();
        }
    }
    Observable<List<Contributor>> observable = Observable.create(new ObservableOnSubscribe<List<Contributor>>() {

        @Override
        public void subscribe(final ObservableEmitter<List<Contributor>> e) throws Exception {
            mMovieLoader.getmMovieService().contributors("square", "retrofit").enqueue(new Callback<List<Contributor>>() {
                @Override
                public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                    Log.i(TAG,"onResponse size:"+response.body().size());
                    e.onNext(response.body());
                    e.onComplete();

                }

                @Override
                public void onFailure(Call<List<Contributor>> call, Throwable t) {
                    Log.i(TAG,"onFailure >>>>>>>>>>");
                }
            });
        }
    });



}
