package com.baiting.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.jsoup.helper.StringUtil;

import com.baiting.config.SettingConfig;

/**
 *
 * @author Administrator
 */
public class Utils {
	
	private static final Logger log = Logger.getLogger(Util.class.getName()) ;
	private static Utils instance = null ;
	//外部配置文件路径
	private final SettingConfig config = SettingConfig.getInstance();
	
	private Utils(){
		log.log(Level.INFO, "init Utils ...") ;
	}
	
	/**
     *
     * @return
     */
    public synchronized static Utils getInstance() {
		if(null == instance) {
            instance = new Utils() ;
        }
		return instance ;
	}
	
	/**
	 * 得到网络连接
	 * @return
	 */
	public CloseableHttpClient getConn(){
		if(config.isUseProxy()){
			log.log(Level.INFO, "使用代理连接网络...") ;
			HttpHost proxy = new HttpHost(config.getProxyHost(), Integer.parseInt(config.getProxyPort()));
    		if (!StringUtil.isBlank(config.getProxyUserName()) && !StringUtil.isBlank(config.getProxyPwd())) {
    			// 设置http访问要使用的代理服务器的用户名和密码
    			CredentialsProvider credsProvider = new BasicCredentialsProvider();
    	        credsProvider.setCredentials(
    	                new AuthScope(config.getProxyHost(), Integer.parseInt(config.getProxyPort())),
    	                new UsernamePasswordCredentials(config.getProxyUserName(), config.getProxyPwd())
    	        );
    	        return HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setProxy(proxy).build();
    		}
    		// 设置http访问要使用的代理服务器的地址
    		return HttpClients.custom().setProxy(proxy).build() ;
    	}
		return HttpClients.createDefault() ;
	}
	
	/**
	 * 得到网络连接
	 * @return
	 */
	public CloseableHttpAsyncClient getAsyncConn(){
		if(config.isUseProxy()){
			log.log(Level.INFO, "使用代理连接网络...") ;
			HttpHost proxy = new HttpHost(config.getProxyHost(), Integer.parseInt(config.getProxyPort()));
    		if (!StringUtil.isBlank(config.getProxyUserName()) && !StringUtil.isBlank(config.getProxyPwd())) {
    			// 设置http访问要使用的代理服务器的用户名和密码
    			CredentialsProvider credsProvider = new BasicCredentialsProvider();
    	        credsProvider.setCredentials(
    	                new AuthScope(config.getProxyHost(), Integer.parseInt(config.getProxyPort())),
    	                new UsernamePasswordCredentials(config.getProxyUserName(), config.getProxyPwd())
    	        );
    	        return HttpAsyncClients.custom().setDefaultCredentialsProvider(credsProvider).setProxy(proxy).build();
    		}
    		// 设置http访问要使用的代理服务器的地址
    		return HttpAsyncClients.custom().setProxy(proxy).build() ;
    	}
		return HttpAsyncClients.createDefault() ;
	}
	
	 /**
     * 根据给定类名称得到所有定义的字段
     * @param className
     * 					类名称
     */
    public static List<String> reflect(String className) {
    	List<String> fields = new ArrayList<String>() ;
    	try {
    		Class<?> clazz = Class.forName(className) ;
    		Field[] field =  clazz.getDeclaredFields() ;
    		for (Field field2 : field) {
				fields.add(field2.getName()) ;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return fields ;
    }
    
    /**
     * 运用java自省特性设置值
     * @param clazz
     * 				被设置的对象
     * @param propertyName
     * 				被设置的字段名
     * @param propertyValue
     * 				设置的值
     */
    public static <T> void setPropertyValue(T clazz, String propertyName, Object propertyValue){
        if(clazz == null)
            throw new RuntimeException("clazz 参数不能为空!");
        if(propertyName == null)
            throw new RuntimeException("propertyName 参数不能为空!");
        try {
        	PropertyDescriptor proDescriptor = new PropertyDescriptor(propertyName, clazz.getClass());   
            Method methodSet = proDescriptor.getWriteMethod();   
            methodSet.invoke(clazz, propertyValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
     }
    
    /**
     * 运用java自省特性取值
     * @param clazz
     * 				被取的对象
     * @param propertyName
     * 				被取的字段名
     */
    public static <T> T getPropertyValue(T clazz, String propertyName){
        if(clazz == null)
            throw new RuntimeException("object 参数不能为空!");
        if(propertyName == null)
            throw new RuntimeException("propertyName 参数不能为空!");
        try {
        	PropertyDescriptor proDescriptor = new PropertyDescriptor(propertyName, clazz.getClass());
            Method methodGet = proDescriptor.getReadMethod();   
            methodGet.invoke(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz ;
     }
	
	/**
     *
     * @param args
     */
    public static void main(String[] args) {
		
	}
}
