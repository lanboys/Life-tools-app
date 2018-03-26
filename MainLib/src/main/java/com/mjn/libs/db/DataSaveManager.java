package com.mjn.libs.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

import com.mjn.libs.api.ResponseResult;
import com.mjn.libs.utils.Tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 数据存储类
 *
 */
public class DataSaveManager {

	/**
	 * 应用上下文实例
	 */
	private Context context= com.bing.lan.comm.app.AppUtil.getAppContext();
	
	/**
	 * sdcard存储目录名
	 */
	public static final String saveDataDir = "/mcyd/";
	public static final String sfkeyString = "key";
	/**
	 * 缓存默认值
	 */
	private static final int DEFAULT_DISK_USAGE_BYTES = 5 * 1024 * 1024;
	/**
	 * 最大缓存容量
	 */
	private final int mMaxCacheSizeInBytes = DEFAULT_DISK_USAGE_BYTES;
	/**
	 * 当前缓存容量
	 */
	private long mTotalSize = 0;

	private DataSaveManager() {

	}

	/**
	 * 获取数据存储管理单例
	 * @return
	 */
	public static DataSaveManager getInstance(){
		return DataSaveManagerLoader.INSTANCE;
	}

	private static class DataSaveManagerLoader {
		private static final DataSaveManager INSTANCE = new DataSaveManager();
	}


	/**
	 * 获取上下文
	 * @param context
	 */
	public void setContext(Context context){
		this.context = context;
		initDiskCurSize();
	}
	
