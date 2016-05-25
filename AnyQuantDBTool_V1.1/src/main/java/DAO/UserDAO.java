package DAO;


import entity.UserEntity;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/4
 */
public interface UserDAO {
        void save(UserEntity u);
        List<UserEntity> findAll();
        String checkIfValid(UserEntity u);
}
