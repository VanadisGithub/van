package taobao;

import lombok.Data;

/**
 * @description:
 * @author: Created by 遥远 on 2019-01-29 20:55
 */
@Data
public class TaoBaoCoupon {

    private String activityId;
    private String sellerId;
    private String type;
    private String title;
    private boolean isGot;

}
