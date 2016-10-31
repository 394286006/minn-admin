package p.minn.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import p.minn.common.utils.ConstantCommon;
import p.minn.common.utils.MyGsonMap;
import p.minn.common.utils.Page;
import p.minn.common.utils.UtilCommon;
import p.minn.privilege.entity.Globalization;
import p.minn.privilege.entity.IdEntity;
import p.minn.privilege.repository.GlobalizationDao;
import p.minn.security.cas.springsecurity.auth.User;
import p.minn.workflow.entity.LeaveProcess;
import p.minn.workflow.repository.LeaveProcessDao;
import p.minn.workflow.repository.ProcessAuditDao;
import p.minn.workflow.repository.ProcessAuditStatusDao;
import p.minn.workflow.repository.ProcessDefinitionDao;
import p.minn.workflow.repository.ProcessNodeModelDao;
import p.minn.workflow.service.WorkFlowService;



/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment
 *
 */
@Service
public class LeaveProcessService {
	
    @Autowired
	private LeaveProcessDao dao;
    
    @Autowired
    private ProcessDefinitionDao processDefinitionDao;
    
    @Autowired
    private ProcessAuditDao processAuditDao;
    
    @Autowired
    private ProcessNodeModelDao processNodeModelDao;
    
    @Autowired
    private ProcessAuditStatusDao processAuditStatusDao;
    
    @Autowired 
    private WorkFlowService  workFlowService;
    
    @Autowired
    private GlobalizationDao globalizationDao;

