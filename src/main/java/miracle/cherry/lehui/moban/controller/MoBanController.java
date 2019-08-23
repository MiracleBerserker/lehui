package miracle.cherry.lehui.moban.controller;

import miracle.cherry.lehui.common.controller.CommonUrl;
import miracle.cherry.lehui.common.dao.UnitDao;
import miracle.cherry.lehui.common.entity.Unit;
import miracle.cherry.lehui.common.service.UnitService;
import miracle.cherry.lehui.moban.dao.*;
import miracle.cherry.lehui.moban.entity.*;
import org.mapstruct.Mapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-27 9:06
 * @Modified:
 * @Description:
 */
@Transactional//事务管理
@RestController
@RequestMapping(value = CommonUrl.TEMPLATE)
public class MoBanController {

    @Resource
    UnitService unitService;
    @Resource
    LunBoTuDao lunBoTuDao;
    @Resource
    BriefDao briefDao;
    @Resource
    ProductDao productDao;
    @Resource
    NewDao newDao;
    @Resource
    ActivityDao activityDao;
    @Resource
    ContactDao contactDao;
    @Resource
    NewDetailsDao newDetailsDao;
    @Resource
    DiscoverDao discoverDao;
    @Resource
    UnitDao unitDao;
    @Resource
    HelpDao helpDao;
    @Resource
    HelpDetailDao helpDetailDao;
    @Resource
    ShareDao shareDao;
    @Resource
    VipDao vipDao;
    @Resource
    VipUnitDao vipUnitDao;
    /**
     * 首页跳转
     * @param req
     * @param unitId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{unitId}",method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest req,
                              @PathVariable Integer unitId) {
        ModelAndView mv = new ModelAndView();
        //注入unit
        Unit unit = unitService.getById(unitId);
        if(unit==null||unit.getTemplate()==null){
            mv.addObject("error","无法找到企业或者商会的模版页面 请确认访问地址！！！！");
            mv.setViewName("/error.html");
            return mv;
        }else {
            mv.addObject("unit",unit);
            //注入简介
            Brief brief = briefDao.findByUnitId(unitId);
            mv.addObject("brief",brief);
            //注入联系方式
            Contact contact = contactDao.findByUnitId(unitId);
            mv.addObject("contact",contact);
            if(unit.getType().equals("商会")){
                //注入发现
                List<Discover> discovers = discoverDao.findAllByUnitIdAndStatus(unitId,"正常");
                mv.addObject("discovers",discovers);
                //注入会员单位
                List<VipUnit> units = vipUnitDao.findAllByUnitId(unitId);
                mv.addObject("units",units);
                //注入轮播图
                List<LunBoTu> lunBoTus = lunBoTuDao.findAllByUnitIdAndStatusOrderByCreateTimeDesc(unitId,"正常");
                if (lunBoTus!=null&&lunBoTus.size()>0){
                    mv.addObject("lunBoTus",lunBoTus);
                }else {
                    mv.addObject("lunBoTus",null);
                }
                //注入新闻
                List<New> news = newDao.findAllByUnitIdAndStatus(unitId,"正常");
                mv.addObject("news",news);
                //注入活动
                List<Activity> activities = activityDao.findAllByUnitIdAndStatus(unitId,"正常");
                mv.addObject("activities",activities);
                //注入分享
                List<Share> shares = shareDao.findAllByUnitIdAndStatus(unitId,"正常");
                mv.addObject("shares",shares);
                //注入会员
                List<Vip> vips = vipDao.findAllByUnitId(unitId);
                mv.addObject("vips",vips);
            }else {
                //注入轮播图
                List<LunBoTu> lunBoTus = lunBoTuDao.findAllByUnitIdOrderByCreateTimeDesc(unitId);
                if (lunBoTus!=null&&lunBoTus.size()>0){
                    mv.addObject("lunBoTus",lunBoTus);
                }else {
                    mv.addObject("lunBoTus",null);
                }
                //注入业务产品
                List<Product> products = productDao.findAllByUnitId(unitId);
                mv.addObject("products",products);
                //注入新闻
                List<New> news = newDao.findAllByUnitIdAndStatus(unitId,"正常");
                mv.addObject("news",news);
                //注入活动
                List<Activity> activities = activityDao.findAllByUnitId(unitId);
                mv.addObject("activities",activities);
            }
        }
        mv.setViewName(unit.getTemplate()+"/index.html");
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/{unitId}/vips",method = RequestMethod.GET)
    public ModelAndView vips(HttpServletRequest req,
                               @PathVariable Integer unitId) {
        ModelAndView mv = new ModelAndView();
        //注入unit
        Unit unit = unitService.getById(unitId);
        if(unit==null||unit.getTemplate()==null){
            mv.addObject("error","无法找到企业或者商会的模版页面 请确认访问地址！！！！");
            mv.setViewName("/error.html");
            return mv;
        }else {
            mv.addObject("unit",unit);
            //注入会员收费
            List<Vip> vips = vipDao.findAllByUnitId(unitId);
            mv.addObject("vips",vips);
        }
        mv.setViewName(unit.getTemplate()+"/chember_vip_fee.html");
        return mv;
    }



    @ResponseBody
    @RequestMapping(value = "/{unitId}/shares",method = RequestMethod.GET)
    public ModelAndView shares(HttpServletRequest req,
                              @PathVariable Integer unitId) {
        ModelAndView mv = new ModelAndView();
        //注入unit
        Unit unit = unitService.getById(unitId);
        if(unit==null||unit.getTemplate()==null){
            mv.addObject("error","无法找到企业或者商会的模版页面 请确认访问地址！！！！");
            mv.setViewName("/error.html");
            return mv;
        }else {
            mv.addObject("unit",unit);
            //注入帮助
            List<Share> shares = shareDao.findAllByUnitIdAndStatus(unitId,"正常");
            mv.addObject("shares",shares);
        }
        mv.setViewName(unit.getTemplate()+"/chember_share_detail.html");
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/{unitId}/helps",method = RequestMethod.GET)
    public ModelAndView helps(HttpServletRequest req,
                                  @PathVariable Integer unitId) {
        ModelAndView mv = new ModelAndView();
        //注入unit
        Unit unit = unitService.getById(unitId);
        if(unit==null||unit.getTemplate()==null){
            mv.addObject("error","无法找到企业或者商会的模版页面 请确认访问地址！！！！");
            mv.setViewName("/error.html");
            return mv;
        }else {
            mv.addObject("unit",unit);
            //注入帮助
            List<Help> helps = helpDao.findAllByUnitIdAndStatus(unitId,"正常");
            for(Help help :helps){
                List<HelpDetail> helpDetails = helpDetailDao.findAllByHelpId(help.getId());
                help.setSum(helpDetails.size());
            }
            mv.addObject("helps",helps);
        }
        mv.setViewName(unit.getTemplate()+"/chember_help_detail.html");
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/{unitId}/{helpId}/add",method = RequestMethod.GET)
    public ModelAndView add(HttpServletRequest req,
                              @PathVariable Integer unitId,
                              @PathVariable Integer helpId) {
        ModelAndView mv = new ModelAndView();
        //注入unit
        Unit unit = unitService.getById(unitId);
        if(unit==null||unit.getTemplate()==null){
            mv.addObject("error","无法找到企业或者商会的模版页面 请确认访问地址！！！！");
            mv.setViewName("/error.html");
            return mv;
        }else {
            mv.addObject("unit",unit);
            //注入帮助id
            mv.addObject("helpId",helpId);
        }
        mv.setViewName(unit.getTemplate()+"/chember_contact_way.html");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/{unitId}/{helpId}/detail",method = RequestMethod.GET)
    public ModelAndView detail(HttpServletRequest req,
                              @PathVariable Integer unitId,
                              @PathVariable Integer helpId) {
        ModelAndView mv = new ModelAndView();
        //注入unit
        Unit unit = unitService.getById(unitId);
        if(unit==null||unit.getTemplate()==null){
            mv.addObject("error","无法找到企业或者商会的模版页面 请确认访问地址！！！！");
            mv.setViewName("/error.html");
            return mv;
        }else {
            mv.addObject("unit",unit);
            //注入帮助
            Help help = helpDao.findById(helpId).get();
            mv.addObject("help",help);
            //注入帮助详情列表
            List<HelpDetail> helpDetails = helpDetailDao.findAllByHelpId(helpId);
            mv.addObject("helpDetails",helpDetails);
        }
        mv.setViewName(unit.getTemplate()+"/chember_helplist_detail.html");
        return mv;
    }



    /**
     * 发现
     * @param req
     * @param unitId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{unitId}/discovers",method = RequestMethod.GET)
    public ModelAndView discovers(HttpServletRequest req,
                              @PathVariable Integer unitId) {
        ModelAndView mv = new ModelAndView();
        //注入unit
        Unit unit = unitService.getById(unitId);
        if(unit==null||unit.getTemplate()==null){
            mv.addObject("error","无法找到企业或者商会的模版页面 请确认访问地址！！！！");
            mv.setViewName("/error.html");
            return mv;
        }else {
            mv.addObject("unit",unit);
            //注入发现
            List<Discover> discovers = discoverDao.findAllByUnitId(unitId);
            mv.addObject("discovers",discovers);
        }
        mv.setViewName(unit.getTemplate()+"/chember_discover.html");
        return mv;
    }



    /**
     * 会员单位
     * @param req
     * @param unitId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{unitId}/units",method = RequestMethod.GET)
    public ModelAndView units(HttpServletRequest req,
                                  @PathVariable Integer unitId) {
        ModelAndView mv = new ModelAndView();
        //注入unit
        Unit unit = unitService.getById(unitId);
        if(unit==null||unit.getTemplate()==null){
            mv.addObject("error","无法找到企业或者商会的模版页面 请确认访问地址！！！！");
            mv.setViewName("/error.html");
            return mv;
        }else {
            mv.addObject("unit",unit);
            //注入会员单位
            List<VipUnit> units = vipUnitDao.findAllByUnitId(unitId);
            mv.addObject("units",units);
        }
        mv.setViewName(unit.getTemplate()+"/chember_member_detail.html");
        return mv;
    }

    /**
     * 产品
     * @param req
     * @param unitId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{unitId}/products",method = RequestMethod.GET)
    public ModelAndView products(HttpServletRequest req,
                                 @PathVariable Integer unitId) {
        ModelAndView mv = new ModelAndView();
        //注入unit
        Unit unit = unitService.getById(unitId);
        if(unit==null||unit.getTemplate()==null){
            mv.addObject("error","无法找到企业或者商会的模版页面 请确认访问地址！！！！");
            mv.setViewName("/error.html");
            return mv;
        }else {
            mv.addObject("unit",unit);
            //注入业务产品
            List<Product> products = productDao.findAllByUnitId(unitId);
            mv.addObject("products",products);
        }
        mv.setViewName(unit.getTemplate()+"/chember_production.html");
        return mv;
    }



    /**
     * 新闻
     * @param req
     * @param unitId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{unitId}/news",method = RequestMethod.GET)
    public ModelAndView news(HttpServletRequest req,
                                 @PathVariable Integer unitId) {
        ModelAndView mv = new ModelAndView();
        //注入unit
        Unit unit = unitService.getById(unitId);
        if(unit==null||unit.getTemplate()==null){
            mv.addObject("error","无法找到企业或者商会的模版页面 请确认访问地址！！！！");
            mv.setViewName("/error.html");
            return mv;
        }else {
            mv.addObject("unit",unit);
            //注入新闻
            List<New> news = newDao.findAllByUnitIdAndStatus(unitId,"正常");
            mv.addObject("news",news);
        }
        mv.setViewName(unit.getTemplate()+"/chember_news.html");
        return mv;
    }

    /**
     * 新闻详情页面
     * @param req
     * @param unitId
     * @param newId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{unitId}/news/{newId}",method = RequestMethod.GET)
    public ModelAndView newsDetail(HttpServletRequest req,
                             @PathVariable Integer unitId,
                                   @PathVariable Integer newId) {
        ModelAndView mv = new ModelAndView();
        //注入unit
        Unit unit = unitService.getById(unitId);
        if(unit==null||unit.getTemplate()==null){
            mv.addObject("error","无法找到企业或者商会的模版页面 请确认访问地址！！！！");
            mv.setViewName("/error.html");
            return mv;
        }else {
            mv.addObject("unit",unit);
            //注入新闻
            NewDetails newDetail = newDetailsDao.findByNid(newId);
            New neww = newDao.findById(newId).get();
            mv.addObject("neww",neww);
            mv.addObject("newDetail",newDetail);
        }
        mv.setViewName(unit.getTemplate()+"/chember_news_detail.html");
        return mv;
    }

    /**
     * 活动列表
     * @param req
     * @param unitId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{unitId}/activities",method = RequestMethod.GET)
    public ModelAndView activities(HttpServletRequest req,
                                   @PathVariable Integer unitId) {
        ModelAndView mv = new ModelAndView();
        //注入unit
        Unit unit = unitService.getById(unitId);
        if(unit==null||unit.getTemplate()==null){
            mv.addObject("error","无法找到企业或者商会的模版页面 请确认访问地址！！！！");
            mv.setViewName("/error.html");
            return mv;
        }else {
            mv.addObject("unit",unit);
            //注入活动
            List<Activity> activities = activityDao.findAllByUnitId(unitId);
            mv.addObject("activities",activities);
        }
        mv.setViewName(unit.getTemplate()+"/chember_activity.html");
        return mv;
    }
    @ResponseBody
    @RequestMapping(value = "/{unitId}/activities/{aId}",method = RequestMethod.GET)
    public ModelAndView activities(HttpServletRequest req,
                                   @PathVariable Integer unitId,
                                   @PathVariable Integer aId) {
        ModelAndView mv = new ModelAndView();
        //注入unit
        Unit unit = unitService.getById(unitId);
        if(unit==null||unit.getTemplate()==null||unit.getType().equals("审核中")){
            mv.addObject("error","无法找到企业或者商会的模版页面 请确认访问地址！！！！");
            mv.setViewName("/error.html");
            return mv;
        }else {
            mv.addObject("unit",unit);
            //注入活动
            Activity activitie = activityDao.findById(aId).get();
            mv.addObject("activitie",activitie);
        }
        mv.setViewName(unit.getTemplate()+"/chember_activity_detail.html");
        return mv;
    }

}
