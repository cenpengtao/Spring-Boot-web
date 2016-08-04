/**
 * Create at 2016年5月29日 by cpt725@qq.com
 */
package cn.edu.gdut.llc.share.util;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
/**
 * 日志注解
 * @author cpt725@qq.com
 * 
 */
@Retention(RetentionPolicy.RUNTIME) //运行是编译
@Target( { ElementType.FIELD })  //修饰的字段
public @interface AutoLogger {

}
