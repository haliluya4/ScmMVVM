package com.xjx.scm.repository;

import android.arch.lifecycle.LiveData;

import com.xjx.scm.AppExecutors;
import com.xjx.scm.db.ScmDataBase;
import com.xjx.scm.db.UserDao;
import com.xjx.scm.network.UserService;
import com.xjx.scm.vo.Resource;

/**
 * 登录
 */
public class LoginRepository {

    private final ScmDataBase scmDataBase;
    private final UserDao userDao;
    private final UserService userService;
    private final AppExecutors appExecutors;

    public LoginRepository(ScmDataBase scmDataBase, UserDao userDao, UserService userService, AppExecutors appExecutors) {
        this.scmDataBase = scmDataBase;
        this.userDao = userDao;
        this.userService = userService;
        this.appExecutors = appExecutors;
    }

    /**
     * 登录
     * @param userName
     * @param password
     * @return 登录是否成功
     */
    public LiveData<Resource> login(String userName, String password) {
        LoginTask loginTask = new LoginTask(
                userName, password, userService,scmDataBase);
        appExecutors.networkIO().execute(loginTask);
        return loginTask.getLiveData();
    }
}
