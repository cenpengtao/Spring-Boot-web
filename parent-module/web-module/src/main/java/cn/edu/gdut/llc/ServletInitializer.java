/**
 * Create at 2016年5月29日 by cpt725@qq.com
 */
package cn.edu.gdut.llc;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * @author cpt725@qq.com
 * 打包成war需要
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WebLauncher.class);  
	}

}
