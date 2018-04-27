package cn.smbms.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;

class UserTest {

	private ClassPathXmlApplicationContext ctx;
	@Before
	public void init(){
	}
	@Test
	public void testAddUser(){
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService us = ctx.getBean(UserService.class);
		User user=new User();
		user.setAddress("上海市");
		//user.setBirthday(new Date());
		user.setCreatedBy(1);
		user.setCreationDate(new Date());
		user.setGender(1);
		user.setUserCode("fff");
		user.setUserName("tian");
		user.setUserPassword("123456");
		user.setUserRole(1);
		user.setPhone("123455");
		int bl=0;
		try {
			bl=us.add(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(bl);
	}
	@Test
	public void testUserQuery() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService us = ctx.getBean(UserService.class);
		User user=new User();
		try {
			user=us.login("admin","1234567");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}
	@Test
	public void testUserQuery1() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService us = ctx.getBean(UserService.class);
		List<User> list=new ArrayList<User>();
		try {
			list=us.getUserList("", 0, 1, 5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(User u:list) {
			System.out.println(u);
		}
	}
	@Test
	public void testUserQuery2() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService us = ctx.getBean(UserService.class);
		int res=0;
		try {
			res=us.getUserCount("", 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(res);
	}
	@Test
	public void testUserQuery3() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService us = ctx.getBean(UserService.class);
		User user=new User();
		try {
			user=us.getUserById("15");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}
	@Test
	public void testUserDelete() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService us = ctx.getBean(UserService.class);
		boolean bl=false;
		try {
			bl=us.deleteUserById(19);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(bl);
	}
	@Test
	public void testUserup() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService us = ctx.getBean(UserService.class);
		User user =new User();
		user.setUserName("abcde");
		user.setId(18);
		boolean bl=false;
		try {
			bl=us.modify(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(bl);
	}
	@Test
	public void testUserup1() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService us = ctx.getBean(UserService.class);
		boolean bl=false;
		try {
			bl=us.updatePwd(18, "65321");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(bl);
	}
}
