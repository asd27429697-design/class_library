package com.tenco.dto;

//id int auto_increment primary key,
//name varchar(100) not null,
//student_id varchar(20) not null unique

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
    private int id;
    private String name;
    private String studentId;

    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
    }
}
