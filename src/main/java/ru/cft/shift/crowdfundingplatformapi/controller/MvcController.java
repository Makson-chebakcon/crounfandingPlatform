package ru.cft.shift.crowdfundingplatformapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cft.shift.crowdfundingplatformapi.service.ProfileService;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MvcController {

    private final ProfileService profileService;

    @GetMapping("confirm-email")
    public String resetPassword(@RequestParam UUID personId,
                                @RequestParam UUID confirmCode,
                                Model model) {
        try {
            String fullName = profileService.confirmEmailAndGetFullName(personId, confirmCode);
            model.addAttribute("fullName", fullName);
            return "email-confirmed";
        } catch (Exception exception) {
            log.error("Ошибка при подтверждении почты", exception);
            return "error";
        }
    }

}
