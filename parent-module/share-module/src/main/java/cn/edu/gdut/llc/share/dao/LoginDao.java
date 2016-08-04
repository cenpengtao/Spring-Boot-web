/**
 * 
 */
package cn.edu.gdut.llc.share.dao;

import java.util.List;

import cn.edu.gdut.llc.mybatis.model.Login;
import cn.edu.gdut.llc.mybatis.model.LoginRecord;
import cn.edu.gdut.llc.mybatis.model.Weixin;

/**
 * 登录信息持久层
 * @author cpt725
 *
 */
public interface LoginDao {

	/**
	 * 根据用户名获取登录信息
	 * @param username 用户名
	 * @return
	 */
	Login getLoginByUsername(String username);

	/**
	 * 根据编号更新登录信息
	 * @param info
	 * @return
	 */
	Login updateById(Login info);

	/**
	 * 根据登录账号（手机、邮箱或登录名）获取登录信息
	 * @param account 手机、邮箱或登录名
	 * @return
	 */
	Login getLoginByAccount(String account);

	/**
	 * 添加登录记录
	 * @param loginRecord
	 */
	LoginRecord insertLoginRecord(LoginRecord loginRecord);

	/**
	 * 根据用户名获取登录记录
	 * @param username 用户名
	 * @return
	 */
	List<LoginRecord> getLoginRecordByUsername(String username);

	/**
	 * 根据微信openid获取微信资料
	 * @param openid
	 * @return
	 */
	Weixin getWeixinByWeixinOpenid(String openid);

	/**
	 * 插入新登录信息
	 * @param login
	 * @return
	 */
	Login insertLogin(Login login);

	/**
	 * 插入微信用户信息
	 * @param weixin
	 * @return
	 */
	Weixin insertWeixin(Weixin weixin);

	/**
	 * 更新微信用户信息
	 * @param weixin
	 * @return
	 */
	Weixin updateWeixin(Weixin weixin);

}
