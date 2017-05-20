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
import com.brotherd.poemtrip.impl.TextWatcherImpl;
import com.brotherd.poemtrip.model.LoginModel;
import com.brotherd.poemtrip.model.VerifyCodeModel;
import com.brotherd.poemtrip.network.HttpResult;
import com.brotherd.poemtrip.network.NetWork;
import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.util.RegularUtil;
import com.brotherd.poemtrip.util.SpUtil;
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
    @BindView(R.id.text_phone_prefix)
    TextView textPhonePrefix;
    @BindView(R.id.view_split_phone)
    View viewSplitPhone;
    @BindView(R.id.img_clear_phone)
    ImageView imgClearPhone;
    @BindView(R.id.img_clear_verify_code)
    ImageView imgClearVerifyCode;
    @BindView(R.id.img_clear_password)
    ImageView imgClearPassword;
    @BindView(R.id.text_commit)
    TextView textCommit;

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

    @Override
    protected void bindEvent() {
        editPhone.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    imgClearPhone.setVisibility(View.INVISIBLE);
                    textGetVerify.setClickable(true);
                    textGetVerify.setBackgroundColor(getResources().getColor(R.color.text_gray));
                    textCommit.setClickable(false);
                    textCommit.setBackgroundColor(getResources().getColor(R.color.alpha_green));
                } else {
                    imgClearPhone.setVisibility(View.VISIBLE);
                    textGetVerify.setClickable(true);
                    textGetVerify.setBackgroundColor(getResources().getColor(R.color.green));
                    if (!TextUtils.isEmpty(editVerifyCode.getText()) && !TextUtils.isEmpty(editPassword.getText()) && checkProtocol.isChecked()) {
                        textCommit.setClickable(true);
                        textCommit.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                }
            }
        });

        editVerifyCode.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    imgClearVerifyCode.setVisibility(View.INVISIBLE);
                    textCommit.setClickable(false);
                    textCommit.setBackgroundColor(getResources().getColor(R.color.alpha_green));
                } else {
                    imgClearVerifyCode.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(editPhone.getText()) && !TextUtils.isEmpty(editPassword.getText()) && checkProtocol.isChecked()) {
                        textCommit.setClickable(true);
                        textCommit.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                }
            }
        });

        editPassword.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    imgClearPassword.setVisibility(View.INVISIBLE);
                    textCommit.setClickable(false);
                    textCommit.setBackgroundColor(getResources().getColor(R.color.alpha_green));
                } else {
                    imgClearPassword.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(editPhone.getText()) && !TextUtils.isEmpty(editVerifyCode.getText()) && checkProtocol.isChecked()) {
                        textCommit.setClickable(true);
                        textCommit.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                }
            }
        });

        checkProtocol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    textCommit.setClickable(false);
                    textCommit.setBackgroundColor(getResources().getColor(R.color.alpha_green));
                } else {
                    if (!TextUtils.isEmpty(editPhone.getText()) && !TextUtils.isEmpty(editVerifyCode.getText()) && !TextUtils.isEmpty(editPassword.getText())) {
                        textCommit.setClickable(true);
                        textCommit.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                }
            }
        });

    }

    @OnClick(R.id.text_get_verify)
    public void getVerify() {
        phone = editPhone.getText().toString();
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
                        editVerifyCode.setText(verifyCode);
                        Debug.e(TAG, "verifyCode=" + verifyCode);
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
        if (!RegularUtil.checkPhone(phone)) {
            Toast.showToast(this, getString(R.string.phone_pattern_error));
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
                        Debug.e(TAG, "userId=" + userId);
                        SpUtil.getInstance().putLoginModel(loginModel);
                        MainActivity.launch(RegisterActivity.this);
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
    public void seeProtocol() {
    }

    @OnClick({R.id.img_clear_phone, R.id.img_clear_verify_code, R.id.img_clear_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_clear_phone:
                editPhone.setText("");
                imgClearPhone.setVisibility(View.INVISIBLE);
                break;
            case R.id.img_clear_verify_code:
                editVerifyCode.setText("");
                imgClearVerifyCode.setVisibility(View.INVISIBLE);
                break;
            case R.id.img_clear_password:
                editPassword.setText("");
                imgClearPassword.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
