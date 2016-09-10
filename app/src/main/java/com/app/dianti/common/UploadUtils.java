package com.app.dianti.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import android.util.Log;

/**
 * 上传附件工具类
 * @author SongFei
 * @date 2016-5-15
 */
public class UploadUtils {

//	public static boolean uploadFile(String action, String path) {
//		String end = "/r/n";
//		String Hyphens = "--";
//		String boundary = "*****";
//		HttpURLConnection con = null;
//		try {
//			URL url = new URL(action);
//			con = (HttpURLConnection) url.openConnection();
//			// Log.e("dianti", String.valueOf(con.getResponseCode()));
//			// 允许Input、Output，不使用Cache
//			con.setDoInput(true);
//			con.setDoOutput(true);
//			con.setUseCaches(false);
//			// 设定传送的method=POST
//			con.setRequestMethod("POST");
//			// setRequestProperty
//			con.setRequestProperty("Connection", "Keep-Alive");
//			con.setRequestProperty("Charset", "UTF-8");
//			con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//			// 发起请求
//			con.connect();
//			// 设定DataOutputStream
//			DataOutputStream dos = new DataOutputStream(con.getOutputStream());
//			dos.writeBytes(Hyphens + boundary + end);
//			dos.writeBytes("Content-Disposition: form-data;");
//			dos.writeBytes(end);
//			// 取得文件的FileInputStream
//			FileInputStream fis = new FileInputStream(getFilePath(path));
//			// 设定每次写入1024bytes
//			byte[] buffer = new byte[1024];
//			int length = -1;
//			// 从文件读取数据到缓冲区
//			while ((length = fis.read(buffer)) != -1) {
//				// 将数据写入DataOutputStream中
//				dos.write(buffer, 0, length);
//			}
//			dos.writeBytes(end);
//			dos.writeBytes(Hyphens + boundary + Hyphens + end);
//			fis.close();
//			dos.flush();
//			dos.close();
//			// 取得Response内容
//			InputStream is = con.getInputStream();
//			int ch;
//			StringBuffer b = new StringBuffer();
//			while ((ch = is.read()) != -1) {
//				b.append((char) ch);
//			}
//			System.out.println("上传成功");
//			Toast.makeText(MaintenanceFormActivity.this, "上传成功", Toast.LENGTH_LONG).show();
//			return true;
//		} catch (Exception e) {
//			Toast.makeText(MaintenanceFormActivity.this, "上传失败" + e.getMessage(), Toast.LENGTH_LONG).show();
//			System.out.println("上传失败" + e.getMessage());
//			e.printStackTrace();
//			con.disconnect();
//			return false;
//		} finally {
//			if (null != con) {
//				con.disconnect();
//			}
//		}
//	}
	
	public static boolean uploadFile(String url, String path) {
		PostMethod httpPost = HttpUtils.getHttpPost(url, false);
		httpPost.setRequestHeader("Content-Type", "multipart/form-data;boundary=*****");
		try {
			File file = getFilePath(path);
			Part[] parts = {new FilePart(file.getName(), file)};
			httpPost.setRequestEntity(new MultipartRequestEntity(parts, httpPost.getParams()));
			int status = HttpUtils.getHttpClient().executeMethod(httpPost);
			
			if (status == HttpStatus.SC_OK) {
				return true;
			} else {
				return false;
			}
		} catch (FileNotFoundException e) {
			System.out.println("出错：" + e.getMessage());
			Log.e("dianti", e.getMessage());
			return false;
		} catch (HttpException e) {
			System.out.println("出错：" + e.getMessage());
			Log.e("dianti", e.getMessage());
			return false;
		} catch (IOException e) {
			System.out.println("出错：" + e.getMessage());
			Log.e("dianti", e.getMessage());
			return false;
		}
	}
	
	public static File getFilePath(String filePath) {
		makeRootDirectory(filePath);
		File file = null;
		try {
			file = new File(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static void makeRootDirectory(String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
