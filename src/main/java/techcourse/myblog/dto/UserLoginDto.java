package techcourse.myblog.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserLoginDto {
    @Email(message = "올바른 이메일을 입력하세요")
    private String email;
    @Pattern(regexp = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{8,20}$", message = "올바른 비밀번호를 입력하세요")
    private String password;
}
