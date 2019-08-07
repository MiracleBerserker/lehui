package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.Dicts;
import miracle.cherry.lehui.common.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-17 16:32
 * @Modified:
 * @Description:
 */
public interface DictsDao extends JpaRepository<Dicts, Integer> {

    List<Dicts> findAllByGroup(String group);
}
