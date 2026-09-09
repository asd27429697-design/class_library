package com.tenco.dao;

import com.tenco.dto.Borrow;
import com.tenco.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 도서 대출/반납 관련 SQL 실행은 DAO
 */

public class BorrowDAO {




    // 3. 도서 반납 처리 (트랜잭션)
    // 3.1 대출 기록 확인 - SELECT
    // 3.2 반납 기록 등록 - UPDATE


    // 1. 현재 대출 중인 도서 목록 조회
    // 1.2. JOIN 해서 도서 이름 까지 출력
    public List<Borrow> getAllBorrowBooks() {
        List<Borrow> borrowList = new ArrayList<>();

        String sql = """
                SELECT *
                FROM borrows
                """;

        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Borrow borrow = createBorrow(rs);

                borrowList.add(borrow);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return borrowList;
    }

    public List<Borrow> getAllBorrowBooksJoin() {
        List<Borrow> borrowList = new ArrayList<>();

        String sql = """
                select b.*, s.title
                from borrows b
                join books s
                on b.book_id = s.id
                """;

        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Borrow borrow = createBorrow(rs);

                borrowList.add(borrow);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return borrowList;
    }


    private static Borrow createBorrow(ResultSet rs) throws SQLException {
        Borrow borrow = new Borrow();
        borrow.setId(rs.getInt("id"));
        borrow.setBookId(rs.getInt("book_id"));
        borrow.setStudentId(rs.getInt("student_id"));
        borrow.setBorrowDate(rs.getDate("borrow_date").toLocalDate());

        if (rs.getDate("return_date") != null) {
            borrow.setReturnDate(rs.getDate("return_date").toLocalDate());
        }

        borrow.setTitle(rs.getString("title"));

        return borrow;
    }
}





