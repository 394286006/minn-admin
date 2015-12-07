package p.minn.privilege.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import p.minn.common.annotation.MyParam;
import p.minn.privilege.service.DictionaryService;

/**
 * 
 * @author minn 
 * @QQ:3942986006
 * @comment 角色管理
 * 
 */
@Controller
@RequestMapping("/dic")
public class DictionaryController {

	@Autowired
	private DictionaryService dictionaryService;
	

	@RequestMapping(params = "method=getDic")
	@ResponseBody
	public Object getDic(@MyParam("language") String lang,
			 @RequestParam String type) {

		Object entity = null;
		try {
			entity = dictionaryService.getDic(lang, type);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}

		return entity;
	}
	

}
