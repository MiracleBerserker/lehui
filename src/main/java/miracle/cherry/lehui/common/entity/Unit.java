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

    private static final long serialVersionUID = 8735132088673200831L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", files='" + files + '\'' +
                ", state='" + state + '\'' +
                ", mark='" + mark + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
