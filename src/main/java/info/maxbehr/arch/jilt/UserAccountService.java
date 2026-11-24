package info.maxbehr.arch.jilt;

import org.jspecify.annotations.Nullable;

import java.time.Clock;
import java.util.UUID;

/** Creates {@link UserAccount}s via the Jilt-generated staged builder. */
public class UserAccountService {

    private final Clock clock;

    public UserAccountService(Clock clock) {
        this.clock = clock;
    }

    public UserAccount register(
            String username,
            String email,
            String firstName,
            String lastName,
            @Nullable String phoneNumber) {
        return UserAccountBuilder.userAccount()
                .id(UUID.randomUUID())
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .active(true)
                .emailVerified(false)
                .createdAt(clock.instant())
                .phoneNumber(phoneNumber)
                .build(); // lastLoginAt is @Nullable and therefore optional: a new account has never logged in
    }

    public UserAccount breakNullability() {
        return UserAccountBuilder.userAccount().id(UUID.randomUUID()).username("username").email("mail@mail.asdfgh").firstName("firstname").lastName("lastname").active(true).emailVerified(false).createdAt(null).build();
    }
}
