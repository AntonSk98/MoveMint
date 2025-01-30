package ansk98.de.movemintserver;

import ansk98.de.movemintserver.user.ResetPasswordCommand;
import ansk98.de.movemintserver.user.UpdateUserCommand;
import ansk98.de.movemintserver.user.UserDetails;
import ansk98.de.movemintserver.user.UserDetailsParams;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Integration tests that cover user-related functionality.
 *
 * @author Anton Skripin (anton.tech98gmail.com)
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest extends IntegrationTestSupport {

    private static final String USER_ANTON = "anton.test@gmail.com";
    private static final String USER_INTERNAL = "internal.test@gmail.com";
    private static final String USER_TEST = "test.test@gmail.com";
    public static final String UPDATE_USER_PATH = "/public/user/update";
    public static final String FIND_USER_SUB_PATH = "/public/user/find/";
    public static final String RESET_PASSWORD_PATH = "/public/user/reset-password";

    @Test
    public void findAuthenticatedUserTest() throws Exception {
        createUser(USER_ANTON);
        createUser(USER_INTERNAL);

        requestAs(USER_ANTON, MockMvcRequestBuilders.get(FIND_USER_SUB_PATH + USER_ANTON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identity").value(USER_ANTON))
                .andExpect(jsonPath("$.userDetailsParams").exists());

        requestAs(USER_ANTON, MockMvcRequestBuilders.get(FIND_USER_SUB_PATH + "invalidEmail"))
                .andExpect(status().isBadRequest());

        requestAs(USER_INTERNAL, MockMvcRequestBuilders.get(FIND_USER_SUB_PATH + USER_ANTON))
                .andExpect(status().isBadRequest());

        requestAnonymous(MockMvcRequestBuilders.get(FIND_USER_SUB_PATH + USER_ANTON))
                .andExpect(status().isForbidden());

        deleteUser(USER_ANTON);
        deleteUser(USER_INTERNAL);
    }

    @Test
    public void updateUserTest() throws Exception {
        createUser(USER_ANTON);
        createUser(USER_TEST);

        final String newName = "newName";
        final LocalDate newDateOfBirth = LocalDate.of(2002, 1, 1);
        final UserDetails.Gender newGender = UserDetails.Gender.MALE;
        final int newHeight = 180;
        final int newWeight = 85;
        final ZoneId newTimeZone = ZoneId.of("Europe/Moscow");

        var userDetails = new UserDetailsParams(newName, newDateOfBirth, newGender, newHeight, newWeight, newTimeZone);

        UpdateUserCommand updateUserCommand = new UpdateUserCommand(USER_ANTON, userDetails);

        requestAs(USER_ANTON, MockMvcRequestBuilders.put(UPDATE_USER_PATH)
                .content(toJson(updateUserCommand))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identity").value(USER_ANTON))
                .andExpect(jsonPath("$.userDetailsParams.name").value(newName))
                .andExpect(jsonPath("$.userDetailsParams.dateOfBirth").value(newDateOfBirth.toString()))
                .andExpect(jsonPath("$.userDetailsParams.gender").value(newGender.name()))
                .andExpect(jsonPath("$.userDetailsParams.height").value(newHeight))
                .andExpect(jsonPath("$.userDetailsParams.weight").value(newWeight))
                .andExpect(jsonPath("$.userDetailsParams.timezone").value(newTimeZone.toString()));

        // test validation
        UpdateUserCommand notValidEmailCommand = new UpdateUserCommand("abc.", userDetails);
        requestAs(USER_ANTON, MockMvcRequestBuilders.put(UPDATE_USER_PATH)
                .content(toJson(notValidEmailCommand)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        UpdateUserCommand notValidUserDetailsCommand = new UpdateUserCommand(USER_TEST,
                new UserDetailsParams(null, LocalDate.now(), null, 100, 50, null));

        requestAs(USER_TEST, MockMvcRequestBuilders.put(UPDATE_USER_PATH).content(toJson(notValidUserDetailsCommand)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // a user can update only their profile
        UpdateUserCommand userCommand = new UpdateUserCommand(USER_TEST, userDetails);
        requestAs(USER_ANTON, MockMvcRequestBuilders.put(UPDATE_USER_PATH).content(toJson(userCommand)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // anonymous user should not be able to update users
        requestAnonymous(MockMvcRequestBuilders.get(UPDATE_USER_PATH).content(toJson(userCommand)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        deleteUser(USER_TEST);
        deleteUser(USER_ANTON);
    }

    @Test
    public void resetPasswordTest() throws Exception {
        createUser(USER_ANTON);
        createUser(USER_TEST);

        final String newPassword = "newPassword";

        Function<ResetPasswordCommand, MockHttpServletRequestBuilder> requestBuilder = resetPasswordCommand -> MockMvcRequestBuilders
                .post(RESET_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(resetPasswordCommand));

        BiFunction<String, ResetPasswordCommand, ResultActions> resetPassword = (identity, resetPasswordCommand) ->
                requestAs(identity, requestBuilder.apply(resetPasswordCommand));

        // old password does not match
        ResetPasswordCommand resetPasswordCommandFalseOldPassword = new ResetPasswordCommand(USER_ANTON, "falsePassword", newPassword);
        resetPassword.apply(USER_ANTON, resetPasswordCommandFalseOldPassword).andExpect(status().isBadRequest());

        // user is not allowed to reset password for another user
        ResetPasswordCommand resetPasswordCommand = new ResetPasswordCommand(USER_ANTON, "", newPassword);
        resetPassword.apply(USER_TEST, resetPasswordCommand).andExpect(status().isBadRequest());

        // validation
        ResetPasswordCommand invalidCommand = new ResetPasswordCommand(USER_ANTON, "invalidPassword", null);
        resetPassword.apply(USER_TEST, invalidCommand).andExpect(status().isBadRequest());

        // reset password
        resetPassword.apply(USER_ANTON, resetPasswordCommand).andExpect(status().isOk());

        // anonymous user cannot reset a password
        requestAnonymous(requestBuilder.apply(resetPasswordCommand)).andExpect(status().isForbidden());

        deleteUser(USER_TEST);
        deleteUser(USER_ANTON);
    }
}
