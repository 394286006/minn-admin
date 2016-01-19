package p.minn.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;






















import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import p.minn.common.utils.MyGsonMap;
import p.minn.common.utils.Page;
import p.minn.privilege.entity.Globalization;
import p.minn.privilege.entity.IdEntity;
import p.minn.privilege.entity.Picture;
import p.minn.privilege.entity.PicturePath;
import p.minn.privilege.repository.GlobalizationDao;
import p.minn.privilege.repository.PictureDao;
import p.minn.privilege.repository.PicturePathDao;
import p.minn.privilege.utils.Utils;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Service
public class PictureService {

	@Autowired
	private PictureDao pictureDao;
	
	@Autowired
	private PicturePathDao picturePathDao;
	 
	@Autowired
	private GlobalizationDao globalizationDao;
	

	public Object query(String messageBody, String lang) {
		// TODO Auto-generated method stub
		Page page=(Page) Utils.gson2T(messageBody, Page.class);
		Map<String,String> condition=Utils.getCondition(page);
		int total=pictureDao.getTotal(lang,condition);
		page.setPage(page.getPage()+1);
		page.setTotal(total);
		List<Map<String,Object>> list=pictureDao.query(lang,page,condition);
	    page.setResult(list);
	
		return page;
	}


	public void update(String messageBody, String lang) {
		// TODO Auto-generated method stub
		MyGsonMap<Map,Picture> msm=MyGsonMap.getInstance(messageBody,Map.class, Picture.class); 
		Picture picture=msm.gson2T(Picture.class);
		Map map=msm.gson2Map();
		pictureDao.update(picture);
		Globalization glz=new Globalization();
		glz.setId(Double.valueOf(map.get("gid").toString()).intValue());
		glz.setName(map.get("name").toString());
		globalizationDao.update(glz);
		glz=new Globalization();
		glz.setId(Double.valueOf(map.get("gcommentid").toString()).intValue());
		glz.setName(map.get("comment").toString());
		globalizationDao.update(glz);
	}


	public void save(String messageBody, String lang) {
		// TODO Auto-generated method stub
		MyGsonMap<Map,Picture> msm=MyGsonMap.getInstance(messageBody,Map.class, Picture.class); 
		Picture picture=msm.gson2T(Picture.class);
		Map map=msm.gson2Map();
		picture.setCreateid(1);
		if(pictureDao.checkName(lang,map.get("name").toString())>0)
			throw new RuntimeException("name:"+map.get("name").toString()+", exists!");
		pictureDao.save(picture);
		Globalization glz=new Globalization();
		glz.setTableid(picture.getId().toString());
		glz.setName(map.get("name").toString());
		glz.setLanguage(map.get("language").toString());
		glz.setTablename("picture");
		glz.setTablecolumn("name");
		globalizationDao.save(glz);
		glz=new Globalization();
		glz.setTableid(picture.getId().toString());
		glz.setName(map.get("comment").toString());
		glz.setLanguage(map.get("language").toString());
		glz.setTablename("picture");
		glz.setTablecolumn("comment");
		globalizationDao.save(glz);
	}


	public void delete(String messageBody) {
		// TODO Auto-generated method stub
		IdEntity idEntity=(IdEntity) Utils.gson2T(messageBody,IdEntity.class);
		globalizationDao.deleteByTableId(idEntity.getId(),"picture");
		pictureDao.delete(idEntity);
	}


	public Object savePic(String lang,HttpServletRequest req) throws Exception {
		// TODO Auto-generated method stub
	   String  uploadPath =Utils.getReadUploadPath(req); 
	   PicturePath picturePath=null;
	   File disk = null;
	   FileItem item = null;
	   DiskFileItemFactory factory = new DiskFileItemFactory();                                   
	   String fileName = "";
	   String messageBody="";
	   ListIterator iterator = null;
	   List items = null;
	   ServletFileUpload upload = new ServletFileUpload( factory );
	   items = upload.parseRequest(req);
	   iterator = items.listIterator();
	   while(iterator.hasNext() ) {
	        item = (FileItem)iterator.next();
	        if(item.isFormField()){
                if (item.getFieldName().equalsIgnoreCase("upfilename")){
                	fileName = item.getString();                                                 	                }
                if (item.getFieldName().equalsIgnoreCase("messageBody")){
                	messageBody=URLDecoder.decode(item.getString(), "UTF-8");
                }
	          }else{
	        	  MyGsonMap<Map,PicturePath> msm=MyGsonMap.getInstance(messageBody,Map.class, PicturePath.class); 
	    		  picturePath=msm.gson2T(PicturePath.class);
	        	  disk = new File(uploadPath + fileName+"."+picturePath.getPictype());   
	              item.write(disk);    
	           }
	      } 
		picturePath.setIsfirst(0);
		picturePath.setCreateid(1);
		picturePathDao.save(picturePath);
		return picturePath;
	}


