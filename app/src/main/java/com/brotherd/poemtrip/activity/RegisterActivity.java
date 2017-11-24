package com.brotherd.poemtrip.activity;

import android.content.Context;
import android.content.Intent;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.databinding.ActivityRegisterBinding;
import com.brotherd.poemtrip.viewmodel.RegisterViewModel;

public class RegisterActivity extends BaseDataBindingActivity<ActivityRegisterBinding> {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    public static void launch(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void initData() {
        RegisterViewModel viewModel = new RegisterViewModel(this);
        binding.setViewModel(viewModel);
    }

}
