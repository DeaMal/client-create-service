package shop.household.client.create.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
@Entity
@Table(
        name = "client", schema = "shop"
//        , uniqueConstraints = @UniqueConstraint(columnNames = {"firstname", "lastname"})
)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "phone_extra")
    private String phone_extra;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "create_at", nullable = false)
    private Timestamp create_at;

    public Client() {
    }
}
