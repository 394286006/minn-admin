package p.minn.demo.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import p.minn.common.annotation.MyParam;
import p.minn.common.exception.WebPrivilegeException;
import p.minn.common.utils.ConstantCommon;
import p.minn.demo.service.LeaveProcessService;
import p.minn.security.cas.springsecurity.auth.User;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment 
 *
 */
@Controller
@RequestMapping("/leaveProcess")
@SessionAttributes(ConstantCommon.LOGINUSER)
public class LeaveProcessController {

    @Autowired
	private LeaveProcessService service;
	 
	 @RequestMapping(params = "method=save")
	    @ResponseBody
	    public Object save(@ModelAttribute(ConstantCommon.LOGINUSER) User user,@RequestParam("messageBody") String messageBody,@MyParam("language") String lang) {
	        Object entity = null;
	        try {
	            service.save(user,messageBody,lang);
	        } catch (Exception e) {
	            entity = new WebPrivilegeException(e.getMessage());
	        }
	        return entity;
	    }
	    
	    @RequestMapping(params = "method=del")
	    @ResponseBody
	    public Object delete(@RequestParam("messageBody") String messageBody) {
	        Object entity = null;
	        try {
	            service.delete(messageBody);
	        } catch (Exception e) {
	            entity = new WebPrivilegeException(e.getMessage());
	        }
	        return entity;
	    }
	    
	    @RequestMapping(params = "method=update")
	    @ResponseBody
	    public Object update(@ModelAttribute(ConstantCommon.LOGINUSER) User user,@RequestParam("messageBody") String messageBody,@MyParam("language") String lang) {
	        Object entity = null;
	        try {
	            service.update(user,messageBody,lang);
	        } catch (Exception e) {
	            entity = new WebPrivilegeException(e.getMessage());
	        }
	        return entity;
	    }

	    
	    @RequestMapping(params="method=query")
	    public Object query(@RequestParam("messageBody") String messageBody,@MyParam("language") String lang){
	        Object entity = null;
	        try {
	            entity=service.query(messageBody,lang);
	         } catch (Exception e) {
	                entity = new WebPrivilegeException(e.getMessage());
	         }
	        return entity;
	    }
	    
	   @RequestMapping(params = "method=launch")
	   @ResponseBody
	   public Object launch(@ModelAttribute(ConstantCommon.LOGINUSER) User user,@RequestParam("messageBody") String messageBody,@MyParam("language") String lang) {
	        Object entity = null;
	        try {
	             service.launch(user,messageBody,lang);
	         } catch (Exception e) {
	            entity = new WebPrivilegeException(e.getMessage());
	          }
	       return entity;
	    }
	   

}
