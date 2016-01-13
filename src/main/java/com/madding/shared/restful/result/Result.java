package com.madding.shared.restful.result;

import java.io.Serializable;

/**
 * Result.java类描述：service结果包装类
 * 
 * @author madding.lip
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -8871475133531459743L;

    // 调用结果，是否成功
    private boolean           success;
    // 返回的业务数据
    private T                 model;
    // 调用结果编码
    private int               code;
    // 调用结果名字
    private String            name;
    // 调用结果原因
    private String            reason;

    public Result(){
        this(false);
    }

    public Result(boolean success){
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
