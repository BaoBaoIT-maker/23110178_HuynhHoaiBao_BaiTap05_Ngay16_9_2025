package vn.iot.star.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ánh xạ URL '/uploads/**' (được lưu trong DB)
        // tới thư mục vật lý 'file:src/main/resources/static/uploads/' 
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:src/main/resources/static/uploads/");
        
        // Cần thêm cả cấu hình cho thư mục 'static' nếu bạn override cấu hình mặc định
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}