package miracle.cherry.lehui.common.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-19 6:38
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "ROLE")
public class Role implements Serializable {
    private static final long serialVersionUID = 873516398673200831L;

    public static Integer QY_ROLE = 1;
    public static Integer SH_ROLE = 2;
    public static Integer SHHY_ROLE = 3;
    public static Integer ADMIN_ROLE = 4;
    public static Integer ADMIN_MANAGER_ROLE = 5;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private String roleName;
    @Transient
    private Integer unitId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }
}
