<<<<<<< HEAD
package finalproject;
import javax.mail.*;
import javax.mail.internet.*;
=======
package service;
import javax.mail.*; //error
import javax.mail.internet.*; //error
>>>>>>> 27eddc7 (Align Book model, DAO, service, and GUI; add LoanDAO and EmailService stub)
import java.util.Properties;

public class EmailService {


		private final String senderEmail = "yourlibrary@email.com";
	    private final String password = "yourEmailPassword";

	    public void sendOverdueEmail(String recipientEmail, String borrowerName,
<<<<<<< HEAD
	                                 String bookTitle, int daysLate, double fine) throws MessagingException {
=======
	                                 String bookTitle, int daysLate, double fine) throws MessagingException { // error
>>>>>>> 27eddc7 (Align Book model, DAO, service, and GUI; add LoanDAO and EmailService stub)
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com"); // example
	        props.put("mail.smtp.port", "587");

<<<<<<< HEAD
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
=======
	        Session session = Session.getInstance(props, //error
	            new Authenticator() { //error
	                protected PasswordAuthentication getPasswordAuthentication() { //error
	                    return new PasswordAuthentication(senderEmail, password); //error
	                }
	            });

	        Message message = new MimeMessage(session); //error
	        message.setFrom(new InternetAddress(senderEmail));// error
	        message.setRecipients(Message.RecipientType.TO, //error
	                InternetAddress.parse(recipientEmail)); //error
>>>>>>> 27eddc7 (Align Book model, DAO, service, and GUI; add LoanDAO and EmailService stub)
	        message.setSubject("Overdue Book Notice");

	        String content = "Dear " + borrowerName + ",\n\n" +
	                "You have overdue book: \"" + bookTitle + "\".\n" +
	                "It is " + daysLate + " days late.\n" +
	                "Fine: $" + fine + "\n\n" +
	                "Please return it as soon as possible to avoid suspension.\n" +
	                "Library Management";

	        message.setText(content);
<<<<<<< HEAD
	        Transport.send(message);
	    }
	}

}
=======
	        Transport.send(message); //error
	    }
	}

}
>>>>>>> 27eddc7 (Align Book model, DAO, service, and GUI; add LoanDAO and EmailService stub)
