package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.Activity;
import miracle.cherry.lehui.moban.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-28 10:40
 * @Modified:
 * @Description:
 */
public interface InfoDao extends JpaRepository<Info, Integer> {
    List<Info> findAllByUnitId(Integer unitId);
}
