package com.xxniu.HttpClient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.huaxia.middleware.sdk.utils.JsonUtils;

public class HttpClientTest {
	public static void main(String[] args) {
//		post();
		try {
			get();
//			post();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void get() throws Exception {
		//相对于commons-httpclient 3.1这里采用接口的方式来获取httpclient了
        HttpClient httpClient = HttpClients.createDefault();
        
        
        URIBuilder uriBuilder = new URIBuilder("http://112.124.109.171:8080/huaxia-pa-web/CommonInterface?TradeCode=5465&CustAcctId=6021000005745715&ItemId=A34320180327134738366HC&tradeFlowID=20171010");
//        uriBuilder.setParameter("id", "11");
//        System.out.println(uriBuilder.build());
        //声明请求方式
        HttpGet httpGet = new HttpGet(uriBuilder.build());
//        httpGet.
        //获取相应数据，这里可以获取相应的数据
        HttpResponse httpResponse = httpClient.execute(httpGet);
        //拿到实体
        HttpEntity httpEntity= httpResponse.getEntity();
        //获取结果，这里可以正对相应的数据精细字符集的转码
        String result = "";
        if (httpEntity != null) {
            result = EntityUtils.toString(httpEntity,"utf-8");
        }
        EntityUtils.consume(httpEntity);
        
        split(result);
        
//        System.out.println(result);
        
        //关闭连接
        httpGet.releaseConnection();
	}
	
	/**
	 * 截取字符串
	 *
	 * @Title HttpClientTest.split
	 * @Description: TODO
	 *
	 * 
	 * @version: 1.0 
	 * @author niuxinxing
	 * 修改历史: 
	 * 修改人: niuxinxing, 修改日期 : 2018年05月21日 下午3:15:24
	 * 修改内容 : TODO
	 */
	public static void split(String result) {
		String custAcctId1="",custAcctId2="",ItemId1="",ItemId2="";
//		int count=0;
		if (result.indexOf("RspCode=")>=0) {
			if (!"000000".equals(result.substring(result.indexOf("RspCode=")+8, 6+8+result.indexOf("RspCode=")))) {
				System.out.println("查询失败，请重试！");
				return;
			}
		}
		if (result.indexOf("TradeCode值：")>=0) {
			result.substring(result.indexOf("TradeCode值：")+11, 4+11+result.indexOf("TradeCode值："));//11
		}
		if (result.indexOf("CustAcctId值：")>=0) {
			custAcctId1=result.substring(result.indexOf("CustAcctId值：")+12, 16+12+result.indexOf("CustAcctId值："));//23
		}
		if (result.indexOf("ItemId值：")>=0) {
			ItemId1=result.substring(result.indexOf("ItemId值：")+8, 23+8+result.indexOf("ItemId值："));
		}
		
		if (result.indexOf("CustAcctId=")>=0) {
			custAcctId2=result.substring(result.indexOf("CustAcctId=")+11, 16+11+result.indexOf("CustAcctId="));
		}
		if (result.indexOf("ItemId=")>=0) {
			ItemId2=result.substring(result.indexOf("ItemId=")+7, 23+7+result.indexOf("ItemId="));
		}
//		System.out.println(custAcctId1 +"===" + custAcctId2 + "===" + ItemId1 + "===" + ItemId2);
		if (!custAcctId1.equals(custAcctId2)||!ItemId1.equals(ItemId2)) {
			System.out.println(custAcctId1 +"===" + custAcctId2 + "===" + ItemId1 + "===" + ItemId2);
		}
	}
	
	public static void post() throws ClientProtocolException, IOException {
		//相对于commons-httpclient 3.1这里采用接口的方式来获取httpclient了
        HttpClient httpClient = HttpClients.createDefault();
        //声明请求方式
        HttpPost httpPost = new HttpPost("http://localhost:8080/xxniu/hello/post");
        
        //获取相应数据，这里可以获取相应的数据
        HttpResponse httpResponse = httpClient.execute(httpPost);
        //拿到实体
        HttpEntity httpEntity= httpResponse.getEntity();
        //获取结果，这里可以正对相应的数据精细字符集的转码
        String result = "";
        if (httpEntity != null) {
            result = EntityUtils.toString(httpEntity,"utf-8");
        }
        EntityUtils.consume(httpEntity);
        System.out.println(result);
        //关闭连接
        httpPost.releaseConnection();
	}
}
