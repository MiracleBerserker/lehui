package miracle.cherry.lehui.common.service;

import miracle.cherry.lehui.common.dao.*;
import miracle.cherry.lehui.common.entity.*;
import miracle.cherry.lehui.moban.dao.MailDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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
    @Resource
    MailDao mailDao;
    @Resource
    SourceMaterialDao sourceMaterialDao;
    @Resource
    SystemHelpDao systemHelpDao;


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


}
