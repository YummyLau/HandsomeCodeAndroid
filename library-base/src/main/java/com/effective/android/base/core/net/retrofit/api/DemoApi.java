package com.effective.android.base.core.net.retrofit.api;


import com.effective.android.base.core.net.BaseResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 某个模块的api聚合
 * Created by yummyLau on 2018/7/30.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public interface DemoApi {

    @GET("get")
    Observable<BaseResult> mockGet();

    @GET
    Observable<String> getBaidu2(@Url String url);


    /**
     * 上传一张图片
     *
     * @param url
     * @param file
     * @return
     */
    @Multipart
    @POST()
    Observable<ResponseBody> uploadFile(@Url String url, @Part() MultipartBody.Part file);

    /**
     * 上传n张图片
     *
     * @param url
     * @param maps
     * @return
     */
    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(@Url String url, @PartMap() Map<String, RequestBody> maps);




    @GET("/test/getServerInfo")
    Observable<String> getServerInfo();


    /**
     * post或者put上传Json数据。直接将一个实体类，通过序列化为Json字串作为网络请求参数
     *
     * @param s
     * @return
     */
    @FormUrlEncoded
    @POST("/")
    Observable<BaseResult<Body>> testPost(@Body String s);

    /**
     * 使用表单进行数据上传时
     * String.valueOf()把参数值转换为String,然后进行URL编码,当参数值为null值时,会自动忽略,如果传入的是一个List或array,则为每一个非空的item拼接一个键值对,每一个键值对中的键是相同的,值就是非空item的值,如: name=张三&name=李四&name=王五,另外,如果item的值有空格,在拼接时会自动忽略,例如某个item的值为:张 三,则拼接后为name=张三
     *
     * @param name
     * @return
     */
    @POST("/")
    Call<ResponseBody> sample(@Field("name") String name);

    @POST("/list")
    Call<ResponseBody> sample(@Field("name") String... names);

    /**
     * 用于表单数据参数。将Map数据转成对应的表单数据
     *
     * @param fields
     * @return
     */
    @POST("/things")
    Call<ResponseBody> things(@FieldMap Map<String, String> fields);

    /**
     * 用作传入某个header
     *
     * @param lang
     * @return
     */
    @GET("/")
    Call<ResponseBody> header1(@Header("Accept-Language") String lang);

    /**
     * 以map方式同时传入多个Header
     *
     * @param headers
     * @return
     */
    @GET("/search")
    Call<ResponseBody> header2(@HeaderMap Map<String, String> headers);

    @Headers("Cache-Control: max-age=640000")
    @GET("/")
    Call<ResponseBody> header3(@HeaderMap Map<String, String> headers);

    @Headers({
            "X-Foo: Bar",
            "X-Ping: Pong"
    })
    @GET("/")
    Call<ResponseBody> header4(@HeaderMap Map<String, String> headers);


    /**
     * 变化的URL进行请求
     *
     * @param name
     * @return
     */
    @GET("/user/{name}")
    Call<ResponseBody> encoded(@Path("name") String name);

    @GET("/user/{name}")
    Call<ResponseBody> notEncoded(@Path(value = "name", encoded = true) String name);


    /**
     * 拼接URL查询key-value
     *
     * @param page
     * @return
     */
    @GET("/friends")
    Call<ResponseBody> friends(@Query("page") int page);

    /**
     * /friends?group=1&group=2&group=3
     *
     * @param groups
     * @return
     */
    @GET("/friends")
    Call<ResponseBody> friends(@Query("group") String... groups);

    /**
     * 以map方式传值，拼接URL查询key-value
     *
     * @param filters
     * @return
     */
    @GET("/friends")
    Call<ResponseBody> friends(@QueryMap Map<String, String> filters);


}
