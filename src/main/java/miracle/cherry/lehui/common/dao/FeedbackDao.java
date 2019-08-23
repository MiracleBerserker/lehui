package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.Cost;
import miracle.cherry.lehui.common.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-22 20:44
 * @Modified:
 * @Description:
 */
public interface FeedbackDao extends JpaRepository<Feedback, Integer> {
}
