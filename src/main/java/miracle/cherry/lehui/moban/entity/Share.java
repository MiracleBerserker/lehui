package miracle.cherry.lehui.moban.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-02 6:26
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "SHARE")
public class Share implements Serializable {
    private static final long serialVersionUID = 8735137887550831L;

    //id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;
    @Column(columnDefinition = "text")
    private String content;
    @Column
    private String createTime;
    @Column
    private Integer unitId;
    @Column
    private String createName;
    @Column
    private String createImg;
    @Column
    private Integer createId;
    @Column
    private String status;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateImg() {
        return createImg;
    }

    public void setCreateImg(String createImg) {
        this.createImg = createImg;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
