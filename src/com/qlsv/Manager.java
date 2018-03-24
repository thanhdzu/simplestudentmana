package com.qlsv;

import com.qlsv.Student;

import java.nio.channels.ShutdownChannelGroupException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import com.qlsv.DAO;

public class Manager {
	DAO dao = new DAO();

	public void add() {
		Student st = new Student();
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("ID: ");

			st.setId(scanner.nextLine());

			System.out.println("Name: ");
			st.setName(scanner.nextLine());

			System.out.println("Address: ");
			st.setAddress(scanner.nextLine());

			System.out.println("Age: ");
			st.setAge(scanner.nextInt());

			System.out.println("GPA: ");
			st.setGpa(scanner.nextFloat());

		} catch (Exception e) {
			System.out.println("Error! Please try again!");
			add();
		}

		String sql = "insert into student(id, name, age, address, gpa) values(?,?,?,?,?)";
		try {
			PreparedStatement pst = dao.getConnection().prepareStatement(sql);
			pst.setString(1, st.getId());
			pst.setString(2, st.getName());
			pst.setInt(3, st.getAge());
			pst.setString(4, st.getAddress());
			pst.setFloat(5, st.getGpa());
			pst.executeUpdate();
			System.out.println("insert successfuly!");
			pst.close();
		} catch (SQLException e) {
			System.out.println("Error! Please try again!");
			add();
			e.printStackTrace();
		}
		dao.closeConnect();
	}

	public void delete(String id) {
		String sql = "delete from student where id = ?";
		DAO dao = new DAO();
		try {
			PreparedStatement pst = dao.getConnection().prepareStatement(sql);
			pst.setString(1, id);
			pst.executeUpdate();
			System.out.println("Delete successhuly!");
			pst.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void infoStudent(Student st) throws SQLException {
		if (st != null) {
			System.out.format("%5s | ", st.getId());
			System.out.format("%35s | ", st.getName());
			System.out.format("%5d | ", st.getAge());
			System.out.format("%30s | ", st.getAddress());
			System.out.format("%.1f |%n", st.getGpa());
		} else {
			System.out.println("NOT FOUND!");
		}
	}

	public static Student infoST(String id) throws SQLException {
		String info = "select name, age, address, gpa  from student where id=?";
		DAO dao = new DAO();
		Student st = null;
		PreparedStatement ps = dao.getConnection().prepareStatement(info);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			String idd = id;
			String name = rs.getString("name");
			int age = rs.getInt("age");
			String address = rs.getString("address");
			float gpa = rs.getFloat("gpa");
			st = new Student(idd, name, age, address, gpa);
		}
		return st;
	}

	public void edit(String id) throws SQLException {
		Student st = new Student();
		if (infoST(id) == null) {
			System.out.println("NOT FOUND!");
		} else {
			infoStudent(infoST(id));

			Scanner scanner = new Scanner(System.in);
			try {
				st.setId(id);

				System.out.println("Name: ");
				st.setName(scanner.nextLine());

				System.out.println("Address: ");
				st.setAddress(scanner.nextLine());

				System.out.println("Age: ");
				st.setAge(scanner.nextInt());

				System.out.println("GPA: ");
				st.setGpa(scanner.nextFloat());

			} catch (Exception e) {
				System.out.println("Error! Please try again!");
				edit(id);
			}

			String sql = "update student set name=?, age=?, address=?, gpa=? where id=?";
			try {
				PreparedStatement pst = dao.getConnection().prepareStatement(sql);
				pst.setString(5, st.getId());
				pst.setString(1, st.getName());
				pst.setInt(2, st.getAge());
				pst.setString(3, st.getAddress());
				pst.setFloat(4, st.getGpa());
				pst.executeUpdate();
				System.out.println("update successfuly!");
				pst.close();
			} catch (SQLException e) {
				System.out.println("Error! Please try again!");
				add();
				e.printStackTrace();
			}
			dao.closeConnect();
		}
	}

	public void searchID(String id) throws SQLException {
		Student st = new Student();
		if (infoST(id) == null) {
			System.out.println("NOT FOUND!");
		} else {
			infoStudent(infoST(id));
		}
	}

	public static ArrayList<Student> searchNameList(String fullname) throws SQLException {
		ArrayList<Student> lst = new ArrayList<Student>();
		String sql = "select * from student where name like ?";
		DAO dao = new DAO();
		PreparedStatement ps = dao.getConnection().prepareStatement(sql);
		ps.setString(1, fullname);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String id = rs.getString("id");
			String name = rs.getString("name");
			Byte age = rs.getByte("age");
			String address = rs.getString("address");
			float gpa = rs.getFloat("gpa");
			Student st = new Student(id, name, age, address, gpa);
			lst.add(st);
		}
		return lst;
	}

	public void searchName(String fullname) throws SQLException {
		ArrayList<Student> list = null;
		list = searchNameList(fullname);
		if (list == null) {
			System.out.println("NOT FOUND!");
		} else {
			for (Student st : list) {
				System.out.format("%5s | ", st.getId());
				System.out.format("%35s | ", st.getName());
				System.out.format("%5d | ", st.getAge());
				System.out.format("%30s | ", st.getAddress());
				System.out.format("%.1f |%n", st.getGpa());
			}
		}
	}

	public ArrayList<Student> ListStudent() throws SQLException {
		ArrayList<Student> lst = new ArrayList<Student>();
		String sql = "select * from student";
		DAO dao = new DAO();
		ResultSet rs = dao.getConnection().createStatement().executeQuery(sql);
		while (rs.next()) {
			String id = rs.getString("id");
			String name = rs.getString("name");
			Byte age = rs.getByte("age");
			String address = rs.getString("address");
			float gpa = rs.getFloat("gpa");
			Student st = new Student(id, name, age, address, gpa);
			lst.add(st);
		}
		return lst;
	}

	public void show() {
		ArrayList<Student> list = null;
		try {
			list = ListStudent();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Student st : list) {
			System.out.format("%5s | ", st.getId());
			System.out.format("%35s | ", st.getName());
			System.out.format("%5d | ", st.getAge());
			System.out.format("%30s | ", st.getAddress());
			System.out.format("%.1f |%n", st.getGpa());
		}
	}

	
	// TEST
/*	public static void main(String[] args) throws SQLException {
		Manager mana = new Manager();
		//mana.add();
		// mana.delete("2");
		 mana.show();
		//mana.searchName("van thanh");
		// mana.edit("5");
		// mana.searchID("2");
		// mana.infoStudent(infoST("5"));
		mana.delete("2");
		mana.show();
	}*/
}
