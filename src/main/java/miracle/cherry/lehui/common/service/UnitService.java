package miracle.cherry.lehui.common.service;

import miracle.cherry.lehui.common.dao.UnitDao;
import miracle.cherry.lehui.common.entity.Unit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 6:30
 * @Modified:
 * @Description:
 */
@Service("UnitService")
public class UnitService {
    @Resource
    UnitDao unitDao;

    public List<Unit> finaAll(){
        return unitDao.findAll();
    }


}
