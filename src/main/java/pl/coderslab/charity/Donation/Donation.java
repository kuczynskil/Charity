package pl.coderslab.charity.Donation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.charity.Category.Category;
import pl.coderslab.charity.Organization.Organization;

import javax.persistence.*;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;

    private int quantity;
    private String street;
    private String city;
    private String zipCode;
    private LocalTime pickUpTime;
    private String pickUpComment;
    private String telephoneNumber;
}
