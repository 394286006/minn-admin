package p.minn.upload;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet implementation class ReadFileServlet
 */
public class ReadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(ReadFileServlet.class);    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("utf-8");
		  PrintWriter out = null;
		  File file=null;
		  String files="";
		  out = response.getWriter();
		  file=new File(ftpPath);
		  File[] fs= file.listFiles();
		  int len=fs.length;
		  for(int i=0;i<len;i++){
			  files+=fs[i].getName();
			  if(i!=(len-1))
			    files+=",";
		  }
//		  System.out.println("fileNames:"+files);
		  logger.debug("filename:"+files);
		  out.write(files);
		  out.flush();
		  out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  PrintWriter out = null;
		  File file=null;
		  String files="";
		  out = response.getWriter();
		  file=new File(ftpPath);
		  File[] fs= file.listFiles();
		  int len=fs.length;
		  for(int i=0;i<len;i++){
			  files+=fs[i].getName();
			  if(i!=(len-1))
			    files+=",";
		  }
		  System.out.println("fileNames:"+files);
		  out.write(files);
		  out.flush();
		  out.close();
	}
	public void init() throws ServletException {    
		ftpPath =getServletContext().getRealPath("/video/")+"\\"; 
//	    tempPath=getServletContext().getRealPath("/temp/")+"\\"; 
//	        uploadPath =this.getServletContext().getInitParameter("UploadDirectory");   
//	    tempPath = ....    
	    // 文件夹不存在就自动创建：    
	    if(!new File(ftpPath).isDirectory())    
	        new File(ftpPath).mkdirs();    
//	    if(!new File(tempPath).isDirectory())    
//	        new File(tempPath).mkdirs();    
	    
	}   

	private String ftpPath = "C:\\upload\\"; // 上传文件的目录   
//    private String tempPath = "C:\\upload\\tmp\\"; // 临时文件目录    

}
