package vn.iot.star.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.iot.star.entity.Video;
import vn.iot.star.repository.VideoRepository;
import vn.iot.star.service.VideoService;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Override
    public Page<Video> findAll(Pageable pageable) {
        return videoRepository.findAll(pageable);
    }

    @Override
    public Page<Video> findByTitleContaining(String title, Pageable pageable) {
        return videoRepository.findByTitleContaining(title, pageable);
    }

    @Override
    public Video findById(int id) {
        return videoRepository.findById(id).orElse(null);
    }

    @Override
    public Video save(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public void deleteById(int id) {
        videoRepository.deleteById(id);
    }
}
