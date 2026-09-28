package vn.tqduy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping
    public String error(Model model) {
        model.addAttribute("message", "Đã xảy ra lỗi hoặc bạn không có quyền truy cập.");
        return "error";
    }
}
