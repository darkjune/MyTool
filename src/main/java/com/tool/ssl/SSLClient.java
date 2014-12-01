/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tool.ssl;

import java.net.Socket;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

/**
 *
 * @author ryan_zhu
 */
public class SSLClient {

    public static void main(String[] args) {
        // TODO code application logic here
        Certificate[] certChain = getCertificateChain("www.google.com", 443);
        if (certChain != null && certChain.length > 0) {
            Certificate aCert = certChain[0];
            if (aCert instanceof X509Certificate) {
                X509Certificate x509Cert = (X509Certificate) aCert;
                System.out.println(x509Cert);
            }
        }
    }
    
   public static Certificate[] getCertificateChain(String szHost, int nPort)
            {
        try {
            // Setup a context that does not perform authentication
            SSLContext sslc = SSLContext.getInstance("TLS");
            sslc.init(null,
                    new TrustManager[]{
                new X509ExtendedTrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return (new X509Certificate[0]);
                    }

                    public void checkServerTrusted(X509Certificate[] parm1, String parm2) {
                        System.out.println(parm1.length + "\n" + parm1[0].toString());
                        System.out.println(parm2);
                    }

                    public void checkClientTrusted(X509Certificate[] parm1, String parm2) {
                    }

                    public void checkClientTrusted(X509Certificate[] xcs, String string, Socket socket) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] xcs, String string, Socket socket) throws CertificateException {
                    }

                    public void checkClientTrusted(X509Certificate[] xcs, String string, SSLEngine ssle) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] xcs, String string, SSLEngine ssle) throws CertificateException {
                    }
                }
            },
                    null);

            // Use the context to create the socket
            SSLSocketFactory sslsf = sslc.getSocketFactory();
            SSLSocket sock = (SSLSocket) sslsf.createSocket(szHost, nPort);
            sock.setSoTimeout(5000);

            // Get the Peer's cert
            Certificate[] aCerts = sock.getSession().getPeerCertificates();

            // Close the socket and return the certs
            sock.close();
            return (aCerts);
        } catch (Exception e) {
            // Any errors.. wrap it and rethrow
            e.printStackTrace();
        }
        return null;
    }

}
