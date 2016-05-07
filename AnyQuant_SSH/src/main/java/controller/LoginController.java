package controller;

import entity.UserEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Qiang
 * @date 16/5/4
 */
@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionFactory sessionFactory;

//    public MainController(UserService userService) {
//        this.userService = userService;

//    }

    @RequestMapping("")
    public String index(){
        return "/index";
    }

    @RequestMapping("/json")
    @ResponseBody
    public List<UserEntity> json(){

        return userService.getAllUsernames();
    }



    @RequestMapping(value="/login",method= RequestMethod.POST)
    public String login(String username,String password , String addNewUser){
        System.out.print(addNewUser);
        UserEntity userEntity =  new UserEntity();
        userEntity.setId(Integer.parseInt(username));
        userEntity.setName(password);
        if(addNewUser!= null && addNewUser.equals("on")){


            userService.saveUser(userEntity);
            return "/index";
        }else {
            if(userService.checkIfValid(userEntity)){
                return "/welcome";
            }else {
                return "/index";
            }
        }

    }


}
