package p.minn.privilege.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;







import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import p.minn.common.utils.Page;
import p.minn.privilege.entity.IdEntity;
import p.minn.privilege.entity.Menu;
import p.minn.privilege.entity.User;
import p.minn.privilege.repository.MenuDao;
import p.minn.privilege.utils.Constant;
import p.minn.privilege.utils.Utils;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment 菜单业务管理
 *
 */
@Service
public class MenuService {

	@Autowired
	private MenuDao menuDao;
	 
	
	/**
	 * 获取菜单
	 * @throws Exception 
	 */
	public List<Map<String,Object>> getPrivateMenu(User user,String lang) throws Exception{
		
		List<Map<String,Object>> list=menuDao.getPrivateMenu(lang,user);
		
		return list;
	}
	
	
	/**
	 * 获取菜单
	 */
	public List<Map<String,Object>> getResource(User user,String lang) throws Exception{

		List<Map<String,Object>> list=menuDao.getResource(lang);
		             
		return list;
	}
	
	/**
	 * 保存
	 * @param menu
	 * @return
	 */
	public void save(Menu menu) throws Exception{
		
		menuDao.save(menu);
		
	}
	
	public void update(Menu menu) throws Exception{
		menuDao.update(menu);
		
	}
	
	public void delete(IdEntity idEntity) throws Exception{
		menuDao.delete(idEntity);
	}
	
	
	/**
	 * 分页查找
	 * @param lang
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> query(Page page,String column,String lang,String nodeid) throws Exception{

		int total=menuDao.getTotal(nodeid,page);
		page.setTotal(total);
		
		List<Map<String,Object>> list=menuDao.query(lang,nodeid,page);
		
		List<Map<String,Object>> rows=Utils.list2Grid(list,column.split(Constant.SPLIT));

		Map<String,Object>  rs=Utils.getResultMap(total,page,rows);
	
		return rs;
	}
	
	public Map<String,Object> checkCode(String code,String type) throws Exception{
		
		Map<String,Object>  rs=new HashMap<String, Object>();
		int c=menuDao.checkCode(code);
		rs.put("count", c);
		
		return rs;
	}
}
