package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.Brief;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-28 7:40
 * @Modified:
 * @Description:
 */
public interface BriefDao extends JpaRepository<Brief, Integer> {

    Brief findByUnitId(Integer unitId);

}
