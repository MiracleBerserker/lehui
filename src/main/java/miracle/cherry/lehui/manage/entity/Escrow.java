package miracle.cherry.lehui.manage.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-08-08 16:35
 * @Modified:
 * @Description:
 */
@Entity
@Table(name = "ESCROW")
public class Escrow implements Serializable {
    private static final long serialVersionUID = 87351320887550831L;

    //id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    //联系方式
    //代管开始时间
    //代管结束时间
    //状态
    //申请人id
    //账号
    //密码
    //备注说明
    //申请时间
}
