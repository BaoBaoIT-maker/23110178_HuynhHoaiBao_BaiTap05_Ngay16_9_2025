package vn.iot.star.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.iot.star.entity.User;
import vn.iot.star.repository.UserRepository;
import vn.iot.star.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username, String password) {
    	System.out.println(">>> Check login: " + username + " / " + password);
        User user = userRepository.findByUsernameAndPassword(username, password);
        System.out.println(">>> Found user: " + user);
        return user;
        
    }
    @Override // Thêm cho CRUD
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    
    @Override // Thêm cho CRUD
    public Page<User> findByUsernameContaining(String username, Pageable pageable) {
        return userRepository.findByUsernameContaining(username, pageable);
    }
    @Override // Thêm cho CRUD
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override // Thêm cho CRUD (cũng dùng cho register)
    public User save(User user) {
        return userRepository.save(user);
    }
    
    @Override // Thêm cho CRUD
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
    
    @Override
    public boolean checkExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }
}

