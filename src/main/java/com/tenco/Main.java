package com.tenco;

import com.tenco.dao.BookDAO;
import com.tenco.dao.BorrowDAO;
import com.tenco.dao.StudentDAO;
import com.tenco.dto.Book;
import com.tenco.dto.Borrow;
import com.tenco.dto.Student;

import java.util.List;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {
    public static void main(String[] args) {

//
//         // 학생 전체 조회 테스트
//        StudentDAO studentDAO = new StudentDAO();
//        Student student = studentDAO.getStudentByStudentId("20230003");
//        System.out.println(student);
//
//        // 도서 전체 조회 테스트
//        BookDAO bookDAO = new BookDAO();
////        BookDAO bookDAO = new BookDAO("테스트3", "저자3",
////                "출판사 이름", 2025, "1212321");
//        // Book book = new Book("테스트");
//        Book book = Book.builder()
//                .title("테스트3")
//                .author("저자3")
//                .publisher("코리아출판")
//                .publicationYear(2026)
//                .isbn("1212324155")
//                .build();
////                bookDAO.addBook(book);
//
////        List<Book> bookList = bookDAO.getAllBooks();
//        List<Book> bookList = bookDAO.searchBookByTitle("알고리즘");
//        System.out.println("전체 조회된 row 수: "+ bookList.size());
//        System.out.println("0번째 저장된 Book 객체 정보 출력 해보기 " + bookList.get(0).toString());
        BorrowDAO borrowDAO = new BorrowDAO();

        List<Borrow> borrowList = borrowDAO.getBorrowedBooks();
        for (int i = 0; i < borrowList.size(); i++) {
            System.out.println(borrowList.get(i).toString());
        }

//        borrowDAO.borrowBook(1,1);

        borrowDAO.returnBook(5,100);

        }
    }


