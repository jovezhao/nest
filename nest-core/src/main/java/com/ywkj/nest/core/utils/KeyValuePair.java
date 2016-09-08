/**   
* @Title: KeyValuePair.java 
* @Package： com.appvworks.framework.Utils 
* @Description: TODO
* @author：duanwei 
* @date： 2015年7月2日 上午11:00:00  
*/
package com.ywkj.nest.core.utils;

import java.io.Serializable;

public class KeyValuePair<T extends Object,R extends Object> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T key;
	private R value;
	
	
	public KeyValuePair(T key, R value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	
	
	public KeyValuePair() {
		super();
		// TODO Auto-generated constructor stub
	}



	public T getKey() {
		return key;
	}
	public void setKey(T key) {
		this.key = key;
	}
	public R getValue() {
		return value;
	}
	public void setValue(R value) {
		this.value = value;
	}
}
