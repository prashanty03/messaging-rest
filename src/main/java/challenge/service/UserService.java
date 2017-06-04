package challenge.service;

import challenge.bean.User;

public interface UserService {
    public User findUserByHandle(String name);
    public User findUserById(Long id);
}