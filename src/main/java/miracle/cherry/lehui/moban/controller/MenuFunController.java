package miracle.cherry.lehui.moban.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import miracle.cherry.lehui.common.controller.CommonUrl;
import miracle.cherry.lehui.common.entity.User;
import miracle.cherry.lehui.moban.entity.*;
import miracle.cherry.lehui.moban.service.MenuService;
import miracle.cherry.lehui.common.tools.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-24 6:19
 * @Modified:
 * @Description:
 */
@Transactional//事务管理
@Api(description = "菜单功能-普通功能模块")
@RestController
@RequestMapping(value = CommonUrl.MENU)
public class MenuFunController {

    @Resource
    MenuService menuService;

    /**
     * 获取新闻列表
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @ApiOperation(value="获取：新闻列表带分页",response = Result.class)
    @RequestMapping(value = "/getNews",method = RequestMethod.GET)
    public String getNews( @ApiParam(value = "机构id",required = true)
                           @RequestParam
                           Integer unitId,
                           @ApiParam(value = "状态值 不带就返回全部 正常 审核中")
                           @RequestParam(required = false)
                           String status,HttpServletRequest request)  {
        User user = (User) request.getSession().getAttribute("user");
        List<New> news = menuService.queryNew(unitId,status,user.getId());
        return new Result(Result.SUCCESS, news,"获取成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="上传：新闻",response = Result.class)
    @RequestMapping(value = "/saveNews",method = RequestMethod.POST)
    public String saveNews(@ApiParam(value = "新闻列表",required = true)
                           @RequestBody(required = true) New news, HttpServletRequest request) throws Exception {

        User user = (User) request.getSession().getAttribute("user");
        news.setUserId(user.getId());
        news.setUserName(user.getName());
        New newss = menuService.saveNew(news);
        return new Result(Result.SUCCESS, newss,"上传成功").toJson();
    }
    @ResponseBody
    @ApiOperation(value="删除：新闻",response = Result.class)
    @RequestMapping(value = "/deleteNews",method = RequestMethod.GET)
    public String deleteNews(@ApiParam(value = "新闻id",required = true)
                           @RequestParam(required = true) Integer newId) throws Exception {
        menuService.deleteNew(newId);
        return new Result(Result.SUCCESS, "删除成功","删除成功").toJson();
    }
    @ResponseBody
    @ApiOperation(value="审核：新闻",response = Result.class)
    @RequestMapping(value = "/aggrenNews",method = RequestMethod.GET)
    public String aggrenNews(@ApiParam(value = "新闻id",required = true)
                                @RequestParam(required = true) Integer newId,
                                @ApiParam(value = "正常 未通过")
                                @RequestParam
                                String status
    ) throws Exception {
        menuService.aggrenNews(newId ,status);
        return new Result(Result.SUCCESS, "审核成功","审核成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="上传：简介",response = Result.class)
    @RequestMapping(value = "/saveBrief",method = RequestMethod.POST)
    public String saveBrief(@ApiParam(value = "简介内容",required = true)
                           @RequestBody(required = true) Brief brief, HttpServletRequest request) throws Exception {
         User user = (User) request.getSession().getAttribute("user");
         brief.setEditId(user.getId());
         brief.setEditName(user.getName());
         brief = menuService.saveBrief(brief);
        return new Result(Result.SUCCESS, brief,"上传成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="发布：活动",response = Result.class)
    @RequestMapping(value = "/saveActivity",method = RequestMethod.POST)
    public String saveActivity(@ApiParam(value = "活动内容",required = true)
                            @RequestBody(required = true) Activity activity,
                               HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        activity.setFbrId(user.getId());
        activity.setName(user.getName());
        activity = menuService.saveActivity(activity);
        return new Result(Result.SUCCESS, activity,"上传成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="获取：简介",response = Result.class)
    @RequestMapping(value = "/getBrief",method = RequestMethod.GET)
    public String getBrief(@ApiParam(value = "机构id",required = true)
                           @RequestParam(required = true) Integer unitId

    ) throws Exception {
         Brief brief = menuService.getBrief(unitId);
        return new Result(Result.SUCCESS, brief,"获取成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="获取：活动列表",response = Result.class)
    @RequestMapping(value = "/queryActivity",method = RequestMethod.GET)
    public String queryActivity(@ApiParam(value = "机构id",required = true)
                           @RequestParam(required = true) Integer unitId,
                           @ApiParam(value = "状态值 不带就返回全部 正常 审核中")
                           @RequestParam(required = false) String status,HttpServletRequest request
    ) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        List<Activity> activities = menuService.queryActivity(unitId,status,user.getId());
        return new Result(Result.SUCCESS, activities,"获取成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="获取：活动详情",response = Result.class)
    @RequestMapping(value = "/getActivity",method = RequestMethod.GET)
    public String getActivity(@ApiParam(value = "活动id",required = true)
                             @RequestParam(required = true) Integer id
    ) throws Exception {
        Activity activity = menuService.getActivity(id);
        return new Result(Result.SUCCESS, activity,"获取成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="审核活动",response = Result.class)
    @RequestMapping(value = "/aggrenActivity",method = RequestMethod.GET)
    public String aggrenActivity(@ApiParam(value = "活动id",required = true)
                             @RequestParam(required = true) Integer newId,
                             @ApiParam(value = "正常 未通过")
                             @RequestParam(required = true)
                             String status
    ) throws Exception {
        menuService.aggrenActivity(newId ,status);
        return new Result(Result.SUCCESS, "审核成功","审核成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="删除活动",response = Result.class)
    @RequestMapping(value = "/deleteActivity",method = RequestMethod.GET)
    public String deleteActivity(@ApiParam(value = "活动id",required = true)
                              @RequestParam(required = true) Integer id
    ) throws Exception {
        Activity activity = menuService.deleteActivity(id);
        return new Result(Result.SUCCESS, activity,"删除成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="获取信息收集详情列表",response = Result.class)
    @RequestMapping(value = "/queryInfo",method = RequestMethod.GET)
    public String queryInfo(@ApiParam(value = "机构id",required = true)
                              @RequestParam(required = true) Integer unitId
    ) throws Exception {
        List<Info> infos = menuService.queryInfo(unitId);
        return new Result(Result.SUCCESS, infos,"获取成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="删除：信息",response = Result.class)
    @RequestMapping(value = "/deleteInfo",method = RequestMethod.GET)
    public String deleteInfo(@ApiParam(value = "信息id",required = true)
                                 @RequestParam(required = true) Integer id
    ) throws Exception {
        Info info = menuService.deleteInfo(id);
        return new Result(Result.SUCCESS, info,"删除成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="预留信息",response = Result.class)
    @RequestMapping(value = "/saveInfo",method = RequestMethod.POST)
    public String saveInfo(@ApiParam(value = "上传发现",required = true)
                               @RequestBody(required = true) Info info,
                               HttpServletRequest request) throws Exception {
        info = menuService.saveInfo(info);
        return new Result(Result.SUCCESS, info,"上传成功").toJson();
    }



    @ResponseBody
    @ApiOperation(value="上传：发现",response = Result.class)
    @RequestMapping(value = "/saveDiscover",method = RequestMethod.POST)
    public String saveDiscover(@ApiParam(value = "上传发现",required = true)
                               @RequestBody(required = true) Discover discover,
                               HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        discover.setCreateId(user.getId());
        discover.setCreateName(user.getName());
        discover = menuService.saveDiscover(discover);
        return new Result(Result.SUCCESS, discover,"上传成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="删除：发现",response = Result.class)
    @RequestMapping(value = "/deleteDiscover",method = RequestMethod.GET)
    public String deleteDiscover(@ApiParam(value = "发现id",required = true)
                             @RequestParam(required = true) Integer id
    ) throws Exception {
        Discover discover = menuService.deleteDiscover(id);
        return new Result(Result.SUCCESS, discover,"删除成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="获取：发现列表",response = Result.class)
    @RequestMapping(value = "/queryDiscover",method = RequestMethod.GET)
    public String queryDiscover(@ApiParam(value = "机构id",required = true)
                                @RequestParam(required = true) Integer unitId,
                                @ApiParam(value = "状态值 不带就返回全部 正常 审核中")
                                @RequestParam(required = false) String status,
                                HttpServletRequest request
    ) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        List<Discover> discovers = menuService.queryDiscover(unitId,status,user.getId());
        return new Result(Result.SUCCESS, discovers,"获取成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="审核：发现",response = Result.class)
    @RequestMapping(value = "/aggrenDiscover",method = RequestMethod.GET)
    public String aggrenDiscover(@ApiParam(value = "发现id",required = true)
                                 @RequestParam(required = true) Integer newId,
                                 @ApiParam(value = "正常 未通过")
                                 @RequestParam(required = true)
                                 String status
    ) throws Exception {
        menuService.aggrenDiscover(newId ,status);
        return new Result(Result.SUCCESS, "审核成功","审核成功").toJson();
    }




    @ResponseBody
    @ApiOperation(value="上传：轮播图",response = Result.class)
    @RequestMapping(value = "/saveLunBoTu",method = RequestMethod.POST)
    public String saveLunBoTu(@ApiParam(value = "上传轮播图",required = true)
                               @RequestBody(required = true) LunBoTu lunBoTu,
                               HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        lunBoTu.setCreateId(user.getId());
        lunBoTu.setCreateName(user.getName());
        lunBoTu = menuService.saveLunBoTu(lunBoTu);
        return new Result(Result.SUCCESS, lunBoTu,"上传成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="获取：轮播图列表",response = Result.class)
    @RequestMapping(value = "/queryLunBoTu",method = RequestMethod.GET)
    public String queryLunBoTu(@ApiParam(value = "机构id",required = true)
                               @RequestParam(required = true) Integer unitId,
                               @ApiParam(value = "状态值 不带就返回全部 正常 审核中")
                               @RequestParam(required = false) String status,
                               HttpServletRequest request
    ) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        List<LunBoTu> lunBoTus = menuService.queryLunBoTu(unitId,status,user.getId());
        return new Result(Result.SUCCESS, lunBoTus,"获取成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="审核：轮播图",response = Result.class)
    @RequestMapping(value = "/aggrenLunBoTu",method = RequestMethod.GET)
    public String aggrenLunBoTu(@ApiParam(value = "轮播图id",required = true)
                                 @RequestParam(required = true) Integer newId,
                                 @ApiParam(value = "正常 未通过")
                                 @RequestParam(required = true)
                                         String status
    ) throws Exception {
        menuService.aggrenLunBoTu(newId ,status);
        return new Result(Result.SUCCESS, "审核成功","审核成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="删除：轮播图",response = Result.class)
    @RequestMapping(value = "/deleteLunBoTu",method = RequestMethod.GET)
    public String deleteLunBoTu(@ApiParam(value = "轮播图id",required = true)
                                 @RequestParam(required = true) Integer id
    ) throws Exception {
        LunBoTu lunBoTu = menuService.deleteLunBoTu(id);
        return new Result(Result.SUCCESS, lunBoTu,"删除成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="修改：或者保存联系方式",response = Result.class)
    @RequestMapping(value = "/saveContact",method = RequestMethod.POST)
    public String saveContact(@ApiParam(value = "修改或者保存联系方式",required = true)
                              @RequestBody(required = true) Contact contact) throws Exception {

        contact = menuService.saveContact(contact);
        return new Result(Result.SUCCESS, contact,"上传成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="获取：联系方式",response = Result.class)
    @RequestMapping(value = "/getContact",method = RequestMethod.GET)
    public String getContact(@ApiParam(value = "机构id",required = true)
                              @RequestParam(required = true) Integer unitId
    ) throws Exception {
        Contact contact = menuService.queryContact(unitId);
        return new Result(Result.SUCCESS, contact,"获取成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="上传：企业产品",response = Result.class)
    @RequestMapping(value = "/saveProduct",method = RequestMethod.POST)
    public String saveProduct(@ApiParam(value = "企业产品",required = true)
                              @RequestBody(required = true) Product product,
                              HttpServletRequest request
                              ) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        product.setCreateId(user.getId());
        product.setCreateName(user.getName());
        product = menuService.saveProduct(product);
        return new Result(Result.SUCCESS, product,"上传成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="删除：产品",response = Result.class)
    @RequestMapping(value = "/deleteProduct",method = RequestMethod.GET)
    public String deleteProduct(@ApiParam(value = "产品id",required = true)
                                @RequestParam(required = true) Integer id
    ) throws Exception {
        Product product = menuService.deleteProduct(id);
        return new Result(Result.SUCCESS, product,"删除成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="获取：产品列表",response = Result.class)
    @RequestMapping(value = "/queryProduct",method = RequestMethod.GET)
    public String queryProduct(@ApiParam(value = "机构id",required = true)
                               @RequestParam(required = true) Integer unitId,
                               @ApiParam(value = "正常 未通过")
                               @RequestParam(required = true) String status
    ) throws Exception {
        List<Product> products = menuService.queryProduct(unitId,status);
        return new Result(Result.SUCCESS, products,"获取成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="上传：帮助",response = Result.class)
    @RequestMapping(value = "/saveHelp",method = RequestMethod.POST)
    public String saveHelp(@ApiParam(value = "帮助",required = true)
                              @RequestBody(required = true) Help help,
                              HttpServletRequest request
    ) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        help.setCreateId(user.getId());
        help.setCreateName(user.getName());
        help = menuService.saveHelp(help);
        return new Result(Result.SUCCESS, help,"上传成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="删除：帮助",response = Result.class)
    @RequestMapping(value = "/deleteHelp",method = RequestMethod.GET)
    public String deleteHelp(@ApiParam(value = "帮助id",required = true)
                                @RequestParam(required = true) Integer id
    ) throws Exception {
        Help help = menuService.deleteHelp(id);
        return new Result(Result.SUCCESS, help,"删除成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="获取：帮助列表",response = Result.class)
    @RequestMapping(value = "/queryHelp",method = RequestMethod.GET)
    public String queryHelp(@ApiParam(value = "机构id",required = true)
                            @RequestParam(required = true) Integer unitId,
                            @ApiParam(value = "状态值 不带就返回全部 正常 审核中")
                            @RequestParam(required = false) String status,
                            HttpServletRequest request
    ) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        List<Help> helps = menuService.queryHelp(unitId,status,user.getId());
        return new Result(Result.SUCCESS, helps,"获取成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="审核：帮助",response = Result.class)
    @RequestMapping(value = "/aggrenHelp",method = RequestMethod.GET)
    public String aggrenHelp(@ApiParam(value = "帮助id",required = true)
                                @RequestParam(required = true) Integer newId,
                                @ApiParam(value = "正常 未通过")
                                @RequestParam(required = true)
                                        String status
    ) throws Exception {
        menuService.aggrenHelp(newId ,status);
        return new Result(Result.SUCCESS, "审核成功","审核成功").toJson();
    }





    @ResponseBody
    @ApiOperation(value="删除：帮助详情",response = Result.class)
    @RequestMapping(value = "/deleteHelpDetail",method = RequestMethod.GET)
    public String deleteHelpDetail(@ApiParam(value = "帮助详情id",required = true)
                             @RequestParam(required = true) Integer id
    ) throws Exception {
        HelpDetail helpDetail = menuService.deleteHelpDetail(id);
        return new Result(Result.SUCCESS, helpDetail,"删除成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="获取：帮助详情列表",response = Result.class)
    @RequestMapping(value = "/queryHelpDetail",method = RequestMethod.GET)
    public String queryHelpDetail(@ApiParam(value = "机构id",required = true)
                            @RequestParam(required = true) Integer helpId
    ) throws Exception {
        List<HelpDetail> helpDetails = menuService.queryHelpDetail(helpId);
        return new Result(Result.SUCCESS, helpDetails,"获取成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="分享",response = Result.class)
    @RequestMapping(value = "/saveShare",method = RequestMethod.POST)
    public String saveShare(@ApiParam(value = "分享",required = true)
                                 @RequestBody(required = true) Share share,
                                 HttpServletRequest request
    ) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        share.setCreateId(user.getId());
        share.setCreateName(user.getName());
        share.setCreateImg(user.getImg());
        share = menuService.saveShare(share);
        return new Result(Result.SUCCESS, share,"上传成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="删除：分享",response = Result.class)
    @RequestMapping(value = "/deleteShare",method = RequestMethod.GET)
    public String deleteShare(@ApiParam(value = "分享id",required = true)
                                   @RequestParam(required = true) Integer id
    ) throws Exception {
        Share share = menuService.deleteShare(id);
        return new Result(Result.SUCCESS, share,"删除成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="获取：分享列表",response = Result.class)
    @RequestMapping(value = "/queryShare",method = RequestMethod.GET)
    public String queryShare(@ApiParam(value = "机构id",required = true)
                             @RequestParam(required = true) Integer unitId,
                             @ApiParam(value = "状态值 不带就返回全部 正常 审核中")
                             @RequestParam(required = false) String status,
                             HttpServletRequest request
    ) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        List<Share> shares = menuService.queryShare(unitId,status,user.getId());
        return new Result(Result.SUCCESS, shares,"获取成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="审核：分享",response = Result.class)
    @RequestMapping(value = "/aggrenShare",method = RequestMethod.GET)
    public String aggrenShare(@ApiParam(value = "分享id",required = true)
                             @RequestParam(required = true) Integer newId,
                             @ApiParam(value = "正常 未通过")
                             @RequestParam(required = true)
                                     String status
    ) throws Exception {
        menuService.aggrenShare(newId ,status);
        return new Result(Result.SUCCESS, "审核成功","审核成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="保存：vip服务项目",response = Result.class)
    @RequestMapping(value = "/saveVip",method = RequestMethod.POST)
    public String saveVip(@ApiParam(value = "vip付费项目",required = true)
                            @RequestBody(required = true) Vip vip,
                            HttpServletRequest request
    ) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        vip.setCreateId(user.getId());
        vip.setCreateName(user.getName());
        vip = menuService.saveVip(vip);
        return new Result(Result.SUCCESS, vip,"上传成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="删除：vip付费",response = Result.class)
    @RequestMapping(value = "/deleteVip",method = RequestMethod.GET)
    public String deleteVip(@ApiParam(value = "付费vipid",required = true)
                              @RequestParam(required = true) Integer id
    ) throws Exception {
        Vip vip = menuService.deleteVip(id);
        return new Result(Result.SUCCESS, vip,"删除成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="获取：vip列表",response = Result.class)
    @RequestMapping(value = "/queryVip",method = RequestMethod.GET)
    public String queryVip(@ApiParam(value = "机构id",required = true)
                             @RequestParam(required = true) Integer unitId
    ) throws Exception {
            List<Vip> vips = menuService.queryVip(unitId);
        return new Result(Result.SUCCESS, vips,"获取成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="删除：邮件",response = Result.class)
    @RequestMapping(value = "/deleteMail",method = RequestMethod.GET)
    public String deleteMail(@ApiParam(value = "邮件id",required = true)
                            @RequestParam(required = true) Integer id
    ) throws Exception {
        Mail mail = menuService.deleteMail(id);
        return new Result(Result.SUCCESS, mail,"删除成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="获取：邮件列表",response = Result.class)
    @RequestMapping(value = "/queryMail",method = RequestMethod.GET)
    public String queryMail(HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        List<Mail> mails = menuService.queryMail(user.getId());
        return new Result(Result.SUCCESS, mails,"获取成功").toJson();
    }


    @ResponseBody
    @ApiOperation(value="保存：会员单位",response = Result.class)
    @RequestMapping(value = "/saveVipUnit",method = RequestMethod.POST)
    public String saveVipUnit(@ApiParam(value = "会员单位",required = true)
                          @RequestBody(required = true) VipUnit vipUnit,
                          HttpServletRequest request
    )  {
        User user = (User) request.getSession().getAttribute("user");
        vipUnit.setCreateId(user.getId());
        vipUnit.setCreateName(user.getName());
        vipUnit = menuService.saveVipUnit(vipUnit);
        return new Result(Result.SUCCESS, vipUnit,"保存成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="获取：会员单位列表",response = Result.class)
    @RequestMapping(value = "/findAllVipUnitByUnitId",method = RequestMethod.GET)
    public String findAllVipUnitByUnitId(@ApiParam(value = "机构id",required = true)
                                         @RequestParam(required = true) Integer unitId,
                                         @ApiParam(value = "公司名字 模糊查询",required = false)
                                         @RequestParam(required = false) String name
    )  {
        List<VipUnit> vipUnits = menuService.findAllVipUnitByUnitId(unitId,name);
        return new Result(Result.SUCCESS, vipUnits,"获取成功").toJson();
    }

    @ResponseBody
    @ApiOperation(value="删除：会员单位",response = Result.class)
    @RequestMapping(value = "/deleteVipUnit",method = RequestMethod.GET)
    public String deleteVipUnit(@ApiParam(value = "会员单位id",required = true)
                                @RequestParam(required = true) Integer id
    )  {
        VipUnit vipUnit = menuService.deleteVipUnit(id);
        return new Result(Result.SUCCESS, vipUnit,"删除成功").toJson();
    }

}
