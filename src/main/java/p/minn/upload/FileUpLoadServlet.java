package p.minn.upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
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


public class FileUpLoadServlet extends HttpServlet {
	private static final Log logger = LogFactory.getLog(FileUpLoadServlet.class);
	public void doPost(HttpServletRequest request,    
			HttpServletResponse response)    
			throws IOException, ServletException    
			{    

        PrintWriter out = null;
         
//       logger.debug("********************post method uploadDirectory:"+uploadPath);

       
        File disk = null;
        FileItem item = null;
        DiskFileItemFactory factory = new DiskFileItemFactory();                                   
        // We use the FileUpload package provided by Apache to process the request.
        String statusMessage = "";
        String fileName = "";
        
        ListIterator iterator = null;
        List items = null;
        ServletFileUpload upload = new ServletFileUpload( factory );
 
        // SAX 2.0 ContentHandler.
        TransformerHandler hd = null;
 
        try {
            out = response.getWriter();
            StreamResult streamResult = new StreamResult(out);                                      // Used for writing debug errors to the screen.
   
            SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance(); // SAX XML parsing factory.
           
            items = upload.parseRequest(request);
            iterator = items.listIterator();
           
            hd = tf.newTransformerHandler();                                                        // Set the XML handler.
            Transformer serializer = hd.getTransformer();                                           // You'll serialize the data.
            serializer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");                              // You'll use UTF-8 for the XML encoding.
            serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"response.dtd");                 // Set the doctype to the custom DTD.
            serializer.setOutputProperty(OutputKeys.INDENT,"yes");                                  // Though not required, you can provide automatic indentation of the XML.
            serializer.setOutputProperty(OutputKeys.METHOD,"xml");                                  // Identifies the method used for outputting the result tree.
            hd.setResult(streamResult);
   
            hd.startDocument();                                                                     // Start the XML document.
   
            AttributesImpl atts = new AttributesImpl();                                             // Declare and instantiate a new attributes object.
   
            hd.startElement("","","response",atts);                                                 // Start the main response element.
 
            while( iterator.hasNext() )                                                             // Loop over the items in the request.
            {
           
              atts.clear();
             
              item = (FileItem)iterator.next();
 
              // If the current item is an HTML form field...
              if( item.isFormField() )
              {

                if (item.getFieldName().equalsIgnoreCase("othername")){
                	fileName = item.getString();    
                }
                atts.addAttribute("","","id","CDATA",item.getFieldName());                          // Add the "id" attribute of the "field" element. 
               
                hd.startElement("","","field",atts);                                                // Start element and set its attribute.
                hd.characters(item.getString().toCharArray(),0,item.getString().length());          // Set the "field" tag's value.
                hd.endElement("","","field");                                                       // Close the "field" tag.
                atts.clear();                                                                       // Clear the attributes object so it can be used again.
              } else {  // If the item is a file...
                    
                        disk = new File(uploadPath + fileName);
                        // Instantiate a File object for the file to be written.
                        item.write(disk);    
                        // Write the uploaded file to disk.
                       logger.debug("***************upload file sucess");

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd.yy hh:mm:ss aaa");
                        statusMessage = "File successfully written to server at " + simpleDateFormat.format(calendar.getTime());
                    }
                  
                    atts.addAttribute("","","id","CDATA",fileName);                              // Add the "id" attribute of the "file" element.
                  
                    hd.startElement("","","file",atts);                                             // Start the "file" element.
                    hd.characters(statusMessage.toCharArray(),0,statusMessage.length());            // Set the "file" element tag's value.
                    hd.endElement("","","file");                                                    // End the "file" element.
           
            }       
           
            hd.endElement("","","response");                                                        // End the "response" element.
            hd.endDocument();                                                                       // End the XML document.
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

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

        String othername=req.getParameter("othername");
        int pos=othername.lastIndexOf("/");
        String sustr=othername.substring(pos);
        String name = getServletContext().getRealPath("/tmp/"+sustr); 
        try{
//          String name=this.getServletContext().getRealPath("/tmp/1.avi");
        	   File f=new File(name);
        	   boolean b=f.delete();
        }catch(RuntimeException e){
        	e.printStackTrace();
        } 
        	
     
	}


	public void init() throws ServletException {    
	    uploadPath =getServletContext().getRealPath("/tmp/")+"\\"; 
//	    tempPath=getServletContext().getRealPath("/temp/")+"\\"; 
//	        uploadPath =this.getServletContext().getInitParameter("UploadDirectory");   
//	    tempPath = ....    
	    // 文件夹不存在就自动创建：    
	    if(!new File(uploadPath).isDirectory())    
	        new File(uploadPath).mkdirs();    
//	    if(!new File(tempPath).isDirectory())    
//	        new File(tempPath).mkdirs();    
	    
	}   

	private String uploadPath = "C:\\upload\\"; // 上传文件的目录   
//    private String tempPath = "C:\\upload\\tmp\\"; // 临时文件目录    

}
