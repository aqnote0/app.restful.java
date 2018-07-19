package com.aqnote.shared.restful.resultcode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqnote.shared.restful.util.ClassLoaderUtil;

/**
 * PropertiesFactory.java类描述：创建<code>ResourceBundle</code>的实例的工厂.
 * 
 * @author madding.lip
 */
public class PropertiesFactory {

    private static final Logger logger         = LoggerFactory.getLogger(PropertiesFactory.class);

    private static final String FILE_TYPE      = ".properties";
    private static final String SEPARATOR_SIGN = "_";

    public static Properties getProperties(String enumClassName,
                                           Locale locale) {

        String filename = getFilename(enumClassName, locale);
        ClassLoader classLoader = ClassLoaderUtil.getClassLoader();
        InputStream is = classLoader.getResourceAsStream(filename);
        if (is == null) {
            logger.error("false:load file error1:"
                         + classLoader.getClass().getName() + ":" + filename);
            return null;
        }
        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            logger.error("false:load file error2:"
                         + classLoader.getClass().getName() + ":" + filename,
                         e);
            return null;
        }
        return props;
    }

    protected static String getFilename(String enumClassName, Locale locale) {

        if (StringUtils.isBlank(enumClassName) || locale == null) {
            return null;
        }
        String path = enumClassName.replace('.', '/');
        return path + SEPARATOR_SIGN + locale.toString() + FILE_TYPE;
    }
}
