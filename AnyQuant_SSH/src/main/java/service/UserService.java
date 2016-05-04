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
    boolean checkIfValid(UserEntity u);
}

