package id.my.hendisantika.backend.global.RsData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * Project : backend
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 30/12/24
 * Time: 23.15
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@AllArgsConstructor
public class RsData<T> {
    private String resultCode;
    private String msg;
    private T data;

    public static <T> RsData<T> of(String resultCode, String msg, T data) {
        return new RsData<>(resultCode, msg, data);
    }

    public static <T> RsData<T> of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }

    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }

}
