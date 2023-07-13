package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.GeneratePromoCodesDto;
import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;

import java.util.List;
import java.util.UUID;

public interface PromoCodesService {

    List<String> generatePromoCodes(GeneratePromoCodesDto dto);

    FullPersonDto activate(UUID personId, String promoCode);
}
