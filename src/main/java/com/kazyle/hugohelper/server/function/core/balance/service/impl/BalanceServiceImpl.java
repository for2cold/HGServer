package com.kazyle.hugohelper.server.function.core.balance.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kazyle.hugohelper.server.config.util.HttpUtils;
import com.kazyle.hugohelper.server.function.core.article.repository.ArticleRepository;
import com.kazyle.hugohelper.server.function.core.balance.entity.Balance;
import com.kazyle.hugohelper.server.function.core.balance.repository.BalanceRepository;
import com.kazyle.hugohelper.server.function.core.balance.service.BalanceService;
import com.kazyle.hugohelper.server.function.core.balance.view.*;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
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

    private static final String XIA_ZHUAN_URL = "http://xiazhuan.duoshoutuan.com/shell/android.php";
    private static final String WUDI_ZHUAN_URL = "http://wudizhuan.duoshoutuan.com/shell/android.php";
    private static final String NIUBI_ZHUAN_URL = "http://niubizhuan.duoshoutuan.com/shell/android.php";
    private static final String KUAI_ZHUAN_FA_URL = "http://rwb.dearclick.com/Api/User/my?sid=";

    private static final String XIA_ZHUAN_WIDTHDRAW = "&act=url&network=WIFI&signature=82024da003020102020420cabb1d300d&version=2&";
    private static final String WUDI_ZHUAN_WIDTHDRAW = "&act=url&network=WIFI&signature=82024da003020102020411b11dba300d&version=1";
    private static final String NIUBI_ZHUAN_WIDTHDRAW = "&act=url&network=WIFI&signature=82024da00302010202046fc175f5300d&version=9&";

    // 初始化提现参数
    private static final boolean init = false;
    // 日收入限制
    private static final double xiaZhuanDayMoney = 1.04;
    private static final double wudiZhuanDayMoney = 1.08;
    private static final double niubiZhuanDayMoney = 1;

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
        }

        if (!balances.isEmpty() && balances.size() > 1) {
            Collections.sort(balances, new Comparator<Balance>() {
                @Override
                public int compare(Balance o1, Balance o2) {
                    double m1 = 0.0;
                    double m2 = 0.0;
                    try {
                        m1 = Double.parseDouble(o1.getAmount());
                        m2 = Double.parseDouble(o2.getAmount());
                    } catch (NumberFormatException e) {
                    }
                    if (m1 > m2) {
                        return -1;
                    }
                    return m1 < m2 ? 1 : 0;
                }
            });
        }

        return balances;
    }

    private void getXiaZhuan(List<Balance> balances) {
        for (Balance balance : balances) {
            String params = balance.getParams();
            Map<String, String> map = getParamMap(params);
            if (map.isEmpty()) {
                balance.setAmount("<span class=\"text-danger\">查询失败</span>");
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
                    balance.setAmount("<span class=\"text-danger\">查询失败</span>");
                    continue;
                }
                balance.setAmount(view.getJifenbao());
                balance.setToday(view.getDay_jifenbao());
                if (!view.getIs_block().equals("0")) {
                    balance.setBlock(view.getBlock_remark());
                }
                if (balance.getType() == 1) {
                    try {
                        // 超过指定金额，自动停止链接
                        double day_jifenbao = Double.parseDouble(balance.getToday());
                        if (day_jifenbao >= xiaZhuanDayMoney) {
                            articleRepository.autoStop(balance.getPlatform(), balance.getUsername(), balance.getType(), balance.getUserId());
                        }
                    } catch (NumberFormatException e) {
                    }
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
            } catch (IOException e) {
                balance.setAmount("<span class=\"text-danger\">查询失败</span>");
                continue;
            }
        }
    }

    private void getWuDiZhuan(List<Balance> balances) {
        for (Balance balance : balances) {
            String params = balance.getParams();
            Map<String, String> map = getParamMap(params);
            if (map.isEmpty()) {
                balance.setAmount("<span class=\"text-danger\">查询失败</span>");
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
                    balance.setAmount("<span class=\"text-danger\">查询失败</span>");
                    continue;
                }
                balance.setAmount(view.getJifenbao());
                balance.setToday(view.getDay_zhuanfa_jifenbao());
                if (!view.getIs_block().equals("0")) {
                    balance.setBlock(view.getBlock_remark());
                }
                if (balance.getType() == 1) {
                    try {
                        // 超过指定金额，自动停止链接
                        double day_jifenbao = Double.parseDouble(balance.getToday());
                        if (day_jifenbao >= wudiZhuanDayMoney) {
                            articleRepository.autoStop(balance.getPlatform(), balance.getUsername(), balance.getType(), balance.getUserId());
                        }
                    } catch (NumberFormatException e) {
                    }
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
            } catch (IOException e) {
                balance.setAmount("<span class=\"text-danger\">查询失败</span>");
                continue;
            }
        }
    }

    private void getNiuBiZhuan(List<Balance> balances) {
        for (Balance balance : balances) {
            String params = balance.getParams();
            Map<String, String> map = getParamMap(params);
            if (map.isEmpty()) {
                balance.setAmount("<span class=\"text-danger\">查询失败</span>");
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
                    balance.setAmount("<span class=\"text-danger\">查询失败</span>");
                    continue;
                }
                balance.setAmount(view.getJifenbao());
                balance.setToday(view.getDay_jifenbao());
                if (!view.getIs_block().equals("0")) {
                    balance.setBlock(view.getBlock_remark());
                }
                try {
                    // 超过指定金额，自动停止链接
                    double day_jifenbao = Double.parseDouble(balance.getToday());
                    if (day_jifenbao >= niubiZhuanDayMoney) {
                        articleRepository.autoStop(balance.getPlatform(), balance.getUsername(), balance.getType(), balance.getUserId());
                    }
                } catch (NumberFormatException e) {
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
            } catch (IOException e) {
                balance.setAmount("<span class=\"text-danger\">查询失败</span>");
                continue;
            }
        }
    }

    private void getKuaiZhuanFa(List<Balance> balances) {
        for (Balance balance : balances) {
            String params = balance.getParams();
            if (StringUtils.isEmpty(params)) {
                balance.setAmount("<span class=\"text-danger\">查询失败</span>");
                continue;
            }
            try {
                String result = HttpUtils.get(KUAI_ZHUAN_FA_URL, params);
                Map<String, Object> map = JSON.parseObject(result, Map.class);
                JSONObject obj = (JSONObject) map.get("message");
                KuaiZhuanFaView view = JSON.parseObject(obj.toJSONString(), KuaiZhuanFaView.class);
                if (null == view) {
                    balance.setAmount("<span class=\"text-danger\">查询失败</span>");
                    continue;
                }
                balance.setAmount(view.getCashes());
                balance.setToday(view.getDay_cashes());
            } catch (IOException e) {
                balance.setAmount("<span class=\"text-danger\">查询失败</span>");
                continue;
            }
        }
    }

    public static Map<String, String> getParamMap(String param) {
        Map<String, String> map = new HashMap<>();
        if (org.apache.commons.lang3.StringUtils.isBlank(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    @Override
    public void remove(Long[] ids) {
        if (ids != null && ids.length > 0) {
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
                Map<String, String> map = getParamMap(params);
                String uid = map.get("uid");
                String qqKey = map.get("qqkey");
                String uname = map.get("username");
                String device = map.get("device");
                String widthdraw = "uid=" + uid + "&qqkey=" + qqKey + "&username=" + uname + "&device=" + device + XIA_ZHUAN_WIDTHDRAW;
                pojo.setWithdraw(widthdraw);
            } else if ("无敌赚".equals(platform)) {
                Map<String, String> map = getParamMap(params);
                String uid = map.get("uid");
                String qqKey = map.get("qqkey");
                String uname = map.get("username");
                String device = map.get("device");
                String widthdraw = "uid=" + uid + "&qqkey=" + qqKey + "&username=" + uname + "&device=" + device + WUDI_ZHUAN_WIDTHDRAW;
                pojo.setWithdraw(widthdraw);
            } else if ("牛逼赚".equals(platform)) {
                Map<String, String> map = getParamMap(params);
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
        Map<String, String> map = getParamMap(pojo.getWithdraw());
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
        Map<String, String> map = getParamMap(pojo.getParams());
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
}
