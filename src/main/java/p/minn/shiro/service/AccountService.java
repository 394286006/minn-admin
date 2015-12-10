package p.minn.shiro.service;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import p.minn.cas.service.IAccountService;
import p.minn.privilege.entity.User;
import p.minn.privilege.repository.RoleDao;
import p.minn.privilege.repository.UserDao;

/**
 * @author minn
 * @QQ:394286006
 */
@Service
public class AccountService implements IAccountService{

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	

	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	public String getCurrentUserName() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		return user.getName();
	}

	

	public List<String> getRoleListByUserId(Integer id) {
		// TODO Auto-generated method stub
		return userDao.getUserRoleList(id);
	}


}
