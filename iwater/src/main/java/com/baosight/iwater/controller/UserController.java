package com.baosight.iwater.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baosight.iwater.pojo.User;
import com.baosight.iwater.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;
	
	/**
	 * 返回json数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/showUser1/{id}")
	public @ResponseBody User toIndex(@PathVariable String id){
		int userId = Integer.parseInt(id);
		User user = this.userService.getUserById(userId);
		return user;
	}
	
	/**
	 * 返回html页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/showUser2/{id}")
	public String toIndex2(Model model,@PathVariable String id){
		int userId = Integer.parseInt(id);
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUser";
	}
	
	/**
	 * 获得请求参数
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/showUser3")
	public String toIndex3(Model model,@RequestParam("id") int userId){
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUser";
	}
	
	/**
	 * 获得request对象
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/showUser4")
	public String toIndex4(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUser";
	}
}
