package com.app.dianti.common;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpUtils {
	
	public static final String UTF_8 = "UTF-8"; //UTF-8编码

	private final static int TIMEOUT_CONNECTION = 10000; // 连接超时
	
	private final static int TIMEOUT_SOCKET = 30000; // 读取超时
	
	private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36";
	
	/**
	 * 获取HttpClient对象
	 * @return
	 */
	public static HttpClient getHttpClient() {
		HttpClient httpClient = new HttpClient();
		// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		// 设置 默认的超时重试处理策略
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// 设置 连接超时时间
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT_CONNECTION);
		// 设置 读数据超时时间
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT_SOCKET);
		// 设置 字符集
		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}
	
	/**
	 * 获取Get请求方式的Method对象
	 * @param url 请求的url
	 * @param cookie 缓存信息
	 * @param userAgent 手机和应用信息
	 * @return
	 */
	public static GetMethod getHttpGet(String url, boolean isEncoding) {
		GetMethod httpGet = new GetMethod(url);
		// 设置 请求超时时间
		httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
		// 域名，请求的服务器网址
		// httpGet.setRequestHeader("Host", HOST);
		// 是否需要持久连接
		httpGet.setRequestHeader("Connection", "Keep-Alive");
		// 浏览器类型
		httpGet.setRequestHeader("User-Agent", USER_AGENT);
		if(isEncoding){
			httpGet.setRequestHeader("Content-Encoding", "gzip");
		}
		// 客户端IP，可以伪造哟
		// httpGet.setRequestHeader("Proxy-Client-IP", PROXY_CLIENT_IP);
		return httpGet;
	}

	/**
	 * 获取Post请求方式的Method对象
	 * @param url 请求的url
	 * @param cookie 缓存信息
	 * @param userAgent 手机和应用信息
	 * @return
	 */
	public static PostMethod getHttpPost(String url, boolean isEncoding) {
		PostMethod httpPost = new PostMethod(url);
		// 设置 请求超时时间
		httpPost.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpPost.setRequestHeader("Host", "192.168.1.101");
		httpPost.setRequestHeader("Connection", "Keep-Alive");
		httpPost.setRequestHeader("User-Agent", USER_AGENT);
		if(isEncoding){
			httpPost.setRequestHeader("Content-Encoding", "gzip");
		}
		httpPost.setRequestHeader("Proxy-Client-IP", "192.168.1.101");
		return httpPost;
	}
	
}
