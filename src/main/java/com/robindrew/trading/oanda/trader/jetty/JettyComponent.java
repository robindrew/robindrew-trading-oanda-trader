package com.robindrew.trading.oanda.trader.jetty;

import com.google.common.base.Supplier;
import com.robindrew.common.html.Bootstrap;
import com.robindrew.common.http.servlet.executor.IHttpExecutor;
import com.robindrew.common.http.servlet.executor.IVelocityHttpContext;
import com.robindrew.common.service.component.jetty.JettyVelocityComponent;
import com.robindrew.common.service.component.jetty.handler.MatcherHttpHandler;
import com.robindrew.common.service.component.jetty.handler.page.BeanConsolePage;
import com.robindrew.common.service.component.jetty.handler.page.BeanOperationPage;
import com.robindrew.common.service.component.jetty.handler.page.BeanViewPage;
import com.robindrew.common.service.component.jetty.handler.page.IndexPage;
import com.robindrew.common.service.component.jetty.handler.page.SystemPage;
import com.robindrew.common.template.ITemplateLocator;
import com.robindrew.common.template.velocity.VelocityTemplateLocatorSupplier;
import com.robindrew.trading.oanda.trader.jetty.page.AccountsPage;
import com.robindrew.trading.oanda.trader.jetty.page.InstrumentsPage;

public class JettyComponent extends JettyVelocityComponent {

	@Override
	protected Supplier<ITemplateLocator> getTemplateLocator() {
		return new VelocityTemplateLocatorSupplier();
	}

	@Override
	protected void populate(MatcherHttpHandler handler) {

		// Register standard pages
		handler.uri("/", newIndexPage(getContext(), "site/common/Index.html"));
		handler.uri("/System", new SystemPage(getContext(), "site/common/System.html"));
		handler.uri("/BeanConsole", new BeanConsolePage(getContext(), "site/common/BeanConsole.html"));
		handler.uri("/BeanView", new BeanViewPage(getContext(), "site/common/BeanView.html"));
		// handler.uri("/BeanAttribute", new BeanAttributePage(getContext(), "site/common/BeanAttribute.html"));
		handler.uri("/BeanOperation", new BeanOperationPage(getContext(), "site/common/BeanOperation.html"));

		// Register extra pages
		handler.uri("/Accounts", new AccountsPage(getContext(), "site/oanda/trader/Accounts.html"));
		handler.uri("/Instruments", new InstrumentsPage(getContext(), "site/oanda/trader/Instruments.html"));
	}

	private IHttpExecutor newIndexPage(IVelocityHttpContext context, String templateName) {
		IndexPage page = new IndexPage(context, templateName);
		page.addLink("Accounts", "/Accounts", Bootstrap.COLOR_DEFAULT);
		page.addLink("Positions", "/Positions", Bootstrap.COLOR_DEFAULT);
		page.addLink("Instruments", "/Instruments", Bootstrap.COLOR_DEFAULT);
		return page;
	}

}
