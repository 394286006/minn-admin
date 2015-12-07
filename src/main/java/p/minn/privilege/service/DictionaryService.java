package p.minn.privilege.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





import p.minn.privilege.repository.DictionaryDao;
import p.minn.privilege.utils.Constant;

/**
 * @author minn
 * @QQ:3942986006
 *
 */
@Service
public class DictionaryService {

	
	@Autowired
	private DictionaryDao dictionaryDao;
	
	/**
	 * 获取数据字典
	 * @param lang
	 * @param type 'RESOURCETYPE','RESOURCEURLTYPE'
	 * @return
	 */
	public Map<String,List<Map<String,Object>>> getDic(String lang,String type) throws Exception{

	    List<Map<String,Object>> list=dictionaryDao.query(lang,type);
	    
	    Map<String,List<Map<String,Object>>> keys=new HashMap<String, List<Map<String,Object>>>();
	    List<Map<String,Object>> v=null;
	    
	    for(Map<String,Object> m:list){
	    	String k=m.get(Constant.MKEY).toString();
	    	if(keys.containsKey(k)){
	    		v=keys.get(k);
	    	    v.add(m);
	    	}else{
	    		v=new ArrayList<Map<String,Object>>();
	    		v.add(m);
	    		keys.put(k, v);
	    	}
	    }
	    
		return keys;
	}

}
