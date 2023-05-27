package org.example;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import java.util.Scanner;
public class CertificateAdder {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the path of the certificate to be added: ");
            String filePath = scanner.nextLine();

            //  certificate objesi olusturuyor
            byte[] certBytes = Files.readAllBytes(Paths.get(filePath));
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream bis = new ByteArrayInputStream(certBytes);
            Certificate certificate = certificateFactory.generateCertificate(bis);
            bis.close();

            // cacerts dosyasından keystore objesi olusturuluyor
            final char sep = File.separatorChar;
            File dir = new File("C:\\Program Files\\Java\\jre1.8.0_221\\lib\\security");
            File file = new File(dir, "cacerts");
            InputStream localCertIn = new FileInputStream(file);
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            char[] passphrase = "changeit".toCharArray();
            keystore.load(localCertIn, passphrase);
            if (keystore.containsAlias("myAlias")) {
                localCertIn.close();
                return;
            }
            localCertIn.close();

            // certificate eklenerek keystore update oluyor
            keystore.setCertificateEntry("myAlias", certificate);

            //keystore objesi dosya olarak cikartilacak
            //Burada esas cacert dosyasına yazmaya izin vermedi. output için path soruyorum
            System.out.print("Enter the directory path for updated cacert file: ");
            String directoryPath = scanner.nextLine();
            File directory = new File(directoryPath);

            if (directory.isDirectory()) {
                System.out.println("Directory found: " + directory.getAbsolutePath());
            } else {
                System.out.println("Invalid directory path");
            }
            scanner.close();

            File fileOut = new File(directory,"cacertsOut");
            OutputStream out = new FileOutputStream(fileOut);
            keystore.store(out, passphrase);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
