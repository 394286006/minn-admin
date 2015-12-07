package p.minn.privilege.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;








import p.minn.common.utils.Page;
import p.minn.privilege.repository.CommonDao;
import p.minn.privilege.utils.Constant;
import p.minn.privilege.utils.QueryConfigUtil;
import p.minn.privilege.utils.Utils;

/**
 * @author minn
 * @QQ:3942986006
 *
 */
@Service
public class CommonService {

	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private CommonDao commonDao;
	
	/**
	 * @deprecated
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	public Map<String,List<Map<String,Object>>> getQueryConfigDic(String lang) throws Exception{

		Map<String, List<Map<String, Object>>>  dic= dictionaryService.getDic(lang, "'SYSTEM'");
		List<Map<String, Object>> sys=dic.get("SYSTEM");
	
		Map<String,Object> menu=QueryConfigUtil.getConfigQuery();
	    
	    for(Map<String, Object> m:sys){
	    	if(menu.get(m.get("id"))!=null){
	    		m.put("data", menu.get(m.get("id")));
	    		m.put("enable", true);
	    	}else{
	    		m.put("enable", false);
	    	}
	    }
	    
		return dic;
	}

	
	public Map<String,Object> getViewConfigByName(String lang,String name,String filename) throws Exception{
		Map<String,Object> data=QueryConfigUtil.getViewConfigByName(name, filename);
		data.put("querydic",  dictionaryService.getDic(lang,data.get(Constant.MKEY).toString())) ;
		data.remove(Constant.MKEY);
		return data;
	}


	@SuppressWarnings("unchecked")
	public  Map<String,Object> configQuery(String lang ,Page page,Map<String,String> param,String filename) throws Exception {
		Map<String,Object>  rs=null;
		try{
			SqlSession session=null;
			String name=param.get("name");
				Map<String,String> sqlparam=new HashMap<String,String>();
				String[] qkeys=param.get("qkey").split(Constant.SPLIT);
				String[] qvals=param.get("qval").split(Constant.SPLIT);
				for(int i=0;i<qkeys.length;i++){
						if(!qkeys[i].equals("")){
							sqlparam.put(qkeys[i], qvals[i]);
						}
				}
				QueryConfigUtil.completeWhereParam(name,sqlparam, filename);
				int total=0;
				if(param.get("autototalsql").equals("true")){
					total=commonDao.getTotal(QueryConfigUtil.getTotalSqlByName(name, sqlparam, filename));
				}else{
					session=getSessionByName(QueryConfigUtil.getDs(name,filename));
					
					total=(Integer) session.selectOne(QueryConfigUtil.getNs(name,filename)+"Total",sqlparam);
					
				}
				page.setTotal(total);
				List<Map<String,Object>> list=null;
				if(param.get("autosql").equals("true")){
					
					list= commonDao.query(QueryConfigUtil.getSqlByName(name, lang, sqlparam, page, filename));
				}else{
				   if(session==null){
					   session=getSessionByName(QueryConfigUtil.getDs(name,filename));
				   }
				   
				   sqlparam.put("startR", page.getStartR().toString());
				   sqlparam.put("endR", page.getEndR().toString());
				   sqlparam.put("lang", lang);
				   list=session.selectList(QueryConfigUtil.getNs(name,filename),sqlparam);
				}
				  if(session!=null){
					  session.close();
				  }
				 List<Map<String,Object>> rows=Utils.list2Grid(list,param.get("column").split(Constant.SPLIT));
		
				 rs=Utils.getResultMap(total,page,rows);
				 
				 
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	
	private SqlSession getSessionByName(String name){
		SqlSessionTemplate sfb=appContext.getBean(name,SqlSessionTemplate.class);
		return sfb.getSqlSessionFactory().openSession();
	}
}
