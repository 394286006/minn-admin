package p.minn.privilege.web;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import p.minn.common.annotation.MyParam;
import p.minn.common.utils.Page;
import p.minn.privilege.entity.IdEntity;
import p.minn.privilege.entity.Role;
import p.minn.privilege.service.RoleService;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment 角色管理
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping(params="method=save")
	public Object save(@ModelAttribute("role") Role role){
		Object entity = null;
		try {
			roleService.save(role);
			
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;
	}
	
	@RequestMapping(params="method=del")
	public Object delete(@ModelAttribute("idEntity") IdEntity idEntity){
		Object entity = null;
		try {
			roleService.delete(idEntity);
		
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;

	}
	
	@RequestMapping(params="method=edit")
	public Object edit(@ModelAttribute("role") Role role){
		Object entity = null;
		try {
				roleService.update(role);
				
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
			}
			return entity;
	}
	
	
	
	@RequestMapping(params="method=query")
	public Object query(@RequestParam String column,@ModelAttribute("page") Page page,@MyParam("language") String lang){
		Object entity = null;
		try {
			 entity=roleService.query(page,column,lang);
			
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
			}
			return entity;
	}
	
	
	@RequestMapping(params="method=saveRoleRes")
	public Object saveRoleResource(@RequestParam String roleid,@RequestParam String resourceids){
		Object entity = null;
		try {
			roleService.saveRoleResource(roleid,resourceids);
			
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
			}
			return entity;
	}
	
	@RequestMapping(params="method=getRoleRes")
	public Object getRoleResource(@RequestParam String roleid,@MyParam("language") String lang){
		Object entity = null;
		try {
			 entity=roleService.getRoleResource(lang,roleid);
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
			}
			return entity;
	}
	
	
}
