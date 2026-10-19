package com.springboot.activemq.messaging.config;


import lombok.AllArgsConstructor;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import static com.springboot.activemq.messaging.system.SetEnv.requireEnv;

@AllArgsConstructor
public final class TlsConfig {

    public static ActiveMQConnectionFactory createFactory(
            String username, String password) {

        String trustStore = requireEnv("ARTEMIS_TRUSTSTORE");
        String trustPassword = requireEnv(
                "ARTEMIS_TRUSTSTORE_PASSWORD");

        System.setProperty("javax.net.ssl.trustStore", trustStore);
        System.setProperty(
                "javax.net.ssl.trustStoreType", "PKCS12");
        System.setProperty(
                "javax.net.ssl.trustStorePassword", trustPassword);

        String url =
                "tcp://localhost:61617?sslEnabled=true";

        return new ActiveMQConnectionFactory(
                url, username, password);
    }

}
