package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.VipUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-20 8:26
 * @Modified:
 * @Description:
 */
public interface VipUnitDao extends JpaRepository<VipUnit, Integer> {
    List<VipUnit> findAllByUnitId(Integer unitId);
    @Query(value = "select * from vipunit u where u.unit_id = :unitId and (u.name like %:namee% )", nativeQuery = true)
    List<VipUnit> findAllByUnitIdAndName(@Param("unitId")Integer unitId, @Param("namee")String namee);
}
