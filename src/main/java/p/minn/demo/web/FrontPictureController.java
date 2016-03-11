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
import p.minn.security.cas.springsecurity.auth.User;

/**
 * 
 * @author minn 
 * @QQ:3942986006
 * 
 */
@Controller
@RequestMapping("/frontpic")
@SessionAttributes(Constant.LOGINUSER)
public class FrontPictureController {

	@Autowired
	private PictureService pictureService;
	
	@RequestMapping(params="method=frontquery")
	public Object frontquery(@RequestParam("messageBody") String messageBody,@RequestParam("lang") String lang){
		Object entity = null;
		try {
			entity=pictureService.frontquery(messageBody,lang);
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
		 }
		return entity;
	}
}
