package ansk98.de.movemintserver;

import ansk98.de.movemintserver.auth.AuthenticateUserCommand;
import ansk98.de.movemintserver.auth.AuthenticationDto;
import ansk98.de.movemintserver.auth.IAuthService;
import ansk98.de.movemintserver.auth.RegisterUserCommand;
import ansk98.de.movemintserver.user.IUserRepository;
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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAuthService authService;

    @Autowired
    private IUserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public ResultActions requestAnonymous(MockHttpServletRequestBuilder requestBuilder) {
        try {
            return mockMvc.perform(requestBuilder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultActions requestAs(String identity, MockHttpServletRequestBuilder requestBuilder) {
        AuthenticationDto authenticationDto = authService.login(new AuthenticateUserCommand(identity, ""));

        try {
            return mockMvc.perform(requestBuilder.header(AUTHORIZATION, "Bearer " + authenticationDto.accessToken().token()))
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createUser(String identity) {
        createUser(identity, ZoneId.systemDefault());
    }

    public void createUser(String identity, ZoneId zoneId) {
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

    public void deleteUser(String identity) {
        userRepository.findByIdentity(identity).ifPresent(u -> userRepository.delete(u));
    }

    String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    <T> T asObject(String json, Class<T> clazz) {
        return objectMapper.convertValue(json, clazz);
    }
}
