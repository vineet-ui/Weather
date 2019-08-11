package com.example.weather.weather.connectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RESTConnector implements Connector {

	private HttpClient client;

	private URI uri;

	private HttpRequestBase httpRequest;

	private HttpResponse httpResponse;

	private String host;
	private String path;
	private Map<String, String> httpHeaderMap;
	private Map<String, String> httpParameterMap;

	@Override
	public void initialize(String host, String path) {
		this.host = host;
		this.path = path;
	}

	public void setHttpHeaderMap(Map<String, String> httpHeaderMap) {
		this.httpHeaderMap = httpHeaderMap;
	}

	public void setHttpParameterMap(Map<String, String> httpParameterMap) {
		this.httpParameterMap = httpParameterMap;
	}

	private boolean createAndExecuteRESTCall() {
		this.client = HttpClientBuilder.create().build();

		// http methods are dynamically configured. Need to get the right http instance
		this.httpRequest = new HttpGet();

		// add http headers
		this.addHTTPRequestHeaders(httpRequest);

		// configure http api as <protocol>://<domain>/api path?urlparameters
		this.configureURI();

		this.httpRequest.setURI(this.uri);

		// execute the http request
		return this.executeHTTPRequest();
	}

	private void configureURI() {
		URIBuilder uriBuilder = new URIBuilder();

		// Update the protocol
		uriBuilder.setScheme("https");

		// Update the hostname/domain
		uriBuilder.setHost(host);

		// Update the api path
		uriBuilder.setPath(path);

		// Update the URL Paramters
		this.addHTTPRequestParamters(uriBuilder);

		try {
			this.uri = uriBuilder.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void addHTTPRequestParamters(URIBuilder uriBuilder) {
		if (Objects.nonNull(httpParameterMap)) {
			httpParameterMap.forEach(uriBuilder::addParameter);
		}
	}

	private void addHTTPRequestHeaders(HttpRequestBase httpRequest) {
		// Add Rest of the configured HTTP Headers.
		if (Objects.nonNull(httpHeaderMap)) {
			httpHeaderMap.forEach(httpRequest::addHeader);
		}
	}

	private boolean executeHTTPRequest() {
		try {
			httpResponse = client.execute(httpRequest);
			Integer httpResponseStatusCode = httpResponse.getStatusLine().getStatusCode();
			if (httpResponseStatusCode >= 200 && httpResponseStatusCode < 300) {
				return true;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void execute() {
		this.createAndExecuteRESTCall();
	}

	@Override
	public HttpResponse getResponse() {
		return httpResponse;
	}
	
	@Override
	public String getStringResponse() {
		BufferedReader rd;
		String line;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			while ((line = rd.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
}
