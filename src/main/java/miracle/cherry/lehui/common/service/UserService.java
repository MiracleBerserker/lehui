package miracle.cherry.lehui.common.service;

import miracle.cherry.lehui.common.config.MyConfig;
import miracle.cherry.lehui.common.dao.UnitDao;
import miracle.cherry.lehui.common.dao.UserDao;
import miracle.cherry.lehui.common.dao.UserUnitRelDao;
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
@Service("UserService")
public class UserService {

    @Resource
    UserDao userDao;
    @Resource
    UnitDao unitDao;
    @Resource
    UserUnitRelDao userUnitRelDao;

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
        //保存用户
        user.setState("正常");
        userDao.save(user);
        //保存文件替换地址
        if(user.getQyId() != null){
            if(unitDao.findById(user.getQyId()) != null){
                saveUURel(user.getQyId(),user.getId(),"审核中");
            }else {
                throw new Exception("找不到申请的企业");
            }
        }else if(user.getShId() != null){
            if(unitDao.findById(user.getShId()) != null){
                saveUURel(user.getShId(),user.getId(),"审核中");
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
        String filePath = String.format("%s%s%s",myConfig.getImgPath(),guid,type);
        FileTools.saveFile(filePath,file.getInputStream());
        return guid+type;
    }
}
