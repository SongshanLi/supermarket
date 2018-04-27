package cn.smbms.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.smbms.dao.user.UserDao;
import cn.smbms.pojo.User;

/**
 * service层捕获异常，进行事务处理
 * 事务处理：调用不同dao的多个方法，必须使用同一个connection（connection作为参数传递）
 * 事务完成之后，需要在service层进行connection的关闭，在dao层关闭（PreparedStatement和ResultSet对象）
 * @author Administrator
 *
 */
@Component
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	public int add(User user) {
		int bl = 0;
		try {
			bl=userDao.add(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bl;
	}
	@Override
	public User login(String userCode, String userPassword) {
		User user = null;
		try {
			user = userDao.getLoginUser(userCode).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//匹配密码
		if(null != user){
			if(!user.getUserPassword().equals(userPassword))
				user = null;
		}
		
		return user;
	}
	@Override
	public List<User> getUserList(String queryUserName,Integer queryUserRole,int currentPageNo, int pageSize) {
		List<User> userList = null;
		try {
			userList = userDao.getUserList(queryUserName,queryUserRole,currentPageNo-1,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
	@Override
	public List<User> selectUserCodeExist(String userCode) {
		List<User> list=new ArrayList<User>();
		try {
			list = userDao.getLoginUser(userCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public boolean deleteUserById(Integer delId) {
		boolean flag = false;
		try {
			if(userDao.deleteUserById(delId) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public User getUserById(String id) {
		User user = null;
		try{
			user = userDao.getUserById(id);
		}catch (Exception e) {
			e.printStackTrace();
			user = null;
		}
		return user;
	}
	@Override
	public boolean modify(User user) {
		boolean flag = false;
		try {
			if(userDao.modify(user) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public boolean updatePwd(int id, String pwd) {
		boolean flag = false;
		try{
			if(userDao.updatePwd(id,pwd) > 0)
				flag = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public int getUserCount(String queryUserName, int queryUserRole) {
		int count = 0;
		System.out.println("queryUserName ---- > " + queryUserName);
		System.out.println("queryUserRole ---- > " + queryUserRole);
		try {
			count = userDao.getUserCount(queryUserName,queryUserRole);
			System.out.println("count:"+count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
}
