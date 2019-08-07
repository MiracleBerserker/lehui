package miracle.cherry.lehui.moban.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-24 6:07
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "NEWDETAILS")
public class NewDetails  implements Serializable {
    private static final long serialVersionUID = 8735132088673200831L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    //nid
    @Column
    private Integer nid;
    //details
    @Column(columnDefinition = "text")
    private  String details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
