package miracle.cherry.lehui.common.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-23 9:50
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "WITHDRAWAL")
public class Withdrawal implements Serializable {

    public static final String WITHDRAWAL_STATE_NORMAL = "正常";
    public static final String WITHDRAWAL_STATE_SHZ = "审核中";
    public static final String WITHDRAWAL_STATE_CLZ = "处理中";
    public static final String WITHDRAWAL_STATE_FAIL = "未通过";
    public static final String WITHDRAWAL_TYPE_ZR = "转入";
    public static final String WITHDRAWAL_TYPE_ZC = "转出";
    private static final long serialVersionUID = 873513742573200831L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    //用户名
    private String name;
    //用户电话
    private String tel;
    //用户银行卡绑定电话
    private String bankTel;
    //操作金额
    private Integer withdrawMoney;
    //操作之后账户余额
    private Integer balance;
    //用户操作的银行
    private String bankName;
    //银行卡号
    private String bankCard;
    //此处操作的标题
    private String title;
    //操作的内容说明
    private String content;
    //操作类型  转入 / 转出
    private String type;
    //状态  审核中/处理中/正常
    private String state;
    //操作开始时间
    private String startDateTime;
    //操作完成时间
    private String endDateTime;
    //用户id
    private Integer userId;
    //银行卡id
    private Integer bankCardId;

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public Integer getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(Integer withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBankTel() {
        return bankTel;
    }

    public void setBankTel(String bankTel) {
        this.bankTel = bankTel;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public Integer getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Integer bankCardId) {
        this.bankCardId = bankCardId;
    }
}
