package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-18 12:39
 * @Modified:
 * @Description:
 */
public interface PaymentDao extends JpaRepository<Payment, Integer> {
    Payment findByUnitIdAndCostId(Integer unitId,Integer costId);
    List<Payment> findAllByCostId(Integer costId);
    List<Payment> findAllByCostIdAndUnitId(Integer costId,Integer unitId);
    List<Payment> findAllByUnitId(Integer unitId);
    List<Payment> findAllByType(String type);
    @Query(value = "select * from payment u where u.type = :type and u.name like %:namee% ", nativeQuery = true)
    List<Payment> findAllByTypeAndName(@Param("type")String type, @Param("namee")String namee);
}
