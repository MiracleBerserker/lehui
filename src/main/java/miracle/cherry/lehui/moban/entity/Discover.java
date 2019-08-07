package miracle.cherry.lehui.moban.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-28 11:17
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "DISCOVER")
public class Discover implements Serializable {

    private static final long serialVersionUID = 8735132088796331L;
    //id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    //项目名称
    @Column
    private String name;
    //项目介绍
    @Column(columnDefinition = "text")
    private String introduce;
    //发起人
    @Column
    private String fqrName;
    //tel
    @Column
    private String tel;
    //创建时间
    @Column
    private String createTime;
    //创建人
    @Column
    private String createName;
    //创建人id
    @Column
    private Integer createId;
    //状态
    @Column
    private String status;
    @Column
    private Integer unitId;
    @Column
    private String imgPath;
    @Column
    private String fqTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getFqrName() {
        return fqrName;
    }

    public void setFqrName(String fqrName) {
        this.fqrName = fqrName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getFqTime() {
        return fqTime;
    }

    public void setFqTime(String fqTime) {
        this.fqTime = fqTime;
    }
}
