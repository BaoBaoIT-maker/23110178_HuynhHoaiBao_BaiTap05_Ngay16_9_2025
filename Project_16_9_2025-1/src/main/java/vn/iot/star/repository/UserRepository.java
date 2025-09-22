package vn.iot.star.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.iot.star.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
