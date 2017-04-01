package com.whty.efs.client;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

import com.google.gson.Gson;

public class CxfTest {
	private static final QName SERVICE_NAME = new QName(
			"http://www.tathing.com", "EnterFrontService");

	public static void main(String[] args) throws Exception {
		URL wsdlURL = EnterFrontService.WSDL_LOCATION;
		if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
			File wsdlFile = new File(args[0]);
			try {
				if (wsdlFile.exists()) {
					wsdlURL = wsdlFile.toURI().toURL();
				} else {
					wsdlURL = new URL(args[0]);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		EnterFrontService ss = new EnterFrontService(wsdlURL, SERVICE_NAME);
		IEnterFrontService port = ss.getEnterFrontServicePort();
		System.out.println("Invoking appQuery...");
		AppQueryRequest _appQuery_appQueryRequest = new AppQueryRequest();
		_appQuery_appQueryRequest.setSeID("001");
		_appQuery_appQueryRequest.setAppInstalledTag("002");

		Client client = ClientProxy.getClient(port);
		client.getOutInterceptors().add(new AddHeaderInterceptor());
		client.getOutInterceptors().add(new LoggingOutInterceptor());
		client.getInInterceptors().add(new LoggingInInterceptor());

		AppQueryResponse _appQuery__return = port
				.appQuery(_appQuery_appQueryRequest);
		System.out.println("appQuery.result="
				+ new Gson().toJson(_appQuery__return));

	}
}
