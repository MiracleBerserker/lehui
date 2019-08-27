package miracle.cherry.lehui.common.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-18 11:53
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "COST")
public class Cost  implements Serializable {
    private static final long serialVersionUID = 8735132088673200831L;

    public static final String STATUS_NORMAL="正常";
    public static final String STATUS_SHIXIAO="失效";

    public static final String STATUS_COSTUNIT_DAY="day";
    public static final String STATUS_COSTUNIT_MONTH="month";
    public static final String STATUS_COSTUNIT_YEAR="year";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;
    @Column
    private String unitType;
    @Column
    private String level;
    @Column
    private Double money;
    @Column
    private String measurement;
    @Column
    private String moneymea;
    @Column
    private String type;
    @Column
    private String status;
    @Column
    private Integer nums;
    @Column
    private String startDateTime;
    @Column
    private String endDateTime;

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

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getMoneymea() {
        return moneymea;
    }

    public void setMoneymea(String moneymea) {
        this.moneymea = moneymea;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
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
}
