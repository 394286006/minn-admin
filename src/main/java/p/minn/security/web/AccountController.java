package p.minn.security.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import p.minn.privilege.entity.User;
import p.minn.privilege.utils.Constant;
import p.minn.security.service.IAccountService;


/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Controller
public class AccountController {
	
	private static final String DEFAULT_IDX="index";
	
	@Autowired
	private IAccountService accountService;

	@Autowired
    private LocaleResolver localeResolver;  

	@RequestMapping(value = { "/","idx**"}, method = RequestMethod.GET)
	public ModelAndView defaultIndex(HttpServletRequest req,HttpServletResponse rep, @RequestParam(required=false, defaultValue="zh") String lang){
		Locale local=new Locale(lang);
		localeResolver.setLocale(req, rep, local);
		ModelAndView model = new ModelAndView();
		model.setViewName(DEFAULT_IDX);
	
		String userName = null;
		UserDetails ud=null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			ud=(UserDetails)principal;
			userName = ud.getUsername();
		} else {
			userName = principal.toString();
			
		}
		if(!userName.equals(Constant.ANONYMOUSUSER)){
			User user=accountService.findUserByLoginName(userName);
			req.getSession().setAttribute(Constant.LOGINUSER,user);
		}
		return model;
	}

	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return DEFAULT_IDX;
	}

}
