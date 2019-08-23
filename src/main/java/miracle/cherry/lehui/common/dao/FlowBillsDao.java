package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.Cost;
import miracle.cherry.lehui.common.entity.FlowBills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-20 9:55
 * @Modified:
 * @Description:
 */
public interface FlowBillsDao extends JpaRepository<FlowBills, Integer> {
    List<FlowBills> findAllByUnitId(Integer unitId);
    List<FlowBills> findAllByUnitIdAndCostId(Integer unitId,Integer costId);
    List<FlowBills> findAllByUserId(Integer userId);
    List<FlowBills> findAllByCostId(Integer costId);
    List<FlowBills> findAllByPaymentId(Integer payId);
}
