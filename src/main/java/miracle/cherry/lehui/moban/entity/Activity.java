package miracle.cherry.lehui.moban.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-28 7:51
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "ACTIVITY")
public class Activity implements Serializable {
    private static final long serialVersionUID = 87351320887550831L;

    //id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    //标题
    @Column
    private String title;
    //内容
    @Column(columnDefinition = "text")
    private String content;
    //配图
    @Column
    private String img;
    //负责人
    @Column
    private String fzr;
    //联系方式
    @Column
    private String tel;
    //活动时间
    private String time;
    //地址
    @Column
    private String address;
    //发布人id
    @Column
    private Integer fbrId;
    //发布人姓名
    @Column
    private String name;
    //发布时间
    @Column
    private String createTime;
    //状态值
    @Column
    private String status;
    //机构id
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFzr() {
        return fzr;
    }

    public void setFzr(String fzr) {
        this.fzr = fzr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getFbrId() {
        return fbrId;
    }

    public void setFbrId(Integer fbrId) {
        this.fbrId = fbrId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
