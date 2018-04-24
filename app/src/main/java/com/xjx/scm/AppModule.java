/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xjx.scm;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.xjx.scm.db.ScmDataBase;
import com.xjx.scm.db.UserDao;
import com.xjx.scm.network.UserService;
import com.xjx.scm.repository.LoginRepository;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 维护单例模块
 */
public class AppModule {
    // 单例，可以用Dagger简化，后续再说
    private static UserService sUserService;
    private static ScmDataBase sScmDataBase;

    public static UserService getUserService() {
        return sUserService;
    }

    public static ScmDataBase getScmDataBase() {
        return sScmDataBase;
    }

    public static UserDao getUserDao() {
        return sScmDataBase.userDao();
    }

    static void init(Application app) {
        if (sUserService == null) {
            sUserService = provideUserService();
            sScmDataBase = provideDb(app);
        }
    }

    private static UserService provideUserService() {
        return new Retrofit.Builder()
                .baseUrl("https://api-scm.lingshunbao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(UserService.class);
    }

    private static ScmDataBase provideDb(Application app) {
        return Room.databaseBuilder(app, ScmDataBase.class, "scm.db").addMigrations(ScmDataBase.MIGRATION_1_2).build();
    }

    private static UserDao provideUserDao(ScmDataBase db) {
        return db.userDao();
    }

    // 单例，可以用Dagger简化，后续再说
    private static AppExecutors sAppExecutors;

    public static AppExecutors getAppExecutors() {
        if (sAppExecutors == null) {
            synchronized (AppExecutors.class) {
                if (sAppExecutors == null) {
                    sAppExecutors = new AppExecutors();
                }
            }
        }
        return sAppExecutors;
    }


    // 单例，可以用Dagger简化，后续再说
    private static LoginRepository sLoginRepository;

    public static LoginRepository getLoginRepository() {
        if (sLoginRepository == null) {
            synchronized (LoginRepository.class) {
                if (sLoginRepository == null) {
                    sLoginRepository = new LoginRepository(getScmDataBase(), getUserDao(), getUserService(), getAppExecutors());
                }
            }
        }
        return sLoginRepository;
    }

}
