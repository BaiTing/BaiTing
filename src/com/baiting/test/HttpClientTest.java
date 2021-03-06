package com.baiting.test;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.baiting.util.Utils;

public class HttpClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "http://pic.sogou.com/pics?query=%BA%EE%CF%E6%E6%C3&w=05009900&p=40030500&_asf=pic.sogou.com&_ast=1383230869&sut=1643&sst0=1383230869402";
		CloseableHttpClient httpclient = Utils.getInstance().getConn() ;
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String content = null;
			if(response.getStatusLine().getStatusCode() == 200) {
				content = EntityUtils.toString(entity);
				System.out.println(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			httpclient = null;
		}
	}

}
