package cn.smbms.controller.provider;

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

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.tools.Constants;

@Controller
@RequestMapping("/provider/jsp")
public class ProviderController {
	@Autowired
	private ProviderService providerService;
	@RequestMapping("/query")
	public ModelAndView query(String queryProName,String queryProCode){
		ModelAndView mv=new ModelAndView();
		if(StringUtils.isNullOrEmpty(queryProName)){
			queryProName = "";
		}
		if(StringUtils.isNullOrEmpty(queryProCode)){
			queryProCode = "";
		}
		List<Provider> providerList = new ArrayList<Provider>();
		providerList = providerService.getProviderList(queryProName,queryProCode);
		mv.addObject("providerList", providerList);
		mv.addObject("queryProName", queryProName);
		mv.addObject("queryProCode", queryProCode);
		mv.setViewName("/jsp/providerlist.jsp");
		return mv;
	}
	@RequestMapping("/add")
	public ModelAndView add(Provider provider,HttpSession session){
		ModelAndView mv=new ModelAndView();
		provider.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		provider.setCreationDate(new Date());
		boolean flag = false;
		flag = providerService.add(provider);
		if(flag){
			mv.setViewName("redirect:/provider/jsp/query.do");
			//response.sendRedirect(request.getContextPath()+"/jsp/provider.do?method=query");
		}else{
			mv.setViewName("/jsp/provideradd.jsp");
		}
		return mv;
	}
	@RequestMapping("/view")
	private ModelAndView getProviderById(String proid,@RequestParam(value="method",required=false)String method){
		ModelAndView mv=new ModelAndView();
		if(!StringUtils.isNullOrEmpty(proid)){
			Provider provider = null;
			provider = providerService.getProviderById(proid);
			mv.addObject("provider", provider);
			if(method!=null&&method.equals("modify")) {
				mv.setViewName("/jsp/providermodify.jsp");
			}else {
				mv.setViewName("/jsp/providerview.jsp");
			}
		}
		return mv;
	}
	@RequestMapping("modifysave")
	public ModelAndView modify(String proid,Provider provider,HttpSession session){
		ModelAndView mv=new ModelAndView();
		provider.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		provider.setModifyDate(new Date());
		provider.setId(Integer.parseInt(proid));
		boolean flag = false;
		flag = providerService.modify(provider);
		if(flag){
			mv.setViewName("redirect:/provider/jsp/query.do");
			//response.sendRedirect(request.getContextPath()+"/jsp/provider.do?method=query");
		}else{
			mv.setViewName("/jsp/providermodify.jsp");
		}
		return mv;
	}
	@RequestMapping("/delprovider")
	@ResponseBody
	public Map<String, String> delProvider(String proid){
		Map<String, String> resultMap = new HashMap<String, String>();
		if(!StringUtils.isNullOrEmpty(proid)){
			int flag = providerService.deleteProviderById(proid);
			if(flag == 0){//删除成功
				resultMap.put("delResult", "true");
			}else if(flag == -1){//删除失败
				resultMap.put("delResult", "false");
			}else if(flag > 0){//该供应商下有订单，不能删除，返回订单数
				resultMap.put("delResult", String.valueOf(flag));
			}
		}else{
			resultMap.put("delResult", "notexit");
		}
		return resultMap;
	}
}
