/**
 * 
 */
package cn.edu.gdut.llc.web.controller.rest;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.gdut.llc.mybatis.model.Login;
import cn.edu.gdut.llc.mybatis.model.LoginRecord;
import cn.edu.gdut.llc.share.service.LoginService;
import cn.edu.gdut.llc.share.util.AutoLogger;

/**
 * 
 * 公共接口
 * @author cpt725
 *
 */
@RequestMapping("/customer")
@Secured({ "ROLE_CUSTOMER", "ROLE_ADMIN" })
@RestController
public class CustomerRestController {
	@AutoLogger
	private static Logger logger;
	@Autowired
	@Qualifier("loginServiceImpl")
	private LoginService loginService;
	
	@RequestMapping("/getLoginInfo")
	public Login getLoginInfo(Principal principal)
	{
		logger.debug("getLoginInfo");
		Login login=loginService.getLoginByUsername(principal.getName());
		login.setPassword(null);
		return login;
	}
	
	@RequestMapping("/getLoginRecord")
	public List<LoginRecord> getLoginRecord(Principal principal)
	{
		logger.debug("getLoginRecord");
		List<LoginRecord> list=loginService.getLoginRecordByUsername(principal.getName());
		return list;
	}
	
}
