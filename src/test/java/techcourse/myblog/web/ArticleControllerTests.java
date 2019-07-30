package techcourse.myblog.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.ArticleRepository;
import techcourse.myblog.dto.ArticleDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        String title = "title";
        String contents = "contents";
        String coverUrl = "";

        ArticleDto dto = new ArticleDto(title, contents, coverUrl);
        articleRepository.addArticle(dto);
    }

    @Test
    void index() {
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void postArticleTest() {
        String title = "title";
        String contents = "contents";
        String coverUrl = "";

        webTestClient.post().uri("/articles/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("title", title)
                        .with("coverUrl", coverUrl)
                        .with("contents", contents))
                .exchange()
                .expectStatus().is3xxRedirection();

        assertThat(articleRepository.findByIndex(1)).isEqualTo(new Article(title, contents, coverUrl));


    }

    @Test
    void deleteArticleTest() {
        webTestClient.delete().uri("/articles/0")
                .exchange().expectStatus().is3xxRedirection();
        assertThat(articleRepository.articleCount()).isEqualTo(0);
    }

    @Test
    void getArticleEditTest() {
        webTestClient.get().uri("/articles/0/edit")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void putArticleTest() {
        String title = "a";
        String contents = "b";
        String coverUrl = "c";
        webTestClient.put().uri("/articles/0")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("title", title)
                        .with("contents", contents)
                        .with("coverUrl", coverUrl))
                .exchange()
                .expectStatus().is3xxRedirection();

        assertThat(articleRepository.findByIndex(0)).isEqualTo(new Article(title, contents, coverUrl));
    }

    @AfterEach
    void tearDown() {
        for (int i = 0; i < articleRepository.articleCount(); i++) {
            articleRepository.deleteByIndex(i);
        }
    }
}
