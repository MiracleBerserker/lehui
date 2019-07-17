package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.Unit;
import miracle.cherry.lehui.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 6:29
 * @Modified:
 * @Description:
 */
@Repository
public interface UnitDao extends JpaRepository<Unit, Integer> {

    Unit findByName(String name);
}