	/**
	 * 初始化磁盘当前缓存容量
	 */
	private void initDiskCurSize() {
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return;
		}
		String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		if(!TextUtils.isEmpty(sdCardDir)) {
			File diskPath = new File(sdCardDir+saveDataDir);
			if(diskPath.exists()) {
				File[] files = diskPath.listFiles();
				if(files != null) {
					for(File file : files) {
						setmTotalSize(getmTotalSize() + file.length());
					}
				}
			}
		}
	}

	/**
	 * SharedPreference存储数据
	 * @param name 数据标识
	 * @param data 要存储的数据(复合数据,数据之间用","分割,也可以直接存储json字符串)
	 */
	public void save(String name, String data) {
    	SharedPreferences.Editor sharedData = this.context.getSharedPreferences(
    			name, Context.MODE_PRIVATE).edit();
    	sharedData.putString(sfkeyString, data);
    	sharedData.commit();
	}

	public void save(Context context,String name, String data) {
    	SharedPreferences.Editor sharedData = context.getSharedPreferences(
    			name, Context.MODE_PRIVATE).edit();
    	sharedData.putString(sfkeyString, data);
    	sharedData.commit();
	}

	/**
	 * SharedPreference读取数据
	 * @param name 数据标识
	 * @return 存储的数据(复合数据,数据之间用","分割,也可以直接存储json字符串)
	 */
	public String read(String name) {
		SharedPreferences sharedData = this.context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		return sharedData.getString(sfkeyString, null);
	}

	public String read(Context context,String name) {
		SharedPreferences sharedData = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		return sharedData.getString(sfkeyString, null);
	}

	/**
	 * 清除存储数据
	 * @param name
	 */
	public void clear(String name){
		SharedPreferences sharedData = this.context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		sharedData.edit().clear().commit();
	}

	/**
	 * 文件方式存储数据
	 * @param fileName 文件名
	 * @param data 存储的数据(复合数据,数据之间用","分割,可以存储多个值)
	 * @return
	 * @throws IOException
	 */
	public boolean saveFile(String fileName,String data) throws IOException{
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return false;
		}
		String sdCardDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		String path = sdCardDir + saveDataDir;
		if(mTotalSize >= mMaxCacheSizeInBytes) {
			deleteFile(path);
		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		File savefile = new File(file,fileName);
		Tools.Log(savefile.getAbsolutePath());
		FileOutputStream fout = new FileOutputStream(savefile);
		fout.write(data.getBytes());
		fout.flush();
		fout.close();
		mTotalSize += savefile.length();
		savefile = null;
		fout = null;
		file = null;
		return true;
	}

	/**
	 * 读取sd卡上的文件数据
	 * @param fileName 文件名
	 * @return
	 * @throws IOException
	 */
	public String getSDCardFile(String fileName) throws IOException {
		String sdCardDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		String path = sdCardDir + saveDataDir;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
			return null;
		}
		File readfile = new File(file,fileName);
		FileInputStream fin = new FileInputStream(readfile);
		byte buff[] = new byte[1024];
		ByteArrayOutputStream byOutt = new ByteArrayOutputStream();
		int len = 0;
		while((len = fin.read(buff)) > 0){
			byOutt.write(buff, 0, len);
		}
		byOutt.flush();
		byte[] data = byOutt.toByteArray();
		byOutt.close();
		fin.close();
		byOutt = null;
		fin = null;
		return new String(data,"UTF-8");
	}

	/**
	 * 存储图片文件到sd卡
	 * @param imageName 存储名
	 * @param bitmap 图片实例
	 * @return
	 * @throws IOException
	 */
	public boolean saveImage(String imageName,Bitmap bitmap) throws IOException{
		if(bitmap == null || !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}

		String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		String path = sdCardDir + saveDataDir;
		if(mTotalSize >= mMaxCacheSizeInBytes) {
			deleteFile(path);
		}
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}

		File image = new File(file,imageName);
		if(!image.exists()) {
			image.createNewFile();
		}
		FileOutputStream fout = new FileOutputStream(image);
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
		fout.flush();
		fout.close();
		fout = null;
		file = null;
		mTotalSize += image.length();
		image = null;
		return true;
	}
	
	/**
	 * 删除缓存文件
	 * @param path
	 */
	private void deleteFile(String path) {
		if(!TextUtils.isEmpty(path)) {
			File diskFile = new File(path);
			if(diskFile.exists()) {
				File[] files = diskFile.listFiles();
				if(files != null) {
                    Arrays.sort(files, new Comparator<File>() {
                        public int compare(File f1, File f2) {
                            return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
                        }
                    });
                    for(File file : files) {
                    	if(mTotalSize > (mMaxCacheSizeInBytes>>1)) {
	                    	if(file.exists()) {
	                    		mTotalSize -= file.length();
	                    		file.delete();
	                    	}
                    	} else {
                    		break;
                    	}
                    }
				}
			}
		}
	}

    
	/**
	 * 获取sd卡中的图片文件
	 * @param imageName 文件名
	 * @return
	 * @throws FileNotFoundException
	 */
	public Bitmap getImageFromSdcard(String imageName) throws Exception{
		String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		String path = sdCardDir + saveDataDir;
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
			return null;
		}
		File image = new File(file,imageName);
		if(!image.exists()) {
			return null;
		}
		FileInputStream fIn = new FileInputStream(image);
        BitmapFactory.Options opt = new BitmapFactory.Options();  
        opt.inPreferredConfig = Bitmap.Config.RGB_565;  
        opt.inPurgeable = true;  
        opt.inInputShareable = true;  
		Bitmap bitmap = BitmapFactory.decodeStream(fIn,null,opt);
		fIn.close();
		fIn = null;
		return bitmap;
	}
	
	/**
	 * 删除sdcard上的图片文件
	 * @param imageName
	 * @throws Exception
	 */
	public void deleteSaveImage(String imageName) throws Exception{
		String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		String path = sdCardDir + saveDataDir;
		File file = new File(path);
		if(!file.exists()){
			return;
		}
		File image = new File(file,imageName);
		if(image.exists()) {
			mTotalSize -= image.length();
			image.delete();
		}
	}
	
	/**
	 * 存储model对象
	 * @param name 名字
	 * @param result model
	 */
	public void saveModel(String name, ResponseResult<?> result) {
    	SharedPreferences.Editor sharedData = this.context.getSharedPreferences(
    			name, Context.MODE_PRIVATE).edit(); 
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(result);
			// 将字节流编码成base64的字符窜
//			String obj_Base64 = new String(Base64.encodeBytes(baos.toByteArray()));
			String obj_Base64 = new String(Base64.encode(baos.toByteArray(),Base64.DEFAULT));
			sharedData.putString(sfkeyString, obj_Base64);
			sharedData.commit();
		} catch (Exception e) {
			e.printStackTrace();
			Tools.Log("存储对象失败!"+e.toString());
		}
	}
	
	/**
	 * 获取model对象
	 * @param name 名字
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResponseResult<?> readModel(String name) {
		ResponseResult<?> obj = null;
		SharedPreferences sharedData = this.context.getSharedPreferences(name, Context.MODE_PRIVATE);
		String productBase64 = sharedData.getString(sfkeyString, "");
				
		//读取字节
		byte[] base64 = null;
		try {
//			base64 = Base64.decode(productBase64.getBytes());
			base64 = Base64.decode(productBase64.getBytes(),Base64.DEFAULT);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if(base64 == null || base64.length <= 0) {
			return obj;
		}
		//封装到字节流
		ByteArrayInputStream bais = new ByteArrayInputStream(base64);
		try {
			//再次封装
			ObjectInputStream bis = new ObjectInputStream(bais);
			//读取对象
			obj = (ResponseResult<?>) bis.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 存储对象
	 * @param name 名字
	 * @param obj 实现了Serializable接口的对象
	 */
	public void saveObject(String name, Object obj) {
    	SharedPreferences.Editor sharedData = this.context.getSharedPreferences(
    			name, Context.MODE_PRIVATE).edit(); 
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(obj);
			// 将字节流编码成base64的字符窜
			String obj_Base64 = new String(Base64.encode(baos.toByteArray(),Base64.DEFAULT));
			sharedData.putString(sfkeyString, obj_Base64);
			sharedData.commit();
		} catch (Exception e) {
			e.printStackTrace();
			Tools.Log("存储对象失败!" + e.toString());
		}
	}
	
	/**
	 * 获取model对象
	 * @param name 名字
	 * @return 序列化后的对象
	 */
	@SuppressWarnings("unchecked")
	public Object readObject(String name) {
		Object obj = null;
		SharedPreferences sharedData = this.context.getSharedPreferences(name, Context.MODE_PRIVATE);
		String productBase64 = sharedData.getString(sfkeyString, "");
				
		//读取字节
		byte[] base64 = null;
		try {
			base64 = Base64.decode(productBase64.getBytes(),Base64.DEFAULT);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if(base64 == null || base64.length <= 0) {
			return obj;
		}
		//封装到字节流
		ByteArrayInputStream bais = new ByteArrayInputStream(base64);
		try {
			//再次封装
			ObjectInputStream bis = new ObjectInputStream(bais);
			//读取对象
			obj = bis.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public int getmMaxCacheSizeInBytes() {
		return mMaxCacheSizeInBytes;
	}

	public long getmTotalSize() {
		return mTotalSize;
	}

	public void setmTotalSize(long mTotalSize) {
		this.mTotalSize = mTotalSize;
	}
}
