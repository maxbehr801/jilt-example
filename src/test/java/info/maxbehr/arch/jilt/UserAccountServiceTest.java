package info.maxbehr.arch.jilt;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class UserAccountServiceTest {

    private static final Instant NOW = Instant.parse("2026-11-24T10:15:30Z");

    private final UserAccountService testSubject =
            new UserAccountService(Clock.fixed(NOW, ZoneOffset.UTC));

    @Test
    void registerSetsAllAttributes() {
        UserAccount account =
                testSubject.register("jdoe", "jdoe@example.com", "John", "Doe", "+49 30 1234567");

        assertThat(account.id()).isNotNull();
        assertThat(account)
                .returns("jdoe", UserAccount::username)
                .returns("jdoe@example.com", UserAccount::email)
                .returns("John", UserAccount::firstName)
                .returns("Doe", UserAccount::lastName)
                .returns(true, UserAccount::active)
                .returns(false, UserAccount::emailVerified)
                .returns(NOW, UserAccount::createdAt)
                .returns("+49 30 1234567", UserAccount::phoneNumber)
                .returns(null, UserAccount::lastLoginAt);
    }

    @Test
    void registerAcceptsMissingPhoneNumber() {
        UserAccount account = testSubject.register("jdoe", "jdoe@example.com", "John", "Doe", null);

        assertThat(account.phoneNumber()).isNull();
    }

    @Test
    void registerGeneratesUniqueIds() {
        UserAccount first = testSubject.register("a", "a@example.com", "A", "A", null);
        UserAccount second = testSubject.register("b", "b@example.com", "B", "B", null);

        assertThat(first.id()).isNotEqualTo(second.id());
    }
}
