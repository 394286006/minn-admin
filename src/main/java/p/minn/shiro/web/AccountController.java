package p.minn.shiro.web;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;


/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController {
	
	private static final String DEFAULT_IDX="index";

	@Autowired
    private LocaleResolver localeResolver;  

	@RequestMapping(method=RequestMethod.GET)
	public String defaultIndex(HttpServletRequest req,HttpServletResponse rep, @RequestParam(required=false, defaultValue="zh") String lang){
		Locale local=new Locale(lang);
		localeResolver.setLocale(req, rep, local);
		return DEFAULT_IDX;
	}
	
	
	@RequestMapping(params="method=index")
	public String index(HttpServletRequest req,HttpServletResponse rep, @RequestParam(required=false, defaultValue="zh") String lang){
		return defaultIndex(req,rep,lang);
	
	}
	
	@RequestMapping(params="method=login")
	public String login(HttpServletRequest request,HttpSession session,@RequestParam String username,@RequestParam String password){
		return DEFAULT_IDX;
	}
	
	@RequestMapping(params="method=logout")
	public Object logout(HttpServletRequest request) throws IOException{
		Subject curuser=SecurityUtils.getSubject();
		curuser.logout();
		request.getSession().invalidate();
		return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return DEFAULT_IDX;
	}

}
