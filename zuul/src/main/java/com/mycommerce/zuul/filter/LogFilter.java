package com.mycommerce.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class LogFilter extends ZuulFilter {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean shouldFilter() {

		return true;
	}

	@Override
	public Object run() throws ZuulException {

		final HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

		this.log.info("**** Requête interceptée ! L'URL est : {} ", request.getRequestURL());

		return null;
	}

	@Override
	public String filterType() {

		return "pre";
	}

	@Override
	public int filterOrder() {

		return 2;
	}

}
