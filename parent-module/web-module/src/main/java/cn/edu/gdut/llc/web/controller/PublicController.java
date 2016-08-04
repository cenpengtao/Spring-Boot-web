/**
 * 
 */
package cn.edu.gdut.llc.web.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.gdut.llc.mybatis.model.Login;
import cn.edu.gdut.llc.share.service.LoginService;
import cn.edu.gdut.llc.share.util.AutoLogger;
import cn.edu.gdut.llc.share.util.CreateIdentifyCode;
import cn.edu.gdut.llc.share.util.CreateIdentifyCode.CodeType;

/**
 * 
 * 公共接口
 * @author cpt725
 *
 */
@Controller
public class PublicController {
	@AutoLogger
	private static Logger logger;
	@Autowired
	@Qualifier("loginServiceImpl")
	private LoginService loginService;
	@Autowired 
	private CreateIdentifyCode createIdentifyCode;
	/**
	 * 获取主页
	 * @param principal
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String index()
	{
		logger.debug("index");
		return "index";
	}
	/**
	 * 获取禁止页面
	 * @return
	 */
	@RequestMapping("/forbid")
	public String forbid()
	{
		logger.debug("forbid");
		return "forbid";
	}	
	/**
	 * 获取登录页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/login.action")
	public String login()
	{
		logger.debug("login");
		return "login";
	}
	/**
	 * 获取图片验证码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getIdentifyCode")
	public void getIdentifyCode(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		logger.debug("getIdentifyCode");
		String code=createIdentifyCode.getCodeString(6,CodeType.NumberCode);
		BufferedImage bi=createIdentifyCode.getIdentifyCode(120,30,code);
		request.getSession().setAttribute("validateCode", code);
		logger.debug("getIdentifyCode-validateCode:"+code);
		response.setContentType("image/jpeg");//等同于response.setHeader("Content-Type", "image/jpeg");
		response.setDateHeader("expries", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		ImageIO.write(bi, "jpg", response.getOutputStream());
	}
	/**
	 * 微信登陆页面
	 * @param principal
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/weixin/login.action")
	public String weixinLogin(Principal principal,
			HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		logger.debug("weixinLogin");
		
		//已经登录直接返回
		 if(principal!=null)
			 response.sendRedirect("/");
		 
		String code= request.getParameter("code");
		String state=request.getParameter("state");
		logger.debug("code:"+code+" state:"+state);
        Login login=loginService.getLoginByWeixin(code);
		 String weixinCode= UUID.randomUUID().toString();
		 logger.debug("weixinLogin-weixinCode:"+weixinCode);
		 
		 request.getSession().setAttribute("weixinCodeBack", weixinCode);
		 request.getSession().setAttribute("isWeixinLogin", true);
	
		response.sendRedirect("/weixinlogin.html?username="+login.getUsername()+"&weixinCode="+weixinCode);
		return null;
	}
}
