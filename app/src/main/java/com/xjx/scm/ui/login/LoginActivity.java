package com.xjx.scm.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xjx.scm.R;
import com.xjx.scm.databinding.LoginActBinding;
import com.xjx.scm.ui.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static android.Manifest.permission.READ_CONTACTS;

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

    }

    public static LoginViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        LoginViewModel viewModel =
                ViewModelProviders.of(activity, factory).get(LoginViewModel.class);

        return viewModel;
    }

}

