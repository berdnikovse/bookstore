package com.example.bookstore.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Books implements CommonEntity<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "author")
    private String author;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "cover_type")
    private String cover_type;

    @Column(nullable = false, name = "genre")
    private String genre;

    @Column(nullable = false, name = "size")
    private Integer size;

    @Column(nullable = false, name = "book_number")
    @NonNull
    private Integer book_number;

    @Column(nullable = false, name = "price")
    @NonNull
    private Integer price;

    @Column(nullable = true, name = "annotation")
    private String annotation;

    public void addCopies(Integer added_number) {
        this.book_number += added_number;
    }
    public Integer getBookNumber() {
        return this.book_number;
    }
    public Integer getBookPrice() {
        return this.price;
    }
}