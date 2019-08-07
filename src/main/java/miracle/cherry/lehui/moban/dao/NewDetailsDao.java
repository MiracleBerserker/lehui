package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.NewDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-24 6:14
 * @Modified:
 * @Description:
 */
public interface NewDetailsDao extends JpaRepository<NewDetails, Integer> {
    public NewDetails findByNid(Integer nId);
}
