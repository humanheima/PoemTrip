package com.brotherd.poemtrip.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.adapters.TextViewBindingAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.activity.MainActivity;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.bean.LoginBean;
import com.brotherd.poemtrip.bean.VerifyCodeBean;
import com.brotherd.poemtrip.util.CountDownUtil;
import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.util.RegularUtil;
import com.brotherd.poemtrip.util.SpUtil;
import com.brotherd.poemtrip.util.Toast;
import com.brotherd.poemtrip.viewmodel.model.LoginModel;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by dumingwei on 2017/11/23 0023.
 */

public class LoginViewModel extends BaseViewModel {

    public ObservableField<String> mobile = new ObservableField<>();
    public ObservableField<String> fastMobile = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public ObservableField<String> verifyCode = new ObservableField<>();

    public ObservableField<String> getVerifyText = new ObservableField<>();

    public ObservableInt clearMobileVisibility = new ObservableInt(View.INVISIBLE);
    public ObservableInt clearFastMobileVisibility = new ObservableInt(View.INVISIBLE);
    public ObservableInt clearPasswordVisibility = new ObservableInt(View.INVISIBLE);
    public ObservableInt clearVerifyCodeVisibility = new ObservableInt(View.INVISIBLE);

    public ObservableInt llAccountPwdLoginVisibility = new ObservableInt(View.VISIBLE);
    public ObservableInt llFastLoginVisibility = new ObservableInt(View.INVISIBLE);

    public ObservableBoolean getVerifyEnable = new ObservableBoolean(false);
    public ObservableBoolean loginEnable = new ObservableBoolean();

    public ObservableBoolean accountLogin = new ObservableBoolean(true);


