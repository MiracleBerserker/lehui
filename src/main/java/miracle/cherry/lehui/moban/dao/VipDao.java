package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.Activity;
import miracle.cherry.lehui.moban.entity.Vip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-02 8:17
 * @Modified:
 * @Description:
 */
public interface VipDao extends JpaRepository<Vip, Integer> {
    List<Vip> findAllByUnitId(Integer unitId);
}
