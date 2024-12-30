package id.my.hendisantika.backend.domain.article.controller;

import id.my.hendisantika.backend.domain.article.dto.ArticleDto;
import id.my.hendisantika.backend.domain.article.service.ArticleService;
import id.my.hendisantika.backend.global.RsData.RsData;
import id.my.hendisantika.backend.global.RsData.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping
    public RsData<ArticlesResponse> getArticles() {
        List<ArticleDto> articleDtoList = this.articleService
                .getList()
                .stream()
                .map(article -> new ArticleDto(article))
                .toList();

        return RsData.of("S-1", "success", new ArticlesResponse(articleDtoList));
    }

    @GetMapping("/{id}")
    public RsData<ArticleResponse> getArticle(@PathVariable("id") Long id) {
        return articleService.getArticle(id).map(article -> RsData.of(
                "S-1",
                "success",
                new ArticleResponse(new ArticleDto(article))
        )).orElseGet(() -> RsData.of(
                "F-1",
                "Post %d does not exist.".formatted(id),
                null
        ));
    }
}
