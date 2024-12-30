package id.my.hendisantika.backend.domain.article.repository;

import id.my.hendisantika.backend.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

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
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
