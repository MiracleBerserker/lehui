package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.Role;
import miracle.cherry.lehui.common.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-17 21:49
 * @Modified:
 * @Description:
 */
public interface RoleUserDao extends JpaRepository<RoleUser, Integer> {
    List<RoleUser> findAllByUId(Integer uid);
}
