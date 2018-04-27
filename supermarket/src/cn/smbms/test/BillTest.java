package cn.smbms.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smbms.dao.bill.BillDao;
import cn.smbms.pojo.Bill;
import cn.smbms.service.bill.BillService;

class BillTest {
	private ClassPathXmlApplicationContext ctx;
	@Test
	void testBillAdd() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		BillService billService=ctx.getBean(BillService.class);
		Bill bill=new Bill();
		bill.setBillCode("dddd");
		bill.setProductName("feefe");
		bill.setProductDesc("mmnvmn");
		bill.setProductUnit("oooo");
		bill.setProductCount(new BigDecimal("4"));
		bill.setTotalPrice(new BigDecimal(5));
		bill.setIsPayment(1);
		bill.setProviderId(1);
		bill.setCreatedBy(1);
		bill.setCreationDate(new Date());
		boolean bl=false;
		try {
			bl=billService.add(bill);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(bl);
	}
	@Test
	void testBillQuery() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		BillService billService=ctx.getBean(BillService.class);
		Bill bill=new Bill();
		bill.setProductName("");
		bill.setIsPayment(0);
		bill.setProviderId(10);
		List<Bill> list =new ArrayList<Bill>();
		try {
			list=billService.getBillList(bill);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Bill li:list) {
			System.out.println(li);
		}
	}
	@Test
	void testBillQuery1() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		BillService billService=ctx.getBean(BillService.class);
		Bill bill=new Bill();
		try {
			bill=billService.getBillById("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(bill);
	}
	@Test
	void testBillModify() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		BillService billService=ctx.getBean(BillService.class);
		Bill bill=new Bill();
		bill.setProductName("tttttt");
		bill.setId(20);
		boolean bl=false;
		try {
			bl=billService.modify(bill);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(bl);
	}
	@Test
	void testBillDelete() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		BillService billService=ctx.getBean(BillService.class);
		boolean bl=false;
		try {
			bl=billService.deleteBillById("20");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(bl);
	}
	@Test
	void testBillGetCount() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		BillDao billDao=ctx.getBean(BillDao.class);
		int bl=0;
		try {
			bl=billDao.getBillCountByProviderId("14");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(bl);
	}
}
