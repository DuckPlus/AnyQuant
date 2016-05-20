package service.impl;

import DAO.UserDAO;
import entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.UserService;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/4
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDAO userDao;


    @Override
    public boolean checkIfUserNameExist(String name) {
        return userDao.checkIfUsernameExist(name);
    }
    @Override
    public void saveUser(UserEntity us) {
        userDao.save(us);
    }
    @Override
    public List<UserEntity> getAllUsernames() {
        return userDao.findAll();
    }

    @Override
    public String checkIfValid(UserEntity u) {
        return userDao.checkIfValid(u);
    }

    @Override
    public boolean changePassword(UserEntity user, String newPassword) {
        return userDao.changePassword(user , newPassword);
    }
}
