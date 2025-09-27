package vn.iot.star.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.iot.star.entity.Video;

import java.util.List;

public interface VideoService {
    List<Video> findAll();
    Page<Video> findAll(Pageable pageable);
    Page<Video> findByTitleContaining(String title, Pageable pageable);
    Video findById(int id);
    Video save(Video video);
    void deleteById(int id);
}
