package p.minn.privilege.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import p.minn.common.annotation.MyParam;
import p.minn.common.utils.Page;
import p.minn.privilege.entity.IdEntity;
import p.minn.privilege.entity.User;
import p.minn.privilege.service.UserService;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment 用户管理
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(params="method=save")
	public Object save(@ModelAttribute("user") User user){
		Object entity = null;
		try {
			userService.save(user);
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
			}
			return entity;
	}
	
	@RequestMapping(params="method=del")
	public Object delete(@ModelAttribute("idEntity") IdEntity idEntity){
		Object entity = null;
		try {
			userService.delete(idEntity);
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
			}
			return entity;
	}
	
	@RequestMapping(params="method=edit")
	public Object edit(@ModelAttribute("user") User user){
		Object entity = null;
		try {
			userService.update(user);
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
			}
			return entity;
	}
	
	
	
	@RequestMapping(params="method=query")
	public Object query(@RequestParam String column,@ModelAttribute("page") Page page,@MyParam("language") String lang){
		Object entity = null;
		try {
			
			entity=userService.query(page,column,lang);
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
			}
			return entity;
	}
	
	@RequestMapping(params="method=saveUserRole")
	public Object saveUserRole(@RequestParam String userid,@RequestParam String roleids){
		Object entity = null;
		try {
				userService.saveUserRole(userid,roleids);
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
			}
			return entity;
	}
	
	@RequestMapping(params="method=getUserRole")
	public Object getUserRole(@RequestParam String userid,@MyParam("language") String lang){
		Object entity = null;
		try {
			entity=userService.getUserRole(lang,userid);
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
			}
			return entity;
	}

}
