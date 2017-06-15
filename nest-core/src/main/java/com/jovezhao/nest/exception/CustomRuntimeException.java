package com.jovezhao.nest.exception;

/**
 * 自定义的异常基类
 * @author Jove
 *
 */
public abstract class CustomRuntimeException extends RuntimeException implements ICustomException {
	/**
	 *版本号
	 */
	private static final long serialVersionUID = 1L;
	private int code;

	public int getCode() {
		return code;
	}

	public Object[] getParames() {
		return parames;
	}

	private Object[] parames;
	private Object outParame;

	public Object getOutParame() {
		return outParame;
	}

	public void setOutParame(Object outParame) {
		this.outParame = outParame;
	}

	public CustomRuntimeException(String message, int code, Object... parames) {
		super(message);
		this.code = code;
		this.parames = parames;
	}

}

