package com.brotherd.poemtrip.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseActivity;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.databinding.ActivityRegisterBinding;
import com.brotherd.poemtrip.impl.TextWatcherImpl;
import com.brotherd.poemtrip.bean.LoginBean;
import com.brotherd.poemtrip.bean.VerifyCodeBean;
import com.brotherd.poemtrip.network.HttpResult;
import com.brotherd.poemtrip.network.NetWork;
import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.util.RegularUtil;
import com.brotherd.poemtrip.util.SpUtil;
import com.brotherd.poemtrip.util.TimeCountDownUtil;
import com.brotherd.poemtrip.util.Toast;
import com.brotherd.poemtrip.viewmodel.RegisterViewModel;
import com.brotherd.poemtrip.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
