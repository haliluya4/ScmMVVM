package com.xjx.scm.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.xjx.scm.db.ScmDataBase;
import com.xjx.scm.network.ApiResponse;
import com.xjx.scm.network.UserService;
import com.xjx.scm.util.EncryptionDecryption;
import com.xjx.scm.vo.LoginResult;
import com.xjx.scm.vo.Resource;
import com.xjx.scm.vo.User;

import java.io.IOException;

import retrofit2.Response;

/**
 * 登录任务
 */
public class LoginTask implements Runnable {
    private final MutableLiveData<Resource> liveData = new MutableLiveData<>();
    private final String userName, password;
    private final UserService userService;
    private final ScmDataBase db;

    LoginTask(String userName, String password, UserService userService, ScmDataBase db) {
        this.userName = userName;
        this.password = password;
        this.userService = userService;
        this.db = db;
    }

    @Override
    public void run() {
        try {
            Response<LoginResult> response = userService.login(userName, EncryptionDecryption.md5(password)).execute();
            ApiResponse<LoginResult> apiResponse = new ApiResponse<>(response);
            if (apiResponse.isSuccessful()) {
                if (apiResponse.body != null){
                    if (apiResponse.body.isSuccess()) {
                        try {
                            db.beginTransaction();
                            LoginResult.Login loginResult = apiResponse.body.getResult();
                            User newUser = new User(loginResult.getUserId(), loginResult.getAccessToken());
                            db.userDao().updateCurrentUser(newUser);
                            db.setTransactionSuccessful();
                        } finally {
                            db.endTransaction();
                        }
                        liveData.postValue(Resource.success(null));
                    } else {
                        liveData.postValue(Resource.error(apiResponse.body.getMsg(), null));
                    }
                } else {
                    liveData.postValue(Resource.error("Invalid empty result", null));
                }
            } else {
                liveData.postValue(Resource.error(apiResponse.errorMessage, null));
            }
        } catch (IOException e) {
            liveData.postValue(Resource.error(e.getMessage(), null));
        }
    }

    LiveData<Resource> getLiveData() {
        return liveData;
    }
}
