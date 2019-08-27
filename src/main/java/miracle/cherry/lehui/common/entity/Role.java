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

    public static final Integer QY_ROLE = 1;//企业
    public static final Integer SH_ROLE = 2;//商会管理员
    public static final Integer SHHY_ROLE = 3;//商会会员
    public static final Integer ADMIN_ROLE = 4;//超级管理员
    public static final Integer ADMIN_MANAGER_ROLE = 5;//普通管理员
    public static final Integer TYPE_AGENT = 6;//代理
    public static final Integer TYPE_DISTRIBUTION = 7;//分销
    public static final Integer TYPE_MARKETING = 8;//全员营销
    public static final Integer TYPE_INITIAL = 9;
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
