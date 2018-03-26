package com.mjn.libs.api;

import com.bing.lan.comm.api.HttpResult;
import com.mjn.libs.comm.bean.Home;
import com.mjn.libs.comm.bean.ISmsCode;
import com.mjn.libs.comm.bean.IUser;
import com.mjn.libs.comm.bean.UserBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiService {

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/iuser/login")
    Observable<ResponseResult<IUser>> login(@FieldMap Map<String, String> map);

    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("user/ismscode/sendSms")
    Observable<ResponseResult<Object>> getVcode(@FieldMap Map<String, String> map);

    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("user/ismscode/sendSms")
    Observable<ResponseResult<ISmsCode>> getIDCardVcode(@FieldMap Map<String, String> map);

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/iregisteruser/register")
    Observable<ResponseResult<UserBean>> register(@FieldMap Map<String, String> map);

    /**
     * 注册
     */
    @GET("index/home")
    Observable<ResponseResult<Home>> home(@QueryMap Map<String, String> map);

    /**
     * 找回密码
     */
    @FormUrlEncoded
    @POST("platform/login/found-pass")
    Observable<HttpResult<Object>> foundPassword(
            @Field("phone") String phone,
            @Field("newPwd") String newPwd,
            @Field("newPwd_ag") String newPwd_ag
    );
}
