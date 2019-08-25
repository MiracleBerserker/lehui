package miracle.cherry.lehui.common.service;

import io.swagger.models.auth.In;
import miracle.cherry.lehui.common.config.MyConfig;
import miracle.cherry.lehui.common.dao.*;
import miracle.cherry.lehui.common.entity.*;
import miracle.cherry.lehui.moban.dao.MailDao;
import miracle.cherry.lehui.moban.service.MenuService;
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
    @Resource
    AccountDao accountDao;
    @Resource
    BankCardDao bankCardDao;
    @Resource
    WithdrawalDao withdrawalDao;

    @Resource
    MenuService menuService;
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(cost.getUnitType()==null||"".equals(cost.getUnitType())){
            throw new Exception("unitType 不能为空 必须是企业  或者 商会");
        }
        if(cost.getType()==null||"".equals(cost.getType())){
            throw new Exception("type 不能为空 必须写明是那种类型的付费项目");
        }
        cost.setStatus(Cost.STATUS_NORMAL);
        cost.setMoney(Math.abs(cost.getMoney()));
        String mu = cost.getMoney()+"";
        if(cost.getMeasurement().equals(Cost.STATUS_COSTUNIT_DAY)){
            mu+="元/"+cost.getNums()+"天";
        }else if(cost.getMeasurement().equals(Cost.STATUS_COSTUNIT_MONTH)){
            mu+="元/"+cost.getNums()+"月";
        }else if(cost.getMeasurement().equals(Cost.STATUS_COSTUNIT_YEAR)){
            mu+="元/"+cost.getNums()+"年";
        }
        cost.setMoneymea(mu);
        cost.setStartDateTime(simpleDateFormat.format(new Date()));
        costDao.save(cost);
        return cost;
    }

    public Cost closeCost(Integer id){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Cost cost = costDao.findById(id).get();
        cost.setStatus(Cost.STATUS_SHIXIAO);
        cost.setEndDateTime(simpleDateFormat.format(new Date()));
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(cost==null||unit==null){
            throw new Exception("支付出现故障无法正常支付");
        }
        //查询是否是重复购买支付
        Payment pp = paymentDao.findByUnitIdAndCostId(payment.getUnitId(),payment.getCostId());
        if(pp!=null){
            calendar.setTime(simpleDateFormat.parse(pp.getEndTime()));
            payment.setId(pp.getId());
            payment.setStartTime(pp.getStartTime());
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
        String url = "";
        Payment payment = paymentDao.findById(paymentId).get();
        if(site==null||"".equals(site)){
            url = myConfig.getUrl()+payment.getUnitId();
        }else {
            url = myConfig.getHttp()+site.trim()+myConfig.getIndex()+payment.getUnitId();
        }
        //保存到payment

        payment.setUrl(url);
        paymentDao.save(payment);
        //保存到unit
        Unit unit = unitDao.findById(payment.getUnitId()).get();
        unit.setUrl(url);
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
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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


    /**
     * 获取用户账户 如果没有账户就默认创建
     * @param user
     * @return
     */
    public Account getAccount(User user){
        if(user.getId()==null){
            return null;
        }
        //获取当前用户账号
        Account account = accountDao.getByUserId(user.getId());
        if(account == null){
            account = new Account();
            account.setUserId(user.getId());
            account.setBalance(0);
            account.setState(Account.ACCOUNT_STATE_NORMAL);
            accountDao.save(account);
        }
        return account;
    }

    /**
     * 保存银行卡
     * @param card
     * @param user
     * @return
     */
    public BankCard addBankCard(BankCard card,User user){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        card.setDateTime(simpleDateFormat.format(new Date()));
        card.setUserId(user.getId());
        bankCardDao.save(card);
        return card;
    }

    public BankCard getBankCardById(Integer id){
        BankCard card = bankCardDao.findById(id).get();
        return card;
    }

    public BankCard deleteBankCard(Integer id,Integer userId){
        if(id==null){
            return null;
        }
        BankCard card = bankCardDao.findByIdAndUserId(id,userId);
        bankCardDao.delete(card);
        return card;
    }

    public List<BankCard> findAllBankCard(Integer userId){
        List<BankCard> bankCards = bankCardDao.findAllByUserId(userId);
        return bankCards;
    }

    public Withdrawal addWithdrawal(Withdrawal withdrawal,User user) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Account account = accountDao.findByUserIdAndState(user.getId(),Account.ACCOUNT_STATE_NORMAL);
        withdrawal.setWithdrawMoney(Math.abs(withdrawal.getWithdrawMoney()));
        if(account==null){
            throw new Exception("账户不存在或者异常 无法进行操作");
        }
        if(withdrawal.getType().equals(Withdrawal.WITHDRAWAL_TYPE_ZC)){
           if(withdrawal.getWithdrawMoney()>account.getBalance()){
               throw new Exception("你的余额不足 无法申请提现");
           }
            BankCard bankCard = bankCardDao.findById(withdrawal.getBankCardId()).get();
            if(bankCard==null){
                throw new Exception("无法查询到银行卡信息 无法进行操作");
            }
            withdrawal.setStartDateTime(simpleDateFormat.format(new Date()));
            withdrawal.setBankCard(bankCard.getBankNumber());
            withdrawal.setBankName(bankCard.getBankName());
            withdrawal.setBankTel(bankCard.getTel());
            withdrawal.setState(Withdrawal.WITHDRAWAL_STATE_SHZ);
            withdrawal.setTel(user.getAccount());
            withdrawal.setName(user.getName());
            withdrawal.setUserId(user.getId());
            account.setBalance(account.getBalance()-withdrawal.getWithdrawMoney());
            withdrawal.setBalance(account.getBalance());
            accountDao.save(account);
        }else{
            throw new Exception("非法类型禁止操作");
        }
        withdrawalDao.save(withdrawal);
        return withdrawal;
    }

    /**
     * 更新提现状态
     */
    public void updateWithdrawal(String state,Integer id,User user) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(state==null){
            return;
        }
        Withdrawal withdrawal = withdrawalDao.findById(id).get();
        if(withdrawal==null){
            throw new Exception("不存在该申请 无法进行操作");
        }
        if(withdrawal.getState().equals(Withdrawal.WITHDRAWAL_STATE_FAIL)&&state.equals(Withdrawal.WITHDRAWAL_STATE_SHZ)){
            withdrawal.setState(state);
            Account account = accountDao.getByUserId(withdrawal.getUserId());
            account.setBalance(account.getBalance()-withdrawal.getWithdrawMoney());
            accountDao.save(account);
            withdrawal.setBalance(account.getBalance());
            withdrawal.setStartDateTime(simpleDateFormat.format(new Date()));
            withdrawalDao.save(withdrawal);
            return;
        }

        if(user.getShId()!=Unit.ADMIN_SHID){
            throw new Exception("非法操作行为 请停止操作");
        }
        if(Withdrawal.WITHDRAWAL_STATE_SHZ.equals(withdrawal.getState())&&state.equals(Withdrawal.WITHDRAWAL_STATE_CLZ)){
            withdrawal.setState(state);
        }else if(Withdrawal.WITHDRAWAL_STATE_CLZ.equals(withdrawal.getState())&&state.equals(Withdrawal.WITHDRAWAL_STATE_NORMAL)) {
            withdrawal.setState(state);
        } else if(Withdrawal.WITHDRAWAL_STATE_SHZ.equals(withdrawal.getState())&&state.equals(Withdrawal.WITHDRAWAL_STATE_FAIL)){
            Account account = accountDao.getByUserId(withdrawal.getUserId());
            account.setBalance(account.getBalance()+withdrawal.getWithdrawMoney());
            accountDao.save(account);
            withdrawal.setState(state);
            //发送失败邮件
            menuService.sendMail("尊敬的用户,非常抱歉您于"+withdrawal.getStartDateTime()+",申请提现"+withdrawal.getWithdrawMoney()
                    +"元,没有通过审核 如果有疑问请咨询乐汇公司",withdrawal.getUserId());
        }else if(Withdrawal.WITHDRAWAL_STATE_CLZ.equals(withdrawal.getState())&&state.equals(Withdrawal.WITHDRAWAL_STATE_FAIL)){
            Account account = accountDao.getByUserId(withdrawal.getUserId());
            account.setBalance(account.getBalance()+withdrawal.getWithdrawMoney());
            accountDao.save(account);
            withdrawal.setState(state);
            //发送失败邮件
            menuService.sendMail("尊敬的用户,非常抱歉您于"+withdrawal.getStartDateTime()+",申请提现"+withdrawal.getWithdrawMoney()
                    +"元,没有通过审核 如果有疑问请咨询乐汇公司",withdrawal.getUserId());
        }else {
            throw new Exception("非法操作行为 请停止操作");
        }

        withdrawalDao.save(withdrawal);
    }

    public Withdrawal getWithdrawal(Integer id,User user){
        Withdrawal withdrawal = null;
        if(user.getShId()!=Unit.ADMIN_SHID){
            withdrawal = withdrawalDao.findById(id).get();
        }else {
            withdrawal = withdrawalDao.findByIdAndUserId(id,user.getId());
        }

        return withdrawal;
    }

    public List<Withdrawal> findAllWithdrawal(String type,String state,String name,User user) throws Exception {
        if(user.getShId()!=Unit.ADMIN_SHID){
            throw new Exception("非法操作行为 请停止操作");
        }
        List<Withdrawal> withdrawals = null;
        if(type ==null || state==null){
            return null;
        }
        if(name==null){
            withdrawals= withdrawalDao.findAllByStateAndType(state,type);
        }else {
            withdrawals = withdrawalDao.findAllByStateAndTypeAndName(state,type,name);
        }
        return withdrawals;
    }


    public List<Withdrawal> findAllWithdrawalByUser(Integer userId){
        List<Withdrawal> withdrawals = null;
        withdrawals = withdrawalDao.findAllByUserId(userId);
        return withdrawals;
    }

    /**
     * 统计一些基本信息
     * @return
     */
    public Map<String,String> getReport(){
        Map<String,String> map = new HashMap<>();
        map.put("dayMoney",userDao.getDayMoney().get("num"));
        map.put("monthMoney",userDao.getMonthMoney().get("num"));
        map.put("allMoney",userDao.getAllMoney().get("num"));
        map.put("dayUser",userDao.getDayUser().get("num"));
        map.put("monthUser",userDao.getMonthUser().get("num"));
        map.put("allUser",userDao.getAllUser().get("num"));
        return map;
    }

}
