package com.tenco.dto;

import lombok.*;

//id int auto_increment primary key,
//title varchar(255) not null,
//author varchar(255) not null,
//publisher varchar(255) not null,
//publication_year int,
//isbn  varchar(13),
//available boolean default true

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Book {
    private int id;
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private String isbn;
    private boolean available;

}
