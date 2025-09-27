package vn.iot.star.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iot.star.entity.User;
import vn.iot.star.service.UserService;

@Controller
@RequestMapping("/admin/users") // 👈 Khớp với cấu trúc file: admin/users/...
public class UserController { 

    @Autowired
    private UserService userService;

    // --- 1. Danh sách (List) có phân trang + tìm kiếm ---
    @GetMapping("")
    public String list(
        ModelMap model,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "5") int size,
        @RequestParam(name = "username", required = false) String username
    ) {
        Page<User> users;
        if (StringUtils.hasText(username)) {
            users = userService.findByUsernameContaining(username, PageRequest.of(page, size));
            model.addAttribute("username", username);
        } else {
            users = userService.findAll(PageRequest.of(page, size));
        }
        
        model.addAttribute("users", users.getContent());
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("currentPage", page);
        return "admin/users/list"; // 👈 list.html
    }

    // --- 2. Thêm mới (Add) ---
    @GetMapping("add")
    public String add(ModelMap model) {
        model.addAttribute("user", new User());
        return "admin/users/form"; // 👈 form.html
    }

    // --- 3. Sửa (Edit) ---
    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") int id, ModelMap model) {
        User user = userService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "admin/users/form"; // Tái sử dụng form.html
        }
        return "redirect:/admin/users";
    }

    // --- 4. Xem chi tiết (View) ---
    @GetMapping("view/{id}")
    public String view(@PathVariable("id") int id, ModelMap model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/admin/users";
        }
        model.addAttribute("user", user);
        return "admin/users/view"; // 👈 view.html
    }

    // --- 5. Lưu hoặc Cập nhật (Save/Update) ---
    @PostMapping("saveOrUpdate")
    public String saveOrUpdate(
            @ModelAttribute("user") User user,
            RedirectAttributes redirectAttributes
    ) {
        // 1. Nếu là UPDATE (user.getId() != 0) VÀ người dùng KHÔNG nhập mật khẩu mới
        if (user.getId() != 0 && user.isPasswordEmpty()) {
            
            // 2. Lấy đối tượng user CŨ từ DB (chỉ lấy ID, mật khẩu, và username)
            User existingUser = userService.findById(user.getId());
            
            if (existingUser != null) {
                // 3. Gán mật khẩu CŨ từ DB vào đối tượng user đang được bind
                user.setPassword(existingUser.getPassword()); 
            }
        } 
        // Lưu ý: Nếu là ADD (user.getId() == 0), password sẽ là giá trị người dùng nhập
        // Nếu là UPDATE VÀ người dùng CÓ nhập mật khẩu mới, password mới sẽ được lưu
        
        // 4. Thực hiện lưu
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "User saved successfully!");
        return "redirect:/admin/users";
    }

    // --- 6. Xóa (Delete) ---
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        return "redirect:/admin/users";
    }
}
