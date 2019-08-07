package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.Activity;
import miracle.cherry.lehui.moban.entity.Product;
import miracle.cherry.lehui.moban.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-28 14:26
 * @Modified:
 * @Description:
 */
public interface ProductDao extends JpaRepository<Product, Integer> {

    List<Product> findAllByUnitId(Integer unitId);
    List<Product> findAllByUnitIdAndStatus(Integer unitId, String status);
}
