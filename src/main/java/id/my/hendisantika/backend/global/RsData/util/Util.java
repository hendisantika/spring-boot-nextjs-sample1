package id.my.hendisantika.backend.global.RsData.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : backend
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 30/12/24
 * Time: 23.22
 * To change this template use File | Settings | File Templates.
 */
public class Util {
    public static class json {
        public static Object toStr(Map<String, Object> map) {
            try {
                return new ObjectMapper().writeValueAsString(map);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
    }
}
