package com.mengbai.yygh.common.util;


import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.mengbai.yygh.common.result.Result;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zcc
 * @version 2021/04/24/22:02
 * @description <描述>
 * @create 2021/04/24/22:02
 */
public class CrowdUtil {

	/***
	 * 专门负责上传文件到 OSS 服务器的工具方法
	 *@param endPoint OSS 参数
	 *@param accessKeyId OSS 参数
	 *@param accessKeySecret OSS 参数
	 *@param inputStream 要上传的文件的输入流
	 *@param bucketName OSS 参数 * @param bucketDomain OSS 参数
	 *@param originalName 要上传的文件的原始文件名
	 *@return 包含上传结果以及上传的文件在 OSS 上的访问路径 */
	public static Result<String> uploadFileToOSS(
			String endPoint,
			String accessKeyId,
			String accessKeySecret,
			InputStream inputStream,
			String bucketName,
			String bucketDomain,
			String originalName ){

		// 创建OSSClient实例
		OSS ossClient = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);

		// 生成上传文件的目录，按照日期来划分目录
		String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());

		// 生成上传文件在OSS服务器上保存的文件名,通过uuid生成随机uuid，将其中的“-”删去（替换成空字符串）
		String fileMainName = UUID.randomUUID().toString().replace("-", "");

		// 从原始文件名中获取文件扩展名
		String extensionName = originalName.substring(originalName.lastIndexOf("."));

		// 使用目录、文件主体名称、文件扩展名拼接得到对象名称
		String objectName = folderName + "/" + fileMainName + extensionName;


		try {
			// 调用OSS客户端对象的方法上传文件并获取响应结果数据
			PutObjectResult putObjectResult = ossClient.putObject(bucketName,objectName,inputStream);

			// 从响应结果中获取具体的响应消息
			ResponseMessage responseMessage = putObjectResult.getResponse();

			// 根据响应状态判断是否成功
			if (responseMessage == null) {
				// 拼接访问刚刚上传的文件的路径
				String ossFileAccessPath = bucketDomain + "/" + objectName;

				// 返回成功，并带上访问路径
				return Result.ok(ossFileAccessPath);
			}else {
				// 获取响应状态码
				int statusCode = responseMessage.getStatusCode();
				// 没有成功 获取错误消息
				String errorMessage = responseMessage.getErrorResponseAsString();

				return Result.fail("当前响应状态码=" + statusCode + " 错误消息=" + errorMessage);
			}
		} catch (Exception e){
			e.printStackTrace();
			return Result.fail(e.getMessage());
		} finally {
			// 关闭OSSClient
			ossClient.shutdown();
		}

	}



	/***
	 * 给远程第三方短信接口发送请求把验证码发送到用户手机上
	 * @param host              短信接口调用的URL地址
	 * @param path              具体发送短信功能的地址
	 * @param method            请求方式
	 * @param phoneNum          接受验证码的手机
	 * @param appcode            来调用第三方短信API的AppCode
	 * @param sign              签名编号
	 * @param skin              模板编号
	 * @return 返回调用结果是否成功，以及失败消息
	 *          成功：返回验证码
	 *          失败: 返回失败消息
	 */
	public static Result<String> sendCodeByShortMessage (String host, String path, String method, String
			phoneNum, String appcode, String skin){


		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);

//       生成验证码
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 4; i++) {

			int random = (int) (Math.random() * 10);
			builder.append(random);
		}
		String code = builder.toString();

		//  封装的其它参数
		Map<String, String> querys = new HashMap<String, String>();
//        收短信的手机号
		querys.put("receive", phoneNum);
//        要发送的验证码
		querys.put("tag", code);
//        模板编号
		querys.put("templateId", skin);
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
			StatusLine statusLine = response.getStatusLine();
//            状态码：200 成功：400	参数错误；404请求资源不存在；500系统内部错误，请联系服务商；501第三方服务异常；604接口停用；1001其他，以实际返回为准
			int statusCode = statusLine.getStatusCode();
//            信息
			String reasonPhrase = statusLine.getReasonPhrase();
			if (statusCode == 200) {
//                如果成功返回验证码
				return Result.ok(code);
			}
//            失败返回消息
			return Result.fail(reasonPhrase);
//            System.out.println(response.toString());
			//获取response的body
//            System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			return Result.fail(e.getMessage());
		}
	}




	/***
	 * 判断当前方法是否为ajax请求
	 * @param request 请求对象f:q
	 *:
	 * @return
	 *         true:当前请求对象是ajax请求
	 *         false:当前请求不是ajax请求
	 */
	public static boolean judgeRequestType (HttpServletRequest request){
		//获取请求头
		String requestHeaderAccept = request.getHeader("Accept");
		String requestHeaderX_Requested_With = request.getHeader("X-Requested-With");
		//判断
		return (requestHeaderAccept != null && requestHeaderAccept.contains("application/json"))
				||
				(requestHeaderX_Requested_With != null && requestHeaderX_Requested_With.equals("XMLHttpRequest"));

	}
	//
	// /***
	//  * 对明文字符串进行md5加密
	//  * @param source
	//  * @return 加密结果
	//  */
	// public static String md5 (String source){
	// 	// 1.判断source是否有效
	// 	if (source == null || source.length() == 0) {
	// 		// 2.如果不是有效的字符串抛出异常
	// 		throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
	// 	}
	// 	try {
	// 		// 3.获取MessageDigest对象
	// 		String algorithm = "md5";
	// 		MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
	//
	// 		// 4.获取明文对应的字符数
	// 		byte[] input = source.getBytes();
	//
	// 		// 5.执行加密
	// 		byte[] output = messageDigest.digest(input);
	//
	// 		//  6.创建BigInteger对象
	// 		int sigum = 1;
	// 		BigInteger bigInteger = new BigInteger(sigum, output);
	//
	// 		// 7.按照16进制将bigInteger转为字符串
	// 		int radix = 16;
	// 		String encode = bigInteger.toString(radix).toUpperCase();
	//
	// 		return encode;
	//
	// 	} catch (NoSuchAlgorithmException e) {
	// 		e.printStackTrace();
	// 	}
	//
	// 	return null;
	//
	// }


}

