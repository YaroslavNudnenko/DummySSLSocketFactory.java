package imapClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.SecureRandom;

import javax.net.SocketFactory;
import javax.net.ssl.*;

/**
 * This class create SSLSocketFactory based on our DummyTrustManager.
 */

public class DummySSLSocketFactory extends SSLSocketFactory {
    
	private SSLSocketFactory factory;

    public DummySSLSocketFactory() {
    	try {
    		SSLContext sslcontext = SSLContext.getInstance("TLS");
    		sslcontext.init(null, new TrustManager[]{new DummyTrustManager()}, new SecureRandom());    		
    		factory = (SSLSocketFactory)sslcontext.getSocketFactory();
    	} catch(Exception ex) {
    		addExToLogs(ex);    		
    	}
    }
	
	private void addExToLogs(Exception ex) {
		Form.saveExceptions(ex, "imapClient.DummySSLSocketFactory");//ex.printStackTrace();
		String exception = ex.getClass().getName();
		String exMsg = ex.getMessage();
		if (exMsg != null) exception += ": " + exMsg;
		else exception += " at " + ex.getStackTrace()[0].toString();
		Throwable cause = ex.getCause();
		if (cause != null) exception = exception + " Caused by: " + cause.getMessage();
		Form.addLogs("ERROR: "+exception+"\r\n", -1);	
	}
	
    public static SocketFactory getDefault() {
    	return new DummySSLSocketFactory();
    }

    public Socket createSocket() throws IOException {
    	return factory.createSocket();
    }

    public Socket createSocket(Socket socket, String s, int i, boolean flag) throws IOException {
    	return factory.createSocket(socket, s, i, flag);
    }

    public Socket createSocket(InetAddress inaddr, int i, InetAddress inaddr1, int j) throws IOException {
    	return factory.createSocket(inaddr, i, inaddr1, j);
    }

    public Socket createSocket(InetAddress inaddr, int i) throws IOException {
    	return factory.createSocket(inaddr, i);
    }

    public Socket createSocket(String s, int i, InetAddress inaddr, int j) throws IOException {
    	return factory.createSocket(s, i, inaddr, j);
    }

    public Socket createSocket(String s, int i) throws IOException {
    	return factory.createSocket(s, i);
    }

    public String[] getDefaultCipherSuites() {
    	return factory.getDefaultCipherSuites();
    }

    public String[] getSupportedCipherSuites() {
    	return factory.getSupportedCipherSuites();
    }
}