package com.zyrc.housekeeper.controller.servlet.account;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zyrc.housekeeper.common.OnlineService;
import com.zyrc.housekeeper.model.entity.AccountEntity;
import com.zyrc.housekeeper.service.account.AccountServiceImpl;
import com.zyrc.housekeeper.service.account.AccountService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "Account")
public class AccountServlet {
	
	@Resource(name=AccountServiceImpl.NAME)
	AccountService accountService;

	@RequestMapping(value = "/index.do")
	public ModelAndView goIndex(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		AccountEntity account = (AccountEntity) request.getSession().getAttribute("account");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("usrName", account.getName());
		return new ModelAndView("index", map);
	}
	
	@RequestMapping(value = "/welcome.do")
	public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		if (!OnlineService.isOnline(request.getSession())){
			return new ModelAndView("redirect:/account/login.do");
		}else{
			return new ModelAndView("redirect:/account/index.do");
		}
	}

	@RequestMapping(value = "/validate.do")
	public ModelAndView validate(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{	
		if (!OnlineService.isOnline(request.getSession())){
			String name = request.getParameter("username");
			String psw = request.getParameter("password");
			AccountEntity account = accountService.validate(name, psw);
			if (null != account){
				OnlineService.goOnline(request);
				request.getSession().setAttribute("account", account);
				return new ModelAndView("redirect:/account/index.do");
			}else{
				return new ModelAndView("redirect:/account/login.do?error=failed");
			}
		}else{
			return new ModelAndView("redirect:/account/index.do");
		}
	}
	
	@RequestMapping(value = "/login.do")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		String error = request.getParameter("error");
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != error){
			map.put("showError", "visiable");
		}else{
			map.put("showError", "hidden");
		}
		return new ModelAndView("login", map);
	}
	
	@RequestMapping(value = "/logout.do")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		if (OnlineService.isOnline(request)){
			request.getSession().setAttribute("account", null);
			OnlineService.goOffline(request);
		}
		return new ModelAndView("redirect:/account/login.do");
	}
}
