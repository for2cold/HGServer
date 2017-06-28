package com.kazyle.hugohelper.server.function.core.balance.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.kazyle.hugohelper.server.config.util.ExcelUtils;
import com.kazyle.hugohelper.server.config.util.HttpUtils;
import com.kazyle.hugohelper.server.function.core.article.entity.Article;
import com.kazyle.hugohelper.server.function.core.article.repository.ArticleRepository;
import com.kazyle.hugohelper.server.function.core.article.service.ArticleService;
import com.kazyle.hugohelper.server.function.core.balance.entity.Balance;
import com.kazyle.hugohelper.server.function.core.balance.repository.BalanceRepository;
import com.kazyle.hugohelper.server.function.core.balance.service.BalanceService;
import com.kazyle.hugohelper.server.function.core.balance.view.*;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <p>
 * <b>BalanceServiceImpl</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/3
 */
@Service
public class BalanceServiceImpl implements BalanceService {

    @Resource
    private BalanceRepository balanceRepository;

    @Resource
    private ArticleRepository articleRepository;

    @Resource
    private ArticleService articleService;

    private static final String XIA_ZHUAN_URL = "http://xiazhuan.duoshoutuan.com/shell/android.php";
    private static final String WUDI_ZHUAN_URL = "http://wudizhuan.duoshoutuan.com/shell/android.php";
    private static final String NIUBI_ZHUAN_URL = "http://niubizhuan.duoshoutuan.com/shell/android.php";
    private static final String KUAI_ZHUAN_FA_URL = "http://rwb.dearclick.com/Api/User/my?sid=";
    private static final String ZHAO_CAI_TU_TOKEN_URL = "http://zqw.2662126.com/App/Member/checkLoginToken";
    private static final String ZHAO_CAI_TU_INDEX_URL = "http://zqw.2662126.com/App/Index/index";
    private static final String KUAI_DE_BAO_URL = "http://www.snabq.cn/User/index/index";

    private static final String NIUBI_ZHUAN_HOST = "http://niubizhuan.duoshoutuan.com/";

    private static final String XIA_ZHUAN_WIDTHDRAW = "&act=url&network=WIFI&signature=82024da003020102020420cabb1d300d&version=2&";
    private static final String WUDI_ZHUAN_WIDTHDRAW = "&act=url&network=WIFI&signature=82024da003020102020411b11dba300d&version=1";
    private static final String NIUBI_ZHUAN_WIDTHDRAW = "&act=url&network=WIFI&signature=82024da00302010202046fc175f5300d&version=9&";

    // 初始化提现参数
    private static final boolean init = false;
    // 日收入限制
    private static final double xiaZhuanDayMoney = 1;
    private static final double wudiZhuanDayMoney = 1.08;
    private static final double niubiZhuanDayMoney = 1;
    private static final double zhaocaituDayMoney = 20;
    private static final double kuaiDeBaoTodayMoney = 10;

    @Override
    public List<Balance> findAll(Long userId, String platform, Integer type, Date date) {
        if (type == null) {
            type = 0;
        }
        if (StringUtils.isEmpty(platform)) {
            platform = "瞎转";
        }
        List<Balance> balances = balanceRepository.findAll(userId, platform, type, date);

        switch (platform) {
            case "瞎转": {
                getXiaZhuan(balances);
                break;
            }
            case "无敌赚": {
                getWuDiZhuan(balances);
                break;
            }
            case "牛逼赚": {
                getNiuBiZhuan(balances);
                break;
            }
            case "快转发": {
                getKuaiZhuanFa(balances);
                break;
            }
            case "招财兔": {
                getZhaoCaiTu(balances);
                break;
            }
            case "快得宝": {
                getKuaiDeBao(balances);
                break;
            }
        }

        if (!balances.isEmpty() && balances.size() > 1) {
            Collections.sort(balances, new Comparator<Balance>() {
                @Override
                public int compare(Balance o1, Balance o2) {
                    Double m1 = 0.0;
                    Double m2 = 0.0;
                    try {
                        m1 = Double.parseDouble(o1.getAmount());
                        m2 = Double.parseDouble(o2.getAmount());
                        return m2.compareTo(m1);
                    } catch (NumberFormatException e) {
                    }
                    return 0;
                }
            });
        }

        return balances;
    }

