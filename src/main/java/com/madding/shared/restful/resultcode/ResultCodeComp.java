package com.madding.shared.restful.resultcode;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;
import org.springframework.beans.MethodInvocationException;

import com.madding.shared.restful.log.LogFactory;
import com.madding.shared.restful.util.MessageUtil;

/**
 * ResultCodeComp.java类描述：resultCode内部功能封装类
 * 
 * @author madding.lip
 */
public class ResultCodeComp {

    private final IResultCode resultCode;
    private final Locale      locale;

    public ResultCodeComp(IResultCode resultCode, Locale locale){
        if (resultCode == null || locale == null) {
            throw new IllegalArgumentException("ResultCode/Locale should not be null: "
                                               + getClass().getName());
        }

        if (!resultCode.getClass().isEnum()
            || !(resultCode instanceof IResultCode)) {
            throw new IllegalArgumentException("ResultCode should be java.lang.Enum: "
                                               + getClass().getName());
        }

        this.resultCode = resultCode;
        this.locale = locale;
    }

    public ResultCodeComp(IResultCode resultCode){
        this(resultCode, Locale.CHINA);
    }

    public String getName() {
        return Enum.class.cast(resultCode).name();
    }

    public String getMessage() {
        return getMessage(null);
    }

    public String getMessage(Object[] param) {
        final Properties props = getResource();
        return MessageUtil.getMessage(props, resultCode.getName(), param);
    }

    public String getRichMessage(Map<String, Object> param) {

        final Properties props = getResource();
        String instring = MessageUtil.getMessage(props, resultCode.getName(),
                                                 param);

        VelocityContext context = new VelocityContext();
        if (param != null) {
            Iterator<String> iter = param.keySet().iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                context.put(key, param.get(key));
            }
        }

        StringWriter writer = new StringWriter();
        Velocity.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM,
                             new NullLogChute());
        try {
            Velocity.evaluate(context, writer, null, instring);
        } catch (ParseErrorException e) {
            LogFactory.resultcode.error("resultCodeComp.getRichMessage error: ", e);
            return null;
        } catch (MethodInvocationException e) {
            LogFactory.resultcode.error("resultCodeComp.getRichMessage error: ", e);
            return null;
        } catch (ResourceNotFoundException e) {
            LogFactory.resultcode.error("resultCodeComp.getRichMessage error: ", e);
            return null;
        } catch (IOException e) {
            LogFactory.resultcode.error("resultCodeComp.getRichMessage error: ", e);
            return null;
        }

        return writer.toString();
    }

    protected final synchronized Properties getResource() {

        Properties props = ResultCodeCache.get(getCacheKey());

        if (props == null) {
            String enumClassName = resultCode.getClass().getName();
            props = PropertiesFactory.getProperties(enumClassName, locale);
            if (props != null) {
                fixedProperites(props);
            } else {
                setDefaultProperties(props);
            }
            ResultCodeCache.put(getCacheKey(), props);
        }

        return props;
    }

    // 将配置文件中未配置的枚举采用name显示
    protected final synchronized void fixedProperites(Properties props) {
        IResultCode[] elements = resultCode.getClass().getEnumConstants();
        for (IResultCode element : elements) {
            String value = (String) props.get(element.getName());
            if (StringUtils.isBlank(value)) {
                props.setProperty(element.getName(), element.getName());
            }
        }
    }

    // 使用枚举的name当作默认值
    protected final synchronized void setDefaultProperties(Properties props) {
        props = new Properties();
        IResultCode[] elements = resultCode.getClass().getEnumConstants();
        for (IResultCode element : elements) {
            props.setProperty(element.getName(), element.getName());
        }
    }

    private String getCacheKey() {
        String enumClassName = resultCode.getClass().getName();
        return enumClassName + "_" + locale.toString();
    }

}
