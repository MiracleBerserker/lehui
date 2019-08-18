package miracle.cherry.lehui.common.dao;


import miracle.cherry.lehui.common.entity.SystemHelp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-18 8:46
 * @Modified:
 * @Description:
 */
public interface SystemHelpDao extends JpaRepository<SystemHelp, Integer> {

    @Query(value = "select * from systemhelp s where s.type= :type and s.problem like %:problem%)", nativeQuery = true)
    List<SystemHelp> findAllByTypeAndProblem(@Param("type")String type , @Param("problem")String problem);

    @Query(value = "select * from systemhelp s where s.problem like %:problem%)", nativeQuery = true)
    List<SystemHelp> findAllByProblem(@Param("problem")String problem);

}
