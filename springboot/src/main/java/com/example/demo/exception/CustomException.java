package com.example.demo.exception;

import com.example.demo.common.CommonResp;

public class CustomException extends RuntimeException implements CommonResp {

    private final CommonResp commonResp;

    public CustomException(CommonResp commonResp) {
        super();  // 调用父类的无参构造方法
        this.commonResp = commonResp;
    }

    @Override
    public String getMessage() {
        return this.commonResp.getMsg();
    }

    // 接收自定义msg的方式构造业务异常
    public CustomException(String msg, CommonResp commonResp) {
        super();
        this.commonResp = commonResp;
        this.commonResp.setMsg(msg);
    }

    @Override
    public String getCode() {
        return this.commonResp.getCode();
    }

    @Override
    public String getMsg() {
        return this.commonResp.getMsg();
    }

    @Override
    public CommonResp setMsg(String msg) {
        this.commonResp.setMsg(msg);
        return this;
    }
}
