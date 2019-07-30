package techcourse.myblog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import techcourse.myblog.domain.Article.Article;
import techcourse.myblog.dto.ArticleDto;
import techcourse.myblog.service.PageableArticleService;

@Controller
public class ArticleController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);

    private final PageableArticleService articleService;

    public ArticleController(PageableArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index(Model model,
                        @PageableDefault(sort = {"id"}, size = 3, direction = Sort.Direction.DESC) Pageable pageable) {
        log.debug("Pageable : {}", pageable);
        model.addAttribute("articles", articleService.findAllPage(pageable));
        return "index";
    }

    @GetMapping("/writing")
    public String getArticleEditForm() {
        return "article-edit";
    }

    @GetMapping("/articles/{articleId}")
    public String getArticle(@PathVariable long articleId, Model model) {
        setArticleModel(model, articleService.findArticle(articleId));
        return "article";
    }

    @GetMapping("/articles/{articleId}/edit")
    public String getEditArticle(@PathVariable long articleId, Model model) {
        setArticleModel(model, articleService.findArticle(articleId));
        return "article-edit";
    }

    @PostMapping("/articles")
    public String saveArticle(ArticleDto dto) {
        return "redirect:/articles/" + articleService.save(dto.toEntity());
    }

    @PutMapping("/articles/{articleId}")
    public String getModifiedArticle(@PathVariable long articleId, ArticleDto dto, Model model) {
        setArticleModel(model, articleService.update(articleId, dto));
        return "article";
    }

    @DeleteMapping("/articles/{articleId}")
    public String deleteArticle(@PathVariable long articleId) {
        articleService.delete(articleId);
        return "redirect:/";
    }

    private void setArticleModel(Model model, Article article) {
        model.addAttribute("article", article);
    }
}