package com.acooly.epei.util;


import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpTool {

    private static final Logger logger = LoggerFactory.getLogger(HttpTool.class);

	//得到openid
	public final static String get_token_url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	//得到用户详细信息 2.0认证方式
	public final static String oauth_info="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//根据UnionID机制获得用户详细信息
	public final static String unionID_info="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//微店获取access_token
	public final static String get_weidianAccessToken_url="https://api.vdian.com/token?grant_type=client_credential&appkey=APPKEY&secret=SECRET";
	//微店接口调用地址
	public final static String call_weidian_url="https://api.vdian.com/api?param=PARAM&public=PUBLIC";
	
	public static String getToken(String appid,String appsecret, String grantType, String code){
		String requestUrl = get_token_url.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code).replace("authorization_code",grantType);
		JSONObject jsonObject = httpRequest(requestUrl,"GET", null);
		return jsonObject.toString();
	}

    public static String getOpenID(HttpServletRequest request){
        String code = request.getParameter("code");
		logger.info(">>>>>>>>>>>>>>>>>>>: request:{}" , request.getRequestURL() );
		logger.info(">>>>>>>>>>>>>>>>>>>: request-code:{}" ,  code);
        if(StringUtils.isBlank(code)){
            return null;
        }else{
        	try{
        		String returnJSON = HttpTool.getToken(WxpayConfig.APPID, WxpayConfig.APPSECRET, "authorization_code", code);
        		JSONObject obj = JSONObject.fromObject(returnJSON);
                logger.info(">>>>>>>>>>>>>>>>>>>: obj:{}" ,  returnJSON);
                return obj.get("openid").toString();
        	}catch(Exception e){
        		return null;
        	}
        }
    }
	
	public static String getUserDetail(String access_token,String openid){
		String requestUrl=oauth_info.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
		JSONObject jsonObject = httpRequest(requestUrl,"GET", null);
		return jsonObject.toString();
	}
	
	public static String getUserDetailByUUnionID(String access_token,String openid){
		String requestUrl=unionID_info.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
		JSONObject jsonObject = httpRequest(requestUrl,"GET", null);
		return jsonObject.toString();
	}
	/** 
     * 使用Get方式获取数据 
     * @param url 
     * URL包括参数，http://HOST/XX?XX=XX&XXX=XXX 
     * @param charset 
     * @return 
     */  
    public static String sendGet(String url, String charset) {  
        String result = "";  
        BufferedReader in = null;  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection connection = realUrl.openConnection();  
            // 设置通用的请求属性  
            connection.setRequestProperty("accept", "*/*");  
            connection.setRequestProperty("connection", "Keep-Alive");  
            connection.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 建立实际的连接  
            connection.connect();  
            // 定义 BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(  
                    connection.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) { 
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输入流  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (Exception e2) {  
                e2.printStackTrace();  
            }  
        }  
        return result;  
    }  
  
    /**  
     * POST请求，字符串形式数据  
     * @param url 请求地址  
     * @param param 请求数据  
     * @param charset 编码方式  
     */  
    public static String sendPostUrl(String url, String param, String charset) {  
  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(param);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(  
                    conn.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送 POST 请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
    /**  
     * POST请求，Map形式数据  
     * @param url 请求地址  
     * @param param 请求数据  
     * @param charset 编码方式  
     */  
    public static String sendPost(String url, Map<String, String> param,  
            String charset) {  
  
        StringBuffer buffer = new StringBuffer(); 
        buffer.append("<xml>");
        if (param != null && !param.isEmpty()) {  
            for (Map.Entry<String, String> entry : param.entrySet()) {  
            	if(entry.getKey().equals("attach") || entry.getKey().equals("body") || entry.getKey().equals("sign")){
            		buffer.append("<"+entry.getKey()+">");
            		buffer.append("<![CDATA["+entry.getValue()+"]]>");
            		buffer.append("</"+entry.getKey()+">");
            	}else{
            		buffer.append("<"+entry.getKey()+">");
            		buffer.append(entry.getValue());
            		buffer.append("</"+entry.getKey()+">");
            	}
            }  
        }  
        buffer.append("</xml>");
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(buffer);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(  
                    conn.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送 POST 请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
   
    public static JSONObject getWeidianOrderlist(int page_num, int page_size, String access_token){
    	try{
    		//请求参数
    		JSONObject param = new JSONObject();
        	param.put("page_num", page_num);
        	param.put("page_size", 30);
        	param.put("order_type", "all");
//        	String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"+00:00:00";
//        	param.put("add_start", today);
        	param.put("is_wei_order", "2");
        	//通用参数
        	JSONObject mutual = new JSONObject();
        	mutual.put("method", "vdian.order.list.get");
        	mutual.put("access_token", access_token);
        	mutual.put("version", "1.2");
        	//拼接url
        	String url = call_weidian_url.replace("PARAM", param.toString()).replace("PUBLIC", mutual.toString()).trim();
        	String s = sendGet(url, "utf-8");
        	JSONObject json = JSONObject.fromObject(s);
        	return json;
    	}catch(Exception e){
    		return null;
    	}
    }
    
    public static JSONObject getWeidianOrderDetail(String orderId, String access_token){
    	//请求参数
		JSONObject param = new JSONObject();
    	param.put("order_id", orderId);
    	//通用参数
    	JSONObject mutual = new JSONObject();
    	mutual.put("method", "vdian.order.get");
    	mutual.put("access_token", access_token);
    	mutual.put("version", "1.0");
    	//拼接url
    	String url = call_weidian_url.replace("PARAM", param.toString()).replace("PUBLIC", mutual.toString()).trim();
    	String s = sendGet(url, "utf-8");
    	JSONObject json = JSONObject.fromObject(s);
    	return json;
    }
    
    public static JSONObject getWeidianOrderIdList(int page_num, int page_size,String order_date, String access_token){
    	//请求参数
		JSONObject param = new JSONObject();
		param.put("order_date",order_date);
		param.put("is_wei_order", "2");
    	param.put("page_num", page_num);
    	param.put("group_type", 1);
    	param.put("page_size", 50);
    	
    	//通用参数
    	JSONObject mutual = new JSONObject();
    	mutual.put("method", "vdian.order.ids.get");
    	mutual.put("access_token", access_token);
    	mutual.put("version", "1.1");
    	//拼接url
    	String url = call_weidian_url.replace("PARAM", param.toString()).replace("PUBLIC", mutual.toString()).trim();
    	String s = sendGet(url, "utf-8");
    	JSONObject json = JSONObject.fromObject(s);
    	return json;
    }
    
    public static String getWeidianAccessToken(String appkey, String secret){
    	String url = get_weidianAccessToken_url.replace("APPKEY", appkey).replace("SECRET", secret);
    	String result = sendGet(url, "UTF-8");
    	try{
    		JSONObject json = JSONObject.fromObject(result);
        	JSONObject jsonResult = json.getJSONObject("result");
        	String access_token = jsonResult.getString("access_token");
        	return access_token;
    	}catch(Exception e){
    		return null;
    	}	
    }
    
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// /1、解决https请求的问题

			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			// /2、兼容GET、POST两种方式

			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}

			// /3、兼容有数据提交、无数据提交两种情况

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return jsonObject;
	}

}