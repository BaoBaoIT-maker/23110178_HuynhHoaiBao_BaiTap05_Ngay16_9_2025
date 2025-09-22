package vn.iot.star.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

