package com.brotherd.poemtrip.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brotherd.poemtrip.MainActivity;
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

public class LoginActivity extends BaseActivity {

    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_register)
    TextView textAction;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.view_split_title)
    View viewSplitTitle;
    @BindView(R.id.text_account_pwd_login)
    TextView textAccountPwdLogin;
    @BindView(R.id.view_split_login)
    View viewSplitLogin;
    @BindView(R.id.text_fast_login)
    TextView textFastLogin;
    @BindView(R.id.rl_login_type)
    RelativeLayout rlLoginType;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.ll_account_pwd_login)
    LinearLayout llAccountPwdLogin;
    @BindView(R.id.edit_fast_phone)
    EditText editFastPhone;
    @BindView(R.id.edit_verify_code)
    EditText editVerifyCode;
    @BindView(R.id.text_get_verify)
    TextView textGetVerify;
    @BindView(R.id.ll_fast_login)
    LinearLayout llFastLogin;
    @BindView(R.id.text_login)
    TextView textLogin;
    //是否是账号密码登录
    private boolean accountLogin = true;
    private String phone;
    private String password;
    private String verifyCode;
    private TimeCountDownUtil countDownUtil;
    private int i;

    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        loadingDialog = new LoadingDialog(this);
    }

    @OnClick(R.id.text_register)
    public void onTextRegisterClicked() {
        RegisterActivity.launch(this);
    }

    @OnClick(R.id.img_back)
    public void onImgBackClicked() {
        finish();
    }

    @OnClick(R.id.text_account_pwd_login)
    public void onTextAccountPwdLoginClicked() {
        if (!accountLogin) {
            accountLogin = true;
            changeLoginType(accountLogin);
        }
    }

    @OnClick(R.id.text_fast_login)
    public void onTextFastLoginClicked() {
        if (accountLogin) {
            accountLogin = false;
            changeLoginType(accountLogin);
        }
    }

    private void changeLoginType(boolean accountLogin) {
        phone = null;
        if (accountLogin) {
            textAccountPwdLogin.setBackgroundResource(R.drawable.bg_text_layerlist);
            textFastLogin.setBackgroundResource(R.drawable.bg_text);
            llAccountPwdLogin.setVisibility(View.VISIBLE);
            llFastLogin.setVisibility(View.INVISIBLE);
        } else {
            textAccountPwdLogin.setBackgroundResource(R.drawable.bg_text);
            textFastLogin.setBackgroundResource(R.drawable.bg_text_layerlist);
            llAccountPwdLogin.setVisibility(View.INVISIBLE);
            llFastLogin.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.text_get_verify)
    public void onTextGetVerifyClicked() {
        phone = editFastPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.showToast(this, getString(R.string.phone_cant_null));
            return;
        }
        if (!RegularUtil.checkPhone(phone)) {
            Toast.showToast(this, getString(R.string.phone_pattern_error));
            return;
        }
        countDownUtil = new TimeCountDownUtil(30 * 1000, 1000, textGetVerify);
        countDownUtil.start();
        NetWork.getApi().getLoginVerifyCode(phone)
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
                        Toast.showToast(LoginActivity.this, throwable.getMessage());
                    }
                });
    }

    @OnClick(R.id.text_login)
    public void onTextLoginClicked() {
        if (accountLogin) {
            phone = editPhone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                Toast.showToast(this, getString(R.string.phone_cant_null));
                return;
            }
            if (!RegularUtil.checkPhone(phone)) {
                Toast.showToast(this, getString(R.string.phone_pattern_error));
                return;
            }
            password = editPwd.getText().toString();
            if (TextUtils.isEmpty(password)) {
                Toast.showToast(this, getString(R.string.pwd_cant_null));
                return;
            }
            loadingDialog.show();
            //请求网络
            NetWork.getApi().login(phone, password)
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
                            Toast.showToast(LoginActivity.this, getString(R.string.login_success));
                            Debug.e(TAG, "userId:" + loginModel.getUserId());
                            MainActivity.launch(LoginActivity.this);
                            finish();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            loadingDialog.dismiss();
                            Toast.showToast(LoginActivity.this, throwable.getMessage());
                            Debug.e(TAG, throwable.getMessage());
                        }
                    });
        } else {
            phone = editFastPhone.getText().toString();
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
            loadingDialog.show();
            //请求网络
            NetWork.getApi().fastLogin(phone, verifyCode)
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
                            Toast.showToast(LoginActivity.this, getString(R.string.login_success));
                            Debug.e(TAG, "userId:" + loginModel.getUserId());
                            MainActivity.launch(LoginActivity.this);
                            finish();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            loadingDialog.dismiss();
                            Toast.showToast(LoginActivity.this, throwable.getMessage());
                            Debug.e(TAG, throwable.getMessage());
                        }
                    });
        }
    }
}
