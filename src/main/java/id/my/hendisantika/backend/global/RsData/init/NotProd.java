package id.my.hendisantika.backend.global.RsData.init;

import id.my.hendisantika.backend.domain.article.service.ArticleService;
import id.my.hendisantika.backend.domain.member.entity.Member;
import id.my.hendisantika.backend.domain.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

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
@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(ArticleService articleService, MemberService memberService, PasswordEncoder passwordEncoder) {
        String password = passwordEncoder.encode("1234");
        return args -> {
            // Add 3 members
            Member user1 = memberService.join("user1", password, "test@test.com");
            Member user2 = memberService.join("user2", password, "test@test.com");
            Member admin = memberService.join("admin", password, "admin@test.com");

            // Add author member
            articleService.create(user1, "title 1", "detail 1");
            articleService.create(user1, "title 2", "detail 2");
            articleService.create(user2, "title 3", "detail 3");
            articleService.create(user2, "title 4", "detail 4");
            articleService.create(admin, "title 5", "detail 5");
        };
    }

}
