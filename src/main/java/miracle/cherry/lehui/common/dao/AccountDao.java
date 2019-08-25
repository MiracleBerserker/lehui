package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.Account;
import miracle.cherry.lehui.common.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-23 8:37
 * @Modified:
 * @Description:
 */
public interface AccountDao extends JpaRepository<Account, Integer> {

    /**
     * 加上行锁 避免出现并发不同步问题
     * @return
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Account getById(Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Account getByUserId(Integer userId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Account findByUserIdAndState(Integer userId,String state);
}
