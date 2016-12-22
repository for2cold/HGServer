package com.kazyle.hugohelper.server.function.core.account.entity.enums;

/**
 * Created by Kazyle on 2016/8/23.
 */
public enum AccountStatus {

    IDLE, // 空闲中
    RUNNING, // 运行中
    WITHDRAW, // 待提现
    SIGNING, // 签到中
    AUDITING, // 审核中
    RECEIVED; // 已到账


    AccountStatus() {

    }
}
