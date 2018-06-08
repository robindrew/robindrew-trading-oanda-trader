package com.robindrew.trading.oanda.trader.oanda;

import static com.robindrew.common.dependency.DependencyFactory.setDependency;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.mbean.IMBeanRegistry;
import com.robindrew.common.mbean.annotated.AnnotatedMBeanRegistry;
import com.robindrew.common.properties.map.type.EnumProperty;
import com.robindrew.common.properties.map.type.FileProperty;
import com.robindrew.common.properties.map.type.IProperty;
import com.robindrew.common.properties.map.type.StringProperty;
import com.robindrew.common.service.component.AbstractIdleComponent;
import com.robindrew.trading.log.FileBackedTransactionLog;
import com.robindrew.trading.oanda.platform.IOandaSession;
import com.robindrew.trading.oanda.platform.OandaCredentials;
import com.robindrew.trading.oanda.platform.OandaEnvironment;
import com.robindrew.trading.oanda.platform.OandaSession;
import com.robindrew.trading.oanda.trader.jetty.session.SessionManager;

public class OandaComponent extends AbstractIdleComponent {

	private static final Logger log = LoggerFactory.getLogger(OandaComponent.class);

	private static final IProperty<String> propertyAccountId = new StringProperty("oanda.accountId");
	private static final IProperty<String> propertyToken = new StringProperty("oanda.token");
	private static final IProperty<OandaEnvironment> propertyEnvironment = new EnumProperty<>(OandaEnvironment.class, "oanda.environment");
	private static final IProperty<File> propertyTransactionLogDir = new FileProperty("transaction.log.dir");

	@Override
	protected void startupComponent() throws Exception {
		IMBeanRegistry registry = new AnnotatedMBeanRegistry();

		String accountId = propertyAccountId.get();
		String token = propertyToken.get();
		OandaEnvironment environment = propertyEnvironment.get();
		File transactionLogDir = propertyTransactionLogDir.get();

		OandaCredentials credentials = new OandaCredentials(accountId, token);

		log.info("Creating Session");
		log.info("Environment: {}", environment);
		log.info("Account: {}", credentials.getAccountId());
		OandaSession session = new OandaSession(credentials, environment);
		setDependency(IOandaSession.class, session);

		log.info("Creating Session Manager");
		SessionManager sessionManager = new SessionManager(session);
		registry.register(sessionManager);

		log.info("Creating Transaction Log");
		FileBackedTransactionLog transactionLog = new FileBackedTransactionLog(transactionLogDir);
		transactionLog.start("OandaTransactionLog");

	}

	@Override
	protected void shutdownComponent() throws Exception {
		// TODO: Cancel all subscriptions here
	}

}
