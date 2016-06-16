package controller;

import entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.StrategyService;
import service.UserService;
import util.Configure;
import vo.ReportVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Qiang
 * @date 16/5/4
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StrategyService strategyService;


    @RequestMapping("/json")
    @ResponseBody
    public List<UserEntity> json() {
        return userService.getAllUsernames();
    }

    @RequestMapping(value = "/checkIfLogin")
    @ResponseBody
    public boolean checkIfLogin(HttpServletRequest request) {
        return request.getSession().getAttribute(Configure.USERID_KEY) != null;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, String username, String password, String addNewUser) {
        UserEntity userEntity = new UserEntity();


        userEntity.setName(username);
        userEntity.setPassword(password);

        String id;
        if ((id = userService.checkIfValid(userEntity)) != null) {
            //将用户的ID 写入到 Session中
            request.getSession().setAttribute(Configure.USERID_KEY, id);
            System.out.print("用户ID为" + request.getSession().getAttribute(Configure.USERID_KEY));
            return "redirect:/html/duck_main.html";
        } else {
            return "/index.jsp";
        }
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Boolean addNewUser(String userName, String password) {
        if (userService.checkIfUserNameExist(userName)) {
            return false;
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(userName);
            userEntity.setPassword(password);
            userService.saveUser(userEntity);

        }


        return true;
    }


    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public boolean changePassword(HttpServletRequest request, @RequestParam("old") String old, @RequestParam("new") String newPassword) {
        String id = (String) request.getSession().getAttribute(Configure.USERID_KEY);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(Integer.parseInt(id));
        userEntity.setPassword(old);
        return userService.changePassword(userEntity, newPassword);
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().setAttribute(Configure.USERID_KEY, null);
    }

    @RequestMapping("/getAllReports")
    @ResponseBody
    public List<ReportVO> getAllReports(HttpServletRequest request) {
        if (checkIfLogin(request)) {
            return strategyService.getAllReports((String) request.getSession().getAttribute(Configure.USERID_KEY));
        } else {
            return null;
        }
    }

    @RequestMapping("/getUserName")
    @ResponseBody
    public String getUserName(HttpServletRequest request){
        if(checkIfLogin(request)){
            int id = Integer.valueOf((String)request.getSession().getAttribute(Configure.USERID_KEY));
            List<UserEntity> users = userService.getAllUsernames();
            for (UserEntity entity : users){
                if(entity.getId() == id){
                    return entity.getName();
                }
            }
        }

        return null;

    }


}
