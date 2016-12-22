package com.kazyle.hugohelper.server.config.helper;

import com.kazyle.hugohelper.server.config.exception.FileException;
import com.kazyle.hugohelper.server.config.util.Md5Utils;
import com.kazyle.hugohelper.server.function.config.SysConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.*;

/**
 * Created by Kazyle on 2016/8/26.
 */
@Service
public class FileHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

    public static Map<String, Integer> EXTENSION = new HashMap<>();

    public FileHelper() {
        EXTENSION.put(".lua", 1);
        EXTENSION.put(".zip", 2);
        EXTENSION.put(".apk", 3);
    }

    @Resource
    private SysConfig sysConfig;

    public String upload(MultipartFile source, String targetPath) throws FileException {
        String filename = source.getOriginalFilename();
        String ext = getExtension(filename);
        if (!checkExtension(ext)) {
            throw new FileException("文件格式不允许上传");
        }
        filename = getFilename(ext);
        String path = targetPath + filename;
        File root = new File(sysConfig.getPath() + "/" + targetPath);
        LOGGER.debug("upload root------------->", root.getPath());
        if (!root.exists()) {
            root.mkdirs();
        }
        File target = new File(sysConfig.getPath() + "/" + path);
        LOGGER.debug("upload target--------------->{}", target.getPath());
        try {
            source.transferTo(target);
        } catch (IOException e) {
            throw new FileException();
        }
        return path;
    }

    public String getExtension(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return ".lua";
        }
        String ext = filename.substring(filename.lastIndexOf('.'));
        return ext;
    }

    public String getFilename(String suffix) {
        String key = System.currentTimeMillis() + RandomStringUtils.randomNumeric(6);
        key = Md5Utils.hash(key);
        key = key.substring(0, 20);
        if (StringUtils.isNotEmpty(suffix)) {
            key += suffix;
        }
        return key;
    }

    public boolean checkExtension(String ext) {
        if (EXTENSION.get(ext) == null) {
            return false;
        }
        return true;
    }

    /**
     * 压缩指定文件
     * @param files
     * @param dest
     * @param deleteSource
     * @return
     */
    public String zip(Set<File> files, String dest, boolean deleteSource) throws FileException {
        if (CollectionUtils.isEmpty(files)) {
            throw new FileException("要压缩的文件不存在");
        }

        FileOutputStream fos = null;
        CheckedOutputStream csum = null;
        ZipOutputStream zos = null;
        BufferedOutputStream out = null;

        String filename = getFilename(".zip");
        dest = dest + filename;
        String path = sysConfig.getPath() + "/" + dest;
        File destFile = new File(path);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        try {
            fos = new FileOutputStream(path);
            csum = new CheckedOutputStream(fos, new Adler32());
            zos = new ZipOutputStream(csum);
            out = new BufferedOutputStream(zos);
            for (File target : files) {
                if (!target.exists()) {
                    continue;
                }
                FileInputStream fin = new FileInputStream(target);
                zos.putNextEntry(new ZipEntry(target.getName()));

                int len;
                byte[] buf = new byte[1024];
                while ((len = fin.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
                fin.close();
                out.flush();
                if (deleteSource) {
                    target.delete();
                }
            }
            out.close();
        } catch (FileNotFoundException e) {
            LOGGER.error("压缩文件失败:", e);
        } catch (IOException e) {
            LOGGER.error("压缩文件失败:", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (zos != null) {
                    zos.close();
                }
                if (csum != null) {
                    csum.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                LOGGER.error("压缩文件关闭资源失败:", e);
            }
        }
        return sysConfig.getAccessUrl() + dest;
    }

    /**
     * 压缩指定文件
     * @param files
     * @param dest
     * @param deleteSource
     * @return
     */
    public String zip(Set<File> files, String dest, String filename, boolean deleteSource) throws FileException {
        if (CollectionUtils.isEmpty(files)) {
            throw new FileException("要压缩的文件不存在");
        }

        FileOutputStream fos = null;
        CheckedOutputStream csum = null;
        ZipOutputStream zos = null;
        BufferedOutputStream out = null;

        dest = dest + filename;
        String path = sysConfig.getPath() + "/" + dest;
        File destFile = new File(path);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        try {
            fos = new FileOutputStream(path);
            csum = new CheckedOutputStream(fos, new Adler32());
            zos = new ZipOutputStream(csum);
            out = new BufferedOutputStream(zos);
            zos.setComment("008手机数据");
            for (File target : files) {
                if (!target.exists()) {
                    continue;
                }
                FileInputStream fin = new FileInputStream(target);
                zos.putNextEntry(new ZipEntry(target.getName()));

                int len;
                byte[] buf = new byte[1024];
                while ((len = fin.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
                fin.close();
                out.flush();
                if (deleteSource) {
                    target.delete();
                }
            }
            out.close();
        } catch (FileNotFoundException e) {
            LOGGER.error("压缩文件失败:", e);
        } catch (IOException e) {
            LOGGER.error("压缩文件失败:", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (zos != null) {
                    zos.close();
                }
                if (csum != null) {
                    csum.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                LOGGER.error("压缩文件关闭资源失败:", e);
            }
        }
        return sysConfig.getAccessUrl() + dest;
    }

    /**
     * 解压压缩包
     * @param source
     * @param target
     * @return
     */
    public boolean unzip(String source, String target) {

        ZipFile zipFile = null;
        ZipInputStream zipIn = null;

        try {
            File file = new File(sysConfig.getPath() + "/" + source);
            File out = null;
            zipFile = new ZipFile(file);
            zipIn = new ZipInputStream(new FileInputStream(file));
            ZipEntry entry = null;
            InputStream input = null;
            OutputStream output = null;

            target = sysConfig.getPath() + "/" + target;
            LOGGER.debug("unzip---------------->{}", target);

            while ((entry = zipIn.getNextEntry()) != null) {

                String name = entry.getName();
                if (name == null || name.startsWith("__MACOSX")) {
                    continue;
                }
                name = name.replaceAll(".*/", "");
                int pos = name.lastIndexOf(".");
                if (pos == -1) {
                    continue;
                }
                out = new File(target + name);
                if (!out.getParentFile().exists()) {
                    out.getParentFile().mkdirs();
                }
                if (!out.exists()) {
                    out.createNewFile();
                }
                try {
                    input = zipFile.getInputStream(entry);
                    output = new FileOutputStream(out);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = input.read(buf)) != -1) {
                        output.write(buf, 0, len);
                    }
                    input.close();
                    output.close();
                } catch (Exception e) {
                    if (null != input) {
                        input.close();
                    }
                    if (null != output) {
                        output.close();
                    }
                }
            }
            zipIn.close();
            zipFile.close();
            return true;
        } catch (IOException e) {
            LOGGER.error("解压{}失败:", source, e);
        } finally {
            try {
                if (zipIn != null) {
                    zipIn.close();
                }
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (IOException e) {
            }
        }
        return false;
    }

    /**
     * 删除文件
     * @param filename
     */
    public void removeFile(String filename) {
        try {
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
