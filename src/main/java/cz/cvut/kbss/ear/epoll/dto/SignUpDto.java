package cz.cvut.kbss.ear.epoll.dto;

import javax.validation.constraints.*;

public class SignUpDto {

    @NotEmpty
    @Pattern(regexp = "^(?=.{3,20}$)(?:[a-zA-Z\\d]+(?:[.\\-_][a-zA-Z\\d])*)+$")
    private String username;

    @NotEmpty
    @Size(min = 6, max = 256)
    private String password;

    @Email
    @Size(min = 4, max = 256)
    private String email;

    @NotEmpty
    @Pattern(regexp = "([+]?\\d{1,3}[. \\s]?)?(\\d{9}?)")
    private String phoneNumber;

    @Min(0)
    @Max(200)
    private int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
