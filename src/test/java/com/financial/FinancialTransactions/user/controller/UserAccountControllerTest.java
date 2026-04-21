package com.financial.FinancialTransactions.user.controller;

import com.financial.FinancialTransactions.user.dto.UserAccountGetDTO;
import com.financial.FinancialTransactions.user.dto.UserAccountUpdateDTO;
import com.financial.FinancialTransactions.user.sevice.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(UserAccountController.class)
@AutoConfigureRestTestClient
@AutoConfigureMockMvc(addFilters = false)
class UserAccountControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private UserAccountService userAccountService;

    @Test
    void getUserList() {
        UserAccountGetDTO userAccount = new UserAccountGetDTO();
        userAccount.setUserName("test");
        userAccount.setFirstName("test");
        userAccount.setLastName("test");
        userAccount.setPassword("test");
        List<UserAccountGetDTO> userAccountList = new ArrayList<>();
        userAccountList.add(userAccount);

        when(userAccountService.getAllUserAccounts()).thenReturn(userAccountList);

        restTestClient.get()
                .uri("/api/users")
                .exchange()
                .expectBody(String.class)
                .isEqualTo("[{\"userName\":\"test\",\"firstName\":\"test\",\"lastName\":\"test\",\"password\":\"test\"}]");
    }

    @Test
    void createNewUser() {
        UserAccountGetDTO userAccount = new UserAccountGetDTO();
        userAccount.setUserName("test");
        userAccount.setFirstName("test");
        userAccount.setLastName("test");
        userAccount.setPassword("test");

        restTestClient.post()
                .uri("/api/users")
                .body(userAccount)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void updateUser() {
        UserAccountUpdateDTO updateDTO = new UserAccountUpdateDTO();
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
