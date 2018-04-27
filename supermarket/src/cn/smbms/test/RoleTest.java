package cn.smbms.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smbms.pojo.Role;
import cn.smbms.service.role.RoleService;

class RoleTest {
	private ClassPathXmlApplicationContext ctx;
	@Test
	void test() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		RoleService rs=ctx.getBean(RoleService.class);
		List<Role> list=new ArrayList<Role>();
		list=rs.getRoleList();
		for(Role r:list) {
			System.out.println(r);
		}
	}

}
