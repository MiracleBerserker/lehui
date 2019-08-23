package miracle.cherry.lehui.common.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-23 8:45
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "BANKCARD")
public class BankCard implements Serializable {

    private static final long serialVersionUID = 873577742573200831L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String backName;
    private String backNumber;
    private String dateTime;
    private Integer userId;

}
