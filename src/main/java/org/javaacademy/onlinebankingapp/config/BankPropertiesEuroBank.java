package org.javaacademy.onlinebankingapp.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("EuroBank")
public class BankPropertiesEuroBank extends BankProperties {
}
