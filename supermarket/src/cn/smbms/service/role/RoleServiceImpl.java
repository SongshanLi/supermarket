package cn.smbms.service.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.smbms.dao.role.RoleDao;

import cn.smbms.pojo.Role;
@Component
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleDao roleDao;
	
	public List<Role> getRoleList() {
		List<Role> li = null;
		try {
			li = roleDao.getRoleList();
			System.out.println("reloList---size:"+li.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return li;
	}
}
