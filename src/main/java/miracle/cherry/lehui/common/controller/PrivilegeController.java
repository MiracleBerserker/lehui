package miracle.cherry.lehui.common.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import miracle.cherry.lehui.common.dao.RoleDao;
import miracle.cherry.lehui.common.entity.*;
import miracle.cherry.lehui.common.service.PrivilegeService;
import miracle.cherry.lehui.common.service.UnitService;
import miracle.cherry.lehui.common.service.UserService;
import miracle.cherry.lehui.common.tools.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-20 7:37
 * @Modified:
 * @Description:
 */
@Transactional//事务管理
@Api(description = "加入审核和权限管理")
@RestController
@RequestMapping(value = CommonUrl.PRIVILEGE)
public class PrivilegeController {

    @Resource
    UnitService unitService;
    @Resource
    PrivilegeService privilegeService;
    @Resource
    UserService userService;



    @ResponseBody
    @ApiOperation(value="获取申请加入本企业或商会的用户列表",response = Result.class)
    @RequestMapping(value = "/getUnits",method = RequestMethod.GET)
    public String getUnit(@ApiParam(value = "企业还是商会", required = true)
                                  @RequestParam
                                  String type, HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        List<User> users = null;
        if(type.equals("企业")){
            users = privilegeService.findAll(user.getQyId());
        }else {
            users = privilegeService.findAll(user.getShId());
        }
        List<Unit> units = unitService.findAllByType(type);
        return new Result(Result.SUCCESS, users,"获取成功").toJson();
    }
    @ResponseBody
    @ApiOperation(value="获取创建中的商会和企业列表",response = Result.class)
    @RequestMapping(value = "/getCreateUnits",method = RequestMethod.GET)
    public String getCreateUnits( HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        List<Unit> units = privilegeService.findUnit(user.getId());
        return new Result(Result.SUCCESS, units,"获取成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="同意申请加入本企业或商会",response = Result.class)
    @RequestMapping(value = "/acceptUnit",method = RequestMethod.GET)
    public String acceptUnit(
            @ApiParam(value = "企业还是商会", required = true)
            @RequestParam String type,
            @ApiParam(value = "申请人id", required = true)
            @RequestParam Integer uid,
                             HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if(type.equals("企业")){
            userService.accept(user.getQyId(),uid,"企业");
        }else {
            userService.accept(user.getShId(),uid,"商会");
        }
        return new Result(Result.SUCCESS, "申请通过","申请通过").toJson();
    }

    @ResponseBody
    @ApiOperation(value="拒绝加入本企业或商会",response = Result.class)
    @RequestMapping(value = "/deleteUnit",method = RequestMethod.GET)
    public String deleteUnit(
            @ApiParam(value = "企业还是商会", required = true)
            @RequestParam String type,
            @ApiParam(value = "申请人id", required = true)
            @RequestParam Integer uid,
            HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if(type.equals("企业")){
            userService.delete(user.getQyId(),uid,"企业");
        }else {
            userService.delete(user.getShId(),uid,"商会");
        }
        return new Result(Result.SUCCESS, "拒绝通过","拒绝通过").toJson();
    }



    @ResponseBody
    @ApiOperation(value="同意创建企业或商会",response = Result.class)
    @RequestMapping(value = "/acceptCreateUnit",method = RequestMethod.GET)
    public String acceptCreateUnit(
            @ApiParam(value = "企业或商会id", required = true)
            @RequestParam Integer uid,
            HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        unitService.acceptCreateUnit(uid,user.getId());
        return new Result(Result.SUCCESS, "申请通过","申请通过").toJson();
    }

    @ResponseBody
    @ApiOperation(value="拒绝创建企业或商会",response = Result.class)
    @RequestMapping(value = "/refuseCreateUnit",method = RequestMethod.GET)
    public String refuseCreateUnit(
            @ApiParam(value = "企业或商会id", required = true)
            @RequestParam Integer uid,
            HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        unitService.refuseCreateUnit(uid,user.getId());
        return new Result(Result.SUCCESS, "拒绝创建","拒绝创建").toJson();
    }




    @ResponseBody
    @ApiOperation(value="获取当前用户信息",notes = "不包含权限菜单 菜单需要用其它接口请求",response = Result.class)
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    public String getUserInfo(HttpServletRequest request) throws Exception {
        //获取用户对应的商协会和企业名字
        User user = (User) request.getSession().getAttribute("user");
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        List<Role> roles = privilegeService.getUserRole(user.getId());
        for(Role role: roles){
            if (role.getRoleName().equals("企业")){
                role.setUnitId(user.getQyId());
            }else {
                role.setUnitId(user.getShId());
            }
        }
        map.put("mailNum",privilegeService.getMailNum(user.getId()));
        map.put("role",roles);
        return new Result(Result.SUCCESS,map,"获取成功").toJson();
    }
    @ResponseBody
    @ApiOperation(value="根据机构id获取详细信息",notes = "根据机构id获取对应机构信息",response = Result.class)
    @RequestMapping(value = "/getUnitInfo",method = RequestMethod.GET)
    public String getUnitInfo(HttpServletRequest request,
                              @ApiParam(value = "企业或商会id", required = true)
                              @RequestParam Integer uid
                              )  {

        Unit unit = unitService.getById(uid);
        return new Result(Result.SUCCESS,unit,"获取成功").toJson();
    }
    @ResponseBody
    @ApiOperation(value="获取用户对应身份的菜单",notes = "获取当前登录用户对应菜单信息",response = Result.class)
    @RequestMapping(value = "/getMenu",method = RequestMethod.GET)
    public String getMenu(HttpServletRequest request,
                          @ApiParam(value = "角色id", required = true)
                          @RequestParam Integer rId
                          )  {
        User user = (User) request.getSession().getAttribute("user");
        Map<String,List<Privilege>> map = privilegeService.getAllMenu(user.getId(),rId);
        return new Result(Result.SUCCESS,map,"获取成功").toJson(SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 退出登录
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ApiOperation(value="退出登录",response = Result.class)
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request) throws Exception {
        request.getSession().removeAttribute("user");
        return new Result(Result.SUCCESS, "","注销成功").toJson();
    }

    /**
     * 上传头像
     * @param
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ApiOperation(value="上传头像",response = Result.class)
    @RequestMapping(value = "/uploadimg",method = RequestMethod.GET)
    public String uploadimg(HttpServletRequest request,
                            @ApiParam(value = "图片地址", required = true)
                            @RequestParam String img
                            ) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        userService.uploadImg(img,user.getId());
        user.setImg(img);
        return new Result(Result.SUCCESS, "","上传头像成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="修改个人信息",response = Result.class)
    @RequestMapping(value = "/editUser",method = RequestMethod.POST)
    public String editUser(HttpServletRequest request,
                            @ApiParam(value = "用户实体参数", required = true)
                            @RequestBody User user
    ) throws Exception {
        user = userService.editUser(user);
        if(user != null) {
            //查询注入企业和上回
            if (user.getQyId() != null) {
                user.setQy(unitService.getById(user.getQyId()));
            }
            if (user.getShId() != null) {
                user.setSh(unitService.getById(user.getShId()));
            }
        }
            request.getSession().setAttribute("user",user);
        return new Result(Result.SUCCESS, user,"修改成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="修改机构信息",response = Result.class)
    @RequestMapping(value = "/editUnit",method = RequestMethod.POST)
    public String editUnit(HttpServletRequest request,
                           @ApiParam(value = "用户实体参数", required = true)
                           @RequestBody Unit unit
    ) throws Exception {
        unit = unitService.editUnit(unit);
        User user = (User) request.getSession().getAttribute("user");
        if(user != null) {
            //查询注入企业和上回
            if (user.getQyId() != null) {
                user.setQy(unitService.getById(user.getQyId()));
            }
            if (user.getShId() != null) {
                user.setSh(unitService.getById(user.getShId()));
            }
        }
        request.getSession().setAttribute("user",user);
        return new Result(Result.SUCCESS, unit,"修改成功").toJson();
    }

    /**
     * 申请加入企业或者商会
     * @param request
     * @param unitId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ApiOperation(value="申请加入机构",response = Result.class)
    @RequestMapping(value = "/applyUnit",method = RequestMethod.GET)
    public String applyUnit(HttpServletRequest request,
                            @ApiParam(value = "被申请的机构id", required = true)
                            @RequestParam Integer unitId) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        unitService.applyUnit(unitId,user.getId());
        return new Result(Result.SUCCESS, "","申请成功").toJson();
    }


    /**
     * 可用于让用户重新登录和禁止用户登录  ------
     * @param request
     * @param userId
     * @param state
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ApiOperation(value="改变用户状态-------可用于让用户重新登录和禁止用户登录",response = Result.class)
    @RequestMapping(value = "/updateUserState",method = RequestMethod.GET)
    public String updateUserState(HttpServletRequest request,
                            @ApiParam(value = "用户id", required = true)
                            @RequestParam Integer userId,
                            @ApiParam(value = "状态值 正常 禁止 重新登录", required = true)
                            @RequestParam String state) throws Exception {
        User user = userService.updateUserState(state,userId);
        return new Result(Result.SUCCESS, user,"改版成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="查询组织全体人员",response = Result.class)
    @RequestMapping(value = "/getAllUserByUnitId",method = RequestMethod.GET)
    public String getAllUserByUnitId(HttpServletRequest request,
                                  @ApiParam(value = "组织id", required = true)
                                  @RequestParam Integer unitId) throws Exception {
        List<User> users = userService.getAllUserByUnitId(unitId);
        return new Result(Result.SUCCESS, users,"查询成功").toJson();
    }

    /**
     * 将用户移除该组织
     * @param request
     * @param userId
     * @param unitId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ApiOperation(value="将用户移除当前组织 并强制重新登录",response = Result.class)
    @RequestMapping(value = "/removeUser",method = RequestMethod.GET)
    public String removeUser(HttpServletRequest request,
                                     @ApiParam(value = "用户id", required = true)
                                     @RequestParam Integer userId,
                                     @ApiParam(value = "组织id", required = true)
                                     @RequestParam Integer unitId) throws Exception {
        User user = userService.removeUser(userId,unitId);
        return new Result(Result.SUCCESS, user,"删除成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="获取指定用户信息",response = Result.class)
    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public String getUser(HttpServletRequest request,
                             @ApiParam(value = "用户id", required = true)
                             @RequestParam Integer userId) throws Exception {
        User user = new User();
        user.setId(userId);
        user = userService.updateUserInfo(user);
        return new Result(Result.SUCCESS, user,"查询成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="查询系统全体人员",response = Result.class)
    @RequestMapping(value = "/getAllUser",method = RequestMethod.GET)
    public String getAllUser() throws Exception {
        List<User> users = userService.getAllUser();
        return new Result(Result.SUCCESS, users,"查询成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="按条件查询人员",response = Result.class)
    @RequestMapping(value = "/findByConditions",method = RequestMethod.GET)
    public String findByConditions(
            @ApiParam(value = "组织id", required = false)
            @RequestParam(required = false) Integer unitId,
            @ApiParam(value = "账号 或者姓名", required = true)
            @RequestParam String conditions
    ) throws Exception {
        List<User> users = userService.findByConditions(unitId,conditions);
        return new Result(Result.SUCCESS, users,"查询成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="分配乐汇管理员账号",response = Result.class)
    @RequestMapping(value = "/setAdminUser",method = RequestMethod.POST)
    public String setAdminUser(
            @ApiParam(value = "分配账号基本信息", required = true)
            @RequestBody User user
    ) throws Exception {
        user = userService.saveUser(user);
        return new Result(Result.SUCCESS, user,"分配成功").toJson();
    }


    /**
     * 上传素材模版
     * @param sourceMaterial
     * @return
     */
    @ResponseBody
    @ApiOperation(value="上传素材或者模版",response = Result.class)
    @RequestMapping(value = "/saveSourceMaterial",method = RequestMethod.POST)
    public String saveSourceMaterial(
            @ApiParam(value = "type  和 model 必须要 model的值是 模版 或者素材  type是分类 轮播图 还是什么", required = true)
            @RequestBody SourceMaterial sourceMaterial
    )  {
        sourceMaterial = privilegeService.saveSourceMaterial(sourceMaterial);
        return new Result(Result.SUCCESS, sourceMaterial,"保存成功").toJson();
    }

    /**
     * 根据素材或者模版id删除对应的数据
     * @param id
     * @return
     */
    @ResponseBody
    @ApiOperation(value="删除：根据素材或者模版id删除对应的数据",response = Result.class)
    @RequestMapping(value = "/deleteSourceMaterial",method = RequestMethod.GET)
    public String deleteSourceMaterial(
            @ApiParam(value = "素材模版id", required = true)
            @RequestParam Integer id
    )  {
        SourceMaterial sourceMaterial = privilegeService.deleteSourceMaterial(id);
        return new Result(Result.SUCCESS, sourceMaterial,"删除成功").toJson();
    }

    /**
     * 根据条件查询素材或者模版
     * @param type
     * @param model
     * @return
     */
    @ResponseBody
    @ApiOperation(value="获取：根据model 和 type 获取对应列表数据",response = Result.class)
    @RequestMapping(value = "/getAllSourceByTypeAndModel",method = RequestMethod.GET)
    public String getAllSourceByTypeAndModel(
            @ApiParam(value = "type 不传就返回全部类型的模版或者素材", required = false)
            @RequestParam(required = false) String type,
            @ApiParam(value = "model 必传  返回模版  还是素材列表", required = true)
            @RequestParam() String model
    )  {
        Map<String,List<SourceMaterial>> sourceMaterials = privilegeService.getAllSourceByTypeAndModel(type,model);
        return new Result(Result.SUCCESS, sourceMaterials,"查询成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="上传系统帮助",response = Result.class)
    @RequestMapping(value = "/saveSystemHelp",method = RequestMethod.POST)
    public String saveSystemHelp(
            @ApiParam(value = "type字段是保留字段 看你需不需要区分问题类型", required = true)
            @RequestBody SystemHelp systemHelp
    )  {
        systemHelp = privilegeService.saveSystemHelp(systemHelp);
        return new Result(Result.SUCCESS, systemHelp,"保存成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="删除：根据id删除系统帮助",response = Result.class)
    @RequestMapping(value = "/deleteSystemHelp",method = RequestMethod.GET)
    public String deleteSystemHelp(
            @ApiParam(value = "系统帮助id", required = true)
            @RequestParam Integer id
    )  {
        SystemHelp systemHelp = privilegeService.deleteSystemHelp(id);
        return new Result(Result.SUCCESS, systemHelp,"删除成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="获取：查询所有系统帮助 支持根据问题名称进行模糊查询",response = Result.class)
    @RequestMapping(value = "/getAllSystemHelp",method = RequestMethod.GET)
    public String getAllSystemHelp(
            @ApiParam(value = "type 可以不传 传的话会进行区分", required = false)
            @RequestParam(required = false) String type,
            @ApiParam(value = "problem 问题名称 传的话 会根据这个进行模糊匹配", required = false)
            @RequestParam(required = false) String problem
    )  {
       List<SystemHelp> systemHelps = privilegeService.getAllSystemHelp(type,problem);
        return new Result(Result.SUCCESS, systemHelps,"查询成功").toJson();
    }

}
