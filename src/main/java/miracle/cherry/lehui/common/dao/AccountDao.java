package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.Account;
import miracle.cherry.lehui.common.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-23 8:37
 * @Modified:
 * @Description:
 */
public interface AccountDao extends JpaRepository<Account, Integer> {
}
