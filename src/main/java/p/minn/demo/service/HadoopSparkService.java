package p.minn.demo.service;

import java.io.File;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
















import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import p.minn.common.utils.MyGsonMap;
import p.minn.common.utils.Page;
import p.minn.common.utils.UtilCommon;
import p.minn.hadoop.entity.HadoopSpark;
import p.minn.hadoop.hdfs.HDFSFileUtils;
import p.minn.hadoop.repository.HadoopSparkDao;
import p.minn.hive.jdbc.HadoopHiveJDBC;
import p.minn.privilege.utils.Utils;
import p.minn.spark.jdbc.HadoopSparkJDBC;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Service
public class HadoopSparkService {

	
	@Autowired
	private HDFSFileUtils hdfsFileUtils;
	
	@Autowired
    private HadoopHiveJDBC hadoopHiveJDBC;
	

	public Object query(String messageBody, String lang) throws Exception  {
		// TODO Auto-generated method stub
		Page page=(Page) Utils.gson2T(messageBody, Page.class);
		Map<String,String> condition=Utils.getCondition(page);
		Connection conn=hadoopHiveJDBC.getConnect();
		int total=hadoopHiveJDBC.getTotal(conn,"select count(id) from hadoopspark");
		page.setPage(page.getPage()+1);
		page.setTotal(total);
		String sql="select id,name,email,qq from hadoopspark  order by id asc ";
		List<HadoopSpark> list= hadoopHiveJDBC.query(conn,page,sql);
		page.setResult(list);
		hadoopHiveJDBC.close(conn);
		return page;
	}

	public void hdfs2Database(String messageBody) throws Exception{
	  Map map=UtilCommon.gson2Map(messageBody);
      hdfsFileUtils.import2db(map.get("name").toString());
	}
   
	
	public Object upload2hdfs(String lang,HttpServletRequest req) throws Exception{
		String  uploadPath =Utils.getReadUploadHDFSPath(req); 
		Map<String,String> rs=new HashMap<String,String>();
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
		        	  disk = new File(uploadPath + fileName);   
		              item.write(disk);    
		           }
		      } 
		   hdfsFileUtils.uploadFile(fileName, disk);
		   rs.put("filename", fileName);
		   rs.put("info", "ok");
		return rs;
	}
	
	public Object readHDFSFiles() throws Exception{
		return hdfsFileUtils.readFiles();
	}

  public Object readFileContent(String messageBody) throws Exception {
    // TODO Auto-generated method stub
    Map<String,Object> rs=new HashMap<String,Object>();
    Map map=UtilCommon.gson2Map(messageBody);
    String content= hdfsFileUtils.readFileContent(map.get("name").toString());
    rs.put("content", content);
    return rs;
  }

	
}
