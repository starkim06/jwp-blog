package techcourse.myblog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import techcourse.myblog.dto.UserDto;
import techcourse.myblog.service.LoginService;
import techcourse.myblog.web.support.LoginSessionManager;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/logout")
    public String logout(LoginSessionManager loginSessionManager) {
        loginSessionManager.clearSession();
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(UserDto userDto, LoginSessionManager loginSessionManager) {
        UserDto resultDto = loginService.login(userDto);
        loginSessionManager.setLoginSession(resultDto.getName(), resultDto.getEmail());
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }
}
