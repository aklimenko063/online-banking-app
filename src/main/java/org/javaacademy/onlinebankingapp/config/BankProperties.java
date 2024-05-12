package org.javaacademy.onlinebankingapp.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public abstract class BankProperties {
    @Value("${bank.name}")
    private String name;
    @Value("${bank.partner.name}")
    private String partnerBankName;
    @Value("${bank.partner.url}")
    private String partnerUrl;
    @Value("${bank.partner.port}")
    private String partnerPort;
    @Value("${bank.account_prefix_number}")
    private long accountPrefixNumber;
}
