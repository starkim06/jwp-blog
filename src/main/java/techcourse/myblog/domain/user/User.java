package techcourse.myblog.domain.user;

import techcourse.myblog.domain.article.Article;
import techcourse.myblog.domain.comment.Comment;
import techcourse.myblog.dto.UserDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UserName name;

    @Embedded
    private UserPassword password;

    @Column(unique = true)
    @Embedded
    private Email email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Article> aritlces = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    protected User() {
    }

    public User(String name, String password, String email) {
        this.name = UserName.of(name);
        this.password = UserPassword.of(password);
        this.email = Email.of(email);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getUserName();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public void updateNameAndEmail(String name, String email) {
        this.name = UserName.of(name);
        this.email = Email.of(email);
    }

    public boolean isMatchPassword(UserDto dto) {
        return isMatchPassword(dto.getPassword());
    }

    public boolean isMatchPassword(String password) {
        return this.password.equals(UserPassword.of(password));
    }

    public boolean isMatchEmail(String email) {
        return this.email.equals(Email.of(email));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
