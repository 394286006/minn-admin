package p.minn.privilege.web;

import java.util.Map;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import p.minn.common.annotation.MyParam;
import p.minn.common.utils.Page;
import p.minn.privilege.service.LogService;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment 操作日志
 * 
 */

@Controller
@RequestMapping("/log")
public class LogController {

	@Autowired
	private LogService logService;
	
	
	@RequestMapping(params = "method=query")
	@ResponseBody
	public Object query(@RequestParam Map<String,String> param,
			@RequestParam String column, @ModelAttribute("page") Page page,@MyParam("language") String lang) {
	 
		Object entity = null;
		try {
			entity = logService.query(page, column, lang,param);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;

	}
	
	@RequestMapping(params = "method=getLogDetail")
	@ResponseBody
	public Object getLogDetail(
			@RequestParam String column, @RequestParam String id,@MyParam("language") String lang) {
		Object entity = null;
		try {
	        
			entity = logService.getDetail(lang, column,id);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;

	}
	
	@RequestMapping(params = "method=getSignature")
	@ResponseBody
	public Object getSignature(@MyParam("language") String lang) {

		Object entity = null;
		try {
			entity = logService.getSignature(lang);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}

		return entity;
	}
}
