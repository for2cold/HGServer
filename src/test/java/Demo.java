import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.kazyle.hugohelper.server.config.util.HttpUtils;
import com.kazyle.hugohelper.server.function.core.balance.view.ZhuanFaBaoView;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * <b>Demo</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/18
 */
public class Demo {

    public static void main(String[] args) throws IOException {
        String url = "http://api.jubaokeji.cn/index/index.php?c=index&m=userArticle&a=articleListNew";
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("xsign", "200.39063");
        paramMap.put("ysign", "1027.5");
        paramMap.put("type", "1");
        paramMap.put("page", "1");
        paramMap.put("end_time", "");
        paramMap.put("first_time", "");
        paramMap.put("action", "下拉刷新");
        paramMap.put("only", "861735035753689");
        paramMap.put("token", "b7aea0df6c0fb7bd8e4853f282099fb4");
        paramMap.put("cid", "1");
        paramMap.put("uid", "88299");
        paramMap.put("from", "搞笑");
        String result = HttpUtils.postUserAgent(url, paramMap, "ZHIXIAO_A");
        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
        JSONArray obj = (JSONArray) resultMap.get("data");
        List<ZhuanFaBaoView.ZhuanFaBaoArticle> articleList = JSON.parseArray(obj.toJSONString(), ZhuanFaBaoView.ZhuanFaBaoArticle.class);
        Random random = new Random();
        int index = random.nextInt(articleList.size());
        String nid = articleList.get(index).getNid();
        paramMap.clear();
        paramMap.put("nid", nid);
        paramMap.put("type", "1");
        paramMap.put("uid", "88299");

        result = HttpUtils.postUserAgent("http://api.jubaokeji.cn/index/index.php?c=index&m=userArticle&a=get_notice_info_new", paramMap, "ZHIXIAO_A");
        resultMap = JSON.parseObject(result, Map.class);
        Map<String, String> data = (Map<String, String>) resultMap.get("data");
        System.out.println("link-->" + data.get("link"));
    }
}
