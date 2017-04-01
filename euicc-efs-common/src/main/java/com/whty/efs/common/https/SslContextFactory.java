package com.whty.efs.common.https;

import java.security.KeyStore;
import java.security.Security;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class SslContextFactory {
	private static final String PROTOCOL = "TLS"; // TODO: which protocols will
													// be adopted?
	private static final SSLContext CLIENT_CONTEXT;

	static {
		SSLContext clientContext = null;
		// get keystore and trustore locations and passwords
		String keyStorePassword = "123456";
		try {
			// keystore
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			KeyStore ks = KeyStore.getInstance("BKS");
			ks.load(SslContextFactory.class.getClassLoader().getResourceAsStream("android.bks"), keyStorePassword.toCharArray());

			// Set up key manager factory to use our key store
			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, keyStorePassword.toCharArray());

			// truststore
			KeyStore ts = KeyStore.getInstance("BKS");
			ts.load(SslContextFactory.class.getClassLoader().getResourceAsStream("android.bks"), keyStorePassword.toCharArray());

			// set up trust manager factory to use our trust store
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ts);
			clientContext = SSLContext.getInstance(PROTOCOL);
			clientContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(),
					null);
		} catch (Exception e) {
			throw new Error("Failed to initialize the client-side SSLContext",
					e);
		}

		
		CLIENT_CONTEXT = clientContext;
	}


	public static SSLContext getClientContext() {
		return CLIENT_CONTEXT;
	}

	private SslContextFactory() {
		// Unused
	}
}
