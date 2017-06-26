package com.example.zhou.RxJava1;

import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsTools {
	private static final String TAG = "HttpsTools";
	public static int TimeOut  = 10000;
	String m_cookie;					///< 当前的cookie
	Context m_app;						///< app
	static boolean m_inited = false;
	
	public HttpsTools(Context app) {
		m_app = app;
	}
	
	public void setCookie (String c) {
		m_cookie = c;
	}
	
	/** 底层使用https，如果添加openssl，可能使程序太大，使用系统默认的吧
	 * @param oper			"GET" "POST"
	 * @param url			url
	 * @param headers		额外的http头
	 * @param body			消息体
	 * @return				返回的内容，“code\n内容”
	 */
	public synchronized String HttpsOper (String oper, String url, String[] headers, String body) {
		HttpsURLConnection https_conn = null;
		try {
			javax.net.ssl.TrustManager[] tm = {new javax.net.ssl.X509TrustManager () {
				  public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, java.lang.String arg1) throws java.security.cert.CertificateException {
				  }
				  public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, java.lang.String arg1) throws java.security.cert.CertificateException {
				  }
				  public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					  return new java.security.cert.X509Certificate[] {};
				  }
			}};

			javax.net.ssl.SSLContext sslContext = javax.net.ssl.SSLContext.getInstance("TLS");
			sslContext.init(null, tm, new java.security.SecureRandom());
			javax.net.ssl.SSLSocketFactory ssf = sslContext.getSocketFactory();

