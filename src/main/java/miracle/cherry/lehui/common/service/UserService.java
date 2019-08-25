package miracle.cherry.lehui.common.service;

import io.swagger.models.auth.In;
import miracle.cherry.lehui.common.config.MyConfig;
import miracle.cherry.lehui.common.dao.*;
import miracle.cherry.lehui.common.entity.*;
import miracle.cherry.lehui.common.tools.FileTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 5:58
 * @Modified:
 * @Description:
 */
@Transactional//事务管理
@Service("userService")
public class UserService {

    @Resource
    UserDao userDao;
    @Resource
    UnitDao unitDao;
    @Resource
    UserUnitRelDao userUnitRelDao;
    @Resource
    PrivilegeDao privilegeDao;
    @Resource
    RoleUserDao roleUserDao;


    @Resource
    MyConfig myConfig;

    public List<User> findAll(){
        return userDao.findAll();
    }

    public User checkUser(String account,String password) throws Exception {
        User user = userDao.findByAccountAndPassword(account,password);
        if (user==null){
            throw new Exception("账号密码不存在");
        }
        if (user.getState().equals(User.STATE_RELOGIN)){
            user.setState(User.STATE_NORMAL);
            userDao.save(user);
        }
        if(!user.getState().equals(User.STATE_NORMAL)){
            throw new Exception("用户已经被禁止登录 请联系管理员");
        }
        return user;
    }


