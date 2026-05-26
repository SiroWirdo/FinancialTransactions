package com.financial.FinancialTransactions.user.controller;

import com.financial.FinancialTransactions.user.dto.UserAccountDTO;
import com.financial.FinancialTransactions.user.dto.UserAccountWithoutUserNameDTO;
import com.financial.FinancialTransactions.user.sevice.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureRestTestClient
@AutoConfigureMockMvc(addFilters = false)
class UserAccountControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private UserAccountService userAccountService;

    @Test
    void getUserList() {
        UserAccountDTO userAccount = new UserAccountDTO();
        userAccount.setUserName("test");
        userAccount.setFirstName("test");
        userAccount.setLastName("test");
        userAccount.setPassword("test");
        List<UserAccountDTO> userAccountList = new ArrayList<>();
        userAccountList.add(userAccount);

        when(userAccountService.getAllUserAccounts()).thenReturn(userAccountList);

        restTestClient.get()
                .uri("/api/users")
                .exchange()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].userName").isEqualTo("test")
                .jsonPath("$[0].firstName").isEqualTo("test")
                .jsonPath("$[0].lastName").isEqualTo("test")
                .jsonPath("$[0].password").isEqualTo("test");
    }

    @Test
    void updateUser() {
        UserAccountWithoutUserNameDTO updateDTO = new UserAccountWithoutUserNameDTO();
        updateDTO.setFirstName("test");
        updateDTO.setLastName("test");
        updateDTO.setPassword("test");

        restTestClient.patch()
                .uri("/api/users/{id}", 1)
                .body(updateDTO)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
