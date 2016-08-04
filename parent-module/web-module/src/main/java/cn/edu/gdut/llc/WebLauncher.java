/**
 * Create at 2016年5月28日 by cpt725@qq.com
 */
package cn.edu.gdut.llc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 程序入口
 * @author cpt725@qq.com
 *
 */
@SpringBootApplication
public class WebLauncher
{
	private static final Logger logger = LoggerFactory.getLogger(WebLauncher.class);

    public static void main( String[] args )
    {
    	logger.info("Starting!");
    	SpringApplication.run(WebLauncher.class, args);
    	
    }

}
