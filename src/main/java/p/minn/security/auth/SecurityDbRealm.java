package p.minn.security.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import p.minn.security.service.IAccountService;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */

@Service
public class SecurityDbRealm implements UserDetailsService {

	@Autowired
	private IAccountService accountService;
	
	public UserDetails loadUserByUsername(String loginName)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		p.minn.privilege.entity.User user=accountService.findUserByLoginName(loginName);
		List<String> roles=accountService.getRoleRealmListByUserId(user.getId());
		List<GrantedAuthority> gas=getGrantedAuthorities(roles);
		User realmuser=new User(user.getName(),user.getPwd(),true,true,true,true,gas);
		return realmuser;
	}
	
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();        
        for (String role : roles) {
        	System.out.println("role:"+role);
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
