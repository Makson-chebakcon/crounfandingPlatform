package ru.cft.shift.crowdfundingplatformapi.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.shift.crowdfundingplatformapi.enumeration.PersonRole;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenData {
    private UUID id;
    private String email;
    private PersonRole role;
}
