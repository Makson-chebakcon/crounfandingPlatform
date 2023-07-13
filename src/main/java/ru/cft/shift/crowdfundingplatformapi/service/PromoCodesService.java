package ru.cft.shift.crowdfundingplatformapi.service;

import ru.cft.shift.crowdfundingplatformapi.dto.GeneratePromoCodesDto;

import java.util.List;

public interface PromoCodesService {

    List<String> generatePromoCodes(GeneratePromoCodesDto dto);

}
