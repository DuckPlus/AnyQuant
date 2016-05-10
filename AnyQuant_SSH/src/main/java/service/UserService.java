package service;

import entity.UserEntity;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/4
 */

public interface UserService {
    void saveUser(UserEntity us);
    List<UserEntity> getAllUsernames();

    /**
     * 检查用户是否为有效用户
     * @param u 用户信息
     * @return 如果有效返回其ID,否则返回NUll
     */
    String checkIfValid(UserEntity u);
}

