package miracle.cherry.lehui.common.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 13:19
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "USERUNITREL")
public class UserUnitRel implements Serializable {

    private static final long serialVersionUID = 873513266867320831L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private Integer unitId;
    @Column(length = 50)
    private Integer userId;
    @Column(length = 50)
    private String createTime;
    @Column(length = 50)
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserUnitRel{" +
                "id=" + id +
                ", unitId=" + unitId +
                ", userId=" + userId +
                ", createTime='" + createTime + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
