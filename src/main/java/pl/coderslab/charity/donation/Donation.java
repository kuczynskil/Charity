package pl.coderslab.charity.donation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import pl.coderslab.charity.appuser.AppUser;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.organization.Organization;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    private List<Category> categories;
    @ManyToOne
    private Organization organization;
    @ManyToOne
    private AppUser appUser;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdOn;

    @PositiveOrZero
    private int quantity;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String zipCode;
    private LocalTime pickUpTime;
    private String pickUpComment;
    @NotBlank
    @NumberFormat
    private String telephoneNumber;
    private boolean pickedUp;
}
