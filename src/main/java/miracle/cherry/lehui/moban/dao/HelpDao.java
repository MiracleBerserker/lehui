package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.Help;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-02 5:59
 * @Modified:
 * @Description:
 */
public interface HelpDao extends JpaRepository<Help, Integer> {
    List<Help> findAllByUnitId(Integer unitId);
    List<Help> findAllByUnitIdAndStatus(Integer unitId,String status);
    List<Help> findAllByUnitIdAndCreateId(Integer unitId,Integer userId);
    List<Help> findAllByUnitIdAndStatusAndCreateId(Integer unitId,String status,Integer userId);
}
