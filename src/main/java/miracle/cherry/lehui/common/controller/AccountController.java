package miracle.cherry.lehui.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import miracle.cherry.lehui.common.config.MyConfig;
import miracle.cherry.lehui.common.entity.SystemHelp;
import miracle.cherry.lehui.common.entity.Unit;
import miracle.cherry.lehui.common.entity.User;
import miracle.cherry.lehui.common.service.PrivilegeService;
import miracle.cherry.lehui.common.service.UnitService;
import miracle.cherry.lehui.common.service.UserService;
import miracle.cherry.lehui.common.tools.QRCodeUtil;
import miracle.cherry.lehui.common.tools.Result;
import miracle.cherry.lehui.moban.entity.Activity;
import miracle.cherry.lehui.moban.entity.HelpDetail;
import miracle.cherry.lehui.moban.entity.Info;
import miracle.cherry.lehui.moban.entity.LunBoTu;
import miracle.cherry.lehui.moban.service.MenuService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @Description:乐汇项目后台
 * @Copyright: MengHui
 * @Author: MengHui
 * @Date: 2019-07-14 46
 * @Modified:
 * @Description:
 */
@Transactional//事务管理
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

    @Resource
    PrivilegeService privilegeService;

    @Resource
    MyConfig myConfig;

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
                return new Result(Result.SUCCESS,user,"登录成功").toJson();
            }else {
                return new Result(Result.FAIL,user,"账号密码错误").toJson();
            }
    }
    @ResponseBody
    @ApiOperation(value="用户注册接口",response = Result.class)
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(
            @ApiParam(value = "用标准json格式上传数据  ", required = true)
            @RequestBody(required = true) User user,
            HttpServletRequest request) throws Exception {
        user = userService.register(user,request);
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
        userService.saveUURel(unitId,((User)request.getSession().getAttribute("user")).getId(),"审核中");
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
        List<LunBoTu> list = menuService.queryLunBoTu(Unit.ADMIN_SHID,"正常",User.ADMIN_USER);
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


    /**
     * 跳转管理页面 根据用户是否登录进行跳转
     * @param request
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @ApiOperation(value="跳转管理地址----获取地址跳转",response = Result.class)
    @RequestMapping(value = "/jumpManager",method = RequestMethod.GET)
    public void jumpManager(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //重定向的响应代码
        response.setStatus(302);
        //获取url
        String url = myConfig.getManageUrl();
        //判断是否登录
        Object object = request.getSession().getAttribute("user");
        if(!(object==null)){
            //登录跳转到我的页面
            url+=myConfig.getMyPageUrl();
        }else {
            //没有登录就跳转到登录页面
            url+=myConfig.getLoginUrl();
        }
        response.sendRedirect(url);
    }


    @ResponseBody
    @ApiOperation(value="将文字转换为二维码",response = Result.class)
    @RequestMapping(value = "/createQRcode",method = RequestMethod.GET)
    public void createQRcode(HttpServletRequest request,
                             HttpServletResponse response,
                             @ApiParam(value = "文本内容", required = true)
                             @RequestParam String text,
                             @ApiParam(value = "是否将当前登录用户的头像添加到二维码中  true  or  false", required = true)
                             @RequestParam Boolean isAddImg
                             ) throws Exception {
        //获取resonse文件响应流
        OutputStream outputStream = response.getOutputStream();
        //判断是否讲用户头像添加到二维码中
        if(isAddImg){
            //获取当前用户 判断是否为空
            User user = (User) request.getSession().getAttribute("user");
            if(user==null){
                QRCodeUtil.encode(text,outputStream);
            }else {
                String img = myConfig.getProjectUrl();
                if(user.getImg()==null||"".equals(user.getImg())){
                    img+=myConfig.getDefaultImg();
                }else {
                    img+=user.getImg();
                }
                QRCodeUtil.encode(text,img,outputStream,true);
            }
        }else {
            QRCodeUtil.encode(text,outputStream);
        }
        outputStream.flush();
        outputStream.close();
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


    /**
     * 跳转管理页面 根据用户是否登录进行跳转
     * @param request
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @ApiOperation(value="推荐注册跳转接口",response = Result.class)
    @RequestMapping(value = "/jumpManagerParam",method = RequestMethod.GET)
    public void jumpManagerParam(HttpServletRequest request, HttpServletResponse response,
                                 @ApiParam(value = "推荐码 --------实际上是用户id", required = true)
                                 @RequestParam(required = true) Integer parentId,
                                 @ApiParam(value = "跳转页面的地址 node/management/chember_manage_index.html 类似这种", required = true)
                                 @RequestParam(required = true) String path
    ) throws Exception {
        //重定向的响应代码
        response.setStatus(302);
        //将推荐id注入session中
        request.getSession().setAttribute("parentId",parentId);
        response.sendRedirect(path);
    }



}
