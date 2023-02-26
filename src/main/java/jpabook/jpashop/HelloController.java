package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){ // model에다 data를 실어서 view에 넘길 수 있다.
        model.addAttribute("data","hello!!");// 이름이 data란 곳에 hello!!!를 넘김
        return "hello"; // 화면이름 -> template 파일에 hello.html 연결
    }
}
