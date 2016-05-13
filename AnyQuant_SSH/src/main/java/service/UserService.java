package service;

import entity.UserEntity;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/4
 */

public interface UserService {

    /**
     * 检查用户名是否已存在
     * @param name
     * @return
     */
    boolean checkIfUserNameExist(String name);
    /**
     * 增加新用户
     * @param us
     */
    void saveUser(UserEntity us);


    List<UserEntity> getAllUsernames();

    /**
     * 检查用户是否为有效用户
     * @param u 用户信息
     * @return 如果有效返回其ID,否则返回NUll
     */
    String checkIfValid(UserEntity u);

    /**
     * 修改密码
     * @param user 用户原来的信息,必须校验原密码是否正确
     * @return success or not
     */
    boolean changePassword(UserEntity user , String newPassword);
}

