package vn.iot.star.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import vn.iot.star.entity.Category;
import vn.iot.star.entity.Video;
import vn.iot.star.service.CategoryService;
import vn.iot.star.service.VideoService;

@Controller
@RequestMapping("/admin/video")
@RequiredArgsConstructor
public class VideoController {
	@Autowired
    private VideoService videoService;

    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public String list(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword) {
        Page<Video> videoPage;
        if (keyword.isEmpty()) {
            videoPage = videoService.findAll(PageRequest.of(page, 5));
        } else {
            videoPage = videoService.findByTitleContaining(keyword, PageRequest.of(page, 5));
        }
        model.addAttribute("videoPage", videoPage);
        model.addAttribute("keyword", keyword);
        return "admin/video/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("video", new Video());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/video/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Video video) {
        videoService.save(video);
        return "redirect:/admin/video";
    }
 // ✅ Xử lý lưu video (chỗ bạn đang bị lỗi)
    @PostMapping("/save")
    public String saveVideo(@ModelAttribute Video video,
                            @RequestParam("file") MultipartFile file) throws IOException {
        
        // --- 1. Xử lý File Upload (Phần đã đúng) ---
        if (!file.isEmpty()) {
            String uploadDir = "src/main/resources/static/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, file.getBytes());

            video.setUrl("/uploads/" + fileName);
        }
        
        // --- 2. KHẮC PHỤC LỖI CATEGORY BINDING (QUAN TRỌNG) ---
        if (video.getCategory() != null && video.getCategory().getId() != null) {
            // Lấy ID đã được bind từ form
            Long categoryId = video.getCategory().getId();
            
            // Dùng CategoryService để lấy đối tượng Category HOÀN CHỈNH từ DB
            // (findById của bạn nhận int, cần ép kiểu hoặc sửa findById nhận Long)
            Category managedCategory = categoryService.findById(categoryId.intValue()); 
            
            // Gán đối tượng Category đã lấy từ DB vào Video
            video.setCategory(managedCategory);
        } else {
            // Tùy chọn: Xử lý lỗi nếu Category không được chọn
            System.err.println("Lỗi: Category ID không tồn tại hoặc không được bind!");
            // Có thể thêm lỗi vào Model hoặc RedirectAttributes và quay lại form
        }

        // --- 3. Lưu Video ---
        videoService.save(video); // ✅ Bây giờ Video đã có Category được managed bởi JPA
        return "redirect:/admin/video";
    }
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("video", videoService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "admin/video/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Video video) {
        
        // ✅ THÊM LOGIC NÀY TẠI ĐÂY (Tương tự như /save)
        if (video.getCategory() != null && video.getCategory().getId() != null) {
            Long categoryId = video.getCategory().getId();
            // Lấy đối tượng Category đầy đủ từ DB
            Category managedCategory = categoryService.findById(categoryId.intValue()); 
            video.setCategory(managedCategory);
        }
        
        videoService.save(video);
        return "redirect:/admin/video";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        videoService.deleteById(id);
        return "redirect:/admin/video";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("video", videoService.findById(id));
        return "admin/video/view";
    }
}