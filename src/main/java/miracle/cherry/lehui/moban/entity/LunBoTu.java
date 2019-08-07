package miracle.cherry.lehui.moban.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-28 13:25
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "LUNBOTU")
public class LunBoTu  implements Serializable {

    private static final long serialVersionUID = 87351320887550831L;

    //id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    //标题
    @Column
    private String title;
    //图片
    @Column
    private String img;
    //说明
    @Column(columnDefinition = "text")
    private String content;
    //发布人
    @Column
    private String createName;
    //发布人id
    @Column
    private Integer createId;
    //发布时间
    @Column
    private String createTime;
    //状态
    @Column
    private String status;
    //组织id
    @Column
    private Integer unitId;


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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }
}
