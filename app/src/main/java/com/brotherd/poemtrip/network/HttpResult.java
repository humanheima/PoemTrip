package com.brotherd.poemtrip.network;

import android.text.TextUtils;

import com.brotherd.poemtrip.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shucc on 17/1/22.
 * cc@cchao.org
 * 统一返回bean类
 */
public class HttpResult<T> {

    private int ret;

    @SerializedName("msg")
    private String resultMessage;

    private T body;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getResultMessage() {
        if (TextUtils.isEmpty(resultMessage)) {
            return "no resultMessage";
        }
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public boolean isSuccess() {
        return ret == Constant.RESULT_SUCCESS;
    }
}
