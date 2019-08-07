package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.Activity;
import miracle.cherry.lehui.moban.entity.Help;
import miracle.cherry.lehui.moban.entity.LunBoTu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-28 13:32
 * @Modified:
 * @Description:
 */
public interface LunBoTuDao extends JpaRepository<LunBoTu, Integer> {

    List<LunBoTu> queryAllByUnitId(Integer UnitId);
    List<LunBoTu> findAllByUnitIdOrderByCreateTimeDesc(Integer UnitId);
    List<LunBoTu> findAllByUnitId(Integer UnitId);
    List<LunBoTu> findAllByUnitIdAndStatus(Integer unitId,String status);
    List<LunBoTu> findAllByUnitIdAndCreateId(Integer UnitId,Integer userId);
    List<LunBoTu> findAllByUnitIdAndStatusAndCreateId(Integer unitId,String status,Integer userId);
    List<LunBoTu> findAllByUnitIdAndStatusOrderByCreateTimeDesc(Integer UnitId,String status);
}
