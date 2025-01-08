package ansk98.de.movemintserver.user;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/find/{username}")
    public UserDto findUser(@PathVariable String username) {
        return userService.findUserBy(username);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody @Valid ResetPasswordCommand command) {
        userService.resetPassword(command);
    }
}
