package com.brotherd.poemtrip.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseActivity;
import com.brotherd.poemtrip.model.LoginModel;
import com.brotherd.poemtrip.model.VerifyCodeModel;
import com.brotherd.poemtrip.network.HttpResult;
import com.brotherd.poemtrip.network.NetWork;
import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.util.RegularUtil;
import com.brotherd.poemtrip.util.TimeCountDownUtil;
import com.brotherd.poemtrip.util.Toast;
import com.brotherd.poemtrip.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity {

    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.check_protocol)
    CheckBox checkProtocol;
    @BindView(R.id.text_protocol)
    TextView textProtocol;
    @BindView(R.id.edit_verify_code)
    EditText editVerifyCode;
    @BindView(R.id.text_get_verify)
    TextView textGetVerify;
    @BindView(R.id.edit_password)
    EditText editPassword;

    private String phone;
    private String verifyCode;
    private String password;
    private long userId;

    private TimeCountDownUtil countDownUtil;

    public static void launch(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        loadingDialog = new LoadingDialog(this);
    }

    @OnClick(R.id.text_get_verify)
    public void getVerify() {
        phone = editPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.showToast(this, getString(R.string.phone_cant_null));
            return;
        }
        if (!RegularUtil.checkPhone(phone)) {
            Toast.showToast(this, getString(R.string.phone_pattern_error));
            return;
        }
        countDownUtil = new TimeCountDownUtil(textGetVerify);
        countDownUtil.setCountDownListener(new TimeCountDownUtil.CountDownListener() {
            @Override
            public void onCountDownStart() {
                textGetVerify.setBackgroundResource(R.color.text_gray);
                textGetVerify.setClickable(false);
            }

            @Override
            public void onCountDownEnd() {
                textGetVerify.setText(getString(R.string.get_verify_code));
                if (TextUtils.isEmpty(editPhone.getText().toString())) {
                    textGetVerify.setBackgroundResource(R.color.text_gray);
                    textGetVerify.setClickable(false);
                } else {
                    textGetVerify.setBackgroundResource(R.color.green);
                    textGetVerify.setClickable(true);
                }
            }
        });
        countDownUtil.start();
        NetWork.getApi().getRegisterVerifyCode(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<HttpResult<VerifyCodeModel>, ObservableSource<VerifyCodeModel>>() {
                    @Override
                    public ObservableSource<VerifyCodeModel> apply(@NonNull HttpResult<VerifyCodeModel> result) throws Exception {
                        return NetWork.flatResponse(result);
                    }
                })
                .subscribe(new Consumer<VerifyCodeModel>() {
                    @Override
                    public void accept(@NonNull VerifyCodeModel verifyCodeModel) throws Exception {
                        verifyCode = verifyCodeModel.getVerifyCode();
                        Debug.e(TAG, "verifyCode=" + verifyCodeModel);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Debug.e(TAG, throwable.getMessage());
                        Toast.showToast(RegisterActivity.this, throwable.getMessage());
                    }
                });
    }

    @OnClick(R.id.text_commit)
    public void commit() {
        phone = editPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.showToast(this, getString(R.string.phone_cant_null));
            return;
        }
        if (!RegularUtil.checkPhone(phone)) {
            Toast.showToast(this, getString(R.string.phone_pattern_error));
            return;
        }
        verifyCode = editVerifyCode.getText().toString();
        if (TextUtils.isEmpty(verifyCode)) {
            Toast.showToast(this, getString(R.string.verify_code_cant_null));
            return;
        }
        password = editPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            Toast.showToast(this, getString(R.string.password_cant_null));
            return;
        }
        loadingDialog.show();
        NetWork.getApi().register(phone, verifyCode, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<HttpResult<LoginModel>, ObservableSource<LoginModel>>() {
                    @Override
                    public ObservableSource<LoginModel> apply(@NonNull HttpResult<LoginModel> result) throws Exception {
                        return NetWork.flatResponse(result);
                    }
                })
                .subscribe(new Consumer<LoginModel>() {
                    @Override
                    public void accept(@NonNull LoginModel loginModel) throws Exception {
                        loadingDialog.dismiss();
                        userId = loginModel.getUserId();
                        Toast.showToast(RegisterActivity.this, getString(R.string.register_success));
                        SetPasswordNickNameActivity.launch(RegisterActivity.this);
                        Debug.e(TAG, "userId=" + userId);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        loadingDialog.dismiss();
                        Debug.e(TAG, throwable.getMessage());
                        Toast.showToast(RegisterActivity.this, throwable.getMessage());
                    }
                });

    }

    @OnClick(R.id.text_protocol)
    public void onTextProtocolClicked() {
    }
}
