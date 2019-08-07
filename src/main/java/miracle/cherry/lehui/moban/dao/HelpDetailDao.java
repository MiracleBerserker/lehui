package miracle.cherry.lehui.moban.dao;

import miracle.cherry.lehui.moban.entity.HelpDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-02 6:01
 * @Modified:
 * @Description:
 */
public interface HelpDetailDao extends JpaRepository<HelpDetail, Integer> {


    List<HelpDetail> findAllByHelpId(Integer helpId);
}
