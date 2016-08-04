/**
 * 
 */
package cn.edu.gdut.llc.share.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.gdut.llc.mybatis.model.Login;
import cn.edu.gdut.llc.mybatis.model.LoginRecord;
import cn.edu.gdut.llc.mybatis.model.Weixin;
import cn.edu.gdut.llc.share.dao.LoginDao;
import cn.edu.gdut.llc.share.service.LoginService;
import cn.edu.gdut.llc.share.util.AutoLogger;
import cn.edu.gdut.llc.share.util.HttpRequest;
import net.sf.json.JSONObject;

/**
 * @author cpt725
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

	@AutoLogger
	private static Logger logger;
	@Autowired
	private Environment env;
	@Autowired 
	@Qualifier("loginDaoImpl")
	private LoginDao loginDao;
	
	@Override
	public Login getLoginByUsername(String username) {
		logger.debug("getLoginByUsername:"+username);
		Login login=null;
		try {
			login=loginDao.getLoginByUsername(username);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return login;
	}

	@Override
	public boolean updateLogin(Login info) {
		logger.debug("updateLoginInfo");
		boolean flag=false;
		try {
			loginDao.updateById(info);
			flag=true;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return flag;
	}

	@Override
	public Login getLoginByAccount(String account) {
		logger.debug("getLoginByAccount:"+account);
		Login login=null;
		try {
			login=loginDao.getLoginByAccount(account);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return login;
	}

	@Override
	public boolean insertLoginRecord(LoginRecord loginRecord) {
		logger.debug("insertLoginRecord");
		boolean flag=false;
		try {
			loginDao.insertLoginRecord(loginRecord);
			flag=true;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return flag;
	}

	@Override
	public List<LoginRecord> getLoginRecordByUsername(String username) {
		logger.debug("getLoginRecordByUsername");
		List<LoginRecord> list=new ArrayList<>();
		try {
			list=loginDao.getLoginRecordByUsername(username);
			
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return list;
	}

	@Override
	public Login getLoginByWeixin(String code) throws Exception {
		String appid=env.getProperty("weixin.appid").trim();
		String secret=env.getProperty("weixin.secret").trim();
		String getOpenidStr=env.getProperty("weixin.access.token.url").trim();
        //发送 GET 请求
        String result=HttpRequest.sendGet(getOpenidStr,
        		String.format("appid=%s&secret=%s&code=%s&grant_type=authorization_code",
        				appid,secret,code));
        
		//String result="{\"access_token\":\"ACCESS_TOKEN\",\"expires_in\":7200,\"refresh_token\":\"REFRESH_TOKEN\",\"openid\":\"OPENID\",\"scope\":\"SCOPE\",\"unionid\": \"o6_bmasdasdsad6_2sgVt7hMZOPfL\"}";
        logger.debug("weixinLogin-result:"+result);
        
        if(result==null||!result.trim().endsWith("}"))
        {
        	logger.warn("微信授权失败:"+result);
        	throw new Exception("微信授权失败！");
        }
        JSONObject jsonObject=JSONObject.fromObject(result.trim());

        String openid=jsonObject.getString("openid");
        String accessToken=jsonObject.getString("access_token");
        String scope=jsonObject.getString("scope");
        
        logger.debug("openid:"+openid+" access_token:"+accessToken+" scope:"+scope);
        
        if(openid==null||openid.trim().isEmpty())
        {
        	logger.warn("openid获取失败！");
        	throw new Exception("openid获取失败！");
        }
        Weixin weixin= loginDao.getWeixinByWeixinOpenid(openid);
        if(weixin==null)
        	weixin=new Weixin();
        
		//获取用户信息
        //发送 GET 请求
        String resultinfo=HttpRequest.sendGet("https://api.weixin.qq.com/sns/userinfo",
        		String.format("access_token=%s&openid=%s&lang=zh_CN",
        				accessToken,openid));
        JSONObject customerJson=JSONObject.fromObject(resultinfo.trim());
        
        weixin.setOpenid(openid);
        weixin.setNickname(customerJson.getString("nickname"));
        weixin.setSex(customerJson.getInt("sex"));
        weixin.setCountry(customerJson.getString("country"));
        weixin.setProvince(customerJson.getString("province"));
        weixin.setCity(customerJson.getString("city"));
        weixin.setHeadImgUrl(customerJson.getString("headimgurl"));
        
        //如果不存在微信用户则创建
        if(weixin.getUsername()==null||weixin.getUsername().isEmpty())
        {
        	weixin=createWeixinUser(weixin);
        }
        else
        	loginDao.updateWeixin(weixin);
		return loginDao.getLoginByUsername(weixin.getUsername());
	}

	@Transactional
	private Weixin createWeixinUser(Weixin weixin) {
		
		Login login=new Login();
		login.setUsername(getRandomName());
		login.setPassword(getRandomName());
		login.setCreateTime(new Date());
		login.setRole(0);
		login.setState(0);
		loginDao.insertLogin(login);
		weixin.setUsername(login.getUsername());
		loginDao.insertWeixin(weixin);
		return weixin;
	}

	private String getRandomName() {
		String[] strs={"a","b","c","d","h","i","p","v","x","y","z"};
		Calendar now = Calendar.getInstance();
		Random random = new Random();
		String username="";
		do {
			username=strs[random.nextInt(10)]+strs[random.nextInt(10)]+
					"_"+now.getTimeInMillis();
		} while (loginDao.getLoginByUsername(username)!=null);
		
		return username;
	}
	
}
