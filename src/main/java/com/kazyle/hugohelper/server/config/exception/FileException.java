package com.kazyle.hugohelper.server.config.exception;

/**
 * Created by Kazyle on 2016/8/26.
 */
public class FileException extends Exception {

    public FileException() {
        this("文件操作失败");
    }

    public FileException(String message) {
        super(message);
    }
}
