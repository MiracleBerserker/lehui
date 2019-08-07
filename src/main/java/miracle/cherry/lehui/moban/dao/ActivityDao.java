package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.Activity;
import miracle.cherry.lehui.moban.entity.Brief;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-28 8:04
 * @Modified:
 * @Description:
 */
public interface ActivityDao extends JpaRepository<Activity, Integer> {

    List<Activity> findAllByUnitId(Integer unitId);
    List<Activity> findAllByUnitIdAndStatus(Integer unitId,String status);
    List<Activity> findAllByUnitIdAndFbrId(Integer unitId,Integer userId);
    List<Activity> findAllByUnitIdAndStatusAndFbrId(Integer unitId,String status,Integer userId);
}
