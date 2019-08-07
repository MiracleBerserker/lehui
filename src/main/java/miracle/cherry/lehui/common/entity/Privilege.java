package miracle.cherry.lehui.common.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-17 11:03
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "PRIVILEGE")
public class Privilege implements Serializable {
    private static final long serialVersionUID = 8735620886663200831L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 100)
    private String icon;
    @Column
    private String note;
    @Column(length = 20)
    private String state;
    @Column(length = 50)
    private String sbId;
    @Column(length = 50)
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSbId() {
        return sbId;
    }

    public void setSbId(String sbId) {
        this.sbId = sbId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "id=" + id +
                ", icon='" + icon + '\'' +
                ", note='" + note + '\'' +
                ", state='" + state + '\'' +
                ", sbId='" + sbId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
