package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.Account;
import miracle.cherry.lehui.common.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-23 9:47
 * @Modified:
 * @Description:
 */
public interface BankCardDao extends JpaRepository<BankCard, Integer> {
    List<BankCard> findAllByUserId(Integer userId);
    BankCard findByIdAndUserId(Integer id,Integer userId);
}
