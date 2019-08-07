package miracle.cherry.lehui.common.service;

import miracle.cherry.lehui.common.dao.*;
import miracle.cherry.lehui.common.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
