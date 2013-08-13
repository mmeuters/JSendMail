/*
Copyright (c) 2013 Marlon Meuters

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/*
 * This code is based on work done by Marlon Meuters (CLUSTER ONE GmbH)
 * <marlon.meuters@cluster-one.eu>.
 */

import java.util.*;
import java.io.File;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendEmail
{
	// The recipient of the mail
	private String recipient = null;

	// The sender of the mail
	private String sender = null;

	// The host-server the mail is sent from (smtp-server host)
	private String smtp_host = null;

	// The port on which the host-server is listening (smtp-server host port)
	private int smtp_host_port = 0;

	// The username to authenticate on the smtp server
	private String smtp_username = null;

	// The fitting password for the above mentioned user
	private String smtp_password = null;

	// The subject for the mail
	private String subject = null;

	// The message to be sent
	private String message = null;

	// A file-attachement may be added to the mail
	private File attachement = null;

	// Should debug information be printed to console?
	// Default is 'false'
	private boolean debugMode = false;
	public void setDebugMode(boolean value) { this.debugMode = value; }

	// The constructor to create a new SendMail object.
	public SendEmail() {
		super();
	}

	public void send() {
	      Properties properties = System.getProperties();
	      properties.put("mail.smtp.host", this.smtp_host);
	      properties.put("mail.smtp.port", this.smtp_host_port);
	      properties.put("mail.transport.protocol","smtp");
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.starttls.enable", "true");
	      properties.put("mail.smtp.tls", "true");
	      properties.put("mail.smtp.user", this.smtp_username);
	      properties.put("mail.password", this.smtp_password);
	

		  final String auth_username = this.smtp_username;
		  final String auth_password = this.smtp_password;
	      javax.mail.Authenticator auth = new javax.mail.Authenticator() {
	    	   @Override
	    	   public PasswordAuthentication getPasswordAuthentication() {
	    	      return new PasswordAuthentication(auth_username, auth_password);
	    	   }
	      };
	      
	      Session session = Session.getDefaultInstance(properties, auth);
	      session.setDebug(this.debugMode);
	      
	      try {
	    	 MimeMessage message =  new MimeMessage(session);
	    	 message.setFrom(new InternetAddress(this.sender));
	    	 message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.recipient));
	    	 message.setSubject(this.subject);

	         MimeBodyPart messageBodyPart = new MimeBodyPart();
	         messageBodyPart.setText(this.message);

	         Multipart multipart = new MimeMultipart();
	         multipart.addBodyPart(messageBodyPart);
	         
	         if (this.attachement != null) {
	         	messageBodyPart = new MimeBodyPart();
	         	DataSource source = new FileDataSource(this.attachement.getAbsolutePath());
	         	messageBodyPart.setDataHandler(new DataHandler(source));
	         	messageBodyPart.setFileName(this.attachement.getName());
	         	multipart.addBodyPart(messageBodyPart);
	         }

	         message.setContent(multipart);
	         
	         Transport.send(message);
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}

	public static void sendMail(String recipient, String sender, String smtp_host, int smtp_host_port, String smtp_username, String smtp_password, String subject, String message) 
	{
		SendEmail mail = new SendEmail();
		mail.recipient = recipient;
		mail.sender = sender;
		mail.smtp_host = smtp_host;
		mail.smtp_host_port = smtp_host_port;
		mail.smtp_username = smtp_username;
		mail.smtp_password = smtp_password;
		mail.subject = subject;
		mail.message = message;
		mail.send();
	}

	public static void sendMail(String recipient, String sender, String smtp_host, int smtp_host_port, String smtp_username, String smtp_password, String subject, String message, String attachement) 
	{
		SendEmail mail = new SendEmail();
		mail.recipient = recipient;
		mail.sender = sender;
		mail.smtp_host = smtp_host;
		mail.smtp_host_port = smtp_host_port;
		mail.smtp_username = smtp_username;
		mail.smtp_password = smtp_password;
		mail.subject = subject;
		mail.message = message;

		if (attachement != null) {
			File attachement_file = new File(attachement);
			if (attachement_file != null) {
				mail.attachement = attachement_file;
			}
		}

		mail.send();
	}
}