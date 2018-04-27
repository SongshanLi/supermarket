package cn.smbms.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smbms.pojo.Provider;
import cn.smbms.service.provider.ProviderService;

class ProviderTest {
	private ClassPathXmlApplicationContext ctx;
	@Test
	void testProviderAdd() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ProviderService ps=ctx.getBean(ProviderService.class);
		Provider pro=new Provider();
		pro.setProCode("gagagag");
		pro.setProName("rrrr");
		pro.setProDesc("weewe");
		pro.setProContact("ssss");
		pro.setProPhone("12345454");
		pro.setProAddress("tianjian");
		pro.setProFax("eeee");
		pro.setCreatedBy(1);
		pro.setCreationDate(new Date());
		boolean bl=ps.add(pro);
		System.out.println(bl);
	}
	@Test
	void testProviderQuery() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ProviderService ps=ctx.getBean(ProviderService.class);
		List<Provider> list=new ArrayList<Provider>();
		list=ps.getProviderList("", "");
		for(Provider p:list) {
			System.out.println(p);
		}
	}
	@Test
	void testProviderQuery1() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ProviderService ps=ctx.getBean(ProviderService.class);
		Provider pro=new Provider();
		pro=ps.getProviderById("1");
		System.out.println(pro);
	}
	@Test
	void testProviderUpdate() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ProviderService ps=ctx.getBean(ProviderService.class);
		Provider pro=new Provider();
		pro.setProName("lily");
		pro.setId(19);
		boolean bl=false;
		bl=ps.modify(pro);
		System.out.println(bl);
	}
	@Test
	void testProviderDelete() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ProviderService ps=ctx.getBean(ProviderService.class);
		int bl=0;
		bl=ps.deleteProviderById("19");
		System.out.println(bl);
	}
}
