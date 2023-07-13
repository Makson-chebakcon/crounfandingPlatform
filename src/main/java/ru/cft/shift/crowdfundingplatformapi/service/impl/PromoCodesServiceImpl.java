package ru.cft.shift.crowdfundingplatformapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.dto.GeneratePromoCodesDto;
import ru.cft.shift.crowdfundingplatformapi.entity.PromoCode;
import ru.cft.shift.crowdfundingplatformapi.repository.PromoCodeRepository;
import ru.cft.shift.crowdfundingplatformapi.service.PromoCodesService;
import ru.cft.shift.crowdfundingplatformapi.util.CodeGenerator;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromoCodesServiceImpl implements PromoCodesService {

    private static final int PROMO_CODE_SIZE = 8;

    private final PromoCodeRepository promoCodeRepository;
    private final CodeGenerator codeGenerator;

    @Override
    public List<String> generatePromoCodes(GeneratePromoCodesDto dto) {
        List<PromoCode> promoCodes = new ArrayList<>();

        for (int i = 0; i < dto.getCount(); i++) {
            promoCodes.add(generatePromoCode());
        }

        return promoCodeRepository
                .saveAll(promoCodes)
                .stream()
                .map(PromoCode::getValue)
                .toList();
    }

    private PromoCode generatePromoCode() {
        PromoCode promoCode = new PromoCode();
        boolean isGenerated = false;

        while (!isGenerated) {
            String code = codeGenerator.generateCode(PROMO_CODE_SIZE);
            if (!promoCodeRepository.existsByValue(code)) {
                promoCode.setValue(code);
                isGenerated = true;
            }
        }

        return promoCode;
    }

}

