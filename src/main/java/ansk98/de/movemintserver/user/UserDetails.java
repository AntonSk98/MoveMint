package ansk98.de.movemintserver.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Encapsulates user details.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Embeddable
public class UserDetails {

    @Column(nullable = false)
    @Size(min = 1, max = 100)
    private String name;

    @Column(nullable = false)
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Min(100)
    @Max(250)
    @Column(nullable = false)
    private Integer height;

    @Min(30)
    @Max(200)
    @Column(nullable = false)
    private Integer weight;

    @Column(nullable = false)
    private String timezone;

    /**
     * No-args
     */
    protected UserDetails() {

    }

    private UserDetails(String name,
                        LocalDate dateOfBirth,
                        Gender gender,
                        Integer height,
                        Integer weight,
                        ZoneId timezone) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.timezone = timezone.getId();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public ZoneId getTimezone() {
        return ZoneId.of(timezone);
    }

    public static class Builder {
        private String name;
        private LocalDate dateOfBirth;
        private Gender gender;
        private Integer height;
        private Integer weight;
        private ZoneId timezone;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder height(Integer height) {
            this.height = height;
            return this;
        }

        public Builder weight(Integer weight) {
            this.weight = weight;
            return this;
        }

        public UserDetails build() {
            return new UserDetails(name, dateOfBirth, gender, height, weight, timezone);
        }

        public Builder timezone(ZoneId timezone) {
            this.timezone = timezone;
            return this;
        }
    }

    public enum Gender {
        MALE, FEMALE
    }
}
