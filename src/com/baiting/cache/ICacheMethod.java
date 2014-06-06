package com.baiting.cache;

/**
 * cache到期回调方法需要实现的接口
 * @author sunshine
 *
 */
public interface ICacheMethod {

	public void execute(String key); 
}
