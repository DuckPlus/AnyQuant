package DAO.impl;

import DAO.UserDAO;
import entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by 67534 on 2016/5/22.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        (locations = {"classpath:/META-INF/applicationContext.xml","classpath:/META-INF/infrastructure.xml"})

public class UserDAOImplTest {
    @Autowired
    UserDAO dao;

    @Test
    public void save() throws Exception {
        UserEntity entity = new UserEntity();
        entity.setName("ss");
        entity.setPassword("123");
        dao.save(entity);
        if(!dao.checkIfUsernameExist(entity.getName())){
            fail("save()---");
        }

    }

    @Test
    public void findAll() throws Exception {
        List<UserEntity> entities = dao.findAll();
        if(entities==null){
            fail();
        }else{
            System.out.println(entities.size()+" user records");
        }
    }

    @Test
    public void checkIfValid() throws Exception {

    }

    @Test
    public void checkIfUsernameExist() throws Exception {
        String name1 = "czq";
        String name2 = "usename10";
        if(!dao.checkIfUsernameExist(name1)){
            fail("name1");
        }else if(dao.checkIfUsernameExist(name2)){
            fail("name2");
        }
    }

    @Test
    public void changePassword() throws Exception {
        String id = "1";
        UserEntity entity =  dao.getUserEntityByID(id);
        String newpw = "hhh";

        System.out.println("before change:");
        System.out.println("name: "+entity.getName());
        System.out.println("password: "+entity.getPassword());

        dao.changePassword(entity,newpw);
        entity = dao.getUserEntityByID(entity.getId()+"");

        System.out.println("after change:");
        System.out.println("name: "+entity.getName());
        System.out.println("password: "+entity.getPassword());

    }

}