    private void getKuaiDeBao(List<Balance> balances) {
        for (Balance balance : balances) {
            String params = balance.getParams();
            try {
                String result = HttpUtils.getByCookie(KUAI_DE_BAO_URL, params);
                Document doc = Jsoup.parse(result);
                Elements els = doc.getElementsByClass("balance");
                Element e = els.get(0);
                String text = e.text();
                text = text.replace("账户余额（元） ", "");
                balance.setAmount(text);
                els = doc.getElementsByClass("juzhong");
                e = els.get(1);
                text = e.text();
                text = text.replace("元", "");
                balance.setToday(text);
                // 处理链接账号
                String articleActive = balance.getArticleActive();
                if (StringUtils.isNotEmpty(articleActive)) {
                    if (articleActive.contains("1")) {
                        balance.setArtcleStatus(1);
                    } else {
                        balance.setArtcleStatus(0);
                    }
                } else {
                    balance.setArtcleStatus(2);
                }
                    // 超过指定金额，自动停止链接
                try {
                    double day_jifenbao = Double.parseDouble(balance.getToday());
                    if (day_jifenbao >= kuaiDeBaoTodayMoney) {
                        articleRepository.autoStop(balance.getPlatform(), balance.getUsername(), balance.getType(), balance.getUserId());
                    }
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void getZhaoCaiTu(List<Balance> balances) {
        for (Balance balance : balances) {
            String params = balance.getParams();
            Map<String, String> map = HttpUtils.getParamMap(params);
            if (map.isEmpty()) {
                balance.setAmount("-1");
                continue;
            }
            try {
                String result = HttpUtils.postUserAgent(ZHAO_CAI_TU_TOKEN_URL, map);
                ZhaoCaiTuView view = JSON.parseObject(result, ZhaoCaiTuView.class);
                if (view.getCode() == 1 && "success".equals(view.getMessage())) {
                    result = HttpUtils.postUserAgent(ZHAO_CAI_TU_INDEX_URL, map);
                    Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
                    resultMap = (Map<String, Object>) resultMap.get("data");
                    JSONObject obj = (JSONObject) resultMap.get("member");
                    ZhaoCaiTuItemView item = JSON.parseObject(obj.toJSONString(), ZhaoCaiTuItemView.class);
                    balance.setAmount(item.getResidue_money() + "");
                    Map<String, Double> todayInfo = item.getToday_info();
                    Double today = todayInfo.get("today_income");
                    balance.setToday(today + "");
                    if (!(item.getState() == 1)) {
                        balance.setBlock("冻结");
                    }
                    // 处理链接账号
                    String articleActive = balance.getArticleActive();
                    if (StringUtils.isNotEmpty(articleActive)) {
                        if (articleActive.contains("1")) {
                            balance.setArtcleStatus(1);
                        } else {
                            balance.setArtcleStatus(0);
                        }
                    } else {
                        balance.setArtcleStatus(2);
                    }
                    try {
                        // 超过指定金额，自动停止链接
                        double day_jifenbao = Double.parseDouble(balance.getToday());
                        if (day_jifenbao >= zhaocaituDayMoney) {
                            articleRepository.autoStop(balance.getPlatform(), balance.getUsername(), balance.getType(), balance.getUserId());
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getXiaZhuan(List<Balance> balances) {
        for (Balance balance : balances) {
            String params = balance.getParams();
            Map<String, String> map = HttpUtils.getParamMap(params);
            if (map.isEmpty()) {
                balance.setAmount("-1");
                continue;
            }
            if (init) {
                String uid = map.get("uid");
                String qqKey = map.get("qqkey");
                String uname = map.get("username");
                String device = map.get("device");
                String widthdraw = "uid=" + uid + "&qqkey=" + qqKey + "&username=" + uname + "&device=" + device + XIA_ZHUAN_WIDTHDRAW;
                balanceRepository.updateWithdraw(balance.getId(), widthdraw);
            }
            try {
                String result = HttpUtils.post(XIA_ZHUAN_URL, map);
                XiaZhuanView view = JSON.parseObject(result, XiaZhuanView.class);
                if (StringUtils.isEmpty(view.getJifenbao())) {
                    balance.setAmount("-1");
                    continue;
                }
                balance.setAmount(view.getJifenbao());
                balance.setToday(view.getDay_jifenbao());
                if (!view.getIs_block().equals("0")) {
                    balance.setBlock(view.getBlock_remark());
                }
                // 处理链接账号
                String articleActive = balance.getArticleActive();
                if (StringUtils.isNotEmpty(articleActive)) {
                    if (articleActive.contains("1")) {
                        balance.setArtcleStatus(1);
                    } else {
                        balance.setArtcleStatus(0);
                    }
                } else {
                    balance.setArtcleStatus(2);
                }
                try {
                    // 超过指定金额，自动停止链接
                    double day_jifenbao = Double.parseDouble(balance.getToday());
                    if (day_jifenbao >= xiaZhuanDayMoney) {
                        articleRepository.autoStop(balance.getPlatform(), balance.getUsername(), balance.getType(), balance.getUserId());
                    }
                } catch (NumberFormatException e) {
                }
            } catch (IOException e) {
                balance.setAmount("-1");
                continue;
            }
        }
    }

    private void getWuDiZhuan(List<Balance> balances) {
        for (Balance balance : balances) {
            String params = balance.getParams();
            Map<String, String> map = HttpUtils.getParamMap(params);
            if (map.isEmpty()) {
                balance.setAmount("-1");
                continue;
            }
            if (init) {
                String uid = map.get("uid");
                String qqKey = map.get("qqkey");
                String uname = map.get("username");
                String device = map.get("device");
                String widthdraw = "uid=" + uid + "&qqkey=" + qqKey + "&username=" + uname + "&device=" + device + WUDI_ZHUAN_WIDTHDRAW;
                balanceRepository.updateWithdraw(balance.getId(), widthdraw);
            }
            try {
                String result = HttpUtils.post(WUDI_ZHUAN_URL, map);
                WuDiZhuanView view = JSON.parseObject(result, WuDiZhuanView.class);
                if (StringUtils.isEmpty(view.getJifenbao())) {
                    balance.setAmount("-1");
                    continue;
                }
                balance.setAmount(view.getJifenbao());
                balance.setToday(view.getDay_zhuanfa_jifenbao());
                if (!view.getIs_block().equals("0")) {
                    balance.setBlock(view.getBlock_remark());
                }
                // 处理链接账号
                String articleActive = balance.getArticleActive();
                if (StringUtils.isNotEmpty(articleActive)) {
                    if (articleActive.contains("1")) {
                        balance.setArtcleStatus(1);
                    } else {
                        balance.setArtcleStatus(0);
                    }
                } else {
                    balance.setArtcleStatus(2);
                }
                try {
                    // 超过指定金额，自动停止链接
                    double day_jifenbao = Double.parseDouble(balance.getToday());
                    if (day_jifenbao >= wudiZhuanDayMoney ) {
                        articleRepository.autoStop(balance.getPlatform(), balance.getUsername(), balance.getType(), balance.getUserId());
                    }
                } catch (NumberFormatException e) {
                }
            } catch (IOException e) {
                balance.setAmount("-1");
                continue;
            }
        }
    }

    private void getNiuBiZhuan(List<Balance> balances) {
        for (Balance balance : balances) {
            String params = balance.getParams();
            Map<String, String> map = HttpUtils.getParamMap(params);
            if (map.isEmpty()) {
                balance.setAmount("-1");
                continue;
            }
            if (init) {
                String uid = map.get("uid");
                String qqKey = map.get("qqkey");
                String uname = map.get("username");
                String device = map.get("device");
                String widthdraw = "uid=" + uid + "&qqkey=" + qqKey + "&username=" + uname + "&device=" + device + NIUBI_ZHUAN_WIDTHDRAW;
                balanceRepository.updateWithdraw(balance.getId(), widthdraw);
            }
            try {
                String result = HttpUtils.post(NIUBI_ZHUAN_URL, map);
                NiuBiZhuanView view = JSON.parseObject(result, NiuBiZhuanView.class);
                if (StringUtils.isEmpty(view.getJifenbao())) {
                    balance.setAmount("-1");
                    continue;
                }
                balance.setAmount(view.getJifenbao());
                balance.setToday(view.getDay_jifenbao());
                if (!view.getIs_block().equals("0")) {
                    balance.setBlock(view.getBlock_remark());
                }
                // 处理链接账号
                String articleActive = balance.getArticleActive();
                if (StringUtils.isNotEmpty(articleActive)) {
                    if (articleActive.contains("1")) {
                        balance.setArtcleStatus(1);
                    } else {
                        balance.setArtcleStatus(0);
                    }
                } else {
                    balance.setArtcleStatus(2);
                }
                try {
                    // 超过指定金额，自动停止链接
                    double day_jifenbao = Double.parseDouble(balance.getToday());
                    if (day_jifenbao >= niubiZhuanDayMoney) {
                        articleRepository.autoStop(balance.getPlatform(), balance.getUsername(), balance.getType(), balance.getUserId());
                    }
                } catch (NumberFormatException e) {
                }
            } catch (IOException e) {
                balance.setAmount("-1");
                continue;
            }
        }
    }

    private void getKuaiZhuanFa(List<Balance> balances) {
        for (Balance balance : balances) {
            String params = balance.getParams();
            if (StringUtils.isEmpty(params)) {
                balance.setAmount("-1");
                continue;
            }
            try {
                String result = HttpUtils.get(KUAI_ZHUAN_FA_URL, params);
                Map<String, Object> map = JSON.parseObject(result, Map.class);
                JSONObject obj = (JSONObject) map.get("message");
                KuaiZhuanFaView view = JSON.parseObject(obj.toJSONString(), KuaiZhuanFaView.class);
                if (null == view) {
                    balance.setAmount("-1");
                    continue;
                }
                balance.setAmount(view.getCashes());
                balance.setToday(view.getDay_cashes());
            } catch (IOException e) {
                balance.setAmount("-1");
                continue;
            }
        }
    }

    @Override
    public void remove(Long[] ids) {
        if (ids != null && ids.length > 0) {
            List<Balance> balances = balanceRepository.findByIds(ids);
            for (Balance pojo : balances) {
                articleRepository.delete(pojo.getPlatform(), pojo.getType(), pojo.getUserId(), pojo.getUsername());
            }
            balanceRepository.remove(ids);
        }
    }

    @Override
    public void updateDate(Long[] ids) {
        if (ids != null && ids.length > 0) {
            List<Balance> balances = balanceRepository.findByIds(ids);
            Date now = new Date();
            Date date = null;
            int hold = 0;
            for (Balance pojo : balances) {
                if (pojo.getDate() == null) {
                    date = now;
                    hold = 1;
                }
                balanceRepository.update(pojo.getId(), date);
                // 更新文章状态
                articleRepository.updateHold(pojo.getUserId(), pojo.getPlatform(), pojo.getType(), pojo.getUsername(), hold);
            }
        }
    }

    @Override
    public void saveBalance(Long userId, String platform, String username, Integer type, String params) {
        long count = balanceRepository.countOne(userId, platform, username, type);
        if (count == 0) {
            Balance pojo = new Balance();
            pojo.setUserId(userId);
            pojo.setPlatform(platform);
            pojo.setType(type);
            pojo.setParams(params);
            pojo.setUsername(username);
            // 获取提现参数
            if ("瞎转".equals(platform)) {
                Map<String, String> map = HttpUtils.getParamMap(params);
                String uid = map.get("uid");
                String qqKey = map.get("qqkey");
                String uname = map.get("username");
                String device = map.get("device");
                String widthdraw = "uid=" + uid + "&qqkey=" + qqKey + "&username=" + uname + "&device=" + device + XIA_ZHUAN_WIDTHDRAW;
                pojo.setWithdraw(widthdraw);
            } else if ("无敌赚".equals(platform)) {
                Map<String, String> map = HttpUtils.getParamMap(params);
                String uid = map.get("uid");
                String qqKey = map.get("qqkey");
                String uname = map.get("username");
                String device = map.get("device");
                String widthdraw = "uid=" + uid + "&qqkey=" + qqKey + "&username=" + uname + "&device=" + device + WUDI_ZHUAN_WIDTHDRAW;
                pojo.setWithdraw(widthdraw);
            } else if ("牛逼赚".equals(platform)) {
                Map<String, String> map = HttpUtils.getParamMap(params);
                String uid = map.get("uid");
                String qqKey = map.get("qqkey");
                String uname = map.get("username");
                String device = map.get("device");
                String widthdraw = "uid=" + uid + "&qqkey=" + qqKey + "&username=" + uname + "&device=" + device + NIUBI_ZHUAN_WIDTHDRAW;
                pojo.setWithdraw(widthdraw);
            }
            balanceRepository.save(pojo);
        }
    }

    @Override
    public String getWithdrawUrl(Long id) throws IOException {
        Balance pojo = balanceRepository.findOne(id);
        Map<String, String> map = HttpUtils.getParamMap(pojo.getWithdraw());
        if (pojo.getPlatform().equals("瞎转")) {
            return HttpUtils.postUserAgent(XIA_ZHUAN_URL, map);
        }
        if (pojo.getPlatform().equals("无敌赚")) {
            return HttpUtils.postUserAgent(WUDI_ZHUAN_URL, map);
        }
        if (pojo.getPlatform().equals("牛逼赚")) {
            return HttpUtils.postUserAgent(NIUBI_ZHUAN_URL, map);
        }
        return null;
    }

    @Override
    public List<WuDiZhuanRecordView> getRecord(Long id) {
        Balance pojo = balanceRepository.findOne(id);
        Map<String, String> map = HttpUtils.getParamMap(pojo.getParams());
        map.put("pagesize", "10");
        map.put("page", "1");
        map.put("op", "tixian");

        List<WuDiZhuanRecordView> records = Lists.newArrayList();

        try {
            String result = HttpUtils.postUserAgent(WUDI_ZHUAN_URL, map);
            records = JSON.parseArray(result, WuDiZhuanRecordView.class);
        } catch (IOException e) {
        }
        return records;
    }

    @Override
    public void updateArticle(Long id) {
        Balance pojo = balanceRepository.findOne(id);
        if (null != pojo) {
            articleRepository.activeArticle(pojo.getUserId(), pojo.getType(), pojo.getPlatform(), pojo.getUsername());
        }
    }

    @Override
    public void updateLink(Long[] ids) throws IOException {
        if (ids != null && ids.length > 0) {
            List<Balance> balances = balanceRepository.findByIds(ids);
            for (Balance pojo : balances) {
                String url = null;
                if ("瞎转".equals(pojo.getPlatform())) {
                    url = getXiaZhuanUrl(pojo.getParams());
                } else if ("无敌赚".equals(pojo.getPlatform())) {
                    url = getWudiZhuanUrl(pojo.getParams());
                } else if ("牛逼赚".equals(pojo.getPlatform())) {
                    url = getNiubiZhuanUrl(pojo.getParams());
                }
                Date date = new Date();
                // 更新文章状态
                long total = articleRepository.countOne(pojo.getUserId(), pojo.getPlatform(), pojo.getType(), pojo.getUsername());
                if (total > 0) {
                    articleRepository.updateLink(pojo.getUserId(), pojo.getPlatform(), pojo.getType(), pojo.getUsername(), url);
                } else {
                    Article artcile = new Article();
                    artcile.setType(pojo.getType());
                    artcile.setWechat(pojo.getUsername());
                    artcile.setUrl(url);
                    artcile.setPlatform(pojo.getPlatform());
                    artcile.setUserId(pojo.getUserId());
                    artcile.setCreateDate(date);
                    artcile.setTimes(20);
                    artcile.setActive(1);
                    artcile.setHold(0);
                    articleRepository.save(artcile);
                }
            }
        }
    }

    @Override
    public String importAccount(User user, Integer type, MultipartFile file) {
        String msg = "导入内容为空，请检查！";
        try {
            InputStream inputStream = file.getInputStream();
            List<List<String>> lists = ExcelUtils.readExcel(inputStream, 1);
            if (CollectionUtils.isEmpty(lists)) {
                return msg;
            }
            List<Integer> errors = Lists.newArrayList();
            for (int i = 0; i < lists.size(); i++) {
                List<String> list = lists.get(i);
                String username = list.get(0);
                String platform = list.get(1);
                String params = list.get(2);
                String times = list.get(3);
                String url = list.get(4);
                if (StringUtils.isEmpty(username) || StringUtils.isEmpty(platform) || StringUtils.isEmpty(params)) {
                    errors.add(i);
                    continue;
                }
                int time;
                try {
                    time = Integer.parseInt(times);
                } catch (NumberFormatException e) {
                    time = 20;
                }
                Article pojo = new Article();
                pojo.setPlatform(platform);
                pojo.setWechat(username);
                pojo.setParams(params);
                pojo.setTimes(time);
                pojo.setType(type);

                if (StringUtils.isEmpty(url)) {
                    if ("瞎转".equals(platform)) {
                        url = getXiaZhuanUrl(params);
                    } else if ("无敌赚".equals(platform)) {
                        url = getWudiZhuanUrl(params);
                    } else if ("牛逼赚".equals(platform)) {
                        url = getNiubiZhuanUrl(params);
                    }
                }
                pojo.setUrl(url);

                articleService.save(user, pojo);
            }
            msg = "导入成功！";
            if (!errors.isEmpty()) {
                msg += "第" + errors + " 行数据无效，请检查";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    // 获取瞎转分享链接
    public static String getXiaZhuanUrl(String params) throws IOException {
        Map<String, String> map = HttpUtils.getParamMap(params);
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("cate_id", "1");
        paramMap.put("act", "zhuanfa");
        paramMap.put("pagesize", "10");
        paramMap.put("page", "1");
        paramMap.put("uid", map.get("uid"));
        paramMap.put("qqkey", map.get("qqkey"));
        paramMap.put("username", map.get("username"));
        paramMap.put("device", map.get("device"));
        paramMap.put("network", map.get("network"));
        paramMap.put("signature", map.get("signature"));
        paramMap.put("version", map.get("version"));
        String result = HttpUtils.postUserAgent(XIA_ZHUAN_URL, paramMap);
        List<XiaZhuanUrl> urls = JSON.parseArray(result, XiaZhuanUrl.class);
        Random random = new Random();
        int index = random.nextInt(urls.size());
        String url = urls.get(index).getDetail_url();
        return getUrl(url);
    }

    // 获取无敌赚分享链接
    public static String getWudiZhuanUrl(String params) throws IOException {
        Map<String, String> map = HttpUtils.getParamMap(params);
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("cate_id", "105");
        paramMap.put("act", "zhuanfa");
        paramMap.put("pagesize", "10");
        paramMap.put("page", "1");
        paramMap.put("uid", map.get("uid"));
        paramMap.put("qqkey", map.get("qqkey"));
        paramMap.put("username", map.get("username"));
        paramMap.put("device", map.get("device"));
        paramMap.put("network", map.get("network"));
        paramMap.put("signature", map.get("signature"));
        paramMap.put("version", map.get("version"));
//        params = "uid=23907&pagesize=10&qqkey=owxDu0c6ut8_SokxesGPBEGyxbO0&username=xhdjdn&page=1&device=864502025537853&cate_id=105&act=zhuanfa&network=WIFI&signature=82024da003020102020411b11dba300d&version=1&";
        String result = HttpUtils.postUserAgent(WUDI_ZHUAN_URL, paramMap);
        List<XiaZhuanUrl> urls = JSON.parseArray(result, XiaZhuanUrl.class);
        Random random = new Random();
        int index = random.nextInt(urls.size());
        String url = urls.get(index).getDetail_url();
        return getUrl(url);
    }

    // 获取牛逼赚分享链接
    public static String getNiubiZhuanUrl(String params) throws IOException {
        Map<String, String> map = HttpUtils.getParamMap(params);
        String requestUrl = NIUBI_ZHUAN_HOST + "?m=zhuanfa&a=ajax&uid=" + map.get("uid") + "&op=all&cate=%E6%83%85%E6%84%9F";
        String html = HttpUtils.get(requestUrl, "");
        Document doc = Jsoup.parse(html);
        Elements es = doc.getElementsByClass("cursor");
        Random random = new Random();
        int index = random.nextInt(es.size());
        Element e = es.get(index);
        String onclick = e.attr("onclick");
        String url = onclick.substring(onclick.indexOf("?"), onclick.length() - 1);
        return getUrl(NIUBI_ZHUAN_HOST + url);
    }

    public static String getUrl(String requestUrl) throws IOException {
        String target = null;
        Document doc = Jsoup.connect(requestUrl).timeout(10000).get();
        Elements es = doc.getElementsByTag("script");
        for (Element e : es) {
            String[] data = e.data().toString().trim().split("var");
            for(String variable : data){
                variable = variable.trim();
                if(variable.startsWith("url=")){
                    target = variable.substring(6, variable.length() - 2);
                    break;
                }
            }
        }
        return target;
    }
}
