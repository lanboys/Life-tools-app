package com.bing.lan.comm.utils;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.bing.lan.comm.app.AppUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author 写文件的工具类
 */
public class FileUtil {

    protected static final LogUtil log = LogUtil.getLogUtil(FileUtil.class, LogUtil.LOG_VERBOSE);

    private static final String ROOT_DIR = "Android/data/" + AppUtil.getPackageName();
    private static final String DOWNLOAD_DIR = "downloadCache";
    private static final String CACHE_DIR = "cache";
    private static final String JSON_DIR = "jsonCache";
    private static final String ICON_DIR = "iconCache";
    private static final String HTTP_DIR = "httpCache";

    //注： 在Activity中有 getFileDir() 和 getCacheDir(); 方法可以获得当前的手机自带的存储空间中的当前包文件的路径
    //getFileDir() ----- /data/data/cn.xxx.xxx(当前包)/files
    //getCacheDir() ----- /data/data/cn.xxx.xxx（当前包）/cache

    // public static long getFileSize(File file) {
    //     long size = file.length() / 1024;
    //     log.d("getFileSize(): " + size + "KB");
    //     return size;
    // }

    /**
     * 根据 file 删除文件或者文件夹
     */
    public static boolean deleteFileOrFolder(File file) {
        if (file == null) {
            return false;
        }
        boolean success = false;
        if (file.isFile()) {
            success = file.delete();
            return success;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                success = file.delete();
                return success;
            }
            for (File childFile : childFiles) {
                deleteFileOrFolder(childFile);
            }
            success = file.delete();
        }

