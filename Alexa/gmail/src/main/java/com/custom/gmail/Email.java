package com.custom.gmail;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email{
	public static void send(String value,String value1,String value2) {
	  Properties props = new Properties();
	    System.out.println("TSLEmail Start");

	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");
	    //Establishing a session with required user details
	    Session session = Session.getInstance(props, new javax.mail.Authenticator() 
	{
	        protected PasswordAuthentication getPasswordAuthentication() 
	{
	            return new PasswordAuthentication("ssr500x@gmail.com", "ssr50000");
	        }
	    });
	    try {
	        //Creating a Message object to set the email content
	        MimeMessage msg = new MimeMessage(session);
	        //Storing the comma separated values to email addresses
	        String to = value+"@gmail.com";
	        /*Parsing the
	         *  String with default delimiter as a comma by marking the boolean as true and storing the email
	        addresses in an array of InternetAddress objects*/
	        InternetAddress[] address = InternetAddress.parse(to, true);
	        //Setting the recipients from the address variable
	        msg.setRecipients(Message.RecipientType.TO, address);
	        String timeStamp = new SimpleDateFormat("yyyymmdd_hh-mm-ss").format(new Date());
	        msg.setSubject(value1 + timeStamp);
	      
	        msg.setText(value2);
	       
	        Transport.send(msg);
	        System.out.println("Mail has been sent successfully");
	    } catch (MessagingException mex) {
	        System.out.println("Unable to send an email" + mex);
	    }
	}

	//public static void send(String value, String s, String b) {
		// TODO Auto-generated method stub
		
	//}

//public static void send(String value, String s, String b) {
	// TODO Auto-generated method stub
	
}


