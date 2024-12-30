package id.my.hendisantika.backend.domain.article.service;

import id.my.hendisantika.backend.domain.article.entity.Article;
import id.my.hendisantika.backend.domain.article.repository.ArticleRepository;
import id.my.hendisantika.backend.domain.member.entity.Member;
import id.my.hendisantika.backend.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : backend
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 31/12/24
 * Time: 06.16
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> getList() {
        return this.articleRepository.findAll();
    }

    public Optional<Article> getArticle(Long id) {
        return this.articleRepository.findById(id);
    }

    @Transactional
    public RsData<Article> create(Member member, String subject, String content) {
        Article article = Article.builder()
                .author(member)
                .subject(subject)
                .content(content)
                .build();
        this.articleRepository.save(article);

        return RsData.of(
                "S-3",
                "The post has been created.",
                article
        );
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    public RsData<Article> modify(Article article, String subject, String content) {
        article.setSubject(subject);
        article.setContent(content);
        articleRepository.save(article);

        return RsData.of(
                "S-4",
                "Post %d has been edited.".formatted(article.getId()),
                article
        );
    }

    public RsData<Article> deleteById(Long id) {
        articleRepository.deleteById(id);

        return RsData.of(
                "S-5",
                "Post %d has been deleted.".formatted(id),
                null
        );
    }
}
