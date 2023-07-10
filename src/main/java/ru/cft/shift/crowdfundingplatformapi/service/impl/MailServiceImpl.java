package ru.cft.shift.crowdfundingplatformapi.service.impl;

import jakarta.annotation.PostConstruct;
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
            "Чтобы подтвердить почту вам необходимо перейти по ссылке " +
            "%s/api/v1/confirm-email?personId=%s&confirmCode=%s. Срок действия ссылки истечек через 60 минут";

    private static final String NEW_PASSWORD_SUBJECT = "Сгенерирован новый пароль";
    private static final String NEW_PASSWORD_TEXT = "Вы запросили сброс пароля, поэтому чтобы попасть в ваш аккаунт " +
            "необходимо аутентифицироваться с помощью нового пароля: '%s'.";

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${application.hostname}")
    private String hostname;

    @Value("${application.port}")
    private String port;

    @PostConstruct
    private void init() {
        log.info("hostname: {}", hostname);
    }

    @Override
    public void newUserMessage(Person person) {
        SimpleMailMessage message = buildMailMessage(
                NEW_USER_SUBJECT,
                String.format(NEW_USER_TEXT,
                        person.getName(),
                        person.getSurname(),
                        person.getPatronymic(),
                        hostname + ":" + port,
                        person.getId(),
                        person.getEmailConfirmCode()
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

    @Override
    public void sendNewPassword(Person person, String newPassword) {
        SimpleMailMessage message = buildMailMessage(
                NEW_PASSWORD_SUBJECT,
                String.format(NEW_PASSWORD_TEXT, newPassword),
                person.getEmail()
        );

        try {
            mailSender.send(message);
            log.info("Письмо с новым паролем успешно отправлено");
        } catch (Exception exception) {
            log.error("Не удалось отправить письмо с новым паролем для пользователя с id {}", person.getId());
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
