package p.minn.privilege.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import p.minn.common.utils.LogArrayList;
import p.minn.common.utils.Page;
import p.minn.privilege.entity.IdEntity;
import p.minn.privilege.entity.User;
import p.minn.privilege.entity.UserRole;
import p.minn.privilege.repository.UserDao;
import p.minn.privilege.utils.Constant;
import p.minn.privilege.utils.Utils;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment 业务层
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	/**
	 * 保存
	 * @param menu
	 * @return
	 */
	public void save(User user) throws Exception{
		
		userDao.save(user);
		
	}
	
	public void update(User user) throws Exception{
		
		userDao.update(user);
		
	}
	
	public void delete(IdEntity idEntity) throws Exception{
		
		userDao.delete(idEntity);
		
	}
	
	/**
	 * 分页查询
	 * @param lang
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> query(Page page,String column,String lang) throws Exception{
		
		
		int total=userDao.getTotal(page);
		page.setTotal(total);

		List<Map<String,Object>> list=userDao.query(lang,page);
		
		List<Map<String,Object>> rows=Utils.list2Grid(list,column.split(Constant.SPLIT));

		
		Map<String,Object> rs=Utils.getResultMap(total,page,rows);
	
		return rs;
	}
	
	/**
	 * 保存角色资源关系
	 * @param menu
	 * @return
	 */
	@Transactional
	public void saveUserRole(String userid,String rolesidstr) throws Exception{
		String[] roleids=rolesidstr.split(Constant.SPLIT);
		List<UserRole> uss=new LogArrayList<UserRole>();
		
		   for(String rid:roleids){
			   uss.add(new UserRole(userid, rid));
			}
		   userDao.delUserRole(userid);
		   userDao.saveUserRole(uss);
	}
	
	/**
	 * 查找角色资源
	 * @param m
	 * @return
	 */
	public List<Map<String,Object>> getUserRole(String lang,String userid) throws Exception{
		
		List<Map<String,Object>> list=userDao.getUserRole(lang,userid);
		
		Map<String,Object> state=null;
		if(userid!=null&&!userid.equals("")){
		for(Map<String,Object> o:list){
			if(o.get("flag").toString().equals("1")){
				state=new HashMap<String, Object>();
				state.put("selected", true);
				o.put("state", state);
			}
		}
		}

		return list;
	}
	
	/**
	 * 登录验证
	 * @param name
	 * @param pwd
	 * @return
	 */
	public User login(String name,String pwd){
		return userDao.checkUser(name, pwd);
		
	}
}
