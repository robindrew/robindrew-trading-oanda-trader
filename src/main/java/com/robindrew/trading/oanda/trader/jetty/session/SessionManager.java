package com.robindrew.trading.oanda.trader.jetty.session;

import com.robindrew.trading.oanda.platform.OandaSession;

public class SessionManager implements SessionManagerMBean {

	private final OandaSession session;

	public SessionManager(OandaSession session) {
		this.session = session;
	}

	@Override
	public String getAccountId() {
		return session.getCredentials().getAccountId();
	}

	@Override
	public String getRestDomain() {
		return session.getEnvironment().getRestDomain();
	}

	@Override
	public String getStreamDomain() {
		return session.getEnvironment().getStreamDomain();
	}

}
