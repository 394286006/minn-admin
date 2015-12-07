package p.minn.shiro.auth;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import p.minn.common.utils.Encodes;
import p.minn.privilege.entity.User;
import p.minn.privilege.utils.Constant;
import p.minn.shiro.service.AccountService;
/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Service
public class ShiroDbRealm extends AuthorizingRealm {

	@Autowired
	private AccountService accountService;
	

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = accountService.findUserByLoginName(token.getUsername());
		if (user != null) {
			Subject cuser=SecurityUtils.getSubject();
			cuser.getSession().setAttribute(Constant.LOGINUSER, user);
			byte[] salt = Encodes.decodeHex(user.getSalt());
			return new SimpleAuthenticationInfo(user,
					user.getPwd(), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User user = (User) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(accountService.getRoleListByUserId(user.getId()));
		return info;
	}

}
