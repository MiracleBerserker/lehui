package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.User;
import miracle.cherry.lehui.common.entity.UserUnitRel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 16:25
 * @Modified:
 * @Description:
 */
public interface UserUnitRelDao  extends JpaRepository<UserUnitRel, Integer> {

    /**
     * 检测是否重复申请
     * @param unId
     * @param usID
     * @return
     */
     UserUnitRel findByUnitIdAndUserId(Integer unId,Integer usID);
}
