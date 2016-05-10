package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Qiang
 * @date 16/5/9
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/changePassword" , method = RequestMethod.POST)
    public boolean changePassword(){

        return false;
    }



}
