package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // 라우팅 홈화면
    public String home(){
        return "home"; //home.html 반환
    }
}
