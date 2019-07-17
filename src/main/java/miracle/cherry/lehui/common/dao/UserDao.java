package miracle.cherry.lehui.common.dao;

import miracle.cherry.lehui.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
