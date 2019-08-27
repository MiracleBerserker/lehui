package miracle.cherry.lehui.common.entity;

import org.springframework.context.annotation.Primary;

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

    public static final String ACCOUNT_STATE_NORMAL="正常";
    public static final String ACCOUNT_STATE_EXEPETION="异常";
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    //余额
    @Column
    private Double balance;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
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
