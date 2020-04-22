package taobao;

import lombok.Data;

/**
 * @description:
 * @author: Created by 遥远 on 2019-01-29 20:58
 */
@Data
public class TaobaoBuyUrl {

    private String title;
    private String buyUrl;

    public TaobaoBuyUrl(String title, String buyUrl) {
        this.title = title;
        this.buyUrl = buyUrl;
    }

}
