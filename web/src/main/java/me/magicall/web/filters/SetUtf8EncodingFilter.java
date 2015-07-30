package me.magicall.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import me.magicall.consts.StrConst.EncodingConst;

public class SetUtf8EncodingFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(EncodingConst.UTF8);
		chain.doFilter(request, response);
	}

	@Override
	public void init(final FilterConfig arg0) throws ServletException {

	}

}
