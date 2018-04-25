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
import p.minn.demo.service.HadoopSparkService;
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
@RequestMapping("/hdfs")
@SessionAttributes(Constant.LOGINUSER)
public class HadoopSparkController {

	@Autowired
	private HadoopSparkService hadoopSparkService;
	
	@RequestMapping(params="method=query")
	public Object query(@RequestParam("messageBody") String messageBody,@MyParam("language") String lang){
		Object entity = null;
		try {
			entity=hadoopSparkService.query(messageBody,lang);
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
		 }
		return entity;
	}
	
	@RequestMapping(params="method=readFiles")
	public Object readHDFSFiles(@RequestParam("messageBody") String messageBody,@MyParam("language") String lang){
		Object entity = null;
		try {
			entity=hadoopSparkService.readHDFSFiles();
		 } catch (Exception e) {
				entity = new WebPrivilegeException(e.getMessage());
		 }
		return entity;
	}
	
	@RequestMapping(params="method=readContent")
    public Object readFileContent(@RequestParam("messageBody") String messageBody,@MyParam("language") String lang){
        Object entity = null;
        try {
            entity=hadoopSparkService.readFileContent(messageBody);
         } catch (Exception e) {
                entity = new WebPrivilegeException(e.getMessage());
         }
        return entity;
    }
	
	@RequestMapping(params="method=upload")
	public Object upload(@MyParam("language") String lang,HttpServletRequest req,HttpServletResponse rep){
		Object entity = null;
		try {
		     entity=hadoopSparkService.upload2hdfs(lang,req);
		 } catch (Exception e) {
			 e.printStackTrace();
				entity = new WebPrivilegeException(e.getMessage());
		 }
		return entity;
	}
	
	@RequestMapping(params = "method=import2db")
	@ResponseBody
	public Object import2db(@ModelAttribute(Constant.LOGINUSER) User user,@RequestParam("messageBody") String messageBody,@MyParam("language") String lang) {
		Object entity = null;
		try {
			hadoopSparkService.hdfs2Database(messageBody);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new WebPrivilegeException(e.getMessage());
		}
		return entity;
	}
}
