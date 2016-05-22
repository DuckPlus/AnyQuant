package DAO;


import entity.UserEntity;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/4
 */
public interface UserDAO {
        void save(UserEntity u);
        UserEntity getUserEntityByID(String id);
        List<UserEntity> findAll();
        String checkIfValid(UserEntity u);

        boolean checkIfUsernameExist(String name);
        boolean changePassword(UserEntity user, String newPassword);
}