    public User register(User user) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //检测账号是否已经存在
        if(userDao.findByAccount(user.getAccount()) != null){
            throw new Exception("用户已存在");
        }
        Integer qyId= user.getQyId();
        Integer shId = user.getShId();
        user.setShId(null);
        user.setQyId(null);
        //保存用户
        user.setState("正常");
        user.setRegisterTime(simpleDateFormat.format(new Date()));
        user.setImg(myConfig.getDefaultImg());
        userDao.save(user);
        //保存文件替换地址
        if(qyId != null){
            if(unitDao.findById(qyId) != null){
                saveUURel(qyId,user.getId(),"审核中");
            }else {
                throw new Exception("找不到申请的企业");
            }
        }else if(shId != null){
            if(unitDao.findById(shId) != null){
                saveUURel(shId,user.getId(),"审核中");
            }else {
                throw new Exception("找不到申请的商会");
            }
        }else if(user.getQy() != null){
            Unit unit = saveUnit(user.getQy(),"企业","审核中");
            saveUURel(unit.getId(),user.getId(),"创建中");
            unit.setArrayFiles(null);
            user.setQy(unit);
        }else if(user.getSh() != null){
            Unit unit = saveUnit(user.getSh(),"商会","审核中");
            saveUURel(unit.getId(),user.getId(),"创建中");
            unit.setArrayFiles(null);
            user.setSh(unit);
        }else {
            throw new Exception("必须选择或者创建企业或商会");
        }
        //
        return user;
    }


    public User saveUser(User user) throws Exception {
        //检测账号是否已经存在
        if(userDao.findByAccount(user.getAccount()) != null){
            throw new Exception("用户已存在");
        }
        //保存用户
        user.setState("正常");
        user.setImg(myConfig.getDefaultImg());
        user.setShId(Unit.ADMIN_SHID);
        userDao.save(user);
        RoleUser roleUser = roleUserDao.findByUIdAndUnitId(user.getId(),Unit.ADMIN_SHID);
        if(roleUser == null){
            roleUser = new RoleUser();
        }
        roleUser.setuId(user.getId());
        roleUser.setUnitId(Unit.ADMIN_SHID);
        roleUser.setrId(Role.ADMIN_MANAGER_ROLE);
        roleUserDao.save(roleUser);
        return user;
    }



    public UserUnitRel saveUURel(Integer unId, Integer usId,String state) throws Exception {
        if(userUnitRelDao.findByUnitIdAndUserId(unId,usId) != null){
            throw new Exception("禁止重复申请");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UserUnitRel userUnitRel = new UserUnitRel();
        userUnitRel.setUnitId(unId);
        userUnitRel.setUserId(usId);
        userUnitRel.setCreateTime(simpleDateFormat.format(new Date()));
        userUnitRel.setState(state);
        userUnitRelDao.save(userUnitRel);
        return userUnitRel;
    }

    public Unit saveUnit(Unit unit,String types,String state) throws Exception {
        if(unitDao.findByName(unit.getName()) != null){
            throw new Exception("企业或商会已经存在不允许重复创建");
        }
        StringBuffer sb = new StringBuffer();
        for(String s : unit.getArrayFiles()){
            sb.append(s);
            sb.append("##");
        }
        unit.setFiles(sb.toString());
        unit.setType(types);
        unit.setState(state);
        unitDao.save(unit);
        return unit;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = FileTools.getFileName(file.getOriginalFilename());
        String guid = UUID.randomUUID().toString();
        String type = fileName.substring(fileName.lastIndexOf("."));
        //获取当前日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowDay = simpleDateFormat.format(new Date());
        String filePath = String.format("%s%s%s%s%s",myConfig.getImgPath(),nowDay,"/",guid,type);
        FileTools.saveFile(filePath,file.getInputStream());
        return "/img/"+nowDay+"/"+guid+type;
    }

    public void accept(Integer id,Integer uid,String type){
        User user = userDao.getOne(uid);

        //先查询是否已经有角色了 有的话就先干掉在分配
        RoleUser roleUserBack = roleUserDao.findByUIdAndUnitId(uid,id);
        if(roleUserBack!=null){
            roleUserDao.delete(roleUserBack);
        }

        //添加对应的角色
        RoleUser roleUser = new RoleUser();
        if(type.equals("企业")){
           user.setQyId(id);
           roleUser.setrId(Role.QY_ROLE);
           roleUser.setuId(uid);
        }else {
            user.setShId(id);
            roleUser.setrId(Role.SHHY_ROLE);
            roleUser.setuId(uid);
        }
        roleUser.setUnitId(id);
        userDao.save(user);
        roleUserDao.save(roleUser);
        //删除申请表对应数据
        userUnitRelDao.deleteByUnitIdAndUserId(id,uid);
    }


    public  void delete(Integer id,Integer uid,String type){
        //删除申请表对应数据
        userUnitRelDao.deleteByUnitIdAndUserId(id,uid);
    }

    public void uploadImg(String img,Integer userId){
        User user= userDao.getOne(userId);
        user.setImg(img);
        userDao.saveAndFlush(user);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public User editUser(User user){
        User userRel = userDao.findById(user.getId()).get();
        if(user.getName()!=null){
            userRel.setName(user.getName());
        }
        if(user.getMail()!=null){
            userRel.setMail(user.getMail());
        }
        if(user.getWechat()!=null){
            userRel.setWechat(user.getWechat());
        }
        if(user.getPassword()!=null){
            userRel.setPassword(user.getPassword());
        }
        if(user.getAccount()!=null){
            userRel.setAccount(user.getAccount());
        }
        userRel = userDao.saveAndFlush(userRel);
        return userRel;
    }

    public User getUserById(Integer id){
        User user = userDao.findById(id).get();
        return user;
    }

    /**
     * 刷新当前用户信息
     * @param user
     * @return
     */
    public User updateUserInfo(User user){
        //重置用户基础信息
        user = userDao.findById(user.getId()).get();
        //重置用户所属单位
        if(user.getQyId()!=null){
            user.setQy(unitDao.findById(user.getQyId()).get());
        }else if(user.getShId()!=null){
            user.setSh(unitDao.findById(user.getShId()).get());
        }
        return user;
    }


    public User updateUserState(String state,Integer userId){
        if(!state.equals(User.STATE_NORMAL)&&!state.equals(User.STATE_RELOGIN)&&!state.equals(User.STATE_PROHIBIT)){
            return null;
        }else {
            User user = userDao.findById(userId).get();
            user.setState(state);
            userDao.save(user);
            return user;
        }
    }

    public List<User> getAllUserByUnitId(Integer unitId){
        Unit unit = unitDao.findById(unitId).get();
        List<User> users = null;
        if (unit.getType().equals("企业")){
            users = userDao.findAllByQyId(unitId);
        }else {
            users = userDao.findAllByShId(unitId);
        }
        return users;
    }

    public List<User> getAllUser(){
        List<User> users = userDao.findAll();
        return users;
    }

    /**
     * 将用户移除当前组织
     * @param userId
     * @param unitId
     * @return
     */
    public User removeUser(Integer userId,Integer unitId){
        User user = userDao.findById(userId).get();
        RoleUser roleUser = roleUserDao.findByUIdAndUnitId(userId,unitId);
        if(user==null||roleUser==null){
            return null;
        }
        if(user.getQyId()!=null&&Integer.valueOf(user.getQyId())==Integer.valueOf(unitId)){
            user.setQyId(null);
        }else if(user.getShId()!=null&&Integer.valueOf(user.getShId())==Integer.valueOf(unitId)){
            user.setShId(null);
        }
        roleUserDao.delete(roleUser);
        userDao.save(user);
        updateUserState(User.STATE_RELOGIN,userId);
        return user;
    }

    public List<User> findByConditions(Integer unitId,String condition){
        List<User> users = null;
        if(unitId==null && !condition.equals("")){
            users = userDao.findByConditions(condition);
        }else {
            Unit unit = unitDao.findById(unitId).get();
            if(unit.getType().equals("企业")){
                users = userDao.findByConditionsAndQy(unitId,condition);
            }else {
                users = userDao.findByConditionsAndSh(unitId,condition);
            }
        }
       return users;
    }

    public Map<String,String> getUnitUrl(Integer userId){
        User user = userDao.findById(userId).get();
        Map<String,String> map = new HashMap<>();
        if(user.getQyId()!=null){
            Unit unit = unitDao.findById(user.getQyId()).get();
            map.put("企业",unit.getUrl());
        }else if(user.getQyId()==null) {
            map.put("企业",null);
        }

        if(user.getShId()!=null){
            Unit unit = unitDao.findById(user.getShId()).get();
            map.put("商会",unit.getUrl());
        }else if(user.getShId()==null){
            map.put("商会",null);
        }
        return map;
    }


}