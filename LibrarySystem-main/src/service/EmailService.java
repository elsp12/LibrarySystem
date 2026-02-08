package finalproject;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {


		private final String senderEmail = "yourlibrary@email.com";
	    private final String password = "yourEmailPassword";

	    public void sendOverdueEmail(String recipientEmail, String borrowerName,
	                                 String bookTitle, int daysLate, double fine) throws MessagingException {
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com"); // example
	        props.put("mail.smtp.port", "587");

	        Session session = Session.getInstance(props,
	            new Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(senderEmail, password);
	                }
	            });

	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(senderEmail));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(recipientEmail));
	        message.setSubject("Overdue Book Notice");

	        String content = "Dear " + borrowerName + ",\n\n" +
	                "You have overdue book: \"" + bookTitle + "\".\n" +
	                "It is " + daysLate + " days late.\n" +
	                "Fine: $" + fine + "\n\n" +
	                "Please return it as soon as possible to avoid suspension.\n" +
	                "Library Management";

	        message.setText(content);
	        Transport.send(message);
	    }
	}

}
