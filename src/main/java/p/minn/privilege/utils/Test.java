package p.minn.privilege.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import p.minn.privilege.entity.User;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		User user=new User();
		user.setActive(1);
		user.setId(1);
		user.setLoginType(1);
		user.setName("minn民");
		user.setPwd("123");
		user.setType(1);
	 
		//System.out.println(Utils.getBeanValue(user));
		getResourceKey(null);
	}
	
	public static void getResourceKey(Method method) throws Exception{
		
		 Map<String,String> param=new HashMap<String,String>();
		    param.put("name", "Base");
		    param.put("active", "1");
			//QueryConfigUtil.getViewConfigByName("getConfigMenu");
			//QueryConfigUtil.getSqlByName("getConfigMenu","en",param,null);
			//QueryConfigUtil.getTotalSqlByName("getConfigMenu",param);
			//QueryConfigUtil.getQueryConfig();
	}
	
	
	
	
	

}
