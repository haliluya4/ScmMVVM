package com.xjx.scm.ui.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.xjx.scm.R;
import com.xjx.scm.repository.LoginRepository;
import com.xjx.scm.ui.SingleLiveEvent;
import com.xjx.scm.vo.LoginResult;
import com.xjx.scm.vo.Resource;

import timber.log.Timber;

/**
 * Created by johnsonxu on 2018/4/24.
 */

public class LoginViewModel extends AndroidViewModel {
    public final ObservableField<String> userName = new ObservableField<>();

    public final ObservableField<String> password = new ObservableField<>();

    public final ObservableBoolean logining = new ObservableBoolean(false);

    private final SingleLiveEvent<Void> mShowContentEvent = new SingleLiveEvent<>();

    private final SingleLiveEvent<Integer> mShowTipsEvent = new SingleLiveEvent<>();

    private final SingleLiveEvent<String> mShowStringTipsEvent = new SingleLiveEvent<>();

    public LoginViewModel(@NonNull Application application, LoginRepository loginRepository) {
        super(application);
        mLoginHandler = new LoginHandler(loginRepository);
    }


    SingleLiveEvent<Void> getShowContentEvent() {
        return mShowContentEvent;
    }

    SingleLiveEvent<Integer> getShowTipsEvent() {
        return mShowTipsEvent;
    }

    SingleLiveEvent<String> getShowStringTipsEvent() {
        return mShowStringTipsEvent;
    }

    private LoginHandler mLoginHandler;

    public void login() {
        String userName = this.userName.get();
        String password = this.password.get();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            mShowTipsEvent.setValue(R.string.error_username_required);
            return;
        }

        mLoginHandler.login(userName, password);
    }

    class LoginHandler implements Observer<Resource> {
        @Nullable
        private LiveData<Resource> loginResultLiveData;

        private final LoginRepository mLoginRepository;

        LoginHandler(LoginRepository loginRepository) {
            this.mLoginRepository = loginRepository;
        }

        void login(String userName, String password) {
            if (logining.get()) {
                Timber.d("重复点击");
                return;
            }

            unregister();
            logining.set(true);
            loginResultLiveData = mLoginRepository.login(userName, password);
            loginResultLiveData.observeForever(this);
        }

        private void unregister() {
            if (loginResultLiveData != null) {
                loginResultLiveData.removeObserver(this);
                loginResultLiveData = null;
            }
        }

        @Override
        public void onChanged(@Nullable Resource resource) {
            if (resource == null) {
                Timber.d("Empty login result");
                reset();
                return;
            }
            switch (resource.status){
                case SUCCESS:
                    unregister();
                    mShowContentEvent.call();
                    logining.set(false);
                    break;
                case ERROR:
                    unregister();
                    mShowStringTipsEvent.setValue(resource.message);
                    logining.set(false);
                    break;
            }
        }

        private void reset() {
            unregister();
            logining.set(false);
        }
    }
}
