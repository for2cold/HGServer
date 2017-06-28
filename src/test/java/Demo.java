import com.kazyle.hugohelper.server.config.util.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

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
        String url = "http://www.snabq.cn/User/index/index";
        String result = HttpUtils.getByCookie(url, "user_pid=0; code=hHqlqIWmpbCweqapfX25aIB1sp8; sms_user_tel=15603056644; id=hHqlqIWmpbCweqapfX2paH91yJU");
        Document doc = Jsoup.parse(result);
        Elements els = doc.getElementsByClass("balance");
        Element e = els.get(0);
        String text = e.text();
        text = text.replace("账户余额（元） ", "");
        System.out.println("text-->" + text);
        els = doc.getElementsByClass("juzhong");
        e = els.get(1);
        text = e.text();
        text = text.replace("元", "");
        System.out.println("juzhong-->" + text);
    }
}
