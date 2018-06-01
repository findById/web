package com.cn.web.core.util;

import java.math.BigDecimal;

public class MathUtils {

	private static final int DEFAULT_SCALE = 10;

	/**
	 * 比较大小. 如果 arg0 >= arg1 返回true，否则返回false
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws NumberFormatException
	 */
	public static boolean max(String arg0, String arg1) {
		BigDecimal a = new BigDecimal(arg0);
		BigDecimal b = new BigDecimal(arg1);
		if ((a.max(b)).equals(a)) {
			return true;
		}
		return false;
	}

	/**
	 * 提供精确的加法运算
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws NumberFormatException
	 */
	public static BigDecimal add(String arg0, String arg1) {
		BigDecimal a = new BigDecimal(arg0);
		BigDecimal b = new BigDecimal(arg1);
		return a.add(b);
	}

	/**
	 * 提供精确的减法运算
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws NumberFormatException
	 */
	public static BigDecimal subtract(String arg0, String arg1) {
		BigDecimal a = new BigDecimal(arg0);
		BigDecimal b = new BigDecimal(arg1);
		return a.subtract(b);
	}

	/**
	 * 提供精确的乘法运算
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws NumberFormatException
	 */
	public static BigDecimal multiply(String arg0, String arg1) {
		BigDecimal a = new BigDecimal(arg0);
		BigDecimal b = new BigDecimal(arg1);
		return a.multiply(b);
	}

	/**
	 * 提供(相对)精度的除法运算，当发生除不尽的情况时，精确到小数点后10位，后位 四舍五入
	 * 
	 * @param arg0
	 * @param divisor
	 * @return
	 * @throws NumberFormatException
	 */
	public static BigDecimal divide(String arg0, String divisor) {
		return divide(arg0, divisor, DEFAULT_SCALE);
	}

	/**
	 * 提供(相对)精度的除法运算，当发生除不尽的情况时，由 scale控制精度，后位 四舍五入
	 * 
	 * @param arg0
	 * @param arg1
	 * @param scale
	 * @return
	 * @throws NumberFormatException
	 */
	public static BigDecimal divide(String arg0, String arg1, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal a = new BigDecimal(arg0);
		BigDecimal divisor = new BigDecimal(arg1);
		return a.divide(divisor, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 提供精确的小数位处理，由 scale控制精度，后位 四舍五入
	 * 
	 * @param arg0
	 * @param scale
	 * @return
	 * @throws NumberFormatException
	 */
	public static BigDecimal round(String arg0, int scale) {
		return divide(arg0, "1", scale);
	}

}
