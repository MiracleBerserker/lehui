package miracle.cherry.lehui.moban.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-28 13:58
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "CONTACT")
public class Contact  implements Serializable {

    private static final long serialVersionUID = 8735132088673200831L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    //负责人名字
    @Column
    private String name;
    //联系电话
    @Column
    private String tel;
    //公司电话
    @Column
    private String gsTel;
    //公司邮箱
    @Column
    private  String mail;
    //公司地址
    @Column
    private  String address;
    //机构id
    @Column
    private Integer unitId;

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

    public String getGsTel() {
        return gsTel;
    }

    public void setGsTel(String gsTel) {
        this.gsTel = gsTel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }
}
