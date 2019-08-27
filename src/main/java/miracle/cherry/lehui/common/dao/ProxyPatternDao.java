package miracle.cherry.lehui.common.dao;


import miracle.cherry.lehui.common.entity.ProxyPattern;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-26 13:12
 * @Modified:
 * @Description:
 */
public interface ProxyPatternDao extends JpaRepository<ProxyPattern, Integer> {
    ProxyPattern findByRoleId(Integer roleId);
}
