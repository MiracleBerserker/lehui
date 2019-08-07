package miracle.cherry.lehui.common.service;

import miracle.cherry.lehui.common.dao.*;
import miracle.cherry.lehui.common.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 6:30
 * @Modified:
 * @Description:
 */
@Transactional//事务管理
@Service("unitService")
public class UnitService {
    @Resource
    UnitDao unitDao;
    @Resource
    RoleUserDao roleUserDao;
    @Resource
    UserUnitRelDao userUnitRelDao;
    @Resource
    UserDao userDao;
    @Resource
    RoleDao roleDao;
    public List<Unit> finaAll(){
        return unitDao.findAll();
    }
    public List<Unit> findAllByType(String type){
        return unitDao.findAllByTypeAndState(type,"正常");
    }

    public void acceptCreateUnit(Integer unitId,Integer uId) throws Exception {
        //先判断是否具有审核权限
        List<RoleUser> roleUsers = roleUserDao.findAllByUId(uId);
        boolean flag = false;
        for(RoleUser r: roleUsers){
            if (roleDao.getOne(r.getrId()).getRoleName().equals("乐汇")){
                flag = true;
            }
        }
        if(!flag){
            throw new Exception("你不是乐汇系统管理员 禁止审核");
        }
        //改变机构状态值
        Unit unit = unitDao.getOne(unitId);
        if(unit.getState().equals("正常")){
            throw new Exception("该企业已经成功通过审核 不允许重复审核");
        }
        unit.setState("正常");
        unit.setUrl("http://47.107.241.29:8756/index/"+unit.getId());
        //设置创建人机构和删除中间表
        UserUnitRel userUnitRel = userUnitRelDao.findByUnitId(unitId);
        User user = userDao.getOne(userUnitRel.getUserId());
        RoleUser roleUser = new RoleUser();

        if(unit.getType().equals("企业")){
            user.setQyId(unitId);
            roleUser.setrId(1);
            unit.setTemplate("template_v1");
        }else {
            user.setShId(unitId);
            roleUser.setrId(2);
            unit.setTemplate("template_v2");
        }

        roleUser.setuId(user.getId());
        roleUserDao.save(roleUser);
        userDao.save(user);
        userUnitRelDao.deleteByUnitIdAndUserId(unitId,user.getId());
        unitDao.save(unit);
    }

    public void refuseCreateUnit(Integer unitId,Integer uId) throws Exception {
        //先判断是否具有审核权限
        List<RoleUser> roleUsers = roleUserDao.findAllByUId(uId);
        boolean flag = false;
        for(RoleUser r: roleUsers){
            if (roleDao.getOne(r.getrId()).getRoleName().equals("乐汇")){
                flag = true;
            }
        }
        if(!flag){
            throw new Exception("你不是乐汇系统管理员 禁止审核");
        }
        //改变机构状态值
        Unit unit = unitDao.getOne(unitId);

        //设置创建人机构和删除中间表
        UserUnitRel userUnitRel = userUnitRelDao.findByUnitId(unitId);
        userUnitRelDao.delete(userUnitRel);
        unitDao.delete(unit);
    }



    public Unit getById(Integer uId){
        return  unitDao.findById(uId).get();
    }

    /**
     * 修改机构信息
     * @param unit
     * @return
     */
    public Unit editUnit(Unit unit){
        Unit unitRel = unitDao.findById(unit.getId()).get();
        unitRel.setContacts(unit.getContacts());
        unitRel.setTel(unit.getTel());
        unitRel.setRegion(unit.getRegion());
        unitRel.setName(unit.getName());
        unitDao.save(unitRel);
        return unitRel;
    }
    /**
     * 根据社会统一信用代码查询是否已经存在该机构
     * @param code
     * @return
     */
    public Unit findUnitByCode(String code){
        return unitDao.findByCode(code);
    }

    /**
     * 申请加入企业或者商会
     */
    public void applyUnit(Integer unitId,Integer userId) throws Exception {
        UserUnitRel userUnitRel = new UserUnitRel();
        //先判断是否重复申请
       UserUnitRel userUnitRell =  userUnitRelDao.findByUnitIdAndUserId(unitId,userId);
       if(userUnitRell!=null&&userUnitRell.getId()!=null){
           throw new Exception("请不要重复申请 耐心等待审核");
       }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
       userUnitRel.setState("审核中");
       userUnitRel.setCreateTime(createTime);
       userUnitRel.setUserId(userId);
       userUnitRel.setUnitId(unitId);
       userUnitRelDao.save(userUnitRel);
    }

}
