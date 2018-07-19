package com.aqnote.shared.restful.resultcode;

/**
 * ResultCode.java类描述：基础文案显示接口
 * 
 * @author madding.lip
 */
public interface IResultCode {

    /** 获取错误编码 */
    public int getCode();

    /** 获取该对象的名字 */
    public String getName();

    /** 获取该对象的具体消息 */
    public String getMessage();

    /**
     * 获取format后的消息，param是替换参数， 消息格式可写成：已经重发了{0}条，还剩{1}条可以重发
     */
    public String getMessage(Object[] param);

}
