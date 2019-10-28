package ca.mcgill.ecse321.project;

import ca.mcgill.ecse321.project.model.CourseOffering;
import com.sun.mail.smtp.SMTPTransport;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

public class JavaEmail {

    static String SENDER_EMAIL = "project.group.10.email@gmail.com";
    static String SENDER_PASSWORD = "ProjectGroup10";
    static String EMAIL_HOST = "smtp.gmail.com";


    private Session mailSession;
    private String targetEmail = "";
    private String emailTitle = "";
    private String emailDescription = "";

    public static void NotifyTutor(String tutorEmail, ca.mcgill.ecse321.project.model.Session session) throws  MessagingException
    {
        JavaEmail javaEmail = CreateEmail(tutorEmail);
        MimeMessage emailMessage = javaEmail.draftTutorEmailMessage(session);
        javaEmail.sendEmail(emailMessage);
    }

    private static JavaEmail CreateEmail(String targetEmail) throws MessagingException {
        JavaEmail javaEmail = new JavaEmail();
        javaEmail.targetEmail = targetEmail;
        javaEmail.setMailServerProperties();
        return javaEmail;
    }

    private void setMailServerProperties()
    {
        Properties emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", "587");
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        mailSession = Session.getDefaultInstance(emailProperties, null);
    }

    private MimeMessage draftTutorEmailMessage(ca.mcgill.ecse321.project.model.Session session) throws MessagingException {
        String emailSubject = "New Tutoring Booking";
        String emailBody = "A new booking has been reserved on "
                + session.getDate() + " " + session.getTime()
                + ".\n The class demanded is " + session.getCourseOffering().getCourse().getCourseName()
                + "\n<a href='www.google.com'>Click here to confirm</a>";
        MimeMessage emailMessage = new MimeMessage(mailSession);
        emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(targetEmail));
        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");

        return emailMessage;
    }

    private MimeMessage draftStudentEmailMessage() throws MessagingException {
        String[] toEmails = { "ypoulmarck@gmail.com" };
        String emailSubject = "Test email subject";
        String emailBody = "This is an email sent by <b>//howtodoinjava.com</b>.";
        MimeMessage emailMessage = new MimeMessage(mailSession);

        emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress());

        emailMessage.setSubject(emailSubject);

        emailMessage.setContent(emailBody, "text/html");

        return emailMessage;
    }

    public void sendEmail(MimeMessage emailMessage) throws MessagingException {

        Transport transport = mailSession.getTransport("smtp");
        transport.connect(EMAIL_HOST, SENDER_EMAIL, SENDER_PASSWORD);

        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
    }
}

