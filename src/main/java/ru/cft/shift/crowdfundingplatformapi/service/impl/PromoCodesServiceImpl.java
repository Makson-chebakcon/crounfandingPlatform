package ru.cft.shift.crowdfundingplatformapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.dto.GeneratePromoCodesDto;
import ru.cft.shift.crowdfundingplatformapi.dto.person.FullPersonDto;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.entity.PromoCode;
import ru.cft.shift.crowdfundingplatformapi.exception.NotFoundException;
import ru.cft.shift.crowdfundingplatformapi.mapper.PersonMapper;
import ru.cft.shift.crowdfundingplatformapi.repository.PersonRepository;
import ru.cft.shift.crowdfundingplatformapi.repository.PromoCodeRepository;
import ru.cft.shift.crowdfundingplatformapi.service.ProfileService;
import ru.cft.shift.crowdfundingplatformapi.service.PromoCodesService;
import ru.cft.shift.crowdfundingplatformapi.util.CodeGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromoCodesServiceImpl implements PromoCodesService {

    private static final int PROMO_CODE_SIZE = 8;

    private final PromoCodeRepository promoCodeRepository;
    private final CodeGenerator codeGenerator;
    private final ProfileService profileService;
    private final PersonMapper personMapper;
    private final PersonRepository personRepository;

    @Override
    public List<String> generatePromoCodes(GeneratePromoCodesDto dto) {
        List<PromoCode> promoCodes = new ArrayList<>();

        for (int i = 0; i < dto.getCount(); i++) {
            promoCodes.add(generatePromoCode(dto.getAmount()));
        }

        return promoCodeRepository
                .saveAll(promoCodes)
                .stream()
                .map(PromoCode::getValue)
                .toList();
    }

    @Transactional
    @Override
    public FullPersonDto activate(UUID personId, String promoCode) {
        Person person = profileService.getPersonById(personId);
        PromoCode promoCodeEntity = promoCodeRepository
                .findByValue(promoCode)
                .orElseThrow(() -> new NotFoundException("Такой промокод не найден"));

        person.setMoney(person.getMoney().add(promoCodeEntity.getCost()));
        person = personRepository.save(person);
        promoCodeRepository.delete(promoCodeEntity);

        return personMapper.entityToFullDto(person);
    }

    private PromoCode generatePromoCode(int amount) {
        PromoCode promoCode = new PromoCode();
        boolean isGenerated = false;

        while (!isGenerated) {
            String code = codeGenerator.generateCode(PROMO_CODE_SIZE);
            if (!promoCodeRepository.existsByValue(code)) {
                promoCode.setValue(code);
                promoCode.setCost(BigDecimal.valueOf(amount));
                isGenerated = true;
            }
        }

        return promoCode;
    }

}