  public Object query(String messageBody, String lang) {
    // TODO Auto-generated method stub
    Page page=(Page) UtilCommon.gson2T(messageBody, Page.class);
    Map<String,String> condition=UtilCommon.getCondition(page);
    int total=dao.getTotal(lang,condition);
    page.setPage(page.getPage()+1);
    page.setTotal(total);
    
    List<Map<String,Object>> list=dao.query(lang,page,condition);
    page.setResult(list);

    return page;
}


public void update(User user,String messageBody, String lang) {
    // TODO Auto-generated method stub
    MyGsonMap<Map,LeaveProcess> msm=MyGsonMap.getInstance(messageBody,Map.class, LeaveProcess.class); 
    LeaveProcess leaveProcess=msm.gson2T(LeaveProcess.class);
    Map map=msm.gson2Map();
    leaveProcess.setUpdateid(user.getId());
    dao.update(leaveProcess);
    Globalization glz=new Globalization();
    glz.setUpdateid(user.getId());
    glz.setName(map.get("name").toString());
    glz.setTablecolumn("name");
    glz.setTablename("wf_leaveprocess");
    glz.setLanguage(map.get("language").toString());
    globalizationDao.updateByTable(glz);
     glz=new Globalization();
    glz.setUpdateid(user.getId());
    glz.setName(map.get("desc").toString());
    glz.setTablecolumn("desc");
    glz.setTablename("wf_leaveprocess");
    glz.setLanguage(map.get("language").toString());
    globalizationDao.updateByTable(glz);
}


public void save(User user,String messageBody, String lang) {
    // TODO Auto-generated method stub
  try{
    MyGsonMap<Map,LeaveProcess> msm=MyGsonMap.getInstance(messageBody,Map.class, LeaveProcess.class); 
    LeaveProcess leaveProcess=msm.gson2T(LeaveProcess.class);
    
   // Map<String,Object>  pd=processDefinitionDao.findByPId(lang,leaveProcess.getPdId());
    
    leaveProcess.setCreateid(user.getId());
    leaveProcess.setRequestId(user.getId());
    leaveProcess.setMaxActive(1);
    dao.save(leaveProcess);
    Map map=msm.gson2Map();
    Globalization glz=new Globalization();
    glz.setTableid(leaveProcess.getId().toString());
    glz.setCreateid(user.getId());
    glz.setName(map.get("name").toString());
    glz.setLanguage(map.get("language").toString());
    glz.setTablecolumn("name");
    glz.setTablename("wf_leaveprocess");
    globalizationDao.save(glz);
    glz=new Globalization();
    glz.setTableid(leaveProcess.getId().toString());
    glz.setCreateid(user.getId());
    glz.setName(map.get("desc").toString());
    glz.setLanguage(map.get("language").toString());
    glz.setTablecolumn("desc");
    glz.setTablename("wf_leaveprocess");
    globalizationDao.save(glz);
    
    if(leaveProcess.getStatus()==1){
      workFlowService.nextProcessStep(user,leaveProcess.getPdId(),leaveProcess.getId().toString() , leaveProcess.getPdId().toString(),null,"launch");
    }
   
    /*
    String auditdate=UtilCommon.currentDateTime();
    
    ProcessAuditStatus processAuditStatus=new ProcessAuditStatus();
    processAuditStatus.setLpId(leaveProcess.getId());
    processAuditStatus.setMaxActive(leaveProcess.getMaxActive());
    processAuditStatus.setAudit_name(user.getUsername());
    processAuditStatus.setAuditId(user.getId());
    processAuditStatus.setAuditDeptid(user.getDepartments().get(0).getId());
    processAuditStatus.setCreateid(user.getId());
    processAuditStatus.setAuditdate(auditdate);
    processAuditStatus.setComment("system");
    processAuditStatus.setProcessStatus(1);
    processAuditStatus.setPdId(pd.get("id").toString());
    processAuditStatus.setStep("-1");
    processAuditStatus.setStatus(1);
    
    processAuditStatusDao.save(processAuditStatus);
   
    ProcessAudit processAudit=new ProcessAudit();
    processAudit.setFromnode(user.getDepartments().get(0).getId().toString());
    processAudit.setCreateid(user.getId());
    processAudit.setCreatetime(auditdate);
    processAudit.setLpId(leaveProcess.getId());
    processAudit.setPasId(processAuditStatus.getId());
    processAudit.setPdId(pd.get("id").toString());
    processAudit.setMaxActive(leaveProcess.getMaxActive());
    processAudit.setStatus(1);
    processAudit.setTonode(user.getDepartments().get(0).getId().toString());
    processAuditDao.save(processAudit);
    
    
    processAuditStatus.setPaId(processAudit.getId());
    processAuditStatusDao.update(processAuditStatus);*/
  }catch(Exception e){
    e.printStackTrace();
  }
    
}


public void delete(String messageBody) {
    // TODO Auto-generated method stub
    IdEntity idEntity=(IdEntity) UtilCommon.gson2T(messageBody,IdEntity.class);
    globalizationDao.deleteByTableId(idEntity.getId().toString(),"wf_leaveprocess");
    dao.delete(idEntity);
    processAuditDao.deleteByLpId(idEntity.getId());
    processAuditStatusDao.deleteByLpId(idEntity.getId());
}

public void launch(User user,String messageBody,String lang){
  
  try{
    Map condition=(Map) UtilCommon.gson2T(messageBody,Map.class);
    String lpId=condition.get("lpId").toString();
    String pdId=condition.get("pdId").toString();
    IdEntity idEntity=new IdEntity();
    idEntity.setId(Integer.valueOf(lpId));
    LeaveProcess leaveProcess=dao.findEntityById(idEntity);
    if(leaveProcess.getStatus()==1){
      throw new RuntimeException(ConstantCommon.ERROR_CODE_10000);
    }
    leaveProcess.setStatus(1);
    leaveProcess.setUpdateid(user.getId());
    leaveProcess.setStartTime(null);
    leaveProcess.setEndTime(null);
    dao.update(leaveProcess);
     workFlowService.nextProcessStep(user,leaveProcess.getPdId(),idEntity.getId().toString() , pdId,null,"launch");
    /*
    String arr[]=pdId.split("_");
    Map model=processNodeModelDao.getModelByPnId(arr[arr.length-1]);
    Integer maxactive=processAuditStatusDao.getMaxActive(lpId, pdId);
    String auditdate=UtilCommon.currentDateTime();
    ProcessAuditStatus processAuditStatus=new ProcessAuditStatus();
    processAuditStatus.setLpId(Integer.valueOf(lpId));
    processAuditStatus.setMaxActive(maxactive);
    processAuditStatus.setAudit_name(user.getUsername());
    processAuditStatus.setAuditId(user.getId());
    processAuditStatus.setAuditDeptid(user.getDepartments().get(0).getId());
    processAuditStatus.setCreateid(user.getId());
    processAuditStatus.setAuditdate(auditdate);
    processAuditStatus.setComment("process start");
    processAuditStatus.setProcessStatus(0);
    processAuditStatus.setPdnId(model.get("pdnId").toString());
    processAuditStatus.setStep("0");
    processAuditStatus.setStatus(1);
    processAuditStatusDao.save(processAuditStatus);
    
    
    maxactive=processAuditDao.getMaxActive(lpId, pdId);
    
   
    
    ProcessAudit processAudit=new ProcessAudit();
    processAudit.setFromnode(model.get("id").toString());
    processAudit.setCreateid(user.getId());
    processAudit.setCreatetime(auditdate);
    processAudit.setLpId(Integer.valueOf(lpId));
    processAudit.setPasId(processAuditStatus.getId());
    processAudit.setPdId(pdId);
    processAudit.setMaxActive(maxactive);
    processAudit.setStatus(1);
    processAudit.setTonode(model.get("pid").toString());
    processAuditDao.save(processAudit);
    
    processAuditStatusDao.updateProcessStatus(lpId,pdId,2);
    */
  }catch(Exception e){
    e.printStackTrace();
  }
  
}




  
}
