package miracle.cherry.lehui.moban.service;


import jdk.nashorn.internal.ir.IdentNode;
import miracle.cherry.lehui.common.dao.UnitDao;
import miracle.cherry.lehui.common.entity.Unit;
import miracle.cherry.lehui.moban.dao.*;
import miracle.cherry.lehui.moban.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-24 6:21
 * @Modified:
 * @Description:
 */
@Transactional//事务管理
@Service("menuService")
public class MenuService {

    @Resource
    BriefDao briefDao;
    @Resource
    NewDao newDao;
    @Resource
    NewDetailsDao newDetailsDao;
    @Resource
    UnitDao unitDao;
    @Resource
    ActivityDao activityDao;
    @Resource
    InfoDao infoDao;
    @Resource
    DiscoverDao discoverDao;
    @Resource
    LunBoTuDao lunBoTuDao;
    @Resource
    ContactDao contactDao;
    @Resource
    ProductDao productDao;
    @Resource
    HelpDao helpDao;
    @Resource
    HelpDetailDao helpDetailDao;
    @Resource
    ShareDao shareDao;
    @Resource
    VipDao vipDao;
    @Resource
    MailDao mailDao;
    @Resource
    VipUnitDao vipUnitDao;

    public List<New> queryNew(Integer unitId,String status,Integer userId){
        List<New> queryNew = new ArrayList<>();
        if(isSh(unitId)){
            if(status!=null&&!"".equals(status)){
                queryNew = newDao.findAllByUnitIdAndStatus(unitId,status);
            }else {
                queryNew = newDao.findAllByUnitId(unitId);
            }
        }else {
            if(status!=null&&!"".equals(status)){
                queryNew = newDao.findAllByUnitIdAndStatusAndUserId(unitId,status,userId);
            }else {
                queryNew = newDao.findAllByUnitIdAndUserId(unitId,userId);
            }
        }

        return queryNew;
    }

