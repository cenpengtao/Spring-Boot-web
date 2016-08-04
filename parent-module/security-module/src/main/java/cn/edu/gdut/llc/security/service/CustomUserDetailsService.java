package cn.edu.gdut.llc.security.service;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import cn.edu.gdut.llc.mybatis.model.Login;
import cn.edu.gdut.llc.share.service.LoginService;
import cn.edu.gdut.llc.share.util.AutoLogger;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@AutoLogger
	private static Logger logger;
	@Autowired
	@Qualifier("loginServiceImpl")
	private LoginService loginService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.debug("CustomUserDetailsService-loadUserByUsername");
		Login info=loginService.getLoginByAccount(username);
		if(info==null)
			throw new UsernameNotFoundException("Username is null!");
		
		List<OperatorRole> autorities = new ArrayList<OperatorRole>();
		switch (info.getRole()) {
		case 0:
			autorities.add(new OperatorRole("ROLE_CUSTOMER"));
			break;
		case 1:
			autorities.add(new OperatorRole("ROLE_ADMIN"));
			break;
		default:autorities.add(new OperatorRole("ROLE_UNKNOW"));
			break;
		}
		boolean enable = false;
		boolean accountNonLocked = false;
		switch(info.getState())
		{
		case 0: enable=true;accountNonLocked=true;break;
		case 1: enable=true;accountNonLocked=false;break;
		case 2: enable=false;accountNonLocked=true;break;
		}	
		return new User(info.getUsername(),
				info.getPassword(), enable, true, true, accountNonLocked,
				autorities);
	}
	public static class OperatorRole implements GrantedAuthority {
		private static final long serialVersionUID = 1L;
		private String roleType;

		public OperatorRole() {
		}

		public OperatorRole(String roleType) {
			this.roleType = roleType;
		}

		public String getAuthority() {
			return this.roleType;
		}
	}

}
