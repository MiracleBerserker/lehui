package miracle.cherry.lehui.common.service;

import miracle.cherry.lehui.common.config.MyConfig;
import miracle.cherry.lehui.common.dao.*;
import miracle.cherry.lehui.common.entity.*;
import miracle.cherry.lehui.moban.dao.MailDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-17 21:49
 * @Modified:
 * @Description:
 */
@Transactional//事务管理
@Service("privageService")
public class PrivilegeService {
    @Resource
    PrivilegeDao privilegeDao;
    @Resource
    UserUnitRelDao userUnitRelDao;
    @Resource
    UserDao userDao;
    @Resource
    RoleUserDao roleUserDao;
    @Resource
    UnitDao unitDao;
    @Resource
    RolePrivilegeDao rolePrivilegeDao;
    @Resource
    RoleDao roleDao;
    @Resource
    MailDao mailDao;
    @Resource
    SourceMaterialDao sourceMaterialDao;
    @Resource
    SystemHelpDao systemHelpDao;
    @Resource
    CostDao costDao;
    @Resource
    PaymentDao paymentDao;
    @Resource
    FlowBillsDao flowBillsDao;
    @Resource
    FeedbackDao feedbackDao;
    @Resource
    MyConfig myConfig;

    /**
     * 保存权限
     * @param privilege
     * @return
     */
    public Privilege savePrivilege(Privilege privilege){
        return  privilegeDao.save(privilege);
    }

    /**
     * 通过权限id删除权限
     * @param id
     */
    public void deletePrivilege(Integer id){
        privilegeDao.deleteById(id);
    }

    public List<User> findAll(Integer uid){
        List<User> list = new ArrayList<>();
        List<UserUnitRel> userUnitRels = userUnitRelDao.findAllByUnitId(uid);
        for(UserUnitRel u : userUnitRels){
           list.add(userDao.findById(u.getUserId()).get()) ;
        }
        return list;
    }

    public List<Unit> findUnit(Integer uid) throws Exception {
        //判断是否是乐汇系统管理员工
        List<RoleUser> roleUsers = roleUserDao.findAllByUId(uid);
        boolean flag = false;
        for(RoleUser r: roleUsers){
            if (roleDao.getOne(r.getrId()).getRoleName().equals("乐汇")){
               flag = true;
            }
        }
        if(!flag){
            throw new Exception("你不是乐汇系统管理员 禁止审核");
        }
        //返回正在审核中状态的机构
        return unitDao.findAllByState("审核中");
    }

    /**
     * 根据角色id获取用户的所有权限
     * @param uId
     * @return
     */
    public Map<String,List<Privilege>> getAllMenu(Integer uId,Integer rId){
        Map<String,List<Privilege>> map = new HashMap<>();
            List<RolePrivilege> rolePrivileges = rolePrivilegeDao.findAllByRId(rId);
            for(RolePrivilege rolePrivilege : rolePrivileges){
               Privilege privilege = privilegeDao.findById(rolePrivilege.getpId()).get();
               if (privilege == null ||privilege.getType()==null||privilege.getType().equals("")){
                   continue;
               }
               if(map.get(privilege.getType()) != null){
                   map.get(privilege.getType()).add(privilege);
               }else {
                   map.put(privilege.getType(), new ArrayList<Privilege>());
                   map.get(privilege.getType()).add(privilege);
               }
            }
        return map;
    }


    /**
     * 获取用户全部的角色
     * @param uId
     * @return
     */
    public List<Role> getUserRole(Integer uId){
        List<RoleUser> roleUsers = roleUserDao.findAllByUId(uId);
        List<Role> roles = new ArrayList<>();
        for(RoleUser roleUser : roleUsers){
            roles.add(roleDao.findById(roleUser.getrId()).get());
        }
        return roles;
    }

    /**
     * 增加角色
     * @return
     */
    public Role addRole(Role role){
        return role;
    }

    public Integer getMailNum(Integer userId){
        Integer num = mailDao.findAllByUserId(userId).size();
        return num;
    }

    /**
     * 保存素材模版
     * @param sourceMaterial
     * @return
     */
    public SourceMaterial saveSourceMaterial(SourceMaterial sourceMaterial){
        if("".equals(sourceMaterial.getType())||"".equals(sourceMaterial.getModel())){
            return null;
        }
        sourceMaterialDao.save(sourceMaterial);
        return sourceMaterial;
    }

