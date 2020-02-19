package com.wangwi.miaosha.utils;

import com.wangwi.miaosha.exception.BusinessException;
import com.wangwi.miaosha.exception.EmBusinessError;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class AliyunShortMsgUtils {

	/**
	 * 生成6位随机验证码
	 * @return			生成的验证码
	 * @author
	 */
	public static String randomCode() {
		return randomCode(6);
	}
	
	/**
	 * 生成随机验证码
	 * @param length	验证码长度
	 * @return			生成的验证码
	 * @throws	RuntimeException 验证码长度必须大于0
	 * @author 
	 */
	public static String randomCode(int length) {
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < length; i++) {
			
			// 1.生成随机数
			double doubleRandom = Math.random();
			
			// 2.调整
			int integerRandom = (int) (doubleRandom * 10);
			
			// 3.拼接
			builder.append(integerRandom);
		}
		
		return builder.toString();
	}
		
	/**
	 * 发送验证码短信  官方106 山东鼎信科技
	 * @param appcode		阿里云市场中调用API时识别身份的appCode
	 * @param randomCode	验证码值
	 * @param phoneNum		接收验证码短信的手机号
	 */
	public static void sendShortMessage(String appcode, String randomCode, String phoneNum) throws BusinessException {

		// 调用短信发送接口时的访问地址
		String host = "http://dingxin.market.alicloudapi.com";
		// 具体访问路径
		String path = "/dx/sendSms";
		// 请求方式
		String method = "POST";
		// 购买产品的APPID
		// String appcode = "387a661e1f5f424c91fb9c5619deaade";
		// 封装请求头
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);

		// 封装请求体
		Map<String, String> querys = new HashMap<String, String>();
		//手机号
		querys.put("mobile", phoneNum);
		// 要发送的验证码
		querys.put("param", "code:" + randomCode);

		// 模板id,联系客服人员申请成功的消息内容模板ID
		querys.put("tpl_id", "TP1711063");
		Map<String, String> bodys = new HashMap<String, String>();

		try {
			/**
			 * 重要提示如下:
			 * HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			System.out.println(response);
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(EmBusinessError.ALIYUN_SHORT_MESSAGE_SEND_FAILED);
		}
	}
	
	
}