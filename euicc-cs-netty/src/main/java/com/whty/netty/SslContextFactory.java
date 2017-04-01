package com.whty.netty;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
/**
 * https配置加载类
 * @author Administrator
 *
 */
public class SslContextFactory {
	private static final String PROTOCOL = "TLS"; // TODO: which protocols will
													// be adopted?
	private static final SSLContext SERVER_CONTEXT;
	private static final SSLContext CLIENT_CONTEXT;
	
	public static final String EUICC_HOME = "EUICC_HOME";

	static {

		SSLContext serverContext = null;
		SSLContext clientContext = null;

		// get keystore and trustore locations and passwords
		String keyStorePassword = "123456";
		
		String path = System.getenv(EUICC_HOME);
		if (null == path) {
			path = System.getProperty(EUICC_HOME);
			if (null == path) {
				path = new File("").getAbsolutePath();
				System.setProperty(EUICC_HOME, path);
			}
		}
		
		File serverFile = new File(path + "/conf/server.keystore");
		File clientFile = new File(path + "/conf/android.bks");
		try {
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(serverFile), keyStorePassword.toCharArray());

			// Set up key manager factory to use our key store
			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, keyStorePassword.toCharArray());

			// truststore
			KeyStore ts = KeyStore.getInstance("JKS");
			ts.load(new FileInputStream(serverFile), keyStorePassword.toCharArray());

			// set up trust manager factory to use our trust store
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ts);

			// Initialize the SSLContext to work with our key managers.
			serverContext = SSLContext.getInstance(PROTOCOL);
			serverContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(),
					null);

		} catch (Exception e) {
			throw new Error("Failed to initialize the server-side SSLContext",
					e);
		}

		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			// keystore
			KeyStore ks = KeyStore.getInstance("BKS");
			ks.load(new FileInputStream(clientFile), keyStorePassword.toCharArray());

			// Set up key manager factory to use our key store
			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, keyStorePassword.toCharArray());

			// truststore
			KeyStore ts = KeyStore.getInstance("BKS");
			ts.load(new FileInputStream(clientFile), keyStorePassword.toCharArray());

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

		SERVER_CONTEXT = serverContext;
		CLIENT_CONTEXT = clientContext;
	}

	public static SSLContext getServerContext() {
		return SERVER_CONTEXT;
	}

	public static SSLContext getClientContext() {
		return CLIENT_CONTEXT;
	}

	private SslContextFactory() {
		// Unused
	}
	
	public static void main(String[] args) {
		
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			System.out.println(providers[i].getName()+":"+providers[i].getClass().getName());
		}
		
	}
}
