package com.example.code;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;

import com.effective.android.base.activity.BaseBindingActivity;
import com.effective.router.annotation.RouteNode;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Callable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;


@RouteNode(path = "/main", desc = "主app页面")
public class MainActivity extends BaseBindingActivity {

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.app_activity_main;
    }

    @Override
    protected int getThemeColor() {
        return R.color.app_colorPrimary;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        throw new RuntimeException("");
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, FeatureListFragment.newInstance(), "").commit();
//        testRx();
        testMergeWith();
        testConcatWith();
    }

    private void testRx() {
        String TAG = "testRx";
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.i(TAG, " subscribe: " + Thread.currentThread().getName());
                emitter.onNext("发射数据");

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        Log.i(TAG, " map: " + Thread.currentThread().getName());
                        return "拼接" + s;
                    }
                })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                emitter.onNext(s + "flat");
                            }
                        });
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Observer<String>() {


                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, " onSubscribe: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, " onNext: " + Thread.currentThread().getName());
                        Log.i(TAG, " onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, " onError: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, " onComplete: " + Thread.currentThread().getName());
                    }
                });
    }

    private void testCreate(){
        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "";
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void testPress() {
        Flowable.just("hello")
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void testMergeWith(){
        String TAG = "testMergeWith";
        Integer nums[] = new Integer[]{11,12,13,14,15,16,17,18,19,20};
        Observable.just(1,2,3,4,5,6,7,8,9,10)
                .mergeWith(Observable.fromArray(nums))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, " integer: " + integer);
                    }
                });

    }

    private void testConcatWith(){
        String TAG = "testConcatWith";
        Integer nums[] = new Integer[]{11,12,13,14,15,16,17,18,19,20};
        Observable.just(1,2,3,4,5,6,7,8,9,10)
                .concatWith(Observable.fromArray(nums))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, " integer: " + integer);
                    }
                });
    }

}
