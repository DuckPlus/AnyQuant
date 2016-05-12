package controller;

import entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.UserService;
import util.Configure;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Qiang
 * @date 16/5/9
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/changePassword" , method = RequestMethod.POST)
    public boolean changePassword(HttpServletRequest request , @RequestParam("old") String old , @RequestParam("new") String newPassword){
        String id = (String) request.getSession().getAttribute(Configure.USERID_KEY);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(Integer.parseInt(id));
        userEntity.setPassword(old);
        return userService.changePassword(userEntity , newPassword);
    }



}
