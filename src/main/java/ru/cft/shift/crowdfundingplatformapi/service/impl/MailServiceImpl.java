package ru.cft.shift.crowdfundingplatformapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.service.MailService;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private static final String NEW_USER_SUBJECT = "Регистрация на платформе";
    private static final String NEW_USER_TEXT = "%s %s %s, вы только что создали аккаунт на платформе. " +
            "Вы можете подтвердить эту почту через личный кабинет платформы. Приятного использования!";

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;
//T58!!SGUCkj4KEg

    @Override
    public void newUserMessage(Person person) {
        SimpleMailMessage message = buildMailMessage(
                NEW_USER_SUBJECT,
                String.format(NEW_USER_TEXT,
                        person.getName(),
                        person.getSurname(),
                        person.getPatronymic()
                ),
                person.getEmail()
        );

        try {
            mailSender.send(message);
            log.info(
                    "Письмо при регистрации успешно отправлено пользователю с id '{}' на почту '{}'",
                    person.getId(),
                    person.getEmail()
            );
        } catch (Exception exception) {
            log.error(
                    "Не удалось отправить письмо при регистрации пользователя с id '{}' на почту '{}'",
                    person.getId(),
                    person.getEmail(),
                    exception
            );
        }
    }

    private SimpleMailMessage buildMailMessage(String subject, String text, String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailMessage.setFrom(username);
        mailMessage.setTo(email);

        return mailMessage;
    }

}
