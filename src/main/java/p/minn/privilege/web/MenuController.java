package p.minn.privilege.web;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import p.minn.common.annotation.MyParam;
import p.minn.common.utils.Page;
import p.minn.privilege.entity.IdEntity;
import p.minn.privilege.entity.Menu;
import p.minn.privilege.entity.User;
import p.minn.privilege.service.MenuService;
import p.minn.privilege.utils.Constant;

/**
 * 
 * @author minn 
 * @QQ:3942986006
 * @comment 菜单管理
 * 
 */
@Controller
@RequestMapping("/menu")
@SessionAttributes(Constant.LOGINUSER)
public class MenuController {

	@Autowired
	private MenuService menuService;
	

	@RequestMapping(params = "method=getPrivateMenu")
	@ResponseBody
	public Object getPrivateMenu(@ModelAttribute(Constant.LOGINUSER) User user,@MyParam("language") String lang){
		Object entity = null;
		try {
			entity = menuService.getPrivateMenu(user, lang);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}

		return entity;
	}

	@RequestMapping(params = "method=getResource")
	@ResponseBody
	public Object getResource(@ModelAttribute(Constant.LOGINUSER) User user,@MyParam("language") String lang) {
		Object entity = null;
		try {
			entity = menuService.getResource(user, lang);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}

		return entity;
	}

	@RequestMapping(params = "method=save")
	@ResponseBody
	public Object save(@ModelAttribute("menu") Menu menu) {
		Object entity = null;
		try {
			menuService.save(menu);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;
	}

	@RequestMapping(params = "method=del")
	@ResponseBody
	public Object delete(@ModelAttribute("idEntity") IdEntity idEntity) {
		Object entity = null;
		try {
			menuService.delete(idEntity);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;
	}

	@RequestMapping(params = "method=edit")
	@ResponseBody
	public Object edit(@ModelAttribute("menu") Menu menu) {
		Object entity = null;
		try {
			menuService.update(menu);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;
	}

	@RequestMapping(params = "method=query")
	@ResponseBody
	public Object query(@RequestParam String nodeid,
			@RequestParam String column, @ModelAttribute("page") Page page,@MyParam("language") String lang) {
		Object entity = null;
		try {
	        
			entity = menuService.query(page, column, lang, nodeid);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;

	}

	@RequestMapping(params = "method=checkCode")
	@ResponseBody
	public Object checkCode(@RequestParam String code,@RequestParam String type) {
		Object entity = null;
		try {
			entity = menuService.checkCode(code,type);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;

	}
	
}
