/**
 * Create at 2016年6月23日 by cpt725@qq.com
 */
package cn.edu.gdut.llc.security.hanlder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @author cpt725@qq.com
 *
 */
@Component
public class LogoutSuccessJsonHandler implements LogoutSuccessHandler {
	@Autowired
	private Environment env;
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String url=env.getProperty("logout.success.url");
		response.sendRedirect(url);
	}

}
