package com.kazyle.hugohelper.server.config.util;

import com.mysql.jdbc.log.LogUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Kazyle on 2016/8/23.
 */
public class ReflectUtils {

    /**
     * 将map数据封装到对应的实体类
     *
     * @param param
     * @param clazz
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T getBean(Map<String, Object> param, Class clazz) {

        Object value = null;

        Class[] paramTypes = new Class[1];

        Object obj = null;

        try {
            // 创建实例
            obj = clazz.newInstance();
            Field[] f = clazz.getDeclaredFields();
            List<Field[]> flist = new ArrayList<Field[]>();
            flist.add(f);

            Class superClazz = clazz.getSuperclass();
            while (superClazz != null) {
                f = superClazz.getFields();
                flist.add(f);
                superClazz = superClazz.getSuperclass();
            }

            for (Field[] fields : flist) {
                for (Field field : fields) {
                    String fieldName = field.getName();
                    value = param.get(fieldName);
                    if (value != null) {
                        paramTypes[0] = field.getType();
                        Method method = null;
                        // 调用相应对象的set方法
                        StringBuffer methodName = new StringBuffer("set");
                        methodName.append(fieldName.substring(0, 1)
                                .toUpperCase());
                        methodName.append(fieldName.substring(1,
                                fieldName.length()));

                        method = clazz.getMethod(methodName.toString(),
                                paramTypes);
                        method.invoke(obj, ConvertUtil.getValue(
                                value.toString(), fieldName, paramTypes[0]));
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    /**
     * 得到指定类型的指定位置的泛型实参
     *
     * @param clazz
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> findParameterizedType(Class<?> clazz, int index) {
        Type parameterizedType = clazz.getGenericSuperclass();
        if (!(parameterizedType instanceof ParameterizedType)) {
            parameterizedType = clazz.getSuperclass().getGenericSuperclass();
        }
        if (!(parameterizedType instanceof  ParameterizedType)) {
            return null;
        }
        Type[] actualTypeArguments = ((ParameterizedType) parameterizedType).getActualTypeArguments();
        if (actualTypeArguments == null || actualTypeArguments.length == 0) {
            return null;
        }
        return (Class<T>) actualTypeArguments[0];
    }

    /**
     * 包扫描
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> scan(String packageName) {
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        boolean recursive = true;
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
//			dirs = ReflectUtils.class.getClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                //LogUtils.ACCESS_LOG.info("protocol:{}", protocol);
                if ("file".equals(protocol)) {
                    String packagePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    scan(packageName, packagePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    JarFile jar;
                    try {
                        jar = ((JarURLConnection)url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            // 添加到classes
                                            classes.add(Class.forName(packageName + '.'+ className));
                                        } catch (ClassNotFoundException e) {
                                            //LogUtils.logError("初始化：包扫描失败，系统不能正常运行", e);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        //LogUtils.logError("初始化：包扫描失败，系统不能正常运行", e);
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //LogUtils.logError("初始化：包扫描失败，系统不能正常运行", e);
            throw new Error("初始化：包扫描失败，系统不能正常运行");
        }
        return classes;
    }

    public static void scan(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) {

        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {

            return ;
        }

        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                scan(packageName + "."  + file.getName(), file.getAbsolutePath(), recursive,
                        classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    //classes.add(Class.forName(packageName + '.' + className));
                    //经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }
}
