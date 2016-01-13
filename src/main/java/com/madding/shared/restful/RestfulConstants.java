
package com.madding.shared.restful;

/**
 * 类RestfulRequestConstants.java的实现描述：restful相关常量类
 * 
 * @author madding.lip Jan 10, 2014 12:53:22 PM
 */
public interface RestfulConstants {

    public static final String _INPUT_CHARSET   = "_input_charset";
    public static final String SIGN             = "sign";
    public static final String SIGN_TYPE        = "sign_type";
    public static final String TIMESTAMP        = "timestamp";
    public static final String PARTNER          = "partner";
    public static final String RESTFUL_SERVICE  = "service";

    public static final long   URL_EXPIRE_TIME  = 1 * 60 * 1000;   // 60s
    public static final int    VALID_MAC_LENGTH = 12;

    public static final String SUCCESS          = "success";
    public static final String FAIL             = "fail";
    public static final String AGREE            = "agree";
    public static final String REJECT           = "reject";
}
