/**
 * Create at 2016年5月31日 by cpt725@qq.com
 */
package cn.edu.gdut.llc.security.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import cn.edu.gdut.llc.share.util.AutoLogger;

/**
 * @author cpt725@qq.com
 *
 */
public class SecurityUtil {
	@AutoLogger
	private static Logger logger;

	public static int getTimeout(String timeStr) {
		int time=302400;
		try {
			time= Integer.parseInt(timeStr);
		} catch (Exception e) {
			logger.warn("login.timeout set error,will use default time:302400!!! ");
		}
		return time;
	}
	
	public static String getIpAddress(HttpServletRequest request){     
		
        String ip= request.getHeader("x-forwarded-for");
        if(ip== null|| ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {     
        	ip= request.getRemoteAddr();     
        }
        if(ip== null|| ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {      
        	ip= request.getHeader("Proxy-Client-IP");     
        }      
        if(ip==null|| ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
        	ip= request.getHeader("WL-Proxy-Client-IP");     
        }      
        if(ip== null|| ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {      
        	ip= request.getHeader("HTTP_CLIENT_IP");   
        }      
        if(ip== null|| ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {      
        	ip= request.getHeader("HTTP_X_FORWARDED_FOR");     
        }      
        return ip;
    }   
	
}
