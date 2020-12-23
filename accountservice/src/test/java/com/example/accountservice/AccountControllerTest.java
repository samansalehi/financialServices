package com.example.accountservice;

import com.example.accountservice.entities.dto.AccountRequest;
import com.example.accountservice.entities.dto.AccountResponse;
import com.example.accountservice.services.AccountServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    AccountServices accountServices;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createAccountShouldReturnDefult() throws Exception {
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/account/create"
                ,new AccountRequest(1,100),AccountResponse.class).getAccount_id()).
                isNotNull();
    }

}
