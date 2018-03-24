package com.qlsv;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	public static Scanner scanner = new Scanner(System.in);

	public static void Menu() {
		System.out.println("-----menu-----");
		System.out.println("1. ADD");
		System.out.println("2. EDIT BY ID");
		System.out.println("3. DELETE BY ID");
		System.out.println("4. SEARCH BY ID");
		System.out.println("5. SEARCH BY NAME");
		System.out.println("6. LIST");
		System.out.println("0. EXIT");
		System.out.println("--------------");
		System.out.println("PLEASE CHOOSE: ");
	}

	public static void main(String[] args) throws SQLException {
		String choose = null;
		boolean exit = false;
		Manager manager = new Manager();
		int studentid;
		Scanner sc = new Scanner(System.in);
		Menu();

		while (true) {
			choose = scanner.nextLine();
			switch (choose) {
			case "1":
				manager.add();
				break;
			case "2":
				System.out.println("INPUT ID: ");
				manager.edit(sc.nextLine());
				break;
			case "3":
				System.out.println("INPUT ID: ");
				manager.delete(sc.nextLine());
				break;
			case "4":
				System.out.println("INPUT ID: ");
				manager.searchID(sc.nextLine());
				break;
			case "5":
				System.out.println("INPUT NAME: ");
				manager.searchName(sc.nextLine());
				break;
			case "6":
				manager.show();
				break;
			case "0":
				System.out.println("EXITED!");
				break;
			default:
				System.out.println("INVALID! PLEASE CHOOSE AGAIN!: ");
				break;
			}
			if (exit)
				break;
			Menu();
		}
		
	}
}
