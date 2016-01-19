package p.minn.upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


public class ImageUpLoadServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9133312784116999044L;
	private static final Log logger = LogFactory.getLog(FileUpLoadServlet.class);
	public void doPost(HttpServletRequest request,    
			HttpServletResponse response)    
			throws IOException, ServletException    
			{    

        PrintWriter out = null;
         
       logger.info("********************post method uploadDirectory:"+uploadPath);

       
        File disk = null;
        FileItem item = null;
        DiskFileItemFactory factory = new DiskFileItemFactory();                                   
        String fileName = "";
       
        ListIterator iterator = null;
        List items = null;
        ServletFileUpload upload = new ServletFileUpload( factory );
 
        try {
            out = response.getWriter();
   
           
            items = upload.parseRequest(request);
            iterator = items.listIterator();
           
 
            while( iterator.hasNext() )                                                             // Loop over the items in the request.
            {
           
             
              item = (FileItem)iterator.next();
 
              // If the current item is an HTML form field...
              if( item.isFormField() )
              {

                if (item.getFieldName().equalsIgnoreCase("upfilename")){
                	fileName = item.getString();                                                  // Get the value and store it.
                }
              } else {  // If the item is a file...
            	  System.out.println("hello:"+fileName);
                        disk = new File(uploadPath + fileName);   
                       if(disk.exists())
                       {
                    	   disk.delete();
                       }
                        item.write(disk);    
                       logger.info("***************upload sucess");

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd.yy hh:mm:ss aaa");
                    }
                  
                 
                  
           
            }       
           
            out.close();                                                                            // Close the output.
        }
        /*
         * Some very basic exception handling. 
         * Modify as per your needs.
         */
        catch (TransformerConfigurationException tcException) {
            out.println(tcException.getMessage());
        }
        catch (FileUploadException fileUploadException) {
            out.println(fileUploadException.getMessage());
        }
        catch (IOException ioException) {
            out.println(ioException.getMessage());
        }
        catch (SAXException saxException) {
            out.println(saxException.getMessage());
        }
        catch (NullPointerException exception) {
            out.println(exception.getMessage());
        }
        catch (Exception e){
        	e.printStackTrace();
            out.println(e.getMessage());
        }
    
	}   


	public void init() throws ServletException {    
	    uploadPath =getServletContext().getRealPath("/upload/")+"/"; 
	    	//this.getServletContext().getInitParameter("imgDirectory");  
//	    tempPath = ....    
	    System.out.println("uploadpath:"+this.uploadPath);
	    // 文件夹不存在就自动创建：    
	   // if(!new File(uploadPath).isDirectory())    
	     //   new File(uploadPath).mkdirs();    
//	    if(!new File(tempPath).isDirectory())    
//	        new File(tempPath).mkdirs();    
	}   

	private String uploadPath = "/Volumes/d/workspace/bccode/minn-admin/src/main/webapp/upload/"; // 上传文件的目录   
    private String tempPath = "/Volumes/d/workspace/bccode/minn-admin/src/main/webapp/tmp/"; // 临时文件目录    

}
