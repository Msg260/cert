package org.example;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Enumeration;
// bu kodu chatgpt yazdı, sadece path ve şifreyi değiştirmem gerekti.
public class CertificateLister {
    public static void main(String[] args) {
        try {
            // Load the keystore file
            FileInputStream fis = new FileInputStream("C:\\Program Files\\Java\\jre1.8.0_221\\lib\\security\\cacerts");

            //FileInputStream fis = new FileInputStream("C:\\Users\\said.gundogan\\Desktop\\certimport\\cacertsOut");

            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(fis, "changeit".toCharArray());
            fis.close();

            // List the aliases (entries) in the keystore
            Enumeration<String> aliases = keystore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                Certificate certificate = keystore.getCertificate(alias);
                System.out.println("Alias: " + alias);
                System.out.println("Certificate: " + certificate.toString());
                System.out.println("----------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
