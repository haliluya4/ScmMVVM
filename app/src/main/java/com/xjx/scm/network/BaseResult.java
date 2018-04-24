package com.xjx.scm.network;

/**
 * 服务端返回结果基础结构
 *
 * @param <T>
 */
public class BaseResult<T> {

    private String requestId;
    private String msg;
    private int code;
    private T result;

    public String getRequestId() {
        return requestId;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public T getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "requestId='" + requestId + '\'' +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                ", result=" + result +
                '}';
    }

    public boolean isSuccess() {
        return code == ResultCode.CODE_SUCCESS;
    }
}
