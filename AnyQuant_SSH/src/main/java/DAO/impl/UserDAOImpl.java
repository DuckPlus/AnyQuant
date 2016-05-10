package DAO.impl;

import DAO.BaseDAO;
import DAO.UserDAO;
import entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/4
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private BaseDAO baseDao;


    public void save(UserEntity u) {
        baseDao.save(u);
    }

    public List<UserEntity> findAll() {

        return (List<UserEntity>) baseDao.getAllList(UserEntity.class);

    }

    @Override
    public String checkIfValid(UserEntity u) {
//        UserEntity entity = (UserEntity) baseDao.load(UserEntity.class , u.getId());
//        if(entity.getName().equals(u.getName())){
//            return true;
//        }else {
//            return false;
//        }
        return null;
    }
}
