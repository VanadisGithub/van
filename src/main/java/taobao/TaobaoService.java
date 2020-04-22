package taobao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description:
 * @author: Created by 遥远 on 2019-01-30 10:00
 */
public class TaobaoService {

    /**
     * 淘宝优惠券
     *
     * @param taobaoUrl
     * @return
     */
    public List<TaobaoBuyUrl> coupon(String taobaoUrl) {
        String id = StringUtils.urlGetId(taobaoUrl);

        String activityUrl = "https://detailskip.taobao.com/service/getData/1/p1/item/detail/sib.htm?itemId=" + id + "&callback=onSibRequestSuccess&modules=couponActivity";

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("referer", "https://item.taobao.com/");
        String resultStr = HttpUtils.get(activityUrl, headerMap, null);

        String rgex = "onSibRequestSuccess\\((.*?)\\);";
        JSONObject data = JSONObject.parseObject(StringUtils.getSubUtilSimple(resultStr, rgex));
        JSONArray couponList = data.getJSONObject("data").getJSONObject("couponActivity").getJSONObject("coupon").getJSONArray("couponList");

        List<TaoBaoCoupon> taoBaoCoupons = Lists.newArrayList();
        if (Objects.nonNull(couponList)) {
            taoBaoCoupons = JSONArray.parseArray(couponList.toJSONString(), TaoBaoCoupon.class);
        }

        List<TaobaoBuyUrl> taobaoBuyUrls = Lists.newArrayList();
        taobaoBuyUrls.add(new TaobaoBuyUrl("直接购买", "https://uland.taobao.com/coupon/edetail?itemId=" + id + "&pid=mm_129079587_40306380_154602897"));
        taoBaoCoupons.forEach(coupon -> {
            String buyUrl = "https://uland.taobao.com/coupon/edetail?activityId=" + coupon.getActivityId() + "&itemId=" + id + "&pid=mm_129079587_40306380_154602897";
            taobaoBuyUrls.add(new TaobaoBuyUrl(coupon.getTitle(), buyUrl));
        });
        return taobaoBuyUrls;
    }
}
