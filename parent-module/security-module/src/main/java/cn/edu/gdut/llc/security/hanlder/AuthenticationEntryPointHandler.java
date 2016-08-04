/**
 * Create at 2016年6月23日 by cpt725@qq.com
 */
package cn.edu.gdut.llc.security.hanlder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import cn.edu.gdut.llc.share.response.ErrorCode;
import cn.edu.gdut.llc.share.response.ResponseMessage;
import cn.edu.gdut.llc.share.util.AutoLogger;

/**
 * @author cpt725@qq.com
 *
 */
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
	@AutoLogger
	private static Logger logger;
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.debug("AuthenticationEntryPointHandler-commence");
		ResponseMessage responseMessage=new ResponseMessage();
		responseMessage.setStatus(false);
		responseMessage.setErrorCode(ErrorCode.Deny);
		responseMessage.setMessage("访问被禁止！");
		
		response.setContentType("application/json");
		response.setStatus(200);
		response.getWriter()
				.print(responseMessage.getJsonStr());

		response.getWriter().flush();
	}

}
