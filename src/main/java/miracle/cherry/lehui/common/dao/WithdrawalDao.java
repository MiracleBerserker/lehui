package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.Account;
import miracle.cherry.lehui.common.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-23 10:26
 * @Modified:
 * @Description:
 */
public interface WithdrawalDao extends JpaRepository<Withdrawal, Integer> {

    List<Withdrawal> findAllByStateAndType(String state,String type);

    @Query(value = "select * from withdrawal w where w.state=:state and w.type=:type and  w.name like %:namee% ", nativeQuery = true)
    List<Withdrawal> findAllByStateAndTypeAndName(@Param("state")String state, @Param("type")String type, @Param("namee")String name);

    List<Withdrawal> findAllByUserId(Integer userId);

    Withdrawal findByIdAndUserId(Integer id,Integer userId);
}
