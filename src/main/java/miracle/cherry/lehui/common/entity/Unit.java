package miracle.cherry.lehui.common.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 6:21
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "UNIT")
public class Unit implements Serializable {


    public static final Integer ADMIN_SHID=80;

    private static final long serialVersionUID = 8735132088673200831L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String region;
    @Column
    private String files;
    @Column(length = 50)
    private String state;
    @Column
    private String mark;
    @Column(length = 50)
    private String type;
    //联系人
    @Column
    private String contacts;
    //联系电话
    @Column
    private String tel;
    //社会统一代码
    @Column
    private String code;
    @Column
    private  String url;
    @Column
    private String template;
    @Transient
    private List<String> arrayFiles;

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getArrayFiles() {
        return arrayFiles;
    }

    public void setArrayFiles(List<String> arrayFiles) {
        this.arrayFiles = arrayFiles;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
