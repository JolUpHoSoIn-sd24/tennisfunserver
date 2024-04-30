package joluphosoin.tennisfunserver.user.data.dto;

import jakarta.validation.constraints.*;

public class RegistrationDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Password is required")
    // You can add regex for password constraints and additional messages if you like
    private String password;

    @NotNull(message = "NTRP rating is required")
    @DecimalMin(value = "1.0", message = "NTRP rating must be at least 1.0")
    @DecimalMax(value = "7.0", message = "NTRP rating must be at most 7.0")
    private double ntrp;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Must be at least 18 years old")
    @Max(value = 100, message = "Must be at most 100 years old")
    private int age;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "MALE|FEMALE", message = "Gender must be either MALE or FEMALE")
    private String gender;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getNtrp() {
        return ntrp;
    }

    public void setNtrp(double ntrp) {
        this.ntrp = ntrp;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}