package com.miaoshaproject.error;

public enum EmBusinessError implements CommonError {
    PARAMETER_VALIDATION_ERREOR(10001,"参数不合法"),
    UNKNOWN_ERREOR(10002,"未知错误"),
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_FAIL(20002, "用户手机或密码不存在")
    ;
    private int errCode;
    private String errMsg;

    private EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg=errMsg;
        return this;
    }
}
