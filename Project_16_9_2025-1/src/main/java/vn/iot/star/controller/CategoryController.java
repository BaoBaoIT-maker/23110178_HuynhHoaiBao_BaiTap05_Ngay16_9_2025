package vn.iot.star.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iot.star.entity.Category;
import vn.iot.star.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // --- Danh sách có phân trang + tìm kiếm ---
    @GetMapping("")
    public String list(
        ModelMap model,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "5") int size,
        @RequestParam(name = "name", required = false) String name
    ) {
        Page<Category> categories;
        if (StringUtils.hasText(name)) {
            categories = categoryService.findByCategoryNameContaining(name, PageRequest.of(page, size));
            model.addAttribute("name", name);
        } else {
            categories = categoryService.findAll(PageRequest.of(page, size));
        }
        model.addAttribute("categories", categories.getContent());
        model.addAttribute("totalPages", categories.getTotalPages());
        model.addAttribute("currentPage", page);
        return "admin/categories/list";
    }

    // --- Thêm mới ---
    @GetMapping("add")
    public String add(ModelMap model) {
        model.addAttribute("category", new Category());
        return "admin/categories/form"; // form.html
    }

    // --- Sửa ---
    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") int id, ModelMap model) {
        Category category = categoryService.findById(id);
        if (category != null) {
            model.addAttribute("category", category);
            return "admin/categories/form"; // reuse form.html
        }
        return "redirect:/admin/categories";
    }

    // --- Xem chi tiết ---
    @GetMapping("view/{id}")
    public String view(@PathVariable("id") int id, ModelMap model) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return "redirect:/admin/categories";
        }
        model.addAttribute("category", category);
        return "admin/categories/view"; // view.html
    }

    // --- Save hoặc Update ---
    @PostMapping("saveOrUpdate")
    public String saveOrUpdate(
            @ModelAttribute("category") Category category,
            RedirectAttributes redirectAttributes
    ) {
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "Category saved successfully!");
        return "redirect:/admin/categories";
    }

    // --- Xóa ---
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        categoryService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Category deleted successfully!");
        return "redirect:/admin/categories";
    }
}
