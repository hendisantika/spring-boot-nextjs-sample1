package id.my.hendisantika.backend.domain.article.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : backend
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 30/12/24
 * Time: 23.10
 * To change this template use File | Settings | File Templates.
 */
@Getter
public class ArticleDto {
    private final long id;
    private final String subject;
    private final String author;
    private final LocalDateTime createdDate;

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.subject = article.getSubject();
        this.author = article.getAuthor().getUsername();
        this.createdDate = article.getCreatedDate();
    }
}
