
package com.cat.demo.Assist;

import com.google.gson.Gson;
import java.lang.reflect.Type;


/**
 * String helper class.
 *
 */
public class StringTools {
    /**
     * Gson object.
     */
    private static Gson gson = new Gson();
    
    /**
     * Convert object to String(Json).
     * @param t entity object
     * @return json string
     */
    public static <T> String toStringByJson(T t) {
        if (null == t) {
            return null;
        }
        
        return gson.toJson(t);
    }
    
    /**
     * Convert String(Json) to object.
     * @param json string
     * @param type entity object type
     * @return entity object
     */
    public static <T> T toTByJson(String json, Type type) {
        if (json == null || "".equals(json.trim())) {
            return null;
        }

        return gson.<T>fromJson(json, type);
    }

}
