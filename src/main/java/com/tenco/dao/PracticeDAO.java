package com.tenco.dao;

import com.tenco.dto.Student;
import com.tenco.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PracticeDAO {

    public List<Student> getAllStudent() {
        List<Student> studentList = new ArrayList<>();

        String sql = """
                SELECT *
                FROM students
                """;

        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = createStudent(rs);

                studentList.add(student);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return studentList;
    }

    private static Student createStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setStudentId(rs.getString("student_id"));
        return student;
    }
}
