package com.kazyle.hugohelper.server.function.core.balance.service;

import com.kazyle.hugohelper.server.function.core.balance.entity.Balance;
import com.kazyle.hugohelper.server.function.core.balance.view.WuDiZhuanRecordView;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * <b>BalanceService</b> is
 * </p>
 *
 * @author Kazyle
 * @version 1.0.0
 * @since 2017/6/3
 */
public interface BalanceService {

    List<Balance> findAll(Long userId, String platform, Integer type, Date date);

    void remove(Long[] ids);

    void updateDate(Long[] ids);

    void saveBalance(Long userId, String platform, String username, Integer type, String params);

    String getWithdrawUrl(Long id) throws IOException;

    List<WuDiZhuanRecordView> getRecord(Long id);

    String importAccount(User user, Integer type, MultipartFile file);

    void updateArticle(Long id);

    void updateLink(Long[] ids) throws IOException;
}
