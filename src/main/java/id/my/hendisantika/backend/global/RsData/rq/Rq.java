package id.my.hendisantika.backend.global.RsData.rq;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Created by IntelliJ IDEA.
 * Project : backend
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 30/12/24
 * Time: 23.20
 * To change this template use File | Settings | File Templates.
 */
@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final HttpServletResponse resp;
    private final HttpServletRequest req;
    private final EntityManager entityManager;
    private Member member;

}
