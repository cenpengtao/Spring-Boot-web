/**
 * 
 */
package cn.edu.gdut.llc.share.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.gdut.llc.mybatis.mapper.LoginMapper;
import cn.edu.gdut.llc.mybatis.mapper.LoginRecordMapper;
import cn.edu.gdut.llc.mybatis.mapper.WeixinMapper;
import cn.edu.gdut.llc.mybatis.model.Login;
import cn.edu.gdut.llc.mybatis.model.LoginExample;
import cn.edu.gdut.llc.mybatis.model.LoginRecord;
import cn.edu.gdut.llc.mybatis.model.LoginRecordExample;
import cn.edu.gdut.llc.mybatis.model.Weixin;
import cn.edu.gdut.llc.share.dao.LoginDao;
import cn.edu.gdut.llc.share.util.AutoLogger;

/**
 * @author cpt725
 *
 */
@Repository
public class LoginDaoImpl implements LoginDao{
	@AutoLogger
	private static Logger logger;
	@Autowired
	private LoginMapper loginMapper;
	@Autowired
	private LoginRecordMapper loginRecordMapper;
	@Autowired
	private WeixinMapper weixinMapper;
	
	@Override
	public Login getLoginByUsername(String username) {
		LoginExample example=new LoginExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<Login> list=loginMapper.selectByExample(example);
		if(list.isEmpty())
			return null;
		else
			return list.get(0);
	}
	@Override
	public Login updateById(Login info) {
		loginMapper.updateByPrimaryKeySelective(info);
		return info;
	}
	@Override
	public Login getLoginByAccount(String account) {
		Login login=null; 
		LoginExample example=new LoginExample();
		example.createCriteria().andUsernameEqualTo(account);
		
		LoginExample.Criteria criteria1=example.createCriteria();
		criteria1.andPhoneEqualTo(account);
		
		LoginExample.Criteria criteria2=example.createCriteria();
		criteria2.andMailEqualTo(account);
		
		example.or(criteria1);
		example.or(criteria2);
		List<Login> list =loginMapper.selectByExample(example);
		if(!list.isEmpty())
			login=list.get(0);
		return login;
	}
	@Override
	public LoginRecord insertLoginRecord(LoginRecord loginRecord) {
		 loginRecordMapper.insert(loginRecord);
		 return loginRecord;
	}
	@Override
	public List<LoginRecord> getLoginRecordByUsername(String username) {
		LoginRecordExample example=new LoginRecordExample();
		example.createCriteria().andUsernameEqualTo(username);
		return loginRecordMapper.selectByExample(example);
	}
	@Override
	public Weixin getWeixinByWeixinOpenid(String openid) {
		return weixinMapper.selectByPrimaryKey(openid);
	}
	@Override
	public Login insertLogin(Login login) {
		loginMapper.insertSelective(login);
		return login;
	}
	@Override
	public Weixin insertWeixin(Weixin weixin) {
		weixinMapper.insertSelective(weixin);
		return weixin;
	}
	@Override
	public Weixin updateWeixin(Weixin weixin) {
		weixinMapper.updateByPrimaryKeySelective(weixin);
		return weixin;
	}

}