        return success;
    }

    ///**
    // * 根据 路径 后缀名 删除最近修改的文件
    // *
    // * @param path
    // * @param suffix
    // * @return
    // */
    //public static boolean deleteLastFileFromFolder(String path, @NonNull String suffix) {
    //    ArrayList<File> files = new ArrayList<>();
    //    FileUtil.listFilesBySuffix(path, suffix, files);
    //    return findLastFileFromList(files).delete();
    //}

    /**
     * 从集合中 找出最新修改的文件
     */
    public static File findLastFileFromList(@NonNull ArrayList<File> files) {
        File latestSavedFile = null;
        try {
            latestSavedFile = files.get(0);
            if (latestSavedFile.exists()) {
                for (int i = 1; i < files.size(); i++) {
                    File nextFile = files.get(i);
                    if (nextFile.lastModified() > latestSavedFile.lastModified()) {
                        latestSavedFile = nextFile;
                    }
                }
            }
        } catch (Exception e) {
            log.e("findLastFileFromList():  " + e.getLocalizedMessage());
        }
        log.i("findLastFileFromList(): 最新文件: " + latestSavedFile);
        return latestSavedFile;
    }

    /**
     * 根据后缀便利某路径下的所有文件
     *
     * @param path     路径
     * @param suffix   后缀名
     * @param fileList 存放文件的list
     */
    public static void listFilesBySuffix(@NonNull String path, @NonNull String suffix, ArrayList<File> fileList) {
        File[] allFiles = new File(path).listFiles();
        for (File file : allFiles) {
            String absolutePath = file.getAbsolutePath();
            if (file.isFile() && absolutePath.endsWith(suffix)) {
                fileList.add(file);
            } else if (file.isDirectory()) {
                listFilesBySuffix(absolutePath, suffix, fileList);
            }
        }
    }

    /**
     * 获取SD下的应用目录
     */
    public static String getExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        return sb.toString();
    }

    /**
     * 获取SD下的应用的cache目录 /mnt/sdcard/Android/data/packName......../cache/
     */
    public static String getExternalStorageCachePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getExternalStoragePath());
        sb.append(CACHE_DIR);
        sb.append(File.separator);
        return sb.toString();
    }

    /**
     * 获取应用的cache目录  /data/data/packName......../cache/
     */
    public static String getCachePath() {
        File f = AppUtil.getAppContext().getCacheDir();
        if (null == f) {
            return null;
        } else {
            return f.getAbsolutePath() + "/";
        }
    }

    /**
     * 获取指定文件大小
     */
    public static long getFileSize(String file) throws Exception {
        return getFileSize(new File(file));
    }

    /**
     * 获取指定文件大小
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            size = fis.available();
            IOUtils.close(fis);
        } else {
            file.createNewFile();
            log.e("getFileSize(): 文件不存在");
        }
        //log.i("getFileSize() 文件大小: " + formatFileSize(size));

        return size;
    }

    /**
     * 转换文件大小
     * String fileSize = Formatter.formatFileSize(AppUtil.getAppContext(), bitmapSize / 8);
     */
    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";//字节,bit 比特,位
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 将url转成文件名
     **/
    public static String url2FileName(String url) {

        int lastIndexOf = url.lastIndexOf(".");
        String substring = url.substring(0, lastIndexOf);
        String substring1 = url.substring(lastIndexOf);

        log.d("url2FileName():substring " + substring);
        log.d("url2FileName():substring1 " + substring1);
        log.d("url2FileName():url " + url);

        url = url.replace("?", "").replace(".", "").replace(":", "").replace("=", "");

        String[] strs = url.split("/");

        return strs[strs.length - 1];
    }

    /**
     * 判断SD卡是否挂载
     */
    public static boolean isSDCardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取下载目录
     * <p>
     * 当SD卡存在时 /mnt/sdcard/Android/data/packName......../cache/downloadCache/
     * 当SD卡不存在时 /data/data/packName......../cache/downloadCache/
     */
    public static String getDownloadDir() {
        return getDir(DOWNLOAD_DIR);
    }

    /**
     * 获取缓存目录
     * <p>
     * 当SD卡存在时 /mnt/sdcard/Android/data/packName......../cache/
     * 当SD卡不存在时 /data/data/packName......../cache/
     */
    public static String getCacheDir() {
        return getDir(null);
    }

    /**
     * 获取缓存目录
     * <p>
     * 当SD卡存在时 /mnt/sdcard/Android/data/packName......../cache/jsonCache/
     * 当SD卡不存在时 /data/data/packName......../cache/jsonCache/
     */
    public static String getJsonDir() {
        return getDir(JSON_DIR);
    }

    /**
     * 获取缓存目录
     * <p>
     * 当SD卡存在时 /mnt/sdcard/Android/data/packName......../cache/httpCache/
     * 当SD卡不存在时 /data/data/packName......../cache/httpCache/
     */
    public static String getHttpDir() {
        return getDir(HTTP_DIR);
    }

    /**
     * 获取icon目录
     * <p>
     * 当SD卡存在时 /mnt/sdcard/Android/data/packName......../cache/iconCache/
     * 当SD卡不存在时 /data/data/packName......../cache/iconCache/
     */
    public static String getIconDir() {
        return getDir(ICON_DIR);
    }

    /**
     * 获取应用目录，当SD卡存在时，获取SD卡上的目录，当SD卡不存在时，获取应用的cache目录
     */
    public static String getDir(String name) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            sb.append(getExternalStorageCachePath());
        } else {
            sb.append(getCachePath());
        }
        if (!TextUtils.isEmpty(name)) {
            sb.append(name);
            sb.append(File.separator);
        }
        String path = sb.toString();
        if (createDirs(path)) {
            return path;
        } else {
            // return path;
            log.e("getDir(): 可能没有添加读写存储卡权限");
            throw new RuntimeException("directory <" + path + "> can't be created");
        }
    }
    // <!-- 读写存储卡权限 -->
    // <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    // <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

    /**
     * 创建文件夹
     */
    public static boolean createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 复制文件，可以选择是否删除源文件
     */
    public static boolean copyFile(String srcPath, String destPath,
            boolean deleteSrc) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        return copyFile(srcFile, destFile, deleteSrc);
    }

    /**
     * 复制文件，可以选择是否删除源文件
     */
    public static boolean copyFile(File srcFile, File destFile,
            boolean deleteSrc) {
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = in.read(buffer)) > 0) {
                out.write(buffer, 0, i);
                out.flush();
            }
            if (deleteSrc) {
                srcFile.delete();
            }
        } catch (Exception e) {
            log.e("copyFile():  ", e);
            return false;
        } finally {
            IOUtils.close(out);
            IOUtils.close(in);
        }
        return true;
    }

    /**
     * 判断文件是否可写
     */
    public static boolean isWriteable(String path) {
        try {
            if (TextUtils.isEmpty(path)) {
                return false;
            }
            File f = new File(path);
            return f.exists() && f.canWrite();
        } catch (Exception e) {
            log.e("isWriteable():  ", e);
            return false;
        }
    }

    /**
     * 修改文件的权限,例如"777"等
     */
    public static void chmod(String path, String mode) {
        try {
            String command = "chmod " + mode + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (Exception e) {
            log.e("chmod():  ", e);
        }
    }

    /**
     * 把数据写入文件
     *
     * @param is       数据流
     * @param path     文件路径
     * @param recreate 如果文件存在，是否需要删除重建
     * @return 是否写入成功
     */
    public static boolean writeFile(InputStream is, String path,
            boolean recreate) {
        boolean res = false;
        File f = new File(path);
        FileOutputStream fos = null;
        try {
            if (recreate && f.exists()) {
                f.delete();
            }
            if (!f.exists() && null != is) {
                File parentFile = new File(f.getParent());
                parentFile.mkdirs();
                int count = -1;
                byte[] buffer = new byte[1024];
                fos = new FileOutputStream(f);
                while ((count = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, count);
                }
                res = true;
            }
        } catch (Exception e) {
            log.e("writeFile():  ", e);
        } finally {
            IOUtils.close(fos);
            IOUtils.close(is);
        }
        return res;
    }

    /**
     * 把字符串数据写入文件
     *
     * @param content 需要写入的字符串
     * @param path    文件路径名称
     * @param append  是否以添加的模式写入
     * @return 是否写入成功
     */
    public static boolean writeFile(byte[] content, String path, boolean append) {
        boolean res = false;
        File f = new File(path);
        RandomAccessFile raf = null;
        try {
            if (f.exists()) {
                if (!append) {
                    f.delete();
                    f.createNewFile();
                }
            } else {
                f.createNewFile();
            }
            if (f.canWrite()) {
                raf = new RandomAccessFile(f, "rw");
                raf.seek(raf.length());
                raf.write(content);
                res = true;
            }
        } catch (Exception e) {
            log.e("writeFile():  ", e);
        } finally {
            IOUtils.close(raf);
        }
        return res;
    }

    /**
     * 把字符串数据写入文件
     *
     * @param content 需要写入的字符串
     * @param path    文件路径名称
     * @param append  是否以添加的模式写入
     * @return 是否写入成功
     */
    public static boolean writeFile(String content, String path, boolean append) {
        return writeFile(content.getBytes(), path, append);
    }

    /**
     * 把键值对写入文件
     *
     * @param filePath 文件路径
     * @param key      键
     * @param value    值
     * @param comment  该键值对的注释
     */
    public static void writeProperties(String filePath, String key,
            String value, String comment) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(filePath)) {
            return;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File f = new File(filePath);
        try {
            if (!f.exists() || !f.isFile()) {
                f.createNewFile();
            }
            fis = new FileInputStream(f);
            Properties p = new Properties();
            p.load(fis);// 先读取文件，再把键值对追加到后面
            p.setProperty(key, value);
            fos = new FileOutputStream(f);
            p.store(fos, comment);
        } catch (Exception e) {
            LogUtil.getLogUtil(FileUtil.class).e("writeProperties: ", e);
        } finally {
            IOUtils.close(fis);
            IOUtils.close(fos);
        }
    }

    /**
     * 根据值读取
     */
    public static String readProperties(String filePath, String key,
            String defaultValue) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(filePath)) {
            return null;
        }
        String value = null;
        FileInputStream fis = null;
        File f = new File(filePath);
        try {
            if (!f.exists() || !f.isFile()) {
                f.createNewFile();
            }
            fis = new FileInputStream(f);
            Properties p = new Properties();
            p.load(fis);
            value = p.getProperty(key, defaultValue);
        } catch (IOException e) {
            LogUtil.getLogUtil(FileUtil.class).e("readProperties: ", e);
        } finally {
            IOUtils.close(fis);
        }
        return value;
    }

    /**
     * 把字符串键值对的map写入文件
     */
    public static void writeMap(String filePath, Map<String, String> map,
            boolean append, String comment) {
        if (map == null || map.size() == 0 || TextUtils.isEmpty(filePath)) {
            return;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File f = new File(filePath);
        try {
            if (!f.exists() || !f.isFile()) {
                f.createNewFile();
            }
            Properties p = new Properties();
            if (append) {
                fis = new FileInputStream(f);
                p.load(fis);// 先读取文件，再把键值对追加到后面
            }
            p.putAll(map);
            fos = new FileOutputStream(f);
            p.store(fos, comment);
        } catch (Exception e) {
            log.e("writeMap():  ", e);
        } finally {
            IOUtils.close(fis);
            IOUtils.close(fos);
        }
    }

    /**
     * 把字符串键值对的文件读入map
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, String> readMap(String filePath,
            String defaultValue) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        Map<String, String> map = null;
        FileInputStream fis = null;
        File f = new File(filePath);
        try {
            if (!f.exists() || !f.isFile()) {
                f.createNewFile();
            }
            fis = new FileInputStream(f);
            Properties p = new Properties();
            p.load(fis);
            map = new HashMap<String, String>((Map) p);// 因为properties继承了map，所以直接通过p来构造一个map
        } catch (Exception e) {
            log.e("readMap():  ", e);
        } finally {
            IOUtils.close(fis);
        }
        return map;
    }

    /**
     * 改名
     */
    public static boolean copy(String src, String des, boolean delete) {
        File srcFile = new File(src);
        if (!srcFile.exists()) {
            return false;
        }
        File desFile = new File(des);
        return copy(srcFile, desFile, delete);
    }

    public static boolean copy(File srcFile, File desFile, boolean delete) {

        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(desFile);
            byte[] buffer = new byte[1024];
            int count = -1;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
                out.flush();
            }
        } catch (Exception e) {
            log.e("copy():  ", e);
            return false;
        } finally {
            IOUtils.close(in);
            IOUtils.close(out);
        }
        if (delete) {
            srcFile.delete();
        }
        return true;
    }

    /**
     * 移动文件到指定的目录
     */
    public static boolean moveFile(File srcFile, File desFile, boolean delete) {

        try {

            String absolutePath = desFile.getAbsolutePath();
            String parent = absolutePath.substring(0, absolutePath.lastIndexOf("/"));

            File file = new File(parent);
            if (!file.exists()) { //没主动生成目录会报错,
                file.mkdirs();
            }

            if (srcFile.renameTo(desFile)) {
                log.i("File is moved successful!");
            } else {
                log.i("File is failed to move!");
            }
        } catch (Exception e) {
            log.e("moveFile():  ", e);
            return false;
        }

        if (delete) {
            srcFile.delete();
        }
        return true;
    }
}






