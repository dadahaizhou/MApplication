package com.example.zhou.myapplication.retrofit.movie;



import android.util.Log;

import com.example.zhou.myapplication.retrofit.module.Contributor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 *
 *
 * Created by zhouwei on 16/11/10.
 */

public class MovieLoader extends ObjectLoader {
    private MovieService mMovieService;

    public MovieService getmMovieService() {
        return mMovieService;
    }

    public MovieLoader(String url){
        mMovieService = RetrofitServiceManager.getInstance().create(MovieService.class,url);
    }

    /**
     * @return
     */
    public void getMovie( final List<Contributor> movies) {


        mMovieService.contributors("square", "retrofit").enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                Log.i("MovieLoader","onResponse size:"+response.body().size());
                movies.addAll(response.body());

            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                Log.i("MovieLoader","onFailure >>>>>>>>>>");
            }
        });
    }


    public Observable<String> getWeatherList(String cityId,String key){
        return observe(mMovieService.getWeather(cityId,key)).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s;
            }
        });
    }


    public interface MovieService{

        //获取豆瓣Top250 榜单
//        @GET("top250")
//        Observable<MovieSubject> getTop250(@Query("start") int start, @Query("count") int count);
        @GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors(@Path("owner") String owner,
                                             @Path("repo") String repo);
        @FormUrlEncoded
        @POST("/x3/weather")
        Observable<String> getWeather(@Field("cityId") String cityId, @Field("key") String key);

    }
}
