package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-18 12:39
 * @Modified:
 * @Description:
 */
public interface CostDao extends JpaRepository<Cost, Integer> {
    List<Cost> findAllByTypeAndStatus(String type,String status);
    List<Cost> findAllByTypeAndStatusAndUnitType(String type,String status,String unitType);
}
