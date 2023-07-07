package ru.cft.shift.crowdfundingplatformapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDto {
    private String email;
    private String password;
}
