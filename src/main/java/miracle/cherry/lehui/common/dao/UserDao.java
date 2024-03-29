package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 5:54
 * @Modified:
 * @Description:
 */
@Repository
public interface UserDao  extends JpaRepository<User, Integer> {


    /**
     * 检查用户是否登录
     * @param account
     * @param password
     * @return
     */
   User findByAccountAndPassword(String account,String password);

    /**
     * 检测用户是否已经存在
     * @param account
     * @return
     */
   User findByAccount(String account);

    /**
     * 查询整个企业的所有人员账号
     * @param qyId
     * @return
     */
   List<User> findAllByQyId(Integer qyId);

    /**
     * 查找所有的商会的账号
     * @param shId
     * @return
     */
   List<User> findAllByShId(Integer shId);
   @Query(value = "select * from user u where u.qy_id = :unitId and (u.account like %:condition% or u.name like %:condition%)", nativeQuery = true)
   List<User> findByConditionsAndQy(@Param("unitId")Integer unitId,@Param("condition")String condition);

    @Query(value = "select * from user u where u.sh_id = :unitId and (u.account like %:condition% or u.name like %:condition%)", nativeQuery = true)
    List<User> findByConditionsAndSh(@Param("unitId")Integer unitId,@Param("condition")String condition);

    @Query(value = "select * from user u where(u.account like %:condition% or u.name like %:condition%)", nativeQuery = true)
    List<User> findByConditions(@Param("condition")String condition);

    /**
     * 获取当日的收入
     * @return
     */
    @Query(value = "SELECT SUM(f.money)AS num  FROM  flowbills f " +
            "WHERE DATE_FORMAT(NOW(),'%Y-%m-%d') <= f.create_time", nativeQuery = true)
    Map<String,String> getDayMoney();

    /**
     * 获取当月的收入
     * @return
     */
    @Query(value = "SELECT SUM(f.money)AS num  FROM  flowbills f " +
            "WHERE DATE_ADD(CURDATE(), INTERVAL - DAY(CURDATE()) + 1 DAY)<=f.create_time",nativeQuery = true)
    Map<String,String> getMonthMoney();

    /**
     * 获取所有收入
     * @return
     */
    @Query(value = "SELECT SUM(f.money)AS num  FROM  flowbills f ",nativeQuery = true)
    Map<String,String> getAllMoney();


    @Query(value = "SELECT COUNT(u.id) AS num  FROM  user u " +
            "WHERE DATE_FORMAT(NOW(),'%Y-%m-%d')<=u.register_time",nativeQuery = true)
    Map<String,String> getDayUser();

    @Query(value = "SELECT COUNT(u.id) AS num  FROM  user u " +
            "WHERE DATE_ADD(CURDATE(), INTERVAL - DAY(CURDATE()) + 1 DAY)<=u.register_time",nativeQuery = true)
    Map<String,String> getMonthUser();


    @Query(value = "SELECT COUNT(u.id) AS num  FROM  user u ",nativeQuery = true)
    Map<String,String> getAllUser();

}
