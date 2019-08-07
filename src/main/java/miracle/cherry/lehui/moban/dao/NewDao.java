package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.LunBoTu;
import miracle.cherry.lehui.moban.entity.New;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-24 6:13
 * @Modified:
 * @Description:
 */
public interface NewDao extends JpaRepository<New, Integer> {

  List<New> findAllByUnitIdAndStatusAndUserId (Integer uinitId,String status,Integer userId);
  List<New> findAllByUnitIdAndStatus (Integer uinitId,String status);
  List<New> findAllByUnitId(Integer unitId);
  List<New> findAllByUnitIdAndUserId(Integer unitId,Integer userId);
}
