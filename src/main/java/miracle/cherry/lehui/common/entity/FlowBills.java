package miracle.cherry.lehui.common.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-20 9:42
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "FLOWBILLS")
public class FlowBills implements Serializable {

    private static final long serialVersionUID = 8735132088673200831L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer costId;
    @Column
    private Integer unitId;
    @Column
    private Integer userId;
    @Column
    private Integer paymentId;
    @Column
    private String createTime;
    @Column
    private Integer money;
    @Column
    private String  orderNumber;
    @Column
    private String name;
    @Column(columnDefinition = "text")
    private String content;
    @Column
    private String unitName;
    @Column
    private String userName;
    @Column
    private String costName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCostId() {
        return costId;
    }

    public void setCostId(Integer costId) {
        this.costId = costId;
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

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }
}
