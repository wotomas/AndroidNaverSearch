package info.kimjihyok.androidnaversearch.dagger.module;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.kimjihyok.androidnaversearch.Config;
import info.kimjihyok.androidnaversearch.controller.ApiController;
import info.kimjihyok.androidnaversearch.controller.retrofit.NaverSearchAPI;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jihyokkim on 2017. 11. 8..
 */

@Module
public class NetworkModule {

  @Provides
  @Singleton
  RxJava2CallAdapterFactory providesRxJava2CallAdapterFactory() {
    return RxJava2CallAdapterFactory.create();
  }

  @Provides
  @Singleton
  GsonConverterFactory providesGsonConverterFactory() {
    return GsonConverterFactory.create();
  }

  @Provides
  @Singleton
  Retrofit providesRetrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {
    return new Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Config.API_URL)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .build();
  }

  @Provides
  @Singleton
  NaverSearchAPI providesNewsApi(Retrofit retrofit) {
    return retrofit.create(NaverSearchAPI.class);
  }


  @Provides
  @Singleton
  ApiController providesApiController(NaverSearchAPI service) {
    return new ApiController(service, 3);
  }


  @Provides
  @Singleton
  OkHttpClient providesOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.addInterceptor(httpLoggingInterceptor).addInterceptor(chain -> {
      Request original = chain.request();

      Request request = original.newBuilder()
          .header("X-Naver-Client-Id", Config.CLIENT_ID)
          .header("X-Naver-Client-Secret", Config.CLIENT_SERECT)
          .method(original.method(), original.body())
          .build();

      return chain.proceed(request);
    });

    return builder.build();
  }

  @Provides
  @Singleton
  HttpLoggingInterceptor providesHttpLoggingInterceptor() {
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    return httpLoggingInterceptor;
  }
}

