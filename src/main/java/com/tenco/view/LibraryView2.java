package com.tenco.view;

import com.tenco.dto.Book;
import com.tenco.dto.Student;
import com.tenco.service.LibraryService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

// 사용자의 입출력을 처리하는 View 클래스

// [역활]
// 키보드에 입력을 받아 Service 에 넘기고, 결과를 화면에 출력한다.
// SQL을 직접 실행하지 않고, 업무 규칙을 판단하지 않습니다.
// "빈 값인가", "숫자인가" 같은 입력 형식을 검사하고 서비스단에 맞는 객체내 값을 구해서 일을 위임한다.
public class LibraryView2 {

    private final LibraryService libraryService = new LibraryService();
    private final Scanner scanner = new Scanner(System.in);

    // 현재 로그인한 학생 정보가 null이 아니라면 로그인 된 상태로 보면 된다.
    // 만약 null 이라면 로그인이 필요한 기능에서 로그인 요청을 먼저 유도 해야 한다.
    private Integer currentStudentId = null;
    private String currentStudentName = null;
    private Student currentStudent = null;

    // 프로그램 메인 루프
    // [처리순서]
    // 1. 메뉴를 출력한다
    // 2. 번호를 입력 받는다
    // 3. 번호에 맞는 메서드를 호출한다.
    // 4. 호출 중 SQLException 이 나면 에러 메세지를 출력하고 다시 1번으로 돌아간다.
    // 5. 0번을 입력하면 프로그램 종료 또는 return으로 루프를 빠져 나간다.


    //            // 메뉴 출력
//            // 사용자 입력값 받기
//
    // 예외 처리가 반드시 필요 하다.
//            switch ("선택번호") {
//                case 1: 기능 호출
//            }
    public void start() {
        while (true) {
            try {
                System.out.print("선택: ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("메뉴 번호를 입력해주세요.");
                    continue;
                }
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        toggleLogin();
                        break;
                    case 2:
                        showAllBooks();
                        break;
                    case 3:
                        searchBookByTitle();
                        break;
                    case 4:
                        borrowBook();
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 0:
                        System.out.println("도서 관리 프로그램을 종료합니다");
                        return;
                    default:
                        System.out.println("잘못 된 번호 입니다. 다시 입력하세요");
                }

            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 다시 입력하세요" + e.getMessage());
            }
            System.out.println();
        }
    }

    //  메뉴 출력
    public void showMenu() {
        System.out.println("=== 도서 관리 시스템 ===");
        if (currentStudent != null) {
            System.out.println("이름: " + currentStudentName + "학번: " + currentStudent);
        } else {
            System.out.println("로그인 상태가 아닙니다");
        }

        if (currentStudent == null) {
            System.out.println("1. 로그인");
        } else {
            System.out.println("1. 로그아웃");
        }

        System.out.println("2.  도서 목록");
        System.out.println("3.  도서 검색");
        System.out.println("4.  도서 대출");
        System.out.println("5.  도서 반납");
        System.out.println("6.  도서 등록");
        System.out.println("0.  종료");
    }
    // 1. 로그인 / 로그아웃
    private void toggleLogin() throws SQLException {
        if (currentStudent != null) {
            System.out.println(currentStudent.getName() + " 학생이 로그아웃 되었습니다.");
            currentStudent = null;
            return;
        }
        System.out.print("로그인할 학번을 입력하세요: ");
        String studentId = scanner.nextLine().trim();

        Student student = libraryService.getStudentByStudentId(studentId);
        if (student != null) {
            this.currentStudent = student;
            System.out.println(student.getName() + "님, 로그인에 성공했습니다!");
        } else {
            System.out.println("오류: 등록되지 않은 학번입니다.");
        }
    }


    // 2.  도서 목록
    private void showAllBooks() throws SQLException {
        List<Book> books = libraryService.getAllBooks();
        System.out.println("=== 도서 목록 ===");
        if (books.isEmpty()) {
            System.out.println("등록된 도서가 없습니다");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            String status;
            if (book.isAvailable()) {
                status = "대출 가능";
            } else {
                status = "대출 중";
            }
            System.out.println("ID: " + book.getId() + " | 제목: " + book.getTitle()
                    + " | 저자: " + book.getAuthor() + " | 상태: " + status);
        }

    }

    // 3. 도서 검색
    private void searchBookByTitle() throws SQLException {
        System.out.print("검색할 도서 제목: ");
        String title = scanner.nextLine().trim();

        List<Book> books = libraryService.searchBooksByTitle(title);
        System.out.println("=== 검색 결과 ===");

        if (books.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            String status;
            if (book.isAvailable()) {
                status = "대출 가능";
            } else {
                status = "대출 중";
            }
            System.out.println("ID: " + book.getId() + " | 제목: " + book.getTitle()
                    + " | 저자: " + book.getAuthor() + " | 상태: " + status);
        }

    }
    // 4. 도서 대출
    private void borrowBook() throws SQLException {
        if (currentStudent == null) {
            throw new SQLException("도서를 대출 할려면 먼저 로그인을 하세요");
        }
        System.out.println("대출할 도서의 id를 입력하세요");

        int bookId = Integer.parseInt(scanner.nextLine().trim());
        libraryService.borrowBook(bookId, currentStudent.getId());
        System.out.println("대출이 완료되었습니다");
    }

    // 5.  도서 반납
    

    // 해당 기능을 각각에 메서드로 설계

    // 기능 1

    // 기능 2

    public static void main(String[] args) {
        // 스캐너를 통해서 하고있는 메뉴를 고를 수 있도록 코드를 작성한다.
        // 만약 1번이 도서 추가라면 해당하는 로직을 수행 할 수 있는 로직을 만든다. .
        //
        // .
        LibraryView2 view = new LibraryView2();
        view.start();
    }
}