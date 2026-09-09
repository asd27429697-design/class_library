package com.tenco.dto;

//id int auto_increment primary key,
//book_id int,
//student_id int,
//borrow_date date not null,
//return_date date,
//foreign key(book_id) references books(id),
//foreign key(student_id) references students(id)

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Borrow {
    private int id;
    private int bookId;
    private int studentId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String title;
}
