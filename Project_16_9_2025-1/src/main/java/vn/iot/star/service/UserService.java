package vn.iot.star.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.iot.star.entity.User;

public interface UserService {
    User login(String username, String password);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    User register(User user);
 // Thêm cho CRUD
    Page<User> findAll(Pageable pageable);
    Page<User> findByUsernameContaining(String username, Pageable pageable);
    User findById(int id);
    User save(User user); // Save/Update
    void deleteById(int id);
}
