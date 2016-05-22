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

    private static String tableName = UserEntity.class.getName();

    @Override
    public void save(UserEntity u)
    {
        baseDao.save(u);
    }

    @Override
    public UserEntity getUserEntityByID(String id) {
        return (UserEntity) baseDao.load(UserEntity.class, Integer.parseInt(id));
    }


    @Override
    public List<UserEntity> findAll()
    {
        return (List<UserEntity>) baseDao.getAllList(UserEntity.class);
    }


    @Override
    public String checkIfValid(UserEntity u)
    {
        List<UserEntity> entity =
                (List<UserEntity>) baseDao.find("name" , u.getName() , UserEntity.class);

        if(entity != null && entity.size() != 0){
            if(entity.get(0).getPassword().equals(u.getPassword())){
                return String.valueOf(entity.get(0).getId());
            }


        }

        return null;
    }

    @Override
    public boolean checkIfUsernameExist(String name)
    {
        String hql = "select count(*) from "+tableName+" where name = '"+name+"'";
        long result = baseDao.countByHQL(hql);
        System.out.println("result: "+result);
        return result==0 ? false: true ;
    }

    @Override
    public boolean changePassword(UserEntity user, String newPassword)
    {
        if(!checkIfUsernameExist(user.getName())){
            return false;
        }else{
            UserEntity entity = new UserEntity();
            entity.setId(user.getId());
            entity.setName(user.getName());
            entity.setPassword(newPassword);
            baseDao.update(entity);
            return true;
        }

    }
}
