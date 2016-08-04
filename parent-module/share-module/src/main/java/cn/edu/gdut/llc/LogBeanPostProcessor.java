/**
 * Create at 2016年5月29日 by cpt725@qq.com
 */
package cn.edu.gdut.llc;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import cn.edu.gdut.llc.share.util.AutoLogger;
/**
 * @author cpt725@qq.com
 *
 */
@Component
public class LogBeanPostProcessor implements BeanPostProcessor {
	private static final Logger logger = LoggerFactory.getLogger(LogBeanPostProcessor.class);

	public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
		return arg0;
	}

	public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
   	 List<Class<?>> clazzes = getAllClasses(arg0);
   	 for (Class<?> clazz : clazzes) {
   		 initializeLog(arg0, clazz);
   	 }
   	 return arg0;
	}

    private void initializeLog(Object bean, Class<? extends Object> clazz) {
    	Field[] fiels = clazz.getDeclaredFields();
    	for (Field field : fiels) {
    	   if (field.getAnnotation(AutoLogger.class) == null) {
    	    continue;
    	   }
    	   if (!field.getType().isAssignableFrom(Logger.class)) {
    	    continue;
    	   }
    	   boolean visable = field.isAccessible();
    	   try {
	    	    field.setAccessible(true);
	    	    field.set(bean, LoggerFactory.getLogger(clazz));
    	   } catch (Exception e) {
    		   logger.error("初始化logger失败!bean=%s;field=%s", bean, field);
    	   } finally {
    		   // 恢复原来的访问修饰
    		   field.setAccessible(visable);
    	   }
    	}
    }
    private List<Class<?>> getAllClasses(Object bean) {
    	 Class<? extends Object> clazz = bean.getClass();
    	 List<Class<?>> clazzes = new ArrayList<Class<?>>();
    	 while (clazz != null) {
    		 clazzes.add(clazz);
    		 clazz = clazz.getSuperclass();
    	 }
    	 Collections.reverse(clazzes);
    	 return clazzes;
    }
}
