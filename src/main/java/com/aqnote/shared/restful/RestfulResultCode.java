package com.aqnote.shared.restful;

import java.util.Locale;
import java.util.Map;

import com.aqnote.shared.restful.resultcode.IRichResultCode;
import com.aqnote.shared.restful.resultcode.ResultCodeComp;

/**
 * 类CertResultCode.java的实现描述：证书接口错误码类
 * 
 * @author madding.lip Dec 16, 2013 3:55:36 PM
 */
public enum RestfulResultCode implements IRichResultCode {
                                                          SUCCESS(0),
                                                          SERVICE_NO_EXIST(1),
                                                          SIGN_IS_INVALID(2),
                                                          PARAM_IS_NULL(3),
                                                          MAC_IS_INVALID(4),
                                                          SYSTEM_ERROR(5),
                                                          URL_EXPIRE(6),
                                                          PARAM_IS_NOT_NUMBIC(7);

    private int                  code;

    private final ResultCodeComp comp = new ResultCodeComp(this, Locale.CHINA);

    private RestfulResultCode(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return comp.getName();
    }

    public String getMessage() {
        return comp.getMessage();
    }

    public String getMessage(Object[] param) {
        return comp.getMessage(param);
    }

    public String getRichMessage(Map<String, Object> param) {
        return comp.getRichMessage(param);
    }
}
