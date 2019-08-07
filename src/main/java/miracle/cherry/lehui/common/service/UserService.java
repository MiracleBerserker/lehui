package miracle.cherry.lehui.common.service;

import io.swagger.models.auth.In;
import miracle.cherry.lehui.common.config.MyConfig;
import miracle.cherry.lehui.common.dao.*;
import miracle.cherry.lehui.common.entity.RoleUser;
import miracle.cherry.lehui.common.entity.Unit;
import miracle.cherry.lehui.common.entity.User;
import miracle.cherry.lehui.common.entity.UserUnitRel;
import miracle.cherry.lehui.common.tools.FileTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        if(!user.getState().equals(User.STATE_NORMAL)){
            throw new Exception("用户已经被禁止登录 请联系管理员");
        }
        return user;
    }


    public User register(User user) throws Exception {

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
        user.setImg("/img/icon/default.jpg");
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
//            if(unitDao.findByCode(user.getQy().getCode()) !=null){
//                throw new Exception("该企业已经注册不能重复注册");
//            }
            Unit unit = saveUnit(user.getQy(),"企业","审核中");
            saveUURel(unit.getId(),user.getId(),"创建中");
            unit.setArrayFiles(null);
            user.setQy(unit);
        }else if(user.getSh() != null){
//            if(unitDao.findByCode(user.getSh().getCode()) !=null){
//                throw new Exception("该商会已经注册不能重复注册");
//            }
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
        //添加对应的角色
        RoleUser roleUser = new RoleUser();
        if(type.equals("企业")){
           user.setQyId(id);
           roleUser.setrId(1);
           roleUser.setuId(uid);
        }else {
            user.setShId(id);
            roleUser.setrId(3);
            roleUser.setuId(uid);
        }
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
        userRel.setName(user.getName());
        userRel.setMail(user.getMail());
        userRel.setWechat(user.getWechat());
        userRel.setPassword(user.getPassword());
        userRel = userDao.saveAndFlush(userRel);
        return userRel;
    }

    public User getUserById(Integer id){
        User user = userDao.findById(id).get();
        return user;
    }

}
