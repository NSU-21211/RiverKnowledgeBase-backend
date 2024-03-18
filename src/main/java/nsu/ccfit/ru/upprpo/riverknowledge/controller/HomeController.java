package nsu.ccfit.ru.upprpo.riverknowledge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/api")
    public String home() {
        return "index";
    }
}
