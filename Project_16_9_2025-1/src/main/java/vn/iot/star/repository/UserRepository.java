package vn.iot.star.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.iot.star.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User findByUsername(String username);
    Page<User> findByUsernameContaining(String username, Pageable pageable); // Tìm kiếm theo username

}
