/**
 * Create at 2016年5月31日 by cpt725@qq.com
 */
package cn.edu.gdut.llc.security.hanlder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import cn.edu.gdut.llc.share.response.ErrorCode;
import cn.edu.gdut.llc.share.response.ResponseMessage;
import cn.edu.gdut.llc.share.util.AutoLogger;
/**
 * @author cpt725@qq.com
 *
 */
@Component
public class AccessDeniedJsonHanlder extends AccessDeniedHandlerImpl {
	@AutoLogger
	private static Logger logger;

	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		logger.debug("AccessDeniedJsonHanlder-handle");
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
