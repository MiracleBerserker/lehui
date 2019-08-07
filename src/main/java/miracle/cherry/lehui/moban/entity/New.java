package miracle.cherry.lehui.moban.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-24 6:01
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "NEW")
public class New implements Serializable {
    private static final long serialVersionUID = 8735132088673200831L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    //标题
    @Column
    private String title;
    //时间
    @Column
    private String createTime;
    //展示图片
    @Column
    private String img;
    //简要内容
    @Column
    private String content;
    //属于那个机构
    @Column
    private Integer unitId;
    //创建人id
    @Column
    private Integer userId;
    //创建人姓名
    @Column
    private String userName;
    //新闻状态
    @Column
    private String status;
    @Transient
    private NewDetails newDetails;


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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public NewDetails getNewDetails() {
        return newDetails;
    }

    public void setNewDetails(NewDetails newDetails) {
        this.newDetails = newDetails;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
