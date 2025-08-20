package ansk98.de.movemintserver;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZoneId;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ansk98.de.movemintserver.auth.AuthenticateUserCommand;
import ansk98.de.movemintserver.auth.AuthenticationDto;
import ansk98.de.movemintserver.auth.JwtManager;
import ansk98.de.movemintserver.auth.RefreshTokenCommand;
import ansk98.de.movemintserver.auth.RegisterUserCommand;
import ansk98.de.movemintserver.auth.UserClaims;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationIntegrationTest extends IntegrationTestSupport {

    private static final String REGISTER_USER_PATH = "/auth/register";
    private static final String REFRESH_TOKEN_PATH = "/auth/refresh-token";
    private static final String FIND_USER_PATH = "/public/user/find/";

    private static final String TEST_USER_IDENTITY = "test_user@gamil.com";

    @Autowired
    private JwtManager jwtManager;

    @BeforeEach
    public void setUp() {
        createUser(TEST_USER_IDENTITY);
    }

    @AfterEach
    public void tearDown() {
        deleteUser(TEST_USER_IDENTITY);
    }

    @Test
    public void registerUserTest() {
        var createdUser = userService.findUserBy(TEST_USER_IDENTITY);
        Assertions.assertNotNull(createdUser);
        Assertions.assertEquals(TEST_USER_IDENTITY, createdUser.identity());
    }

    @Test
    public void ensureUniqueUserIdentityTest() {
        // attempting to create a user with the same identity should result in the exception.
        RegisterUserCommand command = registerUserCommand(TEST_USER_IDENTITY, ZoneId.systemDefault());

        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .post(REGISTER_USER_PATH)
                            .content(toJson(command))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void loginUserTest() throws Exception {
        AuthenticateUserCommand wrongUserIdentity = new AuthenticateUserCommand("tesk_user@gamil.com", DEFAULT_PASSWORD);
        login(wrongUserIdentity).andExpect(status().isNotFound());

        AuthenticateUserCommand wrongUserPassword = new AuthenticateUserCommand(TEST_USER_IDENTITY, "wrong_password");
        login(wrongUserPassword).andExpect(status().isForbidden());

        AuthenticateUserCommand successfulLogin = new AuthenticateUserCommand(TEST_USER_IDENTITY, DEFAULT_PASSWORD);
        login(successfulLogin).andExpect(status().isOk())
                .andExpect(result -> {
                    AuthenticationDto authenticationDto = toObject(result.getResponse().getContentAsString(), AuthenticationDto.class);
                    Assertions.assertNotNull(authenticationDto);
                    Assertions.assertNotNull(authenticationDto.accessToken());
                    Assertions.assertNotNull(authenticationDto.refreshToken());

                    UserClaims accessTokenUserClaims = jwtManager.accessTokenClaims(authenticationDto.accessToken().token());
                    Assertions.assertEquals(TEST_USER_IDENTITY, accessTokenUserClaims.identity());

                    UserClaims refreshTokenUserClaims = jwtManager.refreshTokenClaims(authenticationDto.refreshToken().token());
                    Assertions.assertEquals(TEST_USER_IDENTITY, refreshTokenUserClaims.identity());

                    testProtectedGetEndpoint(FIND_USER_PATH + TEST_USER_IDENTITY, authenticationDto.accessToken().token());
                });
    }

    @Test
    public void refreshTokenTest() throws Exception {
        AuthenticationDto authentication = toObject(
                login(new AuthenticateUserCommand(TEST_USER_IDENTITY, DEFAULT_PASSWORD))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                AuthenticationDto.class);

        String authenticationJson = mockMvc.perform(MockMvcRequestBuilders.post(REFRESH_TOKEN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new RefreshTokenCommand(authentication.refreshToken().token()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken.token").exists())
                .andExpect(jsonPath("$.refreshToken.token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // try to access some part of an application with a new token
        testProtectedGetEndpoint(FIND_USER_PATH + TEST_USER_IDENTITY, toObject(authenticationJson, AuthenticationDto.class).accessToken().token());
    }

    private ResultActions testProtectedGetEndpoint(String path, String token) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.get(path).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ResultActions login(AuthenticateUserCommand authenticateUserCommand) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_PATH)
                    .content(toJson(authenticateUserCommand))
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
