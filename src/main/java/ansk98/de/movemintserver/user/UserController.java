package ansk98.de.movemintserver.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.web.bind.annotation.*;

import static ansk98.de.movemintserver.auth.AuthenticationUtils.ensureCanActOnBehalfOf;

/**
 * Controller to manage the state of a user.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@RestController
@RequestMapping("/public/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update")
    public UserDto updateUser(@RequestBody @Valid UpdateUserCommand command) {
        return userService.updateUser(command);
    }

    @GetMapping("/find/{identity}")
    public UserDto findUser(@PathVariable @Email String identity) {
        ensureCanActOnBehalfOf(identity);
        return userService.findUserBy(identity);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody @Valid ResetPasswordCommand command) {
        userService.resetPassword(command);
    }
}
