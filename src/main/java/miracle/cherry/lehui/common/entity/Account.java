package miracle.cherry.lehui.common.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-23 8:12
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {

    private static final long serialVersionUID = 873513742573200831L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    //余额
    @Column
    private Integer balance;
    //用户id
    @Column
    private Integer userId;
    //状态
    @Column
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
