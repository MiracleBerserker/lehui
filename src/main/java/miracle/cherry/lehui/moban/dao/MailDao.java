package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-02 12:59
 * @Modified:
 * @Description:
 */
public interface MailDao extends JpaRepository<Mail, Integer> {
    List<Mail> findAllByUserId(Integer userId);
}
