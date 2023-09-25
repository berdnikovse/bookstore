package com.example.bookstore.models;
import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Users implements CommonEntity<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "nickname")
    @NonNull
    private String nickname;

    @Column(nullable = false, name = "password")
    @NonNull
    private String password;

    @Column(nullable = false, name = "status")
    @NonNull
    private String status;


    /*public Users (Long id, String nickname, String password, String status) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.status = status;
    }*/
    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long aLong) {

    }

    public String getPassword() {
        return password;
    }
    public boolean checkPrivelege() {
        return Objects.equals(status, "admin");
    }
}