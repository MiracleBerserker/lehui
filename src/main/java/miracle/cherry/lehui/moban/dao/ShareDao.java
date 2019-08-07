package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.Activity;
import miracle.cherry.lehui.moban.entity.LunBoTu;
import miracle.cherry.lehui.moban.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-02 6:32
 * @Modified:
 * @Description:
 */
public interface ShareDao extends JpaRepository<Share, Integer> {
    List<Share> findAllByUnitId(Integer unitId);
    List<Share> findAllByUnitIdAndStatus(Integer unitId, String status);
    List<Share> findAllByUnitIdAndCreateId(Integer unitId,Integer userId);
    List<Share> findAllByUnitIdAndStatusAndCreateId(Integer unitId, String status,Integer userId);
}
