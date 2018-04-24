
package com.xjx.scm.network;

import android.arch.lifecycle.LiveData;

import com.xjx.scm.vo.LoginResult;
import com.xjx.scm.vo.User;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 用户相关API
 */
public interface UserService {
    @FormUrlEncoded
    @POST("/agent/user/login")
    Call<LoginResult> login(@Field("username") String userName, @Field("password") String password);

    @GET("/agent/user/userInfo")
    Flowable<ApiResponse<BaseResult<User>>> getUserInfo();

}
