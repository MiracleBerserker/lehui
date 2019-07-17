package miracle.cherry.lehui.common.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import miracle.cherry.lehui.common.config.MyConfig;
import miracle.cherry.lehui.common.entity.Unit;
import miracle.cherry.lehui.common.entity.User;
import miracle.cherry.lehui.common.service.UnitService;
import miracle.cherry.lehui.common.service.UserService;
import miracle.cherry.lehui.common.tools.Result;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:乐汇项目后台
 * @Copyright: MengHui
 * @Author: MengHui
 * @Date: 2019-07-14 46
 * @Modified:
 * @Description:
 */
@Api(description = "用户管理及登录注册部分接口")
@RestController
@RequestMapping(value = CommonUrl.ACCOUNT)
public class Account {

    @Resource
    UserService userService;

    @Resource
    UnitService unitService;


    @ApiOperation(value="用户登录接口",response = Result.class)
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request,
            @ApiParam(value = "账号",required = true)
            @RequestParam
            String account,
            @RequestParam
            @ApiParam(value = "密码",required = true)
            String password) throws Exception {
            User user = userService.checkUser(account.trim(),password.trim());
            if(user != null){
                request.getSession().setAttribute("user",user);
                return new Result(Result.SUCCESS,null,"登录成功").toJson();
            }else {
                return new Result(Result.FAIL,null,"账号密码错误").toJson();
            }
    }

    @ApiOperation(value="用户注册接口",response = Result.class)
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(
            @ApiParam(value = "用标准json格式上传数据  ", required = true)
            @RequestBody(required = true) User user
            ) throws Exception {
        user = userService.register(user);
        return new Result(Result.SUCCESS,user,"注册成功").toJson();
    }

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

    @ApiOperation(value="申请加入企业或者商会",response = Result.class)
    @RequestMapping(value = "/apply",method = RequestMethod.POST)
    public String apply(
            @ApiParam(value = "用标准json格式上传数据", required = true)
            Integer unitId,
            HttpServletRequest request
    ) throws Exception {
        userService.saveUURel(unitId,((User)request.getSession().getAttribute("user")).getId(),"申请中");
        return new Result(Result.SUCCESS,Collections.singletonMap("unitId",unitId),"申请成功").toJson();
    }


    @ApiOperation(value="上传文件通用接口",response = Result.class)
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public String uploadFile(
            @ApiParam(value = "选择文件上传  单文件上传", required = true)
            MultipartFile file
    ) throws Exception {
        String filePath = userService.uploadFile(file);
        return new Result(Result.SUCCESS,Collections.singletonMap("filePath",filePath),"上传成功").toJson();
    }

    @ApiOperation(value="获取当前用户信息",notes = "用户所属的商会和团队详细信息需要调用相关接口在查询",response = Result.class)
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    public String getUserInfo(HttpServletRequest request) throws Exception {
        return new Result(Result.SUCCESS,request.getSession().getAttribute("user"),"获取成功").toJson();
    }




}
