package vn.iot.star.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/admin/home")
    public String adminHome(HttpSession session) {
        if (session.getAttribute("account") == null) return "redirect:/login";
        return "admin/home";
    }

    @GetMapping("/manager/home")
    public String managerHome(HttpSession session) {
        if (session.getAttribute("account") == null) return "redirect:/login";
        return "manager/home";
    }

    @GetMapping("/user/home")
    public String userHome(HttpSession session) {
        if (session.getAttribute("account") == null) return "redirect:/login";
        return "user/home";
    }
}
