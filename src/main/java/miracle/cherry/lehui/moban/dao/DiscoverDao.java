package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.Brief;
import miracle.cherry.lehui.moban.entity.Discover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-28 11:24
 * @Modified:
 * @Description:
 */
public interface DiscoverDao extends JpaRepository<Discover, Integer> {
    List<Discover> findAllByUnitId(Integer unitId);
    List<Discover> findAllByUnitIdAndStatus(Integer unitId,String status);
    List<Discover> findAllByUnitIdAndCreateId(Integer unitId,Integer userId);
    List<Discover> findAllByUnitIdAndStatusAndCreateId(Integer unitId,String status,Integer userId);
}
