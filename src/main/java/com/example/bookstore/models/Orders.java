package com.example.bookstore.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Orders implements CommonEntity<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @NonNull
    private Users user_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    @NonNull
    private Books book_id;

    @Column(nullable = false, name = "book_number")
    @NonNull
    private Integer book_number;

    @Column(nullable = false, name = "delivery_place")
    @NonNull
    private String delivery_place;

    @Column(nullable = false, name = "status")
    private String status;

    /*public Orders(Long id, Users user, Books book, Integer bookNumber, String deliveryPlace, String status) {
        this.id = id;
        this.user_id = user;
        this.book_id = book;
        this.book_number = bookNumber;
        this.delivery_place = deliveryPlace;
        this.status = status;
    }*/

    public Users getUser_id() {
        return this.user_id;
    }
    public String getUser_nickname() {
        return this.user_id.getNickname();
    }
    public void setStatus(String NewStatus) {
        this.status = NewStatus;
    }
    public Books getBook_id() {
        return this.book_id;
    }
    public String getBook_name() {
        return this.book_id.getName();
    }
    public String getStatus() {
        return this.status;
    }
    public String getDelivery() {
        return this.delivery_place;
    }
    public Integer getBook_number() {
        return this.book_number;
    }

}