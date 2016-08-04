/**
 * Create at 2016年5月31日 by cpt725@qq.com
 */
package cn.edu.gdut.llc.security.hanlder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import cn.edu.gdut.llc.share.response.ErrorCode;
import cn.edu.gdut.llc.share.response.ResponseMessage;
import cn.edu.gdut.llc.share.util.AutoLogger;

/**
 * @author cpt725@qq.com
 *
 */
@Component
public class AuthenticationFailureJsonHandler implements AuthenticationFailureHandler {
	@AutoLogger
	private static Logger logger;
	
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		logger.debug(exception.getClass().getSimpleName()+"-"+exception.getMessage());
		String message="未知错误！";

		switch (exception.getClass().getSimpleName()) {
		case "UsernameNotFoundException":
			message="用户名不存在！";
			break;
		case "BadCredentialsException":
			message="用户名或密码错误！";
			break;
		case "AccountExpiredException":
			message="账户已过期！";
			break;
		case "LockedException":
			message="账户已锁定！";
			break;
		case "DisabledException":
			message="账户不可用！";
			break;
		default:message="未知错误！";
			break;
		}
		
		ResponseMessage responseMessage=new ResponseMessage();
		responseMessage.setStatus(false);
		responseMessage.setErrorCode(ErrorCode.UsernameOrPasswordError);
		responseMessage.setMessage(message);
		System.out.println(responseMessage.getJsonStr());
		response.setContentType("application/json");
		response.setStatus(200);
		response.getWriter()
				.print(responseMessage.getJsonStr());
		response.getWriter().flush();

	}
}
