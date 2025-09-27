package vn.iot.star.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iot.star.entity.Video;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findByTitleContaining(String title);

	Page<Video> findByTitleContaining(String title, Pageable pageable);
	List<Video> findByTitleContainingIgnoreCase(String keyword);
}
