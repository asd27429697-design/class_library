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
@Builder
// 도서 대출 기록을 담는 DTO
// DTO는 테이블과 꼭 1:1 로 맞출 필요가 없습니다.
// SQL 실행 결과를 담는 그릇 임으로 JOIN 으로 가져온 컬럼 결과도 담을 수 있습니다.
public class Borrow {
    private int id;
    private int bookId;
    private int studentId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String bookTitle;
    private String studentName;
}
