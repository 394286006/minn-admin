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
import p.minn.privilege.entity.Role;
import p.minn.privilege.entity.RoleMenu;
import p.minn.privilege.repository.RoleDao;
import p.minn.privilege.utils.Constant;
import p.minn.privilege.utils.Utils;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment 角色业务
 *
 */
@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;
	
	/**
	 * 保存
	 * @param menu
	 * @return
	 */
	public void save(Role role) throws Exception{
		
		roleDao.save(role);
		
	}
	
	public void update(Role role) throws Exception{
		
		roleDao.update(role);
		
	}
	
	public void delete(IdEntity idEntity) throws Exception{
		
		roleDao.delete(idEntity);
		
	}
	
	/**
	 * 保存角色资源关系
	 * @param menu
	 * @return
	 */
	@Transactional
	public void saveRoleResource(String roleid,String resourceidstr) throws Exception{
          String[] resourceids=resourceidstr.split(",");
		   List<RoleMenu> rms=new LogArrayList<RoleMenu>();
		   for(String rid:resourceids){
			   rms.add(new RoleMenu(roleid,rid));
			   
		   }
		   roleDao.delRoleResource(roleid);
		   roleDao.saveRoleResource(rms);
		
	}
	
	
	/**
	 * 查找角色资源
	 * @param m
	 * @return
	 */
	public List<Map<String,Object>> getRoleResource(String lang,String roleid) throws Exception{
		
		List<Map<String,Object>> list=roleDao.getRoleResource(lang,roleid);
		
		Map<String,Object> state=null;
		if(roleid!=null&&!roleid.equals("")){
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
	 * 分页查询
	 * @param lang
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> query(Page page,String column,String lang) throws Exception{
	
		int total=roleDao.getTotal(page);
		page.setTotal(total);
	 
		List<Map<String,Object>> list=roleDao.query(lang,page);
		
		List<Map<String,Object>> rows=Utils.list2Grid(list,column.split(Constant.SPLIT));

		Map<String,Object>  rs=Utils.getResultMap(total,page,rows);
	
		return rs;
	}
	
}
