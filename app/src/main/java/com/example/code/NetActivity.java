package com.example.code;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.effective.android.base.activity.BaseActivity;
import com.effective.android.net.BaseResult;
import com.example.code.databinding.ActivityNetLayoutBinding;
import com.effective.android.net.retrofit.RetrofitClient;
import com.effective.android.net.retrofit.api.DemoApi;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class NetActivity extends BaseActivity<ActivityNetLayoutBinding> {

    public static void start(Context context) {
        Intent intent = new Intent(context, NetActivity.class);
        context.startActivity(intent);
    }

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.activity_net_layout;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.testGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getService(DemoApi.class)
                        .mockGet()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<BaseResult>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(BaseResult baseResult) {
                                baseResult.isSuccess();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.getMessage();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });

//        binding.test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RetrofitClient.getInstance().getService(RetrofitType.STRING, DemoApi.class)
//                        .getBaidu2("https://www.google.com")
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<String>() {
//                            @Override
//                            public void onSubscribe(Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onNext(String s) {
//                                s.toString();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                e.getMessage();
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });
//            }
//        });


    }
}
