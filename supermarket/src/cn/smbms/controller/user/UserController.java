package cn.smbms.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.mysql.jdbc.StringUtils;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;

@Controller
@RequestMapping("/user/jsp")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@RequestMapping("/loginout")
	public ModelAndView userLoginout(HttpSession session) {
		ModelAndView mv=new ModelAndView();
		session.removeAttribute(Constants.USER_SESSION);
		mv.setViewName("redirect:/login.jsp");
		return mv;
	}
	@RequestMapping("/add")
	public ModelAndView userAdd(User user,HttpSession session) {
		ModelAndView mv=new ModelAndView();
		user.setCreationDate(new Date());
		user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		System.out.println(user);
		int bl=userService.add(user);
		if(bl==1) {
			mv.setViewName("query.do");
		}else {
			mv.setViewName("/jsp/useradd.jsp");
		}
		return mv;
	}
	@RequestMapping("/query")
	public ModelAndView userQuery(String queryname,String queryUserRole,String pageIndex) {
		ModelAndView mv=new ModelAndView();
		List<User> userList = null;
		int temp=0;
		//设置页面容量
    	int pageSize = Constants.pageSize;
    	//当前页码
    	int currentPageNo = 1;
    	if(queryname == null){
    		queryname = "";
		}
		if(queryUserRole != null && !queryUserRole.equals("")){
			temp = Integer.parseInt(queryUserRole);
		}
		
		if(pageIndex != null){
    		try{
    			currentPageNo = Integer.valueOf(pageIndex);
    		}catch(NumberFormatException e){
    			mv.setViewName("redirect:/error.jsp");
    		}
    	}	
    	//总数量（表）	
    	int totalCount	= userService.getUserCount(queryname,temp);
    	System.out.println("totalCount:"+totalCount);
    	//总页数
    	PageSupport pages=new PageSupport();
    	pages.setCurrentPageNo(currentPageNo);
    	pages.setPageSize(pageSize);
    	pages.setTotalCount(totalCount);
    	
    	int totalPageCount = pages.getTotalPageCount();
    	
    	//控制首页和尾页
    	if(currentPageNo < 1){
    		currentPageNo = 1;
    	}else if(currentPageNo > totalPageCount){
    		currentPageNo = totalPageCount;
    	}
    	System.out.println("currentPageNo:"+currentPageNo+",pageSize:"+pageSize);
		userList = userService.getUserList(queryname,temp,currentPageNo, pageSize);
		System.out.println("userListSize:"+userList.size());
		mv.addObject("userList", userList);
		List<Role> roleList = null;
		roleList = roleService.getRoleList();
		mv.addObject("roleList", roleList);
		mv.addObject("queryUserName", queryname);
		mv.addObject("queryUserRole", queryUserRole);
		mv.addObject("totalPageCount", totalPageCount);
		mv.addObject("totalCount", totalCount);
		mv.addObject("currentPageNo", currentPageNo);
		mv.setViewName("/jsp/userlist.jsp");
		return mv;
	}
	@RequestMapping("getrolelist")
	@ResponseBody
	public List<Role> getRoleList() {
		List<Role> roleList = null;
		roleList = roleService.getRoleList();
		return roleList;
	}
	@RequestMapping("ucexist")
	@ResponseBody
	public Map<String, String> userCodeExist(String userCode){		
		Map<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(userCode)){
			//userCode == null || userCode.equals("")
			resultMap.put("userCode", "exist");
		}else{
			List<User> list = userService.selectUserCodeExist(userCode);
			if(null != list && !list.isEmpty()){
				resultMap.put("userCode","exist");
			}else{
				resultMap.put("userCode", "notexist");
			}
		}
		return resultMap;
	}
	@RequestMapping("deluser")
	@ResponseBody
	public Map<String, String> delUser(String uid){
		Integer delId = 0;
		try{
			delId = Integer.parseInt(uid);
		}catch (Exception e) {
			delId = 0;
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		if(delId <= 0){
			resultMap.put("delResult", "notexist");
		}else{
			if(userService.deleteUserById(delId)){
				resultMap.put("delResult", "true");
			}else{
				resultMap.put("delResult", "false");
			}
		}
		return resultMap;
	}
	@RequestMapping("view")
	public ModelAndView getUserById(@RequestParam(value="method",required=false)String method,String uid){
		ModelAndView mv=new ModelAndView();
		if(!StringUtils.isNullOrEmpty(uid)){
			//调用后台方法得到user对象
			User user = userService.getUserById(uid);
			mv.addObject("user", user);
			if(method != null && method.equals("modify")) {
				mv.setViewName("/jsp/usermodify.jsp");
			}else {
				mv.setViewName("/jsp/userview.jsp");
			}
		}
		return mv;
	}
	@RequestMapping("modifyexe")
	public ModelAndView modify(String uid,User user,HttpSession session){	
		ModelAndView mv=new ModelAndView();
		user.setId(Integer.parseInt(uid));
		user.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		user.setModifyDate(new Date());
		if(userService.modify(user)){
			mv.setViewName("/user/jsp/query.do");
		}else{
			mv.setViewName("/jsp/usermodify.jsp");
		}
		return mv;
	}
	@RequestMapping("pwdmodify")
	@ResponseBody
	public Map<String, String> getPwdByUserId(HttpSession session,String oldpassword){
		Object o = session.getAttribute(Constants.USER_SESSION);
		Map<String, String> resultMap = new HashMap<String, String>();		
		if(null == o ){//session过期
			resultMap.put("result", "sessionerror");
		}else if(StringUtils.isNullOrEmpty(oldpassword)){//旧密码输入为空
			resultMap.put("result", "error");
		}else{
			String sessionPwd = ((User)o).getUserPassword();
			if(oldpassword.equals(sessionPwd)){
				resultMap.put("result", "true");
			}else{//旧密码输入不正确
				resultMap.put("result", "false");
			}
		}
		return resultMap;
	}
	@RequestMapping("savepwd")
	public ModelAndView updatePwd(HttpSession session,String newpassword){
		ModelAndView mv=new ModelAndView();
		Object o = session.getAttribute(Constants.USER_SESSION);
		boolean flag = false;
		if(o != null && !StringUtils.isNullOrEmpty(newpassword)){
			flag = userService.updatePwd(((User)o).getId(),newpassword);
			if(flag){
				mv.addObject(Constants.SYS_MESSAGE, "修改密码成功,请退出并使用新密码重新登录！");
				session.removeAttribute(Constants.USER_SESSION);//session注销
			}else{
				mv.addObject(Constants.SYS_MESSAGE, "修改密码失败！");
			}
		}else{
			mv.addObject(Constants.SYS_MESSAGE, "修改密码失败！");
		}
		mv.setViewName("/jsp/pwdmodify.jsp");
		return mv;
	}
}
