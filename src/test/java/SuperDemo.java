import com.alibaba.fastjson.JSON;
import com.kazyle.hugohelper.server.config.util.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * <b>SuperDemo</b> is
 * </p>
 *
 * @author HuangWeigang
 * @version $$id$$
 * @since 2017/7/5
 */
public class SuperDemo {

    public static void main(String[] args) throws IOException {
//        String url = "http://app.584230.com/?cur_plat=android";
        String url = "http://app.584230.com/share_article/tui";
        String cookies = "kdz_uid=7436; kdz_auth=a01cNA1bg6gAlRw0RPsGKAfHLJN2ejTuuTUPZqnNXvHb3uv%2B4IgVeaM2O44yvJsEEg;";
        String userAgent = "Mozilla/5.0 (Linux; Android 4.4.2; 2014011 Build/HM2014011) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36;{device_id:356451044442023};{ver:1.1};{client-kdz-android}";
//        String url = "http://app.aichuan8.com/?cur_plat=android";
//        String cookies = "kdz_uid=84924; kdz_auth=69f2lMQX1Jwcs2cQng%2BP6kr4I90jbPmTqoZ4alaQ9sUOVHF4Z52TXgGtsjalp7I6vUg";
//        String userAgent = "Mozilla/5.0 (Linux; Android 4.4.2; 2014011 Build/HM2014011) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36;{device_id:356451044442023};{ver:1.5};{client-kdz-android}";
        String html = HttpUtils.getByCookieAndUserAgent(url, cookies, userAgent);
        Document doc = Jsoup.parse(html);
        Elements es = doc.getElementsByClass("context-del");
        Random random = new Random();
        int index = random.nextInt(es.size());
        Element e = es.get(index);
        String onclick = e.attr("onclick");
        System.out.println(onclick);
        onclick = onclick.replace("openNewWind('", "").replace("', 'login_blank')", "").replace("','login_blank')", "");
        html = HttpUtils.getByCookieAndUserAgent(onclick, cookies, userAgent);
        doc = Jsoup.parse(html);
        es = doc.getElementsByTag("script");
        for (Element ee : es) {
            String[] data = ee.data().toString().trim().split("var");
            for(String variable : data){
                variable = variable.trim();
                if(variable.startsWith("share_art_data = ")){
                    variable = variable.replace("share_art_data = ", "");
                    variable = variable.substring(0, variable.indexOf(';'));
                    System.out.println("var--->" + variable);
                    Map<String, Object> map = JSON.parseObject(variable, Map.class);
                    Map<String, String> copy = (Map<String, String>) map.get("copy");
                    String target = copy.get("url");
                    System.out.println(target);
                    break;
                }
            }
        }
    }
}