//			Tools.LogCatDebug("HttpsTools","url="+url);
			URL url_https = new URL(url);
			https_conn = (HttpsURLConnection) url_https.openConnection();
			https_conn.setSSLSocketFactory(ssf);

			https_conn.setDoOutput(true);
			https_conn.setDoInput(true);
			https_conn.setUseCaches(false);
			https_conn.setAllowUserInteraction(true);
			https_conn.setHostnameVerifier(new org.apache.http.conn.ssl.AllowAllHostnameVerifier());
			https_conn.setConnectTimeout(10000);//建立连接超时时间10s
			https_conn.setReadTimeout(10000);//读取数据超时时间10s

			for (int i=0; i<headers.length; i++) {
				String[] items = headers[i].split(":[ ]");
				if (items.length < 2) {
					continue;
				}
				https_conn.addRequestProperty(items[0], items[1]);
			}

			https_conn.setRequestMethod(oper);

			if (body != null && body.length() != 0) {
				android.util.Log.d("htts", "开始连接");
				OutputStream out = https_conn.getOutputStream();
				android.util.Log.d("htts", "开始写入");
				out.write(body.getBytes());
				out.close();
				android.util.Log.d("htts", "写入完成");
			}

			String strResp = new String();
			android.util.Log.d("htts", "开始读取响应");
			int nCode = https_conn.getResponseCode();
			android.util.Log.d("htts", "响应 " + Integer.toString(nCode));

			if (nCode >= 200 && nCode < 300) {
				android.util.Log.d("htts", "开始读取消息体");
				InputStream in = https_conn.getInputStream();
				int size = https_conn.getContentLength();
				strResp = SafeReadStream (in, size);
				android.util.Log.d("htts", "读取消息体完成");
			}

			///< 组合成返回字符串
			String strResult;
			if (strResp.length() == 0) {
				strResult = Integer.toString(nCode);
			}
			else {
				strResult = Integer.toString(nCode) + "\n" + strResp;
			}

			try { https_conn.disconnect();
			} catch (Exception e) {}
			https_conn = null;
			Log.d(TAG,"HttpsOper response="+strResult);
			return strResult;
		}
		catch (Exception e) {
			android.util.Log.d("https", e.getMessage());
			if (https_conn != null) {
				https_conn.disconnect();
			}
			return "";
		}
	}

	/**
	 * 如果返回中包含 Location: 字段，则取该Location字段
	 * @param connect
	 * @return Location 字段的值，如果没有，返回nul
	 */
	String get_location_header (HttpURLConnection connect) {
		String location = connect.getHeaderField("Location");
		if (location == null || location.length() <= 0) {
			return null;
		}
		else {
			return "Location: " + location;
		}
	}

	int after_token (String s, String token, int pos) {
		int ret = s.indexOf(token, pos);
		if (ret < 0) {
			return ret;
		}
		return ret + token.length();
	}

	/*
	 * cmwap版本的http请求
	 */
	public String HttpOperCMWAP (String oper, String url, String[] headers, String body) {
		int iHostBegin = after_token(url, "http://", 0);
		int iHostEnd = url.indexOf('/', iHostBegin);
		String host = url.substring(iHostBegin, iHostEnd);
		String proxy_url = "http://10.0.0.172";
		if (iHostEnd > 0) {
			proxy_url = proxy_url + url.substring(iHostEnd);
		}

		HttpURLConnection http_conn = null;
		try {
			URL url_http = new URL(proxy_url);
			http_conn = (HttpURLConnection) url_http.openConnection();

			http_conn.setDoOutput(true);
			http_conn.setDoInput(true);
			http_conn.setUseCaches(false);
			http_conn.setAllowUserInteraction(true);
			http_conn.setConnectTimeout(10000);//建立连接超时时间10s
			http_conn.setReadTimeout(10000);//读取数据超时时间10s

			///< 设置 cookie
			if (m_cookie != null && !(m_cookie.length() <= 0) && need_cookie (url)) {
				CookieManager cookie = CookieManager.getInstance();
				String[] items = m_cookie.split("=");
				cookie.setCookie(items[0], items[1]);
				http_conn.setRequestProperty("cookie", m_cookie);
			}

			if (headers!=null)	{
				for (int i=0; i<headers.length; i++) {
					String[] items = headers[i].split(":[ ]");
					if (items.length < 2) {
						continue;
					}
					http_conn.addRequestProperty(items[0], items[1]);
				}
			}

			http_conn.setRequestMethod(oper);

			if (body != null && body.length() != 0) {
				OutputStream out = http_conn.getOutputStream();
				out.write(body.getBytes());
				out.close();
			}

			String strResp = new String();
			int nCode = http_conn.getResponseCode();

			if (nCode == 200 || nCode == 202) {
				///< 是否是重定向
				String location = get_location_header (http_conn);
				if (location != null) {
					strResp = location;
				}
				///< 读取body体
				else {
					InputStream in = http_conn.getInputStream();
					int size = http_conn.getContentLength();
					Log.i("HttpTools","as server return http header context_length:"+size);
					strResp = SafeReadStream (in, size);
				}
			}
			else {
				InputStream in = http_conn.getInputStream();
				int size = http_conn.getContentLength();
				Log.i("HttpTools","as server return http header context_length:"+size);
				strResp = SafeReadStream (in, size);
			}

			///< 组合成返回字符串
			String strResult;
			if (strResp.length() == 0) {
				strResult = Integer.toString(nCode);
			}
			else {
				strResult = Integer.toString(nCode) + "\n" + strResp;
			}

			try { http_conn.disconnect();
			} catch (Exception e) {}
			http_conn = null;

			return strResult;
		}
		catch (Exception e) {
			String s = e.getMessage();
			if (s == null) {
				e.printStackTrace();
			}
			else {
				android.util.Log.d("https", s);
			}

			if (http_conn != null) {
				http_conn.disconnect();
			}

			return "";
		}
	}
	int exceptionCount;
	/** 底层使用https，如果添加openssl，可能使程序太大，使用系统默认的吧
	 * @param oper			"GET" "POST"
	 * @param url			url
	 * @param headers		额外的http头
	 * @param body			消息体
	 * @return				返回的内容，“code\n内容”
	 *
	 * 登录时，不使用此方法
	 */
	public synchronized String HttpOper (String oper, String url, String[] headers, String body) {
		exceptionCount = 0;
		return HttpOper1(oper, url, headers, body);
	}


	public synchronized String HttpOper1 (String oper, String url, String[] headers, String body) {
		if(oper==null)
        oper="POST";
		HttpURLConnection http_conn = null;
		try {
			Log.i(TAG, "httpoper url:"+url);
			URL url_http = new URL(url);
			http_conn = (HttpURLConnection) url_http.openConnection();

			http_conn.setDoOutput(true);
			http_conn.setDoInput(true);
			http_conn.setUseCaches(false);
			http_conn.setAllowUserInteraction(true);
			http_conn.setRequestProperty("connection","close");
			http_conn.setRequestProperty("Content-Type","text/xml;charset=UTF-8");
			http_conn.setRequestProperty("User-Agent","cmcc v21");
			http_conn.setConnectTimeout(TimeOut);//建立连接超时时间10s
			http_conn.setReadTimeout(10000);//读取数据超时时间10s
			http_conn.setFixedLengthStreamingMode(body.getBytes().length);
			///< 设置 cookie
//			if (m_cookie != null && !(m_cookie.length() <= 0) && need_cookie (url)) {
//				//CookieManager cookie = CookieManager.getInstance();
//				//String[] items = m_cookie.split("=");
//				//cookie.setCookie(items[0], items[1]);
//				//Log.i(TAG, "httpoper m_cookie:"+m_cookie);
//				http_conn.setRequestProperty("cookie", m_cookie);
//			}

			if (headers!=null)	{
				//Log.i(TAG, "httpoper headers.length:"+headers.length);
				for (int i=0; i<headers.length; i++) {
					String[] items = headers[i].split(":[ ]");
					if (items.length < 2) {
						continue;
					}
					if("cookie".equals(items[0].toLowerCase())){
						continue;
					}
					http_conn.addRequestProperty(items[0], items[1]);
				}
			}

			http_conn.setRequestMethod(oper);

			if (body != null && body.length() != 0) {
				Log.i(TAG, "httpoper body.:"+body);
				OutputStream out = http_conn.getOutputStream();
				out.write(body.getBytes());
				out.flush();
				out.close();
			}
            http_conn.connect();
			String strResp = new String();
			int nCode = http_conn.getResponseCode();

			if (nCode == 200 || nCode == 202) {
				///< 是否是重定向
				String location = get_location_header (http_conn);
				if (location != null) {
					strResp = location;
				}
				///< 读取body体
				else {
					InputStream in = http_conn.getInputStream();
					int size = http_conn.getContentLength();
					Log.i("HttpTools","as server return http header context_length:"+size);
					strResp = SafeReadStream (in, size);
				}
			}

			///< 组合成返回字符串
			String strResult;
			if (strResp.length() == 0) {
				strResult = Integer.toString(nCode);
			}
			else {
				strResult = Integer.toString(nCode) + "\n" + strResp;
			}

			try { http_conn.disconnect();
			} catch (Exception e) {}
			http_conn = null;
			//Log.i(TAG, "httpoper strResult.:"+strResult);
			return strResult;
		}
		catch (Exception e) {
			String s = e.getMessage();
			if (s == null) {
				e.printStackTrace();
			}
			else {
				android.util.Log.d("https", s);
			}

			if (http_conn != null) {
				http_conn.disconnect();
			}
//			exceptionCount++;
//			if(exceptionCount<=1) {
//				return HttpOper1(oper, url, headers, body);
//			}
//			else
			return s;
		}
	}

	/*
	 * 有些种类的请求不需要cookie
	 */
	boolean need_cookie (String url) {
		if (url == null) {
			return true;
		}
		else if (url.indexOf("openapi/ctd") >= 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/*
	 * 从stream中读取完整字符串信息
	 */
	String SafeReadStream (InputStream in, int size) throws Exception {
		if (size < 0){
			return "";
		}
		byte[] tmp = new byte [size];
		int readed = 0;
		while (readed < size) {
			int tmp_char = in.read();
			if (tmp_char == -1) {
				break;
			}
			tmp [readed] = (byte ) tmp_char;
			readed++;
		}
		in.close();
		
		if (readed < size) {
			return "";
		}
		else {
			return new String(tmp, "UTF8");
		}
	}
}