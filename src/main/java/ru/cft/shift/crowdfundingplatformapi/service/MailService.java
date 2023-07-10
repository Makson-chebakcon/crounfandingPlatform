package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.entity.Person;

public interface MailService {

    void newUserMessage(Person person);

    void sendNewPassword(Person person, String newPassword);

}