    public TextViewBindingAdapter.AfterTextChanged mobileAfterTextChanged = new TextViewBindingAdapter.AfterTextChanged() {
        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                clearMobileVisibility.set(View.INVISIBLE);
                loginEnable.set(false);
            } else {
                clearMobileVisibility.set(View.VISIBLE);
                if (TextUtils.isEmpty(password.get())) {
                    loginEnable.set(false);
                } else {
                    loginEnable.set(true);
                }
            }
        }
    };

    public TextViewBindingAdapter.AfterTextChanged passwordAfterTextChanged = new TextViewBindingAdapter.AfterTextChanged() {
        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                clearPasswordVisibility.set(View.INVISIBLE);
                loginEnable.set(false);
            } else {
                clearPasswordVisibility.set(View.VISIBLE);
                if (TextUtils.isEmpty(mobile.get())) {
                    loginEnable.set(false);
                } else {
                    loginEnable.set(true);
                }
            }
        }
    };

    public TextViewBindingAdapter.AfterTextChanged fastMobileAfterTextChanged = new TextViewBindingAdapter.AfterTextChanged() {
        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                clearFastMobileVisibility.set(View.INVISIBLE);
                getVerifyEnable.set(false);
                loginEnable.set(false);
            } else {
                clearFastMobileVisibility.set(View.VISIBLE);
                if (!countDown) {
                    getVerifyEnable.set(true);
                }
                if (TextUtils.isEmpty(verifyCode.get())) {
                    loginEnable.set(false);
                } else {
                    loginEnable.set(true);
                }
            }
        }
    };

    public TextViewBindingAdapter.AfterTextChanged verifyCodeAfterTextChanged = new TextViewBindingAdapter.AfterTextChanged() {
        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                clearVerifyCodeVisibility.set(View.INVISIBLE);
                loginEnable.set(false);
            } else {
                clearVerifyCodeVisibility.set(View.VISIBLE);
                if (TextUtils.isEmpty(fastMobile.get())) {
                    loginEnable.set(false);
                } else {
                    loginEnable.set(true);
                }
            }
        }
    };

    private CountDownUtil countDownUtil;
    private boolean countDown;
    private LoginModel model;
    private Disposable getVerifyCodeDisposable;
    private Disposable accountLoginDisposable;
    private Disposable fastLoginDisposable;


    public LoginViewModel(BaseDataBindingActivity context) {
        super(context);
        model = new LoginModel();
        getVerifyText.set(context.getString(R.string.get_verify_code));
    }

    public void accountPwdLogin() {
        accountLogin.set(true);
        changeLoginType();
    }

    public void fastLogin() {
        accountLogin.set(false);
        changeLoginType();
    }

    private void changeLoginType() {
        loginEnable.set(false);
        if (accountLogin.get()) {
            fastMobile.set(null);
            verifyCode.set(null);
            llAccountPwdLoginVisibility.set(View.VISIBLE);
            llFastLoginVisibility.set(View.INVISIBLE);
        } else {
            mobile.set(null);
            password.set(null);
            llAccountPwdLoginVisibility.set(View.INVISIBLE);
            llFastLoginVisibility.set(View.VISIBLE);
        }
    }

    public void clearMobile() {
        mobile.set(null);
        clearMobileVisibility.set(View.INVISIBLE);
    }

    public void clearFastMobile() {
        fastMobile.set(null);
        clearFastMobileVisibility.set(View.INVISIBLE);
    }

    public void clearPassword() {
        password.set(null);
        clearPasswordVisibility.set(View.INVISIBLE);
    }

    public void clearVerifyCode() {
        verifyCode.set(null);
        clearVerifyCodeVisibility.set(View.INVISIBLE);
    }

    public void getVerifyCode() {
        if (!RegularUtil.checkPhone(fastMobile.get())) {
            toastMsg.set(R.string.phone_pattern_error);
            return;
        }
        countDownUtil = new CountDownUtil();
        countDownUtil.setCountDownListener(new CountDownUtil.CountDownListener() {
            @Override
            public void onCountDownStart() {
                countDown = true;
                getVerifyEnable.set(false);
            }

            @Override
            public void onCountDownProcessing(long second) {
                getVerifyText.set(second + "s");
            }

            @Override
            public void onCountDownEnd() {
                countDown = false;
                getVerifyText.set(context.getString(R.string.get_verify_code));
                if (TextUtils.isEmpty(fastMobile.get())) {
                    getVerifyEnable.set(false);
                } else {
                    getVerifyEnable.set(true);
                }
            }
        });
        countDownUtil.start();

        dispose(getVerifyCodeDisposable);
        getVerifyCodeDisposable = model.getVerifyCode(fastMobile.get())
                .subscribe(new Consumer<VerifyCodeBean>() {
                    @Override
                    public void accept(@NonNull VerifyCodeBean verifyCodeBean) throws Exception {
                        verifyCode.set(verifyCodeBean.getVerifyCode());
                        Debug.e(TAG, "verifyCode=" + verifyCodeBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Debug.e(TAG, throwable.getMessage());
                        toastMsg.set(throwable.getMessage());
                    }
                });
    }

    public void login() {
        if (llAccountPwdLoginVisibility.get() == View.VISIBLE) {
            //账号密码登录
            if (!RegularUtil.checkPhone(mobile.get())) {
                toastMsg.set(context.getString(R.string.phone_pattern_error));
                return;
            }
            dispose(accountLoginDisposable);
            isLoading.set(true);
            accountLoginDisposable = model.accountLogin(mobile.get(), password.get())
                    .subscribe(new Consumer<LoginBean>() {
                        @Override
                        public void accept(@NonNull LoginBean loginBean) throws Exception {
                            isLoading.set(false);
                            SpUtil.getInstance().putLoginModel(loginBean);
                            toastMsg.set(context.getString(R.string.login_success));
                            Debug.e(TAG, "userId:" + loginBean.getUserId());
                            MainActivity.launch(context);
                            context.finish();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            isLoading.set(false);
                            toastMsg.set(throwable.getMessage());
                            Debug.e(TAG, throwable.getMessage());
                        }
                    });
        } else {
            if (!RegularUtil.checkPhone(fastMobile.get())) {
                toastMsg.set(context.getString(R.string.phone_pattern_error));
                return;
            }
            isLoading.set(true);
            dispose(fastLoginDisposable);
            fastLoginDisposable = model.fastLogin(fastMobile.get(), verifyCode.get())
                    .subscribe(new Consumer<LoginBean>() {
                        @Override
                        public void accept(@NonNull LoginBean loginBean) throws Exception {
                            isLoading.set(false);
                            SpUtil.getInstance().putLoginModel(loginBean);
                            toastMsg.set(context.getString(R.string.login_success));
                            Debug.e(TAG, "userId:" + loginBean.getUserId());
                            MainActivity.launch(context);
                            context.finish();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            isLoading.set(false);
                            toastMsg.set(throwable.getMessage());
                            Debug.e(TAG, throwable.getMessage());
                        }
                    });
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        dispose(getVerifyCodeDisposable);
        dispose(accountLoginDisposable);
        dispose(fastLoginDisposable);
    }
}
