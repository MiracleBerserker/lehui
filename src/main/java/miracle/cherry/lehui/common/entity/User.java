package miracle.cherry.lehui.common.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:用户表
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 5:39
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

    public static final  Integer ADMIN_USER=1;
    public static final String STATE_PROHIBIT = "禁止";
    public static final String STATE_NORMAL = "正常";
    public static final String STATE_RELOGIN = "重新登录";
    private static final long serialVersionUID = 8735132096673200831L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private String account;
    @Column(length = 50)
    private String password;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String wechat;
    @Column(length = 50)
    private String mail;
    @Column(length = 100)
    private String img;
    @Column(length = 50)
    private Integer shId;
    @Column(length = 50)
    private Integer qyId;
    @Column(length = 50)
    private String state;
    @Column(length = 50)
    private String registerTime;
    @Column(length = 50)
    private String type;
    @Transient
    private Unit qy;
    @Transient
    private Unit sh;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getShId() {
        return shId;
    }

    public void setShId(Integer shId) {
        this.shId = shId;
    }

    public Integer getQyId() {
        return qyId;
    }

    public void setQyId(Integer qyId) {
        this.qyId = qyId;
    }

    public Unit getQy() {
        return qy;
    }

    public void setQy(Unit qy) {
        this.qy = qy;
    }

    public Unit getSh() {
        return sh;
    }

    public void setSh(Unit sh) {
        this.sh = sh;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", wechat='" + wechat + '\'' +
                ", mail='" + mail + '\'' +
                ", img='" + img + '\'' +
                ", shId=" + shId +
                ", qyId=" + qyId +
                ", state='" + state + '\'' +
                ", qy=" + qy +
                ", sh=" + sh +
                '}';
    }
}
