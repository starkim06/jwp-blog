package techcourse.myblog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.myblog.domain.article.Article;
import techcourse.myblog.domain.article.ArticleException;
import techcourse.myblog.dto.ArticleDto;
import techcourse.myblog.repository.ArticleRepository;

import java.util.List;

@Service
public class ArticleService {
    private static final String NOT_EXIST_ARTICLE = "해당 아티클이 없습니다.";
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Article> findAllPage(Pageable pageable) {
        Page<Article> page = articleRepository.findAll(pageable);
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public Article findArticle(long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new ArticleException(NOT_EXIST_ARTICLE));
    }

    @Transactional
    public Long save(Article article) {
        Article newArticle = articleRepository.save(article);
        return newArticle.getId();
    }

    @Transactional
    public Article update(long articleId, ArticleDto articleDto) {
        Article originArticle = findArticle(articleId);
        originArticle.update(articleDto.toEntity());
        return originArticle;
    }

    @Transactional
    public void delete(long articleId) {
        articleRepository.deleteById(articleId);
    }
}
