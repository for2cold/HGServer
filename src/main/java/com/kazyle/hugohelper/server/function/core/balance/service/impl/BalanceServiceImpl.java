package com.kazyle.hugohelper.server.function.core.balance.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kazyle.hugohelper.server.config.util.HttpUtils;
import com.kazyle.hugohelper.server.function.core.balance.entity.Balance;
import com.kazyle.hugohelper.server.function.core.balance.repository.BalanceRepository;
import com.kazyle.hugohelper.server.function.core.balance.service.BalanceService;
import com.kazyle.hugohelper.server.function.core.balance.view.KuaiZhuanFaView;
import com.kazyle.hugohelper.server.function.core.balance.view.WuDiZhuanView;
import com.kazyle.hugohelper.server.function.core.balance.view.XiaZhuanView;
import org.apache.commons.lang.StringUtils;
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

    private static final String XIA_ZHUAN_URL = "http://xiazhuan.duoshoutuan.com/shell/android.php";
    private static final String WUDI_ZHUAN_URL = "http://wudizhuan.duoshoutuan.com/shell/android.php";
    private static final String KUAI_ZHUAN_FA_URL = "http://rwb.dearclick.com/Api/User/my?sid=";

    @Override
    public List<Balance> findAll(Long userId, String platform, Integer type) {
        if (type == null) {
            type = 0;
        }
        if (StringUtils.isEmpty(platform)) {
            platform = "瞎转";
        }
        List<Balance> balances = balanceRepository.findAll(userId, platform, type);

        switch (platform) {
            case "瞎转": {
                getXiaZhuan(balances);
                break;
            }
            case "无敌赚": {
                getWuDiZhuan(balances);
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

    private void getWuDiZhuan(List<Balance> balances) {
        for (Balance balance : balances) {
            String params = balance.getParams();
            Map<String, String> map = getParamMap(params);
            if (map.isEmpty()) {
                balance.setAmount("<span class=\"text-danger\">查询失败</span>");
                continue;
            }
            try {
                String result = HttpUtils.post(WUDI_ZHUAN_URL, map);
                WuDiZhuanView view = JSON.parseObject(result, WuDiZhuanView.class);
                if (StringUtils.isEmpty(view.getJifenbao())) {
                    balance.setAmount("<span class=\"text-danger\">查询失败</span>");
                    continue;
                }
                balance.setAmount(view.getJifenbao());
                balance.setToday(view.getDay_fc_jifenbao());
                if (!view.getIs_block().equals("0")) {
                    balance.setBlock(view.getBlock_remark());
                }
            } catch (IOException e) {
                balance.setAmount("<span class=\"text-danger\">查询失败</span>");
                continue;
            }
        }
    }

    private void getXiaZhuan(List<Balance> balances) {
        for (Balance balance : balances) {
            String params = balance.getParams();
            Map<String, String> map = getParamMap(params);
            if (map.isEmpty()) {
                balance.setAmount("<span class=\"text-danger\">查询失败</span>");
                continue;
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
            for (Balance pojo : balances) {
                if (pojo.getDate() == null) {
                    date = now;
                }
                balanceRepository.update(pojo.getId(), date);
            }
        }
    }
}
