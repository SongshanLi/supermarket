package cn.smbms.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	@RequestMapping("login")
	public ModelAndView userLogin(String userCode,String userPassword,HttpSession session) {
		ModelAndView mv=new ModelAndView();
		User user=userService.login(userCode, userPassword);
		if(null!=user) {
			session.setAttribute(Constants.USER_SESSION, user);
			mv.setViewName("redirect:/jsp/frame.jsp");
		}else {
			mv.addObject("error","用户名或密码不正确");
			mv.setViewName("/login.jsp");
		}
		return mv;
	}
}
