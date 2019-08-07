package miracle.cherry.lehui.common.dao;


import miracle.cherry.lehui.common.entity.RolePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-20 7:33
 * @Modified:
 * @Description:
 */
public interface RolePrivilegeDao extends JpaRepository<RolePrivilege, Integer> {
    List<RolePrivilege> findAllByRId(Integer rId);
}
