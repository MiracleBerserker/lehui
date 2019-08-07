package miracle.cherry.lehui.common.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import miracle.cherry.lehui.common.entity.Dicts;
import miracle.cherry.lehui.common.entity.Unit;
import miracle.cherry.lehui.common.service.DictsService;
import miracle.cherry.lehui.common.service.UnitService;
import miracle.cherry.lehui.common.service.UserService;
import miracle.cherry.lehui.common.tools.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-17 16:34
 * @Modified:
 * @Description:
 */
@RestController
@Api(tags = "数据字典-无登录就可以调用  配有增删改查  可以自己做页面进行增删改查管理")
@RequestMapping(value = CommonUrl.DICTS)
public class DictsController {

    @Resource
    DictsService dictsService;
    @Resource
    UnitService unitService;
    @ResponseBody
    @ApiOperation(value="获取数据库中字典表全部内容-不支持分页查询",response = Result.class)
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public String getAll() throws Exception {
        return new Result(Result.SUCCESS,dictsService.getAll(),"获取成功").toJson();
    }
    @ResponseBody
    @ApiOperation(value="获取制定组的字典值",response = Result.class)
    @RequestMapping(value = "/findDictsByGroup",method = RequestMethod.GET)
    public String getAll(@ApiParam(value = "字典表分组标识", required = true)
                         @RequestParam
                         String group) throws Exception {
        return new Result(Result.SUCCESS,dictsService.findDictsByGroup(group),"获取成功").toJson();
    }
    @ResponseBody
    @ApiOperation(value="删除指定字典",response = Result.class)
    @RequestMapping(value = "/deleteDictsById",method = RequestMethod.GET)
    public String deleteDictsById(@ApiParam(value = "字典表id", required = true)
                                 @RequestParam
                                 Integer did) throws Exception {
        dictsService.deleteDict(did);
        return new Result(Result.SUCCESS, Collections.singletonMap("did",did),"删除成功").toJson();
    }
    @ResponseBody
    @ApiOperation(value="新增字典",response = Result.class)
    @RequestMapping(value = "/addDicts",method = RequestMethod.POST)
    public String deleteDictsById(@ApiParam(value = "字典组", required = true)
                                    @RequestBody Dicts dicts) throws Exception {
        dicts = dictsService.addDict(dicts);
        return new Result(Result.SUCCESS, dicts,"添加成功").toJson();
    }
    @ResponseBody
    @ApiOperation(value="获取企业或商会列表",response = Result.class)
    @RequestMapping(value = "/getUnit",method = RequestMethod.GET)
    public String getUnit(@ApiParam(value = "企业  商会", required = true)
                              @RequestParam
                              String type) throws Exception {
        System.out.println(type);
        List<Unit> units = unitService.findAllByType(type);
        System.out.println(units);
        return new Result(Result.SUCCESS, units,"获取成功").toJson();
    }




}
