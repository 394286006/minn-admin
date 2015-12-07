package p.minn.privilege.web;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import p.minn.common.annotation.MyParam;
import p.minn.common.utils.Page;
import p.minn.privilege.service.CommonService;
import p.minn.privilege.service.DictionaryService;

/**
 * 
 * @author minn 
 * @QQ:3942986006
 * @comment 角色管理
 * 
 */
@Controller
@RequestMapping("/common")
public class CommonController {

	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * @comment 提供页面跳转转换
	 * @param pidx
	 *            对应的jsp页面名称
	 * @return            
	 */
	@RequestMapping(params = "method=index")
	public String index(HttpServletRequest req, @RequestParam String pidx,@RequestParam Map<String,String> param) {
		param.remove("method");
		param.remove("pidx");
		Iterator<String> it=param.keySet().iterator();
		while(it.hasNext()){
			String k=it.next();
			req.setAttribute(k, param.get(k));
		}
		return pidx;
	}

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
	
	
	/**
	 * @deprecated
	 * @param lang
	 * @return
	 */
	@RequestMapping(params = "method=getQCDic")
	@ResponseBody
	public Object getQueryConfigDic(@MyParam("language") String lang) {

		Object entity = null;
		try {
			entity = commonService.getQueryConfigDic(lang);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}

		return entity;
	}
	
	
	
	@RequestMapping(params = "method=configquery")
	@ResponseBody
	public Object configquery(@MyParam("language") String lang,@RequestParam Map<String,String> param,
			 @ModelAttribute("page") Page page,@RequestParam String filename) {

		Object entity = null;
		try {
			entity=commonService.configQuery(lang,page,param,filename);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}

		return entity;
	}
	
	
	@RequestMapping(params = "method=getView")
	@ResponseBody
	public Object getView(@MyParam("language") String lang,@RequestParam String name,@RequestParam String filename) {

		Object entity = null;
		try {
			entity=commonService.getViewConfigByName(lang,name,filename);
			
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}

		return entity;
	}
	
	

}
