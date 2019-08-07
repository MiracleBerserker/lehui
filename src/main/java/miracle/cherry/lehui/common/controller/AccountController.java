package miracle.cherry.lehui.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import miracle.cherry.lehui.common.config.MyConfig;
import miracle.cherry.lehui.common.entity.Unit;
import miracle.cherry.lehui.common.entity.User;
import miracle.cherry.lehui.common.service.UnitService;
import miracle.cherry.lehui.common.service.UserService;
import miracle.cherry.lehui.common.tools.Result;
import miracle.cherry.lehui.moban.entity.Activity;
import miracle.cherry.lehui.moban.entity.HelpDetail;
import miracle.cherry.lehui.moban.entity.Info;
import miracle.cherry.lehui.moban.entity.LunBoTu;
import miracle.cherry.lehui.moban.service.MenuService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @Description:乐汇项目后台
 * @Copyright: MengHui
 * @Author: MengHui
 * @Date: 2019-07-14 46
 * @Modified:
 * @Description:
 */
@Api(description = "用户管理-及登录注册部分接口")
@RestController
@RequestMapping(value = CommonUrl.ACCOUNT)
public class AccountController {

    @Resource
    UserService userService;

    @Resource
    UnitService unitService;

    @Resource
    MenuService menuService;


    @ResponseBody
    @ApiOperation(value="用户登录接口",response = Result.class)
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request,
            @ApiParam(value = "账号",required = true)
            @RequestParam
            String account,
            @ApiParam(value = "密码",required = true)
            @RequestParam
            String password) throws Exception {
            User user = userService.checkUser(account.trim(),password.trim());
            if(user != null){
                //查询注入企业和上回
                if(user.getQyId()!=null){
                    user.setQy(unitService.getById(user.getQyId()));
                }
                if(user.getShId()!=null){
                    user.setSh(unitService.getById(user.getShId()));
                }
                request.getSession().setAttribute("user",user);
                return new Result(Result.SUCCESS,null,"登录成功").toJson();
            }else {
                return new Result(Result.FAIL,null,"账号密码错误").toJson();
            }
    }
    @ResponseBody
    @ApiOperation(value="用户注册接口",response = Result.class)
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(
            @ApiParam(value = "用标准json格式上传数据  ", required = true)
            @RequestBody(required = true) User user,
            HttpServletRequest request) throws Exception {
        user = userService.register(user);
        //查询注入企业和上回
        if(user.getQyId()!=null){
            user.setQy(unitService.getById(user.getQyId()));
        }
        if(user.getShId()!=null){
            user.setSh(unitService.getById(user.getShId()));
        }
        request.getSession().setAttribute("user",user);
        return new Result(Result.SUCCESS,user,"注册成功").toJson();
    }
    @ResponseBody
    @ApiOperation(value="创建企业或者商会",response = Result.class)
    @RequestMapping(value = "/createUnit",method = RequestMethod.POST)
    public String createUnit(
            @ApiParam(value = "用标准json格式上传数据  ", required = true)
            @RequestBody(required = true) Unit unit,
            HttpServletRequest request
    ) throws Exception {
        unit = userService.saveUnit(unit,unit.getType(),"审核中");
        userService.saveUURel(unit.getId(),((User)request.getSession().getAttribute("user")).getId(),"创建中");
        return new Result(Result.SUCCESS,unit,"申请成功").toJson();
    }
    @ResponseBody
    @ApiOperation(value="申请加入企业或者商会",response = Result.class)
    @RequestMapping(value = "/apply",method = RequestMethod.GET)
    public String apply(
            @ApiParam(value = "用标准json格式上传数据", required = true)
            @RequestParam
            Integer unitId,
            HttpServletRequest request
    ) throws Exception {
        userService.saveUURel(unitId,((User)request.getSession().getAttribute("user")).getId(),"申请中");
        return new Result(Result.SUCCESS,Collections.singletonMap("unitId",unitId),"申请成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="上传文件通用接口",response = Result.class)
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public String uploadFile(
            @ApiParam(value = "选择文件上传  单文件上传", required = true)
            MultipartFile file
    )  {
        String filePath = null;
        Map<String,Object> map = new HashMap<>();
        try {
            filePath = userService.uploadFile(file);
            map.put("url",filePath);
            map.put("status",1);
            map.put("msg","文件上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            map.put("url","");
            map.put("status",0);
            map.put("msg","文件上传失败");
        }

        return JSONObject.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
    }


    @ResponseBody
    @ApiOperation(value="根据信用代码查询机构是否存在",response = Result.class)
    @RequestMapping(value = "/findUnitByCode",method = RequestMethod.GET)
    public String findUnitByCode(
            @ApiParam(value = "信用代码", required = true)
            @RequestParam
            String code,
            HttpServletRequest request
    ) throws Exception {
            Unit unit = unitService.findUnitByCode(code);
        return new Result(Result.SUCCESS,unit,"获取成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="填写收集信息",response = Result.class)
    @RequestMapping(value = "/saveInfo",method = RequestMethod.POST)
    public String saveInfo(@ApiParam(value = "信息实体",required = true)
                           @RequestBody(required = true) Info info) throws Exception {
        info = menuService.saveInfo(info);
        return new Result(Result.SUCCESS, info,"收集成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="获取乐汇系统的轮播图",response = Result.class)
    @RequestMapping(value = "/queryLunBoTu",method = RequestMethod.GET)
    public String queryLunBoTu() throws Exception {
        List<LunBoTu> list = menuService.queryLunBoTu(80,"正常",0);
        return new Result(Result.SUCCESS, list,"拉取成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="留下帮助详情",response = Result.class)
    @RequestMapping(value = "/saveHelpDetail",method = RequestMethod.POST)
    public String saveHelpDetail(@ApiParam(value = "帮助详情",required = true)
                                 @RequestBody(required = true) HelpDetail helpDetail,
                                 HttpServletRequest request
    ) throws Exception {
        helpDetail = menuService.saveHelpDetail(helpDetail);
        return new Result(Result.SUCCESS, helpDetail,"上传成功").toJson();
    }
}
