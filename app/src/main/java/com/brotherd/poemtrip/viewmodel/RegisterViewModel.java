package com.brotherd.poemtrip.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.adapters.TextViewBindingAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

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
import com.brotherd.poemtrip.viewmodel.model.RegisterModel;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by dumingwei on 2017/11/22 0022.
 */

public class RegisterViewModel extends BaseViewModel {

    public ObservableField<String> mobile = new ObservableField<>();
    public ObservableField<String> verifyCode = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public ObservableField<String> getVerifyText = new ObservableField<>();

    public ObservableBoolean getVerifyEnable = new ObservableBoolean(false);
    public ObservableBoolean commitEnable = new ObservableBoolean(false);

    public ObservableInt clearMobileVisibility = new ObservableInt(View.INVISIBLE);
    public ObservableInt clearVerifyCodeVisibility = new ObservableInt(View.INVISIBLE);
    public ObservableInt clearPasswordVisibility = new ObservableInt(View.INVISIBLE);

    public TextViewBindingAdapter.AfterTextChanged mobileAfterTextChanged = new TextViewBindingAdapter.AfterTextChanged() {
        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                clearMobileVisibility.set(View.INVISIBLE);
                getVerifyEnable.set(false);
                commitEnable.set(false);
            } else {
                clearMobileVisibility.set(View.VISIBLE);
                getVerifyEnable.set(true);
                if (!TextUtils.isEmpty(verifyCode.get()) && !TextUtils.isEmpty(password.get()) && checkProtocol) {
                    commitEnable.set(true);
                }
            }
        }
    };

    public TextViewBindingAdapter.AfterTextChanged verifyCodeAfterTextChanged = new TextViewBindingAdapter.AfterTextChanged() {
        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                clearVerifyCodeVisibility.set(View.INVISIBLE);
                commitEnable.set(false);
            } else {
                clearVerifyCodeVisibility.set(View.VISIBLE);
                if (!TextUtils.isEmpty(mobile.get()) && !TextUtils.isEmpty(password.get()) && checkProtocol) {
                    commitEnable.set(true);
                }
            }
        }
    };
    public TextViewBindingAdapter.AfterTextChanged passwordAfterTextChanged = new TextViewBindingAdapter.AfterTextChanged() {
        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                clearPasswordVisibility.set(View.INVISIBLE);
                commitEnable.set(false);
            } else {
                clearPasswordVisibility.set(View.VISIBLE);
                if (!TextUtils.isEmpty(mobile.get()) && !TextUtils.isEmpty(verifyCode.get()) && checkProtocol) {
                    commitEnable.set(true);
                }
            }
        }
    };

    public CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            checkProtocol = isChecked;
            if (isChecked) {
                if (!TextUtils.isEmpty(mobile.get()) && !TextUtils.isEmpty(verifyCode.get()) && !TextUtils.isEmpty(password.get())) {
                    commitEnable.set(true);
                }
            } else {
                commitEnable.set(false);
            }
        }
    };

    private boolean checkProtocol = true;
    private RegisterModel model;

    private CountDownUtil countDownUtil;

    private Disposable verifyDisposable;
    private Disposable registerDisposable;

    public RegisterViewModel(BaseDataBindingActivity context) {
        super(context);
        this.model = new RegisterModel();
        getVerifyText.set(context.getString(R.string.get_verify_code));
    }

    public void clearMobile() {
        mobile.set(null);
        clearMobileVisibility.set(View.INVISIBLE);
    }

    public void clearVerifyCode() {
        verifyCode.set(null);
        clearVerifyCodeVisibility.set(View.INVISIBLE);
    }

    public void getVerifyCode() {
        if (!RegularUtil.checkPhone(mobile.get())) {
            toastMsg.set(context.getString(R.string.phone_pattern_error));
            return;
        }
        dispose(verifyDisposable);
        verifyDisposable = model.getRegisterVerifyCode(mobile.get())
                .subscribe(new Consumer<VerifyCodeBean>() {
                    @Override
                    public void accept(@NonNull VerifyCodeBean verifyCodeBean) throws Exception {
                        //发送验证码成功
                        verifyCode.set(verifyCodeBean.getVerifyCode());
                        toastMsg.set(context.getString(R.string.send_verify_success));
                        countDown();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Debug.e(TAG, throwable.getMessage());
                        toastMsg.set(throwable.getMessage());
                    }
                });
    }

    private void countDown() {
        countDownUtil = new CountDownUtil();
        countDownUtil.setCountDownListener(new CountDownUtil.CountDownListener() {
            @Override
            public void onCountDownStart() {
                getVerifyEnable.set(false);
            }

            @Override
            public void onCountDownProcessing(long second) {
                getVerifyText.set(second + "s");
            }

            @Override
            public void onCountDownEnd() {
                getVerifyText.set(context.getString(R.string.get_verify_code));
                if (TextUtils.isEmpty(mobile.get())) {
                    getVerifyEnable.set(false);
                } else {
                    getVerifyEnable.set(true);
                }
            }
        });
        countDownUtil.start();
    }

    public void clearPassword() {
        password.set(null);
        clearPasswordVisibility.set(View.INVISIBLE);
    }

    public void submit() {
        if (!RegularUtil.checkPhone(mobile.get())) {
            toastMsg.set(context.getString(R.string.phone_pattern_error));
            return;
        }
        isLoading.set(true);
        dispose(registerDisposable);
        registerDisposable = model.register(mobile.get(), verifyCode.get(), password.get())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(@NonNull LoginBean loginBean) throws Exception {
                        isLoading.set(false);
                        toastMsg.set(context.getString(R.string.register_success));
                        SpUtil.getInstance().putLoginModel(loginBean);
                        MainActivity.launch(context);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        isLoading.set(false);
                        Debug.e(TAG, throwable.getMessage());
                        toastMsg.set(throwable.getMessage());
                    }
                });

    }

    public void seeProtocol() {
    }

    @Override
    public void destroy() {
        super.destroy();
        dispose(verifyDisposable);
    }
}
