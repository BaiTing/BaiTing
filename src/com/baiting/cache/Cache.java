package com.baiting.cache;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * 实现简单cache 实现思路： 创建一个静态Hashtable用于保存key和value
 * ,对于cache过期后的方法回调，在cache过期后，再访问cache的时候进行，避免了使用定时器轮询过期时间，进行cache清除的效率损耗。
 * 使用synchronized关键字进行多线程同步。 包括二个类和一个接口：
 * cache类：里面都是静态方法,提供基于key,value的方法进行cache的添加，修改，访问,进行cache过期后调用callback方法。
 * cacheitem类：用于管理每个条目的cache内容和超时时间回调方法 ICacheMethod接口：cache到期回调方法需要实现的接口
 * cache类：里面都是静态方法
 * 
 * @author sunshine
 * 
 */
public class Cache {

	private static final Map<String, Object> cacheTable = new Hashtable<String, Object>();

	private Cache() {
		
	}

	// 添加cache,不过期
	/**
     *
     * @param key
     * @param value
     */
    public synchronized static void put(String key, Object value) {
		Cache.put(key, value, -1);
	}

	// 添加cache有过期时间
	/**
     *
     * @param key
     * @param value
     * @param timeOut
     */
    public synchronized static void put(String key, Object value, long timeOut) {
		Cache.put(key, value, timeOut, null);
	}

	// 添加cache有过期时间并且具有回调方法
	/**
     *
     * @param key
     * @param value
     * @param timeOut
     * @param callback
     */
    public synchronized static void put(String key, Object value, long timeOut,
			ICacheMethod callback) {
		if (timeOut > 0) {
			timeOut += new Date().getTime();
		}
		CacheItem item = new CacheItem(key, value, timeOut, callback);
		Cache.cacheTable.put(key, item);
	}

	// 获取cache
	/**
     *
     * @param key
     * @return
     */
    public synchronized static Object get(String key) {
		Object obj = Cache.cacheTable.get(key);
		if (obj == null) {
			return null;
		}
		CacheItem item = (CacheItem) obj;
		boolean expired = Cache.cacheExpired(key);
		if (expired == true) // 已过期
		{
			if (item.getCallback() == null) {
				Cache.remove(key);
				return null;
			} else {
				ICacheMethod callback = item.getCallback();
				callback.execute(key);
				expired = Cache.cacheExpired(key);
				if (expired == true) {
					Cache.remove(key);
					return null;
				}
			}
		}
		return item.getValue();
	}

	// 移除cache
	/**
     *
     * @param key
     */
    public synchronized static void remove(String key) {
		Object obj = Cache.cacheTable.get(key);
		if (obj != null) {
			obj = null;
		}
		Cache.cacheTable.remove(key);
	}

	// 清理所有cache对象
	/**
     *
     */
    public synchronized static void clear() {

		for (String s : Cache.cacheTable.keySet()) {
			Cache.cacheTable.put(s, null);
		}
		Cache.cacheTable.clear();
	}
	
	//判断是否存在cache
	/**
     *
     * @param key
     * @return
     */
    public synchronized static boolean hasCache(String key) {
        return cacheTable.containsKey(key);
	}

	// 判断是否过期
	private static boolean cacheExpired(String key) {
		CacheItem item = (CacheItem) Cache.cacheTable.get(key);
		if (item == null) {
			return false;
		}
		long milisNow = new Date().getTime();
		long milisExpire = item.getTimeOut();
		if (milisExpire <= 0) { // 不过期
			return false;
		} else if (milisNow >= milisExpire) {
			return true;
		} else {
			return false;
		}
	}
}
