package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.Unit;
import miracle.cherry.lehui.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

      List<Unit> findAllByTypeAndState(String type,String state);

      List<Unit> findAllByState(String state);
      Unit findByCode(String code);
      @Query(value = "select * from unit where id in(select qy_id from user u where u.sh_id=:shId and qy_id is not null) ", nativeQuery = true)
      List<Unit> findAllUnit(@Param("shId")Integer shId);
}
