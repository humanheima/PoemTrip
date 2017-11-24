package com.brotherd.poemtrip.activity;

import android.content.Context;
import android.content.Intent;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.databinding.ActivityLoginBinding;
import com.brotherd.poemtrip.viewmodel.LoginViewModel;
import com.brotherd.poemtrip.widget.TitleLayout;

public class LoginActivity extends BaseDataBindingActivity<ActivityLoginBinding> {


    private LoginViewModel viewModel;

    public static void launch(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        binding.titleLayout.setActionListener(new TitleLayout.OnCustomActionListener() {
            @Override
            public void onCustomAction() {
                RegisterActivity.launch(LoginActivity.this);
            }
        });

        viewModel = new LoginViewModel(this);
        binding.setViewModel(viewModel);

    }
}
