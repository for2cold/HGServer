import com.kazyle.hugohelper.server.config.util.HttpUtils;

import java.io.IOException;
import java.util.Map;

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
        String url = "http://zqw.2662126.com/App/Member/login";
        String params = "phone=13430321448&password=19881027&deviceId=357750052152864";
        Map<String, String> paramMap = HttpUtils.getParamMap(params);
        String result = HttpUtils.postUserAgent(url, paramMap);
        System.out.println("result-->" + result);
    }
}
