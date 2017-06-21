package com.jovezhao.nest.cache;

import java.util.Map;

/**
 * 缓存策略
 * 
 * @author Jove
 *
 */
public interface ICacheProvider {
	/**
	 * 根据指定key，从缓存中获取对象
	 *
	 * @param key
	 *            对象的key
	 * @param groupName
	 *            缓存组
	 * @return 缓存中的对象
	 */
	<T> T get(String groupName, String key,Class<T> clazz);

	/**
	 * 根据指定key集合，从缓存中获取对象Map
	 *
	 * @param groupName
	 *            缓存组
	 * @param keys
	 *            对象的key集合
	 * @return 缓存中的对象Map
	 */
	<T> Map<String, T>  get(String groupName, Class<T> clazz, String... keys);

	/**
	 * 把对象以key的形式放入缓存（同名key覆盖）
	 *
	 * @param groupName
	 *            缓存组
	 * @param key
	 *            指定对象的key
	 * @param value
	 *            放入缓存的对象
	 * @param idleSeconds
	 *            缓存项的空闲时间（秒）
	 */
	void put(String groupName, String key, Object value, long idleSeconds);

	/**
	 * 删除key所对应的缓存，key不存在不报错
	 *
	 * @param groupName
	 *            缓存组
	 * @param key
	 *            需要删除缓存对象的key
	 * @return true=成功，false=失败
	 */
	boolean remove(String groupName, String key);

	/**
	 * 移出指定组的所有缓存项
	 * 
	 * @param groupName
	 */
	void removeAll(String groupName);

	/**
	 * 判断key是否已经已存在
	 *
	 * @param groupName
	 *            缓存组
	 * @param key
	 *            缓存对象的key
	 * @return true=存在，false=不存在
	 */
	boolean containsKey(String groupName, String key);

	/**
	 * 获取本组所有缓存键
	 * @param groupName
	 * @return
	 */
	String[] getKeys(String groupName);
}
