package controller;

import entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;
import util.Configure;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Qiang
 * @date 16/5/4
 */
@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    private UserService userService;


    @RequestMapping("")
    public String index(){
        return "/index";
    }

//    @RequestMapping("/json")
//    @ResponseBody
//    public List<UserEntity> json(){
//
//        return userService.getAllUsernames();
//    }



    @RequestMapping(value="/login",method= RequestMethod.POST)
    public String login(HttpServletRequest request, String username, String password , String addNewUser){
        UserEntity userEntity =  new UserEntity();

        userEntity.setName(username);
        userEntity.setPassword(password);
        if(addNewUser!= null && addNewUser.equals(Configure.BUTTON_CHOSEN)){


            userService.saveUser(userEntity);
            return "/index";
        }else {
            String id;
            if((id = userService.checkIfValid(userEntity) ) != null){
                //将用户的ID 写入到 Session中
                request.getSession().setAttribute(Configure.USERID_KEY, id);
                return "/welcome";
            }else {
                return "/index";
            }
        }

    }


}
