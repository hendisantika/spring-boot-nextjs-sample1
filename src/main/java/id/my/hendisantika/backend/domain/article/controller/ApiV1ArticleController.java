package id.my.hendisantika.backend.domain.article.controller;

import id.my.hendisantika.backend.domain.article.service.ArticleService;
import id.my.hendisantika.backend.global.RsData.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : backend
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 31/12/24
 * Time: 06.19
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {
    private final ArticleService articleService;
    private final Rq rq;
}
