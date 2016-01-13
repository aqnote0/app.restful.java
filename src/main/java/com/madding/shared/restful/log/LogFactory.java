package com.madding.shared.restful.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类LogAgg.java的实现描述：日志聚合
 * 
 * @author madding.lip Nov 25, 2013 2:17:28 PM
 */
public class LogFactory {

    private static final String FALSE_LOG_FORMAT = "false:[%s]:[%s]";
    private static final String TRUE_LOG_FORMAT  = "true:[%s]:[%s]";

    public static final String  COLON_SEP        = ":";

    public static String getTrueMsg(String data, String msg) {
        return String.format(TRUE_LOG_FORMAT, data, msg);
    }

    public static String getFalseMsg(String data, String msg) {
        return String.format(FALSE_LOG_FORMAT, data, msg);
    }

    public static final Logger gateway = LoggerFactory.getLogger("gateway");
    public static final Logger resultcode = LoggerFactory.getLogger("resultcode");
}
