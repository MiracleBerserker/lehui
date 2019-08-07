package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.Activity;
import miracle.cherry.lehui.moban.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-28 14:02
 * @Modified:
 * @Description:
 */
public interface ContactDao extends JpaRepository<Contact, Integer> {
    Contact findByUnitId(Integer unitId);
}
