package com.automation.utilities;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
public class SendEmail {

	public static void SendEmailNow(String body) {
		 final String username = "";
	     final String password = "";

	        Properties props = new Properties();
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

	        Session session = Session.getInstance(props,
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	          });
	        try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        try {
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(""));
	            
	            message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(""));
	            String currentDateTime = new SimpleDateFormat("dd-MMM-yyyy_h:mm a z").format(Calendar.getInstance().getTime());
	            message.setSubject("Tests Run Report on "+currentDateTime);
	            message.setText("Report is attached as a zip file, download, extract in a folder and you can see report files there.");
	            Multipart multipart = new MimeMultipart();
	            BodyPart textPart = new MimeBodyPart(); 
	            textPart.setText(body);
	            
	            BodyPart filePart = new MimeBodyPart();
	         // Part two is attachment
	            filePart = new MimeBodyPart();
	            String filename = ZipUtils.OUTPUT_ZIP_FILE;
	            DataSource source = new FileDataSource(filename);
	            filePart.setDataHandler(new DataHandler(source));
	            filePart.setFileName(source.getName());
	            multipart.addBodyPart(textPart);
	            multipart.addBodyPart(filePart);

	            // Send the complete message parts
	            message.setContent(multipart );
	            Transport.send(message);
	            System.out.println("Email Sent");

	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	    }
}
