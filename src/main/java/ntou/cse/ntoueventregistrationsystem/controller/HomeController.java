package ntou.cse.ntoueventregistrationsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToHomepage() {
        // 將根路徑 "/" 重定向到 "/html/homepage.html"
        return "redirect:/html/homepage.html";
    }
}