    /**
     * 根据id删除素材模版
     * @param id
     * @return
     */
    public SourceMaterial deleteSourceMaterial(Integer id){
        if(id!=null){
            SourceMaterial sourceMaterial = sourceMaterialDao.findById(id).get();
            if (sourceMaterial!=null){
                sourceMaterialDao.delete(sourceMaterial);
                return sourceMaterial;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    /**
     * 返回全部
     * @return
     */
    public List<SourceMaterial> getAllSource(){
        return sourceMaterialDao.findAll();
    }

    /**
     * 返回指定类型
     * @param type
     * @param model
     * @return
     */
    public Map<String,List<SourceMaterial>> getAllSourceByTypeAndModel(String type,String model){
        if ("".equals(model)){
            return null;
        }
        List<SourceMaterial> materials = null;
        if (type==null||"".equals(type)){
            materials = sourceMaterialDao.findAllByModel(model);
        }else {
            materials = sourceMaterialDao.findAllByTypeAndModel(type,model);
        }
        //进行简单的分组
        Map<String,List<SourceMaterial>> map = new HashMap<>();
        for(SourceMaterial sourceMaterial : materials){
            if(map.get(sourceMaterial.getType())!=null){
                map.get(sourceMaterial.getType()).add(sourceMaterial);
            }else {
                map.put(sourceMaterial.getType(),new ArrayList<>());
                map.get(sourceMaterial.getType()).add(sourceMaterial);
            }
        }

        return map;
    }

    /**
     * 保存系统帮助
     * @param systemHelp
     * @return
     */
    public SystemHelp saveSystemHelp(SystemHelp systemHelp){
        systemHelpDao.save(systemHelp);
        return systemHelp;
    }

    /**
     * 根据id删除系统帮助
     * @param id
     * @return
     */
    public SystemHelp deleteSystemHelp(Integer id){
        SystemHelp systemHelp = systemHelpDao.findById(id).get();
        if (systemHelp!=null){
            systemHelpDao.delete(systemHelp);
        }
        return systemHelp;
    }

    public List<SystemHelp> getAllSystemHelp(String type,String problem){
        List<SystemHelp> systemHelps = null;
        if((type==null||"".equals(type))&&(problem==null||"".equals(problem))){
            systemHelps = systemHelpDao.findAll();
        }else if(type==null||"".equals(type)){
            systemHelps = systemHelpDao.findAllByProblem(problem);
        }else {
            systemHelps = systemHelpDao.findAllByTypeAndProblem(type,problem);
        }

        return systemHelps;
    }


    /**
     * 保存花费项目
     * @param cost
     * @return
     * @throws Exception
     */
    public Cost saveCost(Cost cost) throws Exception {
        if(cost.getUnitType()==null||"".equals(cost.getUnitType())){
            throw new Exception("unitType 不能为空 必须是企业  或者 商会");
        }
        if(cost.getType()==null||"".equals(cost.getType())){
            throw new Exception("type 不能为空 必须写明是那种类型的付费项目");
        }
        cost.setStatus(Cost.STATUS_NORMAL);
        String mu = cost.getMoney()+"";
        if(cost.getMeasurement().equals(Cost.STATUS_COSTUNIT_DAY)){
            mu+="元/"+cost.getNums()+"天";
        }else if(cost.getMeasurement().equals(Cost.STATUS_COSTUNIT_MONTH)){
            mu+="元/"+cost.getNums()+"月";
        }else if(cost.getMeasurement().equals(Cost.STATUS_COSTUNIT_YEAR)){
            mu+="元/"+cost.getNums()+"年";
        }
        cost.setMoneymea(mu);
        costDao.save(cost);
        return cost;
    }

    public Cost closeCost(Integer id){
        Cost cost = costDao.findById(id).get();
        cost.setStatus(Cost.STATUS_SHIXIAO);
        costDao.save(cost);
        return cost;
    }

    public Cost updateCost(Cost cost){
        if(cost.getId()==null){
            return null;
        }
        Cost cc = costDao.getOne(cost.getId());
        if(cc==null){
            return null;
        }
        costDao.save(cost);
        return cost;
    }

    public Cost getCost(Integer id){
        return costDao.findById(id).get();
    }

    public List<Cost> findAllCostByTypeAndUnitType(String type,String unitType,String status){
        List<Cost> costs = null;
        if(unitType==null||"".equals(unitType)){
            costs = costDao.findAllByTypeAndStatus(type,status);
        }else {
            costs = costDao.findAllByTypeAndStatusAndUnitType(type,status,unitType);
        }
        return costs;
    }


    public Payment savePayment(Payment payment,User user) throws Exception {
        Unit unit = unitDao.findById(payment.getUnitId()).get();
        Cost cost = costDao.findById(payment.getCostId()).get();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(cost==null||unit==null){
            throw new Exception("支付出现故障无法正常支付");
        }
        //查询是否是重复购买支付
        Payment pp = paymentDao.findByUnitIdAndCostId(payment.getUnitId(),payment.getCostId());
        if(pp!=null){
            calendar.setTime(simpleDateFormat.parse(pp.getEndTime()));
            payment.setId(pp.getId());
        }else {
            payment.setStartTime(simpleDateFormat.format(new Date()));
        }
        if(cost.getMeasurement().equals(Cost.STATUS_COSTUNIT_YEAR)){
            calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)+cost.getNums());
        }else if(cost.getMeasurement().equals(Cost.STATUS_COSTUNIT_MONTH)){
            calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)+cost.getNums());
        }else if(cost.getMeasurement().equals(Cost.STATUS_COSTUNIT_DAY)){
            calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+cost.getNums());
        }else {
            return null;
        }
        payment.setEndTime(simpleDateFormat.format(calendar.getTime()));
        payment.setName(unit.getName());
        payment.setImg(user.getImg());
        payment.setWechat(user.getWechat());
        payment.setTel(user.getAccount());
        payment.setUnitTel(unit.getTel());
        payment.setContent(cost.getName());
        payment.setType(cost.getType());
        paymentDao.save(payment);
        //生成流水账单
        FlowBills flowBills = new FlowBills();
        flowBills.setCostId(payment.getCostId());
        flowBills.setMoney(cost.getMoney());
        flowBills.setUnitId(unit.getId());
        flowBills.setName(cost.getName());
        flowBills.setCreateTime(simpleDateFormat.format(new Date()));
        flowBills.setPaymentId(payment.getId());
        flowBills.setUserName(user.getName());
        flowBills.setUnitName(unit.getName());
        flowBills.setCostName(cost.getName());
        flowBills.setUserId(user.getId());
        flowBills.setOrderNumber(UUID.randomUUID().toString().replaceAll("-",""));
        flowBillsDao.save(flowBills);
        return payment;

    }

    public List<Payment> findPayment(Integer costId,Integer unitId){
        List<Payment> payments = null;
        if(costId!=null&&unitId!=null){
            payments = paymentDao.findAllByCostIdAndUnitId(costId,unitId);
        }else if(costId!=null){
            payments = paymentDao.findAllByCostId(costId);
        }else if(unitId!=null){
            payments = paymentDao.findAllByUnitId(unitId);
        }else {
            payments = paymentDao.findAll();
        }
        return payments;
    }

    public List<Payment> findPayment(String type,String unitName){
        List<Payment> payments = null;
        if(type!=null&&unitName!=null){
            payments = paymentDao.findAllByTypeAndName(type,unitName);
        }else if(type!=null){
            payments = paymentDao.findAllByType(type);
        }
        return payments;
    }

    public List<FlowBills> findAllFlowBills(Integer unitId,Integer costId){
        List<FlowBills> list = null;
        if(unitId!=null&&costId!=null){
            list = flowBillsDao.findAllByUnitIdAndCostId(unitId,costId);
        }else if(unitId!=null){
            list = flowBillsDao.findAllByUnitId(unitId);
        }else if(costId!=null){
            list = flowBillsDao.findAllByCostId(costId);
        }else {
            list = flowBillsDao.findAll();
        }
        return list;
    }

    public List<FlowBills> findAllFlowBillsByUserId(Integer userId){
        if(userId!=null){
            return flowBillsDao.findAllByUserId(userId);
        }else {
            return null;
        }
    }

    public List<FlowBills> findAllFlowBillsByPayId(Integer payId){
        if(payId!=null){
            return flowBillsDao.findAllByPaymentId(payId);
        }else {
            return null;
        }
    }


    public String addSite(String site,Integer paymentId){
        if(site==null||"".equals(site)){
            return null;
        }
        //保存到payment
        Payment payment = paymentDao.findById(paymentId).get();
        payment.setUrl(myConfig.getHttp()+site.trim()+myConfig.getIndex());
        paymentDao.save(payment);
        //保存到unit
        Unit unit = unitDao.findById(payment.getUnitId()).get();
        unit.setUrl(myConfig.getHttp()+site.trim()+myConfig.getIndex());
        unitDao.save(unit);
        return site;
    }

    public Feedback addFeedback(Feedback feedback,User user){
       if(feedback.getId()!=null){
           return null;
       }
       Unit unit = unitDao.findById(feedback.getUnitId()).get();
       if(unit==null){
           return null;
       }
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       feedback.setDate(simpleDateFormat.format(new Date()));
       feedback.setUserName(user.getName());
       feedback.setUnitId(unit.getId());
       feedback.setUnitName(unit.getName());
       feedback.setTel(unit.getTel());
       feedbackDao.save(feedback);
       return feedback;
    }

    public Feedback deleteFeedback(Integer id){
        Feedback feedback = feedbackDao.findById(id).get();
        if(feedback!=null){
            feedbackDao.delete(feedback);
        }
        return feedback;
    }

    public List<Feedback> getAllFeedback(){
        return feedbackDao.findAll();
    }

}
