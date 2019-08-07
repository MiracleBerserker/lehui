package miracle.cherry.lehui.common.service;

import miracle.cherry.lehui.common.dao.DictsDao;
import miracle.cherry.lehui.common.entity.Dicts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-17 16:34
 * @Modified:
 * @Description:
 */
@Transactional//事务管理
@Service("dictsService")
public class DictsService {

    @Resource
    DictsDao dictsDao;

    public List<Dicts> findDictsByGroup(String group){
        return dictsDao.findAllByGroup(group);
    }
    public void deleteDict(Integer did){
        dictsDao.deleteById(did);
    }

    public Dicts addDict(Dicts dicts){
        dictsDao.save(dicts);
        return dicts;
    }
    public List<Dicts> getAll(){
        return dictsDao.findAll();
    }


}
