package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cos.hello.config.DBConn;
import com.cos.hello.dto.JoinDto;
import com.cos.hello.dto.LoginDto;
import com.cos.hello.model.Users;

public class UsersDao {
	
	public int delete (Users user) {
		String sql = "DELETE FROM users WHERE id = ?";
		Connection conn = DBConn.getInstance();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user.getId());
			
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int update (Users user) {
		
		String sql = "UPDATE users SET password = ? , email = ? WHERE id = ?";
		Connection conn = DBConn.getInstance();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getEmail());
			pstmt.setInt(3, user.getId());
			
			int result = pstmt.executeUpdate();
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public Users selectById(int id) {
		
		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션(동기화)
		sb.append("SELECT id, password, username, email FROM users ");
		sb.append("WHERE id = ?");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				Users userEntity = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.password(rs.getString("password"))
						.email(rs.getString("email"))
						.build();
				return userEntity;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public int insert(JoinDto joinDto) {

		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션(동기화)
		sb.append("INSERT INTO users(username, password, email) ");
		sb.append("VALUES(?, ?, ?)");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, joinDto.getUsername());
			pstmt.setString(2, joinDto.getPassword());
			pstmt.setString(3, joinDto.getEmail());
			int result = pstmt.executeUpdate(); // 변경된 행의 개수를 리턴

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public Users login(LoginDto loginDto) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT id, username, email FROM users ");
		sb.append("WHERE username = ? AND password = ?");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginDto.getUsername());
			pstmt.setString(2, loginDto.getPassword());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Users userEntity = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.build();

				return userEntity;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
