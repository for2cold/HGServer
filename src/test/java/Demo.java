import com.alibaba.fastjson.JSON;
import com.kazyle.hugohelper.server.config.util.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

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
        String url = "http://8802680.com.cn/1.html?RUJrN1osMTg1MjgxLDI3MSw2NjUxLDYwNjMxLCZ6eGM9MTQ5Nzc0NjE0Nw==";
        String u = "NaN";
        String id = url.substring(url.indexOf('?') + 1);
        id = URLEncoder.encode(id, "utf-8");
        url = URLEncoder.encode(url, "utf-8");
        String result = HttpUtils.getUserAgent("http://ajax.xiazhuan.cc/?m=api&a=abc", "&u=" + u + "&id=" + id + "&h=" + url);
        result = result.substring(1, result.length() - 1);
        UrlView view = JSON.parseObject(result, UrlView.class);
        Document doc = Jsoup.parse(new URL(view.getMsg()), 30000);
        try {
            Elements els = doc.getAllElements();
            for (Element e : els) {
                /*if ("list".equals(e.className())) {
                    e.remove();
                    continue;
                }*/
                /*if ("dboottom".equals(e.className())) {
//                    e.remove();
                    e.removeClass("dboottom");
                    continue;
                }*/
            }
            doc.getElementById("hengfu_top").remove();
            doc.getElementById("wenzi_top").remove();
            doc.getElementById("wenzi_bottom").remove();
            doc.getElementById("hengfu_bottom").remove();
            doc.getElementById("ad_top").remove();
        } catch (Exception e) {
        }
        System.out.println(doc.outerHtml());
    }

    public static class UrlView {
        private String code;

        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
