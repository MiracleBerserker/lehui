package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.Account;
import miracle.cherry.lehui.common.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

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

    List<Withdrawal>findAllByUserIdAndState(Integer userId,String state);

    List<Withdrawal>findAllByUserIdAndType(Integer userId,String type);

    List<Withdrawal>findAllByUserIdAndTypeAndState(Integer userId,String type,String state);


    /**
     * 获取当日的收入
     * @return
     */
    @Query(value = "SELECT SUM(w.withdraw_money)AS num  FROM  withdrawal w " +
            "WHERE DATE_FORMAT(NOW(),'%Y-%m-%d') <= w.end_date_time AND w.state='正常' " +
            "AND w.type='转入' AND  w.user_id=:userId", nativeQuery = true)
    Map<String,String> getDayMoney(@Param("userId")Integer userId);

    /**
     * 获取当月的收入
     * @return
     */
    @Query(value = "SELECT SUM(w.withdraw_money)AS num  FROM  withdrawal w " +
            "WHERE DATE_ADD(CURDATE(), INTERVAL - DAY(CURDATE()) + 1 DAY)<= w.end_date_time  " +
            "AND w.state='正常' AND w.type='转入' AND w.user_id=:userId",nativeQuery = true)
    Map<String,String> getMonthMoney(@Param("userId")Integer userId);

    /**
     * 获取所有收入
     * @return
     */
    @Query(value = "SELECT SUM(w.withdraw_money)AS num  FROM  withdrawal w " +
            "WHERE w.state='正常' AND w.type='转入' AND w.user_id=:userId ",nativeQuery = true)
    Map<String,String> getAllMoney(@Param("userId")Integer userId);


    @Query(value = "SELECT COUNT(u.id) AS num  FROM  user u " +
            "WHERE DATE_FORMAT(NOW(),'%Y-%m-%d')<=u.register_time AND u.parent_id=:userId",nativeQuery = true)
    Map<String,String> getDayUser(@Param("userId")Integer userId);

    @Query(value = "SELECT COUNT(u.id) AS num  FROM  user u " +
            "WHERE DATE_ADD(CURDATE(), INTERVAL - DAY(CURDATE()) + 1 DAY)<=u.register_time AND u.parent_id=:userId",nativeQuery = true)
    Map<String,String> getMonthUser(@Param("userId")Integer userId);


    @Query(value = "SELECT COUNT(u.id) AS num  FROM  user u where u.parent_id=:userId",nativeQuery = true)
    Map<String,String> getAllUser(@Param("userId")Integer userId);

}
