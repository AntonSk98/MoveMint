package ansk98.de.movemintserver;

import ansk98.de.movemintserver.auth.AuthenticateUserCommand;
import ansk98.de.movemintserver.auth.AuthenticationDto;
import ansk98.de.movemintserver.auth.RegisterUserCommand;
import ansk98.de.movemintserver.user.IUserService;
import ansk98.de.movemintserver.user.UserDetails;
import ansk98.de.movemintserver.user.UserDetailsParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class IntegrationTestSupport {

    static final String LOGIN_PATH = "/auth/login";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    ResultActions requestAnonymous(MockHttpServletRequestBuilder requestBuilder) {
        try {
            return mockMvc.perform(requestBuilder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ResultActions requestAs(String identity, MockHttpServletRequestBuilder requestBuilder) {
        AuthenticateUserCommand authenticateUserCommand = new AuthenticateUserCommand(identity, "");
        try {
            AuthenticationDto authenticationDto = toObject(
                    mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_PATH)
                            .content(toJson(authenticateUserCommand))
                            .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString(),
                    AuthenticationDto.class
            );

            return mockMvc.perform(requestBuilder.contentType(MediaType.APPLICATION_JSON)
                            .header(AUTHORIZATION, "Bearer " + authenticationDto.accessToken().token()))
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void createUser(String identity) {
        createUser(identity, ZoneId.systemDefault());
    }

    void createUser(String identity, ZoneId zoneId) {
        RegisterUserCommand command = new RegisterUserCommand(
                identity,
                "",
                new UserDetailsParams(
                        identity,
                        LocalDate.now().minusYears(RandomUtils.secure().randomInt(20, 70)),
                        RandomUtils.secure().randomBoolean() ? UserDetails.Gender.MALE : UserDetails.Gender.FEMALE,
                        RandomUtils.secure().randomInt(140, 200),
                        RandomUtils.secure().randomInt(40, 100),
                        zoneId
                )
        );

        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/auth/register")
                            .content(toJson(command))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void deleteUser(String identity) {
        userService.deleteUser(identity);
    }

    String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    <T> T toObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
