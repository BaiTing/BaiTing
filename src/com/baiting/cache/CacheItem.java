package com.baiting.cache;


/**
 * 用于管理每个条目的cache内容和超时时间回调方法
 * @author sunshine
 *
 */
public class CacheItem {

	private String key;
	private Object value;
	private long timeOut;
	private ICacheMethod callback = null;

	/**
     *
     */
    public CacheItem() {
		super();
	}

	/**
     *
     * @return
     */
    public ICacheMethod getCallback() {
		return callback;
	}

	/**
     *
     * @param callback
     */
    public void setCallback(ICacheMethod callback) {
		this.callback = callback;
	}

	/**
     *
     * @param key
     * @param value
     */
    public CacheItem(String key, Object value) {
		this.key = key;
		this.value = value;
		this.timeOut = 0;
	}

	/**
     *
     * @param key
     * @param value
     * @param timeOut
     */
    public CacheItem(String key, Object value, long timeOut) {
		this.key = key;
		this.value = value;
		this.timeOut = timeOut;
	}

	/**
     *
     * @param key
     * @param value
     * @param timeOut
     * @param callback
     */
    public CacheItem(String key, Object value, long timeOut,
			ICacheMethod callback) {
		this.key = key;
		this.value = value;
		this.timeOut = timeOut;
		this.callback = callback;
	}

	/**
     *
     * @return
     */
    public String getKey() {
		return key;
	}

	/**
     *
     * @param key
     */
    public void setKey(String key) {
		this.key = key;
	}

	/**
     *
     * @return
     */
    public Object getValue() {
		return value;
	}

	/**
     *
     * @param value
     */
    public void setValue(Object value) {
		this.value = value;
	}

	/**
     *
     * @return
     */
    public long getTimeOut() {
		return timeOut;
	}

	/**
     *
     * @param timeOut
     */
    public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}
}
