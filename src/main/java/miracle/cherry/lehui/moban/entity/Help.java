package miracle.cherry.lehui.moban.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-02 5:49
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "HELP")
public class Help implements Serializable {
    private static final long serialVersionUID = 8735132886553200831L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;
    @Column(columnDefinition = "text")
    private String content;
    @Column
    private String remuneration;
    @Column
    private String qyName;
    @Column
    private String fzrName;
    @Column
    private String fzrTel;
    @Column
    private Integer unitId;
    @Column
    private String createTime;
    @Column
    private String createName;
    @Column
    private Integer createId;
    @Column
    private String status;
    @Transient
    private Integer sum;

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

    public String getRemuneration() {
        return remuneration;
    }

    public void setRemuneration(String remuneration) {
        this.remuneration = remuneration;
    }

    public String getQyName() {
        return qyName;
    }

    public void setQyName(String qyName) {
        this.qyName = qyName;
    }

    public String getFzrName() {
        return fzrName;
    }

    public void setFzrName(String fzrName) {
        this.fzrName = fzrName;
    }

    public String getFzrTel() {
        return fzrTel;
    }

    public void setFzrTel(String fzrTel) {
        this.fzrTel = fzrTel;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
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

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
