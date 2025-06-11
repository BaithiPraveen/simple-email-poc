// package pabithi.poc.email;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.MimeMessageHelper;
// import org.springframework.mail.javamail.MimeMessagePreparator;

// import jakarta.mail.MessagingException;
// import jakarta.mail.internet.MimeMessage;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// @SpringBootTest
// class EmailPocApplicationTests {

// 	@Autowired
// 	private EmailController emailController;

// 	@MockBean
// 	private JavaMailSender javaMailSender;

// 	@Test
// 	void contextLoads() {
// 		// Test if the application context loads successfully
// 	}

// 	@Test
// 	void testSendSimpleMail() {
// 		// Arrange
// 		EmailDetails details = EmailDetails.builder()
// 				.recipient("test@example.com")
// 				.subject("Test Subject")
// 				.msgBody("Test Message")
// 				.build();

// 		doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

// 		// Act
// 		String result = emailController.sendMail(details);

// 		// Assert
// 		assertEquals("Mail Sent Successfully...", result);
// 		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
// 	}

// 	@Test
// 	void testSendMailWithAttachment() throws MessagingException {
// 		// Arrange
// 		EmailDetails details = EmailDetails.builder()
// 				.recipient("test@example.com")
// 				.subject("Test Subject")
// 				.msgBody("Test Message")
// 				.attachment("test.txt")
// 				.build();

// 		MimeMessage mimeMessage = mock(MimeMessage.class);
// 		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
// 		doNothing().when(javaMailSender).send(any(MimeMessage.class));

// 		// Act
// 		String result = emailController.sendMailWithAttachment(details);

// 		// Assert
// 		assertEquals("Mail sent Successfully", result);
// 		verify(javaMailSender, times(1)).send(any(MimeMessage.class));
// 	}

// 	@Test
// 	void testSendSimpleMailFailure() {
// 		// Arrange
// 		EmailDetails details = EmailDetails.builder()
// 				.recipient("test@example.com")
// 				.subject("Test Subject")
// 				.msgBody("Test Message")
// 				.build();

// 		doThrow(new RuntimeException("Mail sending failed"))
// 				.when(javaMailSender).send(any(SimpleMailMessage.class));

// 		// Act
// 		String result = emailController.sendMail(details);

// 		// Assert
// 		assertEquals("Error while Sending Mail", result);
// 	}

// 	@Test
// 	void testSendMailWithAttachmentFailure() throws MessagingException {
// 		// Arrange
// 		EmailDetails details = EmailDetails.builder()
// 				.recipient("test@example.com")
// 				.subject("Test Subject")
// 				.msgBody("Test Message")
// 				.attachment("test.txt")
// 				.build();

// 		MimeMessage mimeMessage = mock(MimeMessage.class);
// 		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
// 		doThrow(new MessagingException("Mail sending failed"))
// 				.when(javaMailSender).send(any(MimeMessage.class));

// 		// Act
// 		String result = emailController.sendMailWithAttachment(details);

// 		// Assert
// 		assertEquals("Error while sending mail!!!", result);
// 	}
// }
// 		// Assert
// 		assertEquals("Error while sending mail!!!", result);
// 	}
// }