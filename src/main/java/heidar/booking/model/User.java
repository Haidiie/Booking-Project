package heidar.booking.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private int id;

    @Column(unique = true)
    @Email(regexp = "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)", message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "You need to enter a password!")
    @Size(min = 6, message = "Minimum 6 characters")
    private String password;

    @NotBlank(message = "You need to enter your first name")
    private String firstName;

    @NotBlank(message = "You need to enter your last name")
    private String lastName;

    private String role;




    public User() {
    }

    public User(@Email(regexp = "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)", message = "Please enter a valid email address") String email, @NotBlank(message = "You need to enter a password!") @Size(min = 6, message = "Minimum 6 characters") String password, @NotBlank(message = "You need to enter your first name") String firstName, @NotBlank(message = "You need to enter your last name") String lastName, String role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
