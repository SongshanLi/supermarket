package cn.smbms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.smbms.pojo.User;

public class SysInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest rq, HttpServletResponse rp, Object arg2) throws Exception {
		System.out.println("SysInterceptor===============");
		User userSession = (User)rq.getSession().getAttribute("userSession");
		System.out.println(userSession);
		if(null == userSession){
			rp.sendRedirect(rq.getContextPath()+"/error.jsp");
			return false;
		}
		return true;
	}

}
