package cn.smbms.controller.bill;

import java.util.ArrayList;
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

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.tools.Constants;

@Controller
@RequestMapping("/bill/jsp")
public class BillController {
	@Autowired
	private BillService billService;
	@Autowired
	private ProviderService providerService;
	@RequestMapping("/query")
	public ModelAndView query(String queryProductName,String queryProviderId,String queryIsPayment){
		ModelAndView mv=new ModelAndView();
		List<Provider> providerList = new ArrayList<Provider>();
		providerList = providerService.getProviderList("","");
		mv.addObject("providerList", providerList);
		if(StringUtils.isNullOrEmpty(queryProductName)){
			queryProductName = "";
		}	
		List<Bill> billList = new ArrayList<Bill>();
		Bill bill = new Bill();
		if(StringUtils.isNullOrEmpty(queryIsPayment)){
			bill.setIsPayment(0);
		}else{
			bill.setIsPayment(Integer.parseInt(queryIsPayment));
		}
		
		if(StringUtils.isNullOrEmpty(queryProviderId)){
			bill.setProviderId(0);
		}else{
			bill.setProviderId(Integer.parseInt(queryProviderId));
		}
		bill.setProductName(queryProductName);
		billList = billService.getBillList(bill);
		System.out.println("billListSize:"+billList.size());
		mv.addObject("billList", billList);
		mv.addObject("queryProductName", queryProductName);
		mv.addObject("queryProviderId", queryProviderId);
		mv.addObject("queryIsPayment", queryIsPayment);
		mv.setViewName("/jsp/billlist.jsp");
		return mv;
	}
	@RequestMapping("/add")
	public ModelAndView add(Bill bill,HttpSession session){	
		ModelAndView mv=new ModelAndView();
		bill.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		bill.setCreationDate(new Date());
		boolean flag = false;
		flag = billService.add(bill);
		System.out.println("add flag -- > " + flag);
		if(flag){
			mv.setViewName("redirect:/bill/jsp/query.do");
		}else{
			mv.setViewName("/jsp/billadd.jsp");
		}
		return mv;
	}
	@RequestMapping("/view")
	public ModelAndView getBillById(String billid,@RequestParam(value="method",required=false)String method){
		ModelAndView mv=new ModelAndView();
		if(!StringUtils.isNullOrEmpty(billid)){
			Bill bill = null;
			bill = billService.getBillById(billid);
			mv.addObject("bill", bill);
			if(method!=null &&method.equals("modify")) {
				mv.setViewName("/jsp/billmodify.jsp");
			}else {
				mv.setViewName("/jsp/billview.jsp");
			}
		}
		return mv;
	}
	@RequestMapping("/modifysave")
	public ModelAndView modify(Bill bill,HttpSession session){
		ModelAndView mv=new ModelAndView();
		System.out.println("modify===============");			
		bill.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		bill.setModifyDate(new Date());
		boolean flag = false;
		flag = billService.modify(bill);
		if(flag){
			mv.setViewName("redirect:/bill/jsp/query.do");
			//response.sendRedirect(request.getContextPath()+"/jsp/bill.do?method=query");
		}else{
			mv.setViewName("/jsp/billmodify.jsp");
		}
		return mv;
	}
	@RequestMapping("/delbill")
	@ResponseBody
	public Map<String, String> delBill(String billid){
		Map<String, String> resultMap = new HashMap<String, String>();
		if(!StringUtils.isNullOrEmpty(billid)){
			boolean flag = billService.deleteBillById(billid);
			if(flag){//删除成功
				resultMap.put("delResult", "true");
			}else{//删除失败
				resultMap.put("delResult", "false");
			}
		}else{
			resultMap.put("delResult", "notexit");
		}
		return resultMap;
	}
//	@RequestMapping(value="/getproviderlist", produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public Object getProviderlist(){	
//		System.out.println("getproviderlist ========================= ");		
//		List<Provider> providerList = new ArrayList<Provider>();
//		providerList = providerService.getProviderList("","");
//		System.out.println("providerList:"+providerList.size());
//		return JSONArray.toJSONString(providerList) ;
//	}
	@RequestMapping(value="/getproviderlist")
	@ResponseBody
	public List<Provider> getProviderlist(){	
		System.out.println("getproviderlist ========================= ");		
		List<Provider> providerList = new ArrayList<Provider>();
		providerList = providerService.getProviderList("","");
		System.out.println("providerList:"+providerList.size());
		return providerList ;
	}
}
