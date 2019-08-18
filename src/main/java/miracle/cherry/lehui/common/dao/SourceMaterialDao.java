package miracle.cherry.lehui.common.dao;


import miracle.cherry.lehui.common.entity.SourceMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-18 8:16
 * @Modified:
 * @Description:
 */
public interface SourceMaterialDao extends JpaRepository<SourceMaterial, Integer> {

    List<SourceMaterial> findAllByTypeAndModel(String type,String model);
    List<SourceMaterial> findAllByModel(String model);
}
