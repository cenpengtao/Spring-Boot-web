/**
 * 
 */
package cn.edu.gdut.llc.share.service;

import java.util.List;

import cn.edu.gdut.llc.mybatis.model.Login;
import cn.edu.gdut.llc.mybatis.model.LoginRecord;

/**
 * 登录信息服务接口
 * @author cpt725
 *
 */
public interface LoginService {
	/**
	 * 根据用户名获取登录信息
	 * @param username 用户名
	 * @return
	 */
	public Login getLoginByUsername(String username);

	/**
	 * 根据编号更新登录信息
	 * @param info
	 */
	public boolean updateLogin(Login info);

	/**
	 * 根据登录账号（手机、邮箱或登录名）获取登录信息
	 * @param account 手机、邮箱或登录名
	 * @return
	 */
	public Login getLoginByAccount(String account);

	/**
	 * 添加登录记录
	 * @param loginRecord
	 * @return
	 */
	public boolean insertLoginRecord(LoginRecord loginRecord);

	/**
	 * 根据编用户名获取登录记录
	 * @param username 用户名
	 * @return
	 */
	public List<LoginRecord> getLoginRecordByUsername(String username);

	/**
	 * 根据微信code获取登录信息
	 * @param code 微信登陆连接包含的参数code
	 * @return
	 * @throws Exception 
	 */
	public Login getLoginByWeixin(String code) throws Exception;
	
}