	public Page queryPic(String messageBody, String lang) {
		// TODO Auto-generated method stub
		Page page=(Page) Utils.gson2T(messageBody, Page.class);
		Map<String,String> condition=Utils.getCondition(page);
		List<Map<String,Object>> list=picturePathDao.query(lang,page,condition);
	    page.setResult(list);
		return page;
	}


	public void deletePic(String messageBody) {
		// TODO Auto-generated method stub
		IdEntity idEntity=(IdEntity) Utils.gson2T(messageBody,IdEntity.class);
		System.out.println("id:"+idEntity.getId());
		picturePathDao.delete(idEntity);
	}


	public PicturePath updatePic(String lang,HttpServletRequest req) throws Exception {
		// TODO Auto-generated method stub
		String  uploadPath =Utils.getReadUploadPath(req); 
		   PicturePath picturePath=null;
		   File disk = null;
		   FileItem item = null;
		   DiskFileItemFactory factory = new DiskFileItemFactory();                                   
		   String fileName = "";
		   String messageBody="";
		   ListIterator iterator = null;
		   List items = null;
		   ServletFileUpload upload = new ServletFileUpload( factory );
		   items = upload.parseRequest(req);
		   iterator = items.listIterator();
		   while(iterator.hasNext() ) {
		        item = (FileItem)iterator.next();
		        if(item.isFormField()){
	                if (item.getFieldName().equalsIgnoreCase("upfilename")){
	                	fileName = item.getString();                                                 	                }
	                if (item.getFieldName().equalsIgnoreCase("messageBody")){
	                	messageBody=URLDecoder.decode(item.getString(), "UTF-8");
	                }
		          }else{
		        	  IdEntity idEntity=(IdEntity) Utils.gson2T(messageBody,IdEntity.class);
		    		  picturePath=picturePathDao.findEntityById(idEntity);
		        	  disk = new File(uploadPath + fileName+"."+picturePath.getPictype());   
		              item.write(disk);    
		           }
		      } 
     	 MyGsonMap<Map,PicturePath> msm=MyGsonMap.getInstance(messageBody,Map.class, PicturePath.class); 
		 picturePath=msm.gson2T(PicturePath.class);
		picturePathDao.update(picturePath);
		return picturePath;
	}


	public PicturePath setFirstPagePic(String messageBody, String lang) {
		// TODO Auto-generated method stub
		 MyGsonMap<Map,PicturePath> msm=MyGsonMap.getInstance(messageBody,Map.class, PicturePath.class); 
		 PicturePath picturePath=msm.gson2T(PicturePath.class);
		 picturePathDao.setFirstPagePic(picturePath);
		return picturePath;
	}

	public Map<String,String> genFirstPagePic(String messageBody, String lang,HttpServletRequest req) {
		// TODO Auto-generated method stub
		Map<String,String> data=null;
		String filename="firstpage_"+lang+".json";
		 String servletpath=Utils.getReadDataPath(req);
		 FileOutputStream fos=null;
		try {
			List<Map<String,Object>> pics= picturePathDao.getFirstPagePic(lang);
			File f=new File(servletpath+filename);
			if(f.exists()){
				fos = new FileOutputStream(f);
			}else{
				f=new File(servletpath+filename);
				if(f.exists()){
					fos = new FileOutputStream(f);
				}else{
				    throw new Exception("没有找到文件:"+filename);
				}
			}
			PrintWriter oos=new PrintWriter(fos);
			oos.print(Utils.gson2Str(pics));
	       oos.flush();
	       oos.close();
	       data=new HashMap<String,String>();
	       data.put("info", "succeess");
		}catch(Exception e){
			e.printStackTrace();
	    	   new RuntimeException("gen firstpage.json fail!");
	       }
		return data;
	}
}
