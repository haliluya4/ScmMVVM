package com.xjx.scm.ui.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.xjx.scm.R;
import com.xjx.scm.databinding.LoginActBinding;
import com.xjx.scm.ui.ViewModelFactory;

import timber.log.Timber;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = obtainViewModel(this);

        mViewModel.getShowContentEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void useless) {
//                Intent intent = new Intent(this, TaskDetailActivity.class);
//                startActivity(intent);
                Timber.d("登录成功");
            }
        });
        mViewModel.getShowTipsEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Toast.makeText(LoginActivity.this, integer, Toast.LENGTH_SHORT).show();
            }
        });
        mViewModel.getShowStringTipsEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String info) {
                Toast.makeText(LoginActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });

        LoginActBinding binding = DataBindingUtil.setContentView(this, R.layout.login_act);
        binding.setViewmodel(mViewModel);
        binding.login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(view.getWindowToken());
                String userName = binding.userName.getText().toString();
                String password = binding.password.getText().toString();
                mViewModel.login(userName, password);
            }
        });

    }

    private void dismissKeyboard(IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    private static LoginViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return ViewModelProviders.of(activity, factory).get(LoginViewModel.class);
    }

}

