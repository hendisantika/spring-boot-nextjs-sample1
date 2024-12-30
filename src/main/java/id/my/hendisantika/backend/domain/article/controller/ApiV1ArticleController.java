package id.my.hendisantika.backend.domain.article.controller;

import id.my.hendisantika.backend.domain.article.dto.ArticleDto;
import id.my.hendisantika.backend.domain.article.entity.Article;
import id.my.hendisantika.backend.domain.article.service.ArticleService;
import id.my.hendisantika.backend.domain.member.entity.Member;
import id.my.hendisantika.backend.global.RsData.RsData;
import id.my.hendisantika.backend.global.RsData.rq.Rq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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

    @PostMapping
    public RsData<WriteResponse> write(@Valid @RequestBody WriteRequest writeRequest) {
        Member member = rq.getMember();

        RsData<Article> writeRs = this.articleService.create(member, writeRequest.getSubject(), writeRequest.getContent());

        if (writeRs.isFail()) return (RsData) writeRs;

        return RsData.of(
                writeRs.getResultCode(),
                writeRs.getMsg(),
                new WriteResponse(new ArticleDto(writeRs.getData()))
        );
    }

    @PatchMapping("/{id}")
    public RsData modify(@Valid @RequestBody ModifyRequest modifyRequest, @PathVariable("id") Long id) {
        Optional<Article> optionalArticle = this.articleService.findById(id);

        if (optionalArticle.isEmpty()) return RsData.of(
                "F-1",
                "Post %d does not exist.".formatted(id),
                null
        );

        // Check member permissions canModify();
        RsData<Article> modifyRs = this.articleService.modify(optionalArticle.get(), modifyRequest.getSubject(), modifyRequest.getContent());
        return RsData.of(
                modifyRs.getResultCode(),
                modifyRs.getMsg(),
                new ModifyResponse(modifyRs.getData())
        );
    }

    @DeleteMapping("/{id}")
    public RsData<RemoveResponse> remove(@PathVariable("id") Long id) {
        Optional<Article> optionalArticle = this.articleService.findById(id);

        if (optionalArticle.isEmpty()) return RsData.of(
                "F-1",
                "Post %d does not exist.".formatted(id),
                null
        );

        RsData<Article> deleteRs = articleService.deleteById(id);

        return RsData.of(
                deleteRs.getResultCode(),
                deleteRs.getMsg(),
                new RemoveResponse(optionalArticle.get())
        );
    }

    public record ArticlesResponse(List<ArticleDto> articles) {
    }

    public record ArticleResponse(ArticleDto article) {
    }

    @Data
    public static class WriteRequest {
        @NotBlank
        private String subject;
        @NotBlank
        private String content;
    }

    public record WriteResponse(ArticleDto articledto) {
    }

    @Data
    public static class ModifyRequest {
        @NotBlank
        private String subject;
        @NotBlank
        private String content;
    }

    public record ModifyResponse(Article article) {
    }

    public record RemoveResponse(Article article) {
    }
}
