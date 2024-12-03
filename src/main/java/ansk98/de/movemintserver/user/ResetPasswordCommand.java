package ansk98.de.movemintserver.user;

import jakarta.validation.constraints.NotNull;

public record ResetPasswordCommand(@NotNull String email, @NotNull String password) {
}
