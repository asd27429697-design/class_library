package com.tenco.dao;

import com.tenco.dto.Admin;
import com.tenco.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 관리자 관련 SQL을 실행하는 DAO 클래스
public class AdminDAO {

    // 관리자 id로 관리자 한명을 조회

    // 비밀번호는 SQL에서 비교하지 않고 DB애 저장된 값을 그대로 가져오게 합니다.
    // "입력한 비밀번호가 맞는가"는 업무 규칙이므로 Service 에게 판단을 시킬 예정이다.
    // 이렇게 하면 추후 나중에 비밀번호 암호화(해시처리) 바꿀 때 크게 변경할 부분이 없어집니다.
    public Admin findByAdminId(String adminId) {

        String sql = """
                 select id, admin_id, password, name
                 from admins
                 where admin_id = ?                 
                 """;

        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, adminId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Admin.builder()
                            .id(rs.getInt("id"))
                            .adminId(rs.getString("admin_id"))
                            .password(rs.getString("password"))
                            .name(rs.getString("name"))
                            .build();
                }

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // 관리자 등록 기능 추가
    public void addAdmin(Admin admin) {
        String sql = """
                INSERT INTO admins(admin_id, password, name)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, admin.getAdminId());
            pstmt.setString(2, admin.getPassword());
            pstmt.setString(3, admin.getName());
            // 쿼리 실행
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // 테스트 코드 작성
    public static void main(String[] args) throws SQLException {
        AdminDAO adminDAO = new AdminDAO();
        // DAO 단계에서 해싱 처리를 하지 않고 단순히 값 INSERT 가 되는지 확인한다.
        Admin admin = Admin.builder()
                .adminId("admin5")
                .name("티모관리자")
                .password("123")
                .build();
        adminDAO.addAdmin(admin);
    }



}