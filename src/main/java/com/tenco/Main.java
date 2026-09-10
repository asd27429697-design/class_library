package com.tenco;



import com.tenco.view.LibraryView;

import java.sql.SQLException;

// 프로그램 시작점
public class Main {
    public static void main(String[] args) throws SQLException {

        LibraryView libraryView = new LibraryView();
        libraryView.start();

    }
}
