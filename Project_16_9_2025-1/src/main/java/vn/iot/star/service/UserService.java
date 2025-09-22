package vn.iot.star.service;

import vn.iot.star.entity.User;

public interface UserService {
    User login(String username, String password);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    User register(User user);
}
