package p.minn.demo.web;



import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import p.minn.common.annotation.MyParam;
import p.minn.common.exception.WebPrivilegeException;
import p.minn.demo.service.PictureService;
import p.minn.privilege.utils.Constant;
import p.minn.vo.User;

/**
 * 
 * @author minn 
 * @QQ:3942986006
 * 
 */
@Controller
@RequestMapping("/pic")
@SessionAttributes(Constant.LOGINUSER)
public class PictureController {

	@Autowired
	private PictureService pictureService;
	


	@RequestMapping(params = "method=save")
	@ResponseBody
	public Object save(@ModelAttribute(Constant.LOGINUSER) User user,@RequestParam("messageBody") String messageBody,@MyParam("language") String lang) {
		Object entity = null;
		try {
			pictureService.save(user,messageBody,lang);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;
	}

	
	@RequestMapping(params = "method=del")
	@ResponseBody
	public Object delete(@RequestParam("messageBody") String messageBody) {
		Object entity = null;
		try {
			pictureService.delete(messageBody);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;
	}

	
	
	@RequestMapping(params = "method=update")
	@ResponseBody
	public Object update(@ModelAttribute(Constant.LOGINUSER) User user,@RequestParam("messageBody") String messageBody,@MyParam("language") String lang) {
		Object entity = null;
		try {
			pictureService.update(user,messageBody,lang);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;
	}


	@RequestMapping(params="method=query")
	public Object query(@RequestParam("messageBody") String messageBody,@MyParam("language") String lang){
		Object entity = null;
		try {
			entity=pictureService.query(messageBody,lang);
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
		 }
		return entity;
	}
	
	@RequestMapping(params="method=savePic")
	public Object savePic(@MyParam("language") String lang,HttpServletRequest req,HttpServletResponse rep){
		Object entity = null;
		try {
		     entity=pictureService.savePic(lang,req);
		 } catch (Exception e) {
			 e.printStackTrace();
				entity = new WebPrivilegeException(e.getMessage());
		 }
		return entity;
	}

	@RequestMapping(params="method=queryPic")
	public Object queryPic(@RequestParam("messageBody") String messageBody,@MyParam("language") String lang){
		Object entity = null;
		try {
			entity=pictureService.queryPic(messageBody,lang);
		 } catch (Exception e) {
			 e.printStackTrace();
				entity = new WebPrivilegeException(e.getMessage());
		 }
		return entity;
	}
	
	@RequestMapping(params = "method=delPic")
	@ResponseBody
	public Object deletePic(@RequestParam("messageBody") String messageBody,HttpServletRequest req) {
		Object entity = null;
		try {
			pictureService.deletePic(URLDecoder.decode(messageBody, "UTF-8"), req);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;
	}
	
	@RequestMapping(params = "method=updatePic")
	@ResponseBody
	public Object updatePic(@MyParam("language") String lang,HttpServletRequest req,HttpServletResponse rep) {
		Object entity = null;
		try {
			  entity=pictureService.updatePic(lang,req);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;
	}
	
	@RequestMapping(params = "method=setFirstPagePic")
	@ResponseBody
	public Object setFirstPagePic(@RequestParam("messageBody") String messageBody,@MyParam("language") String lang) {
		Object entity = null;
		try {
			  entity=pictureService.setFirstPagePic(messageBody,lang);
		} catch (Exception e) {
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;
	}
	
	@RequestMapping(params = "method=genFirstPagePic")
	@ResponseBody
	public Object genFirstPagePic(@RequestParam("messageBody") String messageBody,@MyParam("language") String lang,HttpServletRequest req) {
		Object entity = null;
		try {
			  entity=pictureService.genFirstPagePic(messageBody,lang,req);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;
	}
}
