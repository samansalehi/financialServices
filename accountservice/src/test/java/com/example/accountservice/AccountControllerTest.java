package com.example.accountservice;

import com.example.accountservice.entities.dto.AccountRequest;
import com.example.accountservice.entities.dto.AccountResponse;
import com.example.accountservice.controller.AccountController;
import com.example.common.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    AccountController accountController;
    @Autowired
    private TestRestTemplate restTemplate;


    //    @Test
    public void createAccountForValidCustomerId() throws Exception {
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/account/create"
                , new AccountRequest("121212", 100, Currency.DOLLER), AccountResponse.class).getAccount_id()).
                isEqualTo("121212");
    }

}
