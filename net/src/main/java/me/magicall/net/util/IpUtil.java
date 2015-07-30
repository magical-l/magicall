package me.magicall.net.util;

import me.magicall.consts.StrConst.UrlConst;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class IpUtil {

	private static final String QUOTED_IP_SEPERATOR = Pattern.quote(UrlConst.IP_SEPERATOR);

	public static boolean isValidIpv4(final String ip) {
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			return false;
		}
		final String[] ss = ip.split(QUOTED_IP_SEPERATOR, -1);//当字符的最后就是分隔符时，负数控制不丢弃分割后最后的空字符串，默认的0则丢弃
		if (ss.length != 4) {
			return false;
		}
		for (final String s : ss) {
			try {
				final int i = Integer.parseInt(s.trim());
				if (i < 0 || i > 255) {
					return false;
				}
			} catch (final NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 取客户端的真实IP，考虑了反向代理等因素的干扰
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(final HttpServletRequest request) {
//		if (logger.isDebugEnabled()) {// 打印所有日志
//			logger.debug(new StringBuffer().append("X-Forwarded-For:").append(request.getHeader("X-Forwarded-For")).append("\tProxy-Client-IP:").append(
//					request.getHeader("Proxy-Client-IP")).append("\t:WL-Proxy-Client-IP:").append(request.getHeader("WL-Proxy-Client-IP")).append(
//					"\tRemoteAddr:").append(request.getRemoteAddr()).toString());
//		}
		final Enumeration<String> xffs = request.getHeaders("X-Forwarded-For");
		if (xffs.hasMoreElements()) {
			final String xff = xffs.nextElement();
			final String ip = resolveClientIPFromXFF(xff);
			if (isValidIpv4(ip)) {
				return ip;
			}
		}
		{
			final String ip = request.getHeader("Proxy-Client-IP");
			if (isValidIpv4(ip)) {
				return ip;
			}
		}
		{
			final String ip = request.getHeader("WL-Proxy-Client-IP");
			if (isValidIpv4(ip)) {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}

	/**
	 * 从X-Forwarded-For头部中获取客户端的真实IP。
	 * X-Forwarded-For并不是RFC定义的标准HTTP请求Header
	 * ，可以参考http://en.wikipedia.org/wiki/X-Forwarded-For
	 * 
	 * @param xff X-Forwarded-For头部的值
	 * @return 如果能够解析到client IP，则返回表示该IP的字符串，否则返回null
	 */
	private static String resolveClientIPFromXFF(final String xff) {
		final String[] ss = xff.split(",");
		for (int i = ss.length - 1; i >= 0; i--) {//x-forward-for链反向遍历
			final String ip = ss[i];
			if (isValidIpv4(ip)) { //判断ip是否合法，是否是公司机房ip
				return ip;
			}
		}

		//如果反向遍历没有找到格式正确的外网IP，那就正向遍历找到第一个格式合法的IP
		for (final String element : ss) {
			final String ip = element;
			if (isValidIpv4(ip)) {
				return ip;
			}
		}
		return null;
	}

	public static void main(final String... args) {
		final String[] ips = {//
		"127.0.0.1",//
				" 255 . 255 . 255 . 255 ",//
				"0.0.0.0",//
				"256.1.1.1",//
				"a.b.c.d",//
				"1.1.1.1.1",//
				"1.1.1",//
				"1.1.1.1.",//
				".1.1.1.1",//
		};
		for (final String s : ips) {
			System.out.println("@@@@@@" + isValidIpv4(s));
		}
	}
}
