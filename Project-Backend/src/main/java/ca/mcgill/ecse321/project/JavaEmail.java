package ca.mcgill.ecse321.project;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.util.Properties;

public class JavaEmail {

    static String SENDER_EMAIL = "project.group.10.email@gmail.com";
    static String SENDER_PASSWORD = "ProjectGroup10";
    static String EMAIL_HOST = "smtp.gmail.com";


    private Session mailSession;
    private String targetEmail = "";

    public static void NotifyTutor(String tutorEmail, ca.mcgill.ecse321.project.model.Session session) throws  MessagingException
    {
        JavaEmail javaEmail = CreateEmail(tutorEmail);
        MimeMessage emailMessage = javaEmail.draftTutorEmailMessage(session);
        javaEmail.sendEmail(emailMessage);
    }

    public static void NotifyStudent(String tutorEmail, ca.mcgill.ecse321.project.model.Session session) throws  MessagingException
    {
        JavaEmail javaEmail = CreateEmail(tutorEmail);
        MimeMessage emailMessage = javaEmail.draftStudentEmailMessage(session);
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
                + ".\n The class demanded is" + session.getCourseOffering().getCourse().getCourseName()
                //TODO Insert correct api call here
                + "\n<a href='www.google.com'>Click here to confirm</a>";
        MimeMessage emailMessage = draftEmail(emailSubject, emailBody);
        return emailMessage;
    }

    private MimeMessage draftStudentEmailMessage(ca.mcgill.ecse321.project.model.Session session) throws MessagingException {
        String emailSubject = "Session confirmed";
        String emailBody = "Your session, "
                + session.getCourseOffering()
                + "at " + session.getTime()
                + " on " + session.getDate()
                + " with " + session.getTutor().getUsername()
                + ", has been confirmed.";
        MimeMessage emailMessage = draftEmail(emailSubject, emailBody);
        return emailMessage;
    }

    private MimeMessage draftEmail( String emailSubject, String emailBody) throws MessagingException
    {
        MimeMessage emailMessage = new MimeMessage(mailSession);
        emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(targetEmail));
        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");
        return emailMessage;
    }

    private void sendEmail(MimeMessage emailMessage) throws MessagingException {

        Transport transport = mailSession.getTransport("smtp");
        transport.connect(EMAIL_HOST, SENDER_EMAIL, SENDER_PASSWORD);

        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
    }
}