    public New saveNew(New news) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        news.setStatus(getStatus(news.getUnitId()));
        news.setCreateTime(createTime);
        newDao.save(news);
        NewDetails newDetails = news.getNewDetails();
        newDetails.setNid(news.getId());
        newDetailsDao.save(newDetails);
        return news;
    }

    public New getNewById(Integer id){
        New neww = newDao.findById(id).get();
        NewDetails newDetails = newDetailsDao.findByNid(neww.getId());
        neww.setNewDetails(newDetails);
        return neww;
    }

    public void deleteNew(Integer newId){
        New news = newDao.getOne(newId);
        newDao.delete(news);
        NewDetails newDetails = newDetailsDao.findByNid(newId);
        newDetailsDao.delete(newDetails);
    }

    public void aggrenNews(Integer newId,String status){
        New news = newDao.findById(newId).get();
        if("未通过".equals(status)){
            newDao.delete(news);
            sendMail("对不起尊敬的"+news.getUserName()+"用户，您提交的《"+news.getTitle()+"》新闻未通过审核，请重新提交"
                    ,news.getUserId());
        }else {
            news.setStatus(status);
            newDao.save(news);
        }
    }

    /**\
     * 保存简介
     * @param brief
     * @return
     */
    public  Brief saveBrief(Brief brief) throws Exception {
        if(brief.getUnitId()==null){
            throw new Exception("机构id不能为空");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String editTime = simpleDateFormat.format(new Date());
        brief.setEditTime(editTime);
        briefDao.save(brief);
        return brief;
    }

    /**
     * 获取当前企业的简介
     * @param unitId
     * @return
     */
    public Brief getBrief(Integer unitId){
        Brief brief = briefDao.findByUnitId(unitId);
        return brief;
    }

    /**
     * 生成活动
     * @param activity
     * @return
     * @throws Exception
     */
    public Activity saveActivity(Activity activity) throws Exception {
        activity.setStatus(getStatus(activity.getUnitId()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        activity.setCreateTime(createTime);
        activity.setStatus(getStatus(activity.getUnitId()));
        activityDao.saveAndFlush(activity);
        return activity;
    }

    /**
     * 获取活动列表
     * @param unitId
     * @return
     */
    public List<Activity> queryActivity(Integer unitId,String status,Integer userId){
        List<Activity> activities = new ArrayList<>();
        if(isSh(unitId)) {
            if (status != null && !status.equals("")) {
                activities = activityDao.findAllByUnitIdAndStatus(unitId, status);
            } else {
                activities = activityDao.findAllByUnitId(unitId);
            }
        }else {
            if (status != null && !status.equals("")) {
                activities = activityDao.findAllByUnitIdAndStatusAndFbrId(unitId, status,userId);
            } else {
                activities = activityDao.findAllByUnitIdAndFbrId(unitId,userId);
            }
        }

        return activities;
    }


    public void aggrenActivity(Integer activityId,String status){
        Activity activity = activityDao.findById(activityId).get();
        if("未通过".equals(status)){
            activityDao.delete(activity);
            sendMail("对不起尊敬的"+activity.getName()+"用户，您提交的《"+activity.getTitle()+"》活动未通过审核，请重新提交"
                    ,activity.getFbrId());
        }else {
            activity.setStatus(status);
            activityDao.save(activity);
        }
    }


    /**
     * 获取单个活动
     * @param id
     * @return
     */
    public Activity getActivity(Integer id){
        Activity activitiy = activityDao.findById(id).get();
        return activitiy;
    }

    /**
     * 删除活动
     * @param id
     * @return
     */
    public Activity deleteActivity(Integer id){
        Activity activitiy = activityDao.findById(id).get();
        activityDao.delete(activitiy);
        return activitiy;
    }

    /**
     * 上传收集信息
     * @param info
     * @return
     */
    public Info saveInfo(Info info){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        info.setCreateTime(createTime);
        info = infoDao.save(info);
        return info;
    }

    /**
     * 查找全部的收集信息
     * @param unitId
     * @return
     */
    public List<Info> queryInfo(Integer unitId){
        List<Info> list = new ArrayList<>();
        list = infoDao.findAllByUnitId(unitId);
        return list;
    }

    /**
     * 删除预留信息
     * @param iId
     * @return
     */
    public Info deleteInfo(Integer iId){
       Info info =  infoDao.findById(iId).get();
       infoDao.delete(info);
       return info;
    }

    /**
     * 上传发现
     * @param discover
     * @return
     */
    public Discover saveDiscover(Discover discover) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        discover.setCreateTime(createTime);
        discover.setStatus(getStatus(discover.getUnitId()));
        discover = discoverDao.save(discover);
        return discover;
    }


    public void aggrenDiscover(Integer discoverId,String status){
        Discover discover = discoverDao.findById(discoverId).get();
        if("未通过".equals(status)){
            discoverDao.delete(discover);
            sendMail("对不起尊敬的"+discover.getCreateName()+"用户，您提交的《"+discover.getName()+"》发现未通过审核，请重新提交"
                    ,discover.getCreateId());
        }else {
            discover.setStatus(status);
            discoverDao.save(discover);
        }
    }


    /**
     * 查找全部的发现
     * @param unitId
     * @return
     */
    public List<Discover> queryDiscover(Integer unitId,String status,Integer userId){
        List<Discover> queryDiscover = new ArrayList<>();
        if(isSh(unitId)) {
            if (status != null && !"".equals(status)) {
                queryDiscover = discoverDao.findAllByUnitIdAndStatus(unitId, status);
            } else {
                queryDiscover = discoverDao.findAllByUnitId(unitId);
            }
        }else {
            if (status != null && !"".equals(status)) {
                queryDiscover = discoverDao.findAllByUnitIdAndStatusAndCreateId(unitId, status,userId);
            } else {
                queryDiscover = discoverDao.findAllByUnitIdAndCreateId(unitId,userId);
            }
        }
        return queryDiscover;
    }

    /**
     * 删除指定的发现
     * @param id
     * @return
     */
    public Discover deleteDiscover(Integer id){
        Discover discover = discoverDao.findById(id).get();
        discoverDao.delete(discover);
        return discover;
    }


    public Discover getDiscoverById(Integer id){
        return discoverDao.findById(id).get();
    }

    /**
     * 上传轮播图
     * @param
     * @return
     */
    public LunBoTu saveLunBoTu(LunBoTu lunBoTu) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        lunBoTu.setCreateTime(createTime);
        lunBoTu.setStatus(getStatus(lunBoTu.getUnitId()));
        lunBoTu =lunBoTuDao.save(lunBoTu);
        return lunBoTu;
    }

    /**
     * 删除轮播图
     * @param id
     * @return
     */
    public LunBoTu deleteLunBoTu(Integer id){
        LunBoTu lunBoTu = lunBoTuDao.findById(id).get();
        lunBoTuDao.delete(lunBoTu);
        return lunBoTu;
    }


    public void aggrenLunBoTu(Integer lunBoTuId,String status){
        LunBoTu lunBoTu = lunBoTuDao.findById(lunBoTuId).get();
        if("未通过".equals(status)){
            lunBoTuDao.delete(lunBoTu);
            sendMail("对不起尊敬的"+lunBoTu.getCreateName()+"用户，您提交的《"+lunBoTu.getTitle()+"》轮播图未通过审核，请重新提交"
                    ,lunBoTu.getCreateId());
        }else {
            lunBoTu.setStatus(status);
            lunBoTuDao.save(lunBoTu);
        }
    }


    /**
     * 查询轮播图列表
     * @param unitId
     * @return
     */
    public List<LunBoTu> queryLunBoTu (Integer unitId,String status,Integer userId){
        List<LunBoTu> queryLunBoTu = new ArrayList<>();
        if(isSh(unitId)) {
            if (status != null && !"".equals(status)) {
                queryLunBoTu = lunBoTuDao.findAllByUnitIdAndStatus(unitId, status);
            } else {
                queryLunBoTu = lunBoTuDao.findAllByUnitId(unitId);
            }
        }else {
            if (status != null && !"".equals(status)) {
                queryLunBoTu = lunBoTuDao.findAllByUnitIdAndStatusAndCreateId(unitId, status,userId);
            } else {
                queryLunBoTu = lunBoTuDao.findAllByUnitIdAndCreateId(unitId,userId);
            }
        }
        return queryLunBoTu;
    }

    public LunBoTu getLunBoTuById(Integer id){
        return lunBoTuDao.findById(id).get();
    }

    /**
     * 更新和修改联系方式
     * @param contact
     * @return
     */
    public Contact saveContact(Contact contact){
        Contact contact1 = contactDao.findByUnitId(contact.getUnitId());
        if(contact1!=null&&contact1.getUnitId()!=null){
            contact.setId(contact1.getId());
        }
        contact = contactDao.save(contact);
        return contact;
    }

    /**
     * 根据id查找联系方式
     * @param unitId
     * @return
     */
    public Contact queryContact(Integer unitId){
        Contact contact = contactDao.findByUnitId(unitId);
        return contact;
    }

    /**
     * 保存企业产品
     * @param product
     * @return
     */
    public Product saveProduct(Product product) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        product.setCreateName(createTime);
        product.setStatus(getStatus(product.getUnitId()));
        product = productDao.save(product);
        return product;
    }

    public Product getProductById(Integer id){
        return productDao.findById(id).get();
    }

    /**
     * 删除企业产品
     * @param id
     * @return
     */
    public Product deleteProduct(Integer id){
        Product product = productDao.findById(id).get();
        productDao.delete(product);
        return product;
    }

    /**
     * 查询当前企业的所有产品
     * @param unitId
     * @return
     */
    public List<Product> queryProduct(Integer unitId,String status){
        List<Product> queryProduct = new ArrayList<>();
        if(status!=null&&!"".equals(status)){
            queryProduct = productDao.findAllByUnitIdAndStatus(unitId,status);
        }else {
            queryProduct = productDao.findAllByUnitId(unitId);
        }
        return queryProduct;
    }

    /**
     * 查找企业所有的帮助列表
     * @param unitId
     * @return
     */
    public List<Help> queryHelp(Integer unitId,String status,Integer userId){
        List<Help> queryHelp = new ArrayList<>();
        if(isSh(unitId)) {
            if (status != null && !"".equals(status)) {
                queryHelp = helpDao.findAllByUnitIdAndStatus(unitId, status);
            } else {
                queryHelp = helpDao.findAllByUnitId(unitId);
            }
        }else {
            if (status != null && !"".equals(status)) {
                queryHelp = helpDao.findAllByUnitIdAndStatusAndCreateId(unitId, status,userId);
            } else {
                queryHelp = helpDao.findAllByUnitIdAndCreateId(unitId,userId);
            }
        }
        return queryHelp;
    }


    public void aggrenHelp(Integer helpId,String status){
        Help help = helpDao.findById(helpId).get();
        if("未通过".equals(status)){
            helpDao.delete(help);
            sendMail("对不起尊敬的"+help.getCreateName()+"用户，您提交的《"+help.getTitle()+"》帮助信息未通过审核，请重新提交"
                    ,help.getCreateId());
        }else {
            help.setStatus(status);
            helpDao.save(help);
        }
    }


    /**
     * 删除企业
     * @param id
     * @return
     */
    public  Help deleteHelp(Integer id){
        Help help = helpDao.findById(id).get();
        List<HelpDetail> helpDetails = helpDetailDao.findAllByHelpId(help.getId());
        helpDetailDao.deleteAll(helpDetails);
        helpDao.delete(help);
        return help;
    }

    public Help getHelpById(Integer id){
        return helpDao.findById(id).get();
    }

    /**
     * 保存或者更新帮助
     * @param help
     * @return
     */
    public Help saveHelp(Help help) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        help.setCreateTime(createTime);
        help.setStatus(getStatus(help.getUnitId()));
        help = helpDao.save(help);
        return help;
    }

    /**
     * 查找全部帮助对应的联系方式
     * @param helpId
     * @return
     */
    public List<HelpDetail> queryHelpDetail(Integer helpId){
        List<HelpDetail> helpDetails = helpDetailDao.findAllByHelpId(helpId);
        return helpDetails;
    }

    /**
     * 删除详情
     * @param id
     * @return
     */
    public HelpDetail deleteHelpDetail(Integer id){
        HelpDetail helpDetail = helpDetailDao.findById(id).get();
        helpDetailDao.delete(helpDetail);
        return helpDetail;
    }


    /**
     * 保存帮助联系方式
     * @param helpDetail
     * @return
     */
    public HelpDetail saveHelpDetail(HelpDetail helpDetail){
        if(helpDetail.getHelpId()==null){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        helpDetail.setCreateTime(createTime);
        helpDetail = helpDetailDao.save(helpDetail);
        return helpDetail;
    }





    /**
     * 查找全部分享
     * @param unitId
     * @return
     */
    public List<Share> queryShare(Integer unitId,String status,Integer userId){
        List<Share> queryShare = new ArrayList<>();
        if(isSh(unitId)) {
            if (status != null && !"".equals(status)) {
                queryShare = shareDao.findAllByUnitIdAndStatus(unitId, status);
            } else {
                queryShare = shareDao.findAllByUnitId(unitId);
            }
        }else {
            if (status != null && !"".equals(status)) {
                queryShare = shareDao.findAllByUnitIdAndStatusAndCreateId(unitId, status,userId);
            } else {
                queryShare = shareDao.findAllByUnitIdAndCreateId(unitId,userId);
            }
        }
        return queryShare;
    }

    /**
     * 删除分享
     * @param id
     * @return
     */
    public Share deleteShare(Integer id){
        Share share = shareDao.findById(id).get();
        shareDao.delete(share);
        return share;
    }

    public void aggrenShare(Integer shareId,String status){
        Share share = shareDao.findById(shareId).get();
        if("未通过".equals(status)){
            shareDao.delete(share);
            sendMail("对不起尊敬的"+share.getCreateName()+"用户，您提交的《"+share.getTitle()+"》分享未通过审核，请重新提交"
                    ,share.getCreateId());
        }else {
            share.setStatus(status);
            shareDao.save(share);
        }
    }



    /**
     * 保存分享
     * @param
     * @return
     */
    public Share saveShare(Share share) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        share.setCreateTime(createTime);
        share.setStatus(getStatus(share.getUnitId()));
        share = shareDao.save(share);
        return share;
    }


    public Share getShareById(Integer id){
        return shareDao.findById(id).get();
    }


    /**
     * 查找全部vip项目
     * @param unitId
     * @return
     */
    public List<Vip> queryVip(Integer unitId){
        List<Vip> vips = vipDao.findAllByUnitId(unitId);
        return vips;
    }

    /**
     * 删除vip项目
     * @param id
     * @return
     */
    public Vip deleteVip(Integer id){
        Vip vip = vipDao.findById(id).get();
        vipDao.delete(vip);
        return vip;
    }


    /**
     * 保存vip项目
     * @param
     * @return
     */
    public Vip saveVip(Vip vip){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        vip.setCreateTime(createTime);
        vip = vipDao.save(vip);
        return vip;
    }

    public Vip getVipById(Integer id){
        return vipDao.findById(id).get();
    }

    private String getStatus(Integer unitId) throws Exception {
            String status = "正常";
        if(unitId==null){
            throw new Exception("无法识别的机构无法进行正常上传新闻");
        }else {
            if(Integer.valueOf(unitId)==Integer.valueOf(Unit.ADMIN_SHID)){
                return status;
            }
            Unit unit =  unitDao.findById(unitId).get();
            if (unit.getType().equals("商会")){
                status = "审核中";
            }
        }
            return status;
    }

    public List<Mail> queryMail(Integer userId){
        List<Mail> mail = mailDao.findAllByUserId(userId);
        return mail;
    }
    public Mail deleteMail(Integer id){
        Mail mail = mailDao.findById(id).get();
        mailDao.delete(mail);
        return mail;
    }


    public void sendMail(String content,Integer userId){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
       Mail mail = new Mail();
       mail.setContent(content);
       mail.setStatus("未读");
       mail.setUserId(userId);
       mail.setTime(createTime);
       mailDao.save(mail);
    }

    private Boolean isSh(Integer unitId){
        Unit unit = unitDao.getOne(unitId);
        if("商会".equals(unit.getType())){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 保存会员单位
     */
    public VipUnit saveVipUnit(VipUnit vipUnit){
        if(vipUnit.getUnitId()==null){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        vipUnit.setCreateTime(createTime);
        vipUnitDao.save(vipUnit);
        return vipUnit;
    }

    /**
     * 删除会员单位
     * @param id
     * @return
     */
    public VipUnit deleteVipUnit(Integer id){
       VipUnit vipUnit = vipUnitDao.findById(id).get();
       if (vipUnit!=null){
           vipUnitDao.delete(vipUnit);
       }
       return vipUnit;
    }

    /**
     * 查询所有的会员单位
     * @param unitId
     * @return
     */
    public List<VipUnit> findAllVipUnitByUnitId(Integer unitId,String name){
        List<VipUnit> vipUnits = null;
        if(unitId!=null&&name!=null&&!"".equals(name)){
            vipUnits = vipUnitDao.findAllByUnitIdAndName(unitId,name);
        }else if(unitId!=null){
            vipUnits = vipUnitDao.findAllByUnitId(unitId);
        }

        return vipUnits;
    }

}
