package com.robindrew.trading.oanda.trader.jetty.page;

import static com.robindrew.common.dependency.DependencyFactory.getDependency;

import java.util.Map;

import com.robindrew.common.http.servlet.executor.IVelocityHttpContext;
import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.service.component.jetty.handler.page.AbstractServicePage;
import com.robindrew.trading.oanda.platform.IOandaSession;

public class InstrumentsPage extends AbstractServicePage {

	public InstrumentsPage(IVelocityHttpContext context, String templateName) {
		super(context, templateName);
	}

	@Override
	protected void execute(IHttpRequest request, IHttpResponse response, Map<String, Object> dataMap) {
		super.execute(request, response, dataMap);

		IOandaSession session = getDependency(IOandaSession.class);
		dataMap.put("account", session.getCredentials().getAccountId());
		dataMap.put("environment", session.getEnvironment());
	}
}
