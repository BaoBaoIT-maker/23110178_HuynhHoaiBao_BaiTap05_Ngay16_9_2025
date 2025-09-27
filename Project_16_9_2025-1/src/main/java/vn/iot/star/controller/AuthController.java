package vn.iot.star.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import vn.iot.star.entity.User;
import vn.iot.star.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // --- Login ---
    @GetMapping("/login")
    public String loginForm(ModelMap model) {
        model.addAttribute("user", new User());
        return "auth/login"; // login.html
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session, ModelMap model) {
        User u = userService.login(user.getUsername(), user.getPassword());
        if (u == null) {
            model.addAttribute("alert", "Tài khoản hoặc mật khẩu không đúng!");
            return "auth/login";
        }
        session.setAttribute("account", u);

        if ("ADMIN".equals(u.getRole())) {
            // Trả về view: templates/admin/home.html
            return "redirect:/admin/home"; 
            
        } else if ("MANAGER".equals(u.getRole())) {
            // Trả về view: templates/manager/home.html
            // Lưu ý: Đảm bảo bạn có mapping cho /manager/home
            return "redirect:/manager/home"; 
            
        } else if ("USER".equals(u.getRole())) {
            // Trả về view: templates/user/home.html
            // Lưu ý: Đảm bảo bạn có mapping cho /user/home
            return "redirect:/user/home";
            
        } else {
            // Trường hợp role không xác định (Mặc định)
            model.addAttribute("alert", "Vai trò người dùng không xác định.");
            session.invalidate(); // Hủy session
            return "auth/login";
        }
    }
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        // Trả về view: templates/auth/forgot-password.html
        return "auth/forgot-password"; 
    }
 // ✅ 2. Xử lý yêu cầu quên mật khẩu
    @PostMapping("/forgot-password")
    public String processForgotPassword(
            @RequestParam("identifier") String identifier, 
            ModelMap model) {
        
        // TODO: THÊM LOGIC GỬI EMAIL KHÔI PHỤC MẬT KHẨU TẠI ĐÂY
        // Trong môi trường demo, ta chỉ hiển thị thông báo.
        
        model.addAttribute("message", "Yêu cầu khôi phục đã được gửi đi. Vui lòng kiểm tra email.");
        // Giữ nguyên trang để hiển thị thông báo thành công
        return "auth/forgot-password"; 
    }
    // --- Logout ---
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // --- Register ---
    @GetMapping("/register")
    public String registerForm(ModelMap model) {
        model.addAttribute("user", new User());
        return "auth/register"; // register.html
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, ModelMap model) {
        if (userService.checkExistEmail(user.getEmail())) {
            model.addAttribute("alert", "Email đã tồn tại!");
            return "auth/register";
        }
        if (userService.checkExistUsername(user.getUsername())) {
            model.addAttribute("alert", "Tài khoản đã tồn tại!");
            return "auth/register";
        }
        user.setRole("USER"); // default user
        userService.register(user);
        return "redirect:/login";
    }
}
