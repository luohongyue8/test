/**
 * 
 */
package com.cat.demo.Config;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Properties;

/**
 * set properties
 *
 */
public class ConfigUtil {
    
    /**
     * Setting properties.
     */
    private static Properties _PROPS = null;
    
    static {
        InputStream in = null;
        
        try {
            if (null == _PROPS || _PROPS.size() == 0) {
                _PROPS = new Properties();
                in = ConfigUtil.class.getResourceAsStream("/city.properties"); ;
                _PROPS.load(in);
            }
        } catch (FileNotFoundException e) {
            // do nothing
        } catch (IOException e) {
            // do nothing
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }
    
    /**
     * Get application setting.
     * @param key properties key
     * @return properties value
     */
    public static String getProperty(String key) {
        String value = "";
        
        if (null == _PROPS || _PROPS.size() == 0) {
            return value;
        }
        
        value = (String)_PROPS.get(key);
        
        return value;
    }


    public static Properties getProps() {
        return _PROPS;
    }

    public static void setProps(Properties Props) {
        _PROPS = Props;
    }
}
