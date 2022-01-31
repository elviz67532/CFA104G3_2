package com.server_manager.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.util.SQLUtil;

public class ServerManagerDAOJDBCImpl implements ServerManagerDAO {

	public static final String INSERT_STMT = "INSERT INTO SERVERMANAGER (SMGR_EMAIL, SMGR_ACCOUNT, SMGR_PASSWORD, SMGR_NAME, "
			+ "SMGR_PHONE, SMGR_GENDER, SMGR_ADDRESS) VALUES (?,?,?,?,?,?,?)";
	public static final String UPDATE_STMT = "UPDATE SERVERMANAGER SET SMGR_EMAIL=?, SMGR_ACCOUNT=?, SMGR_PASSWORD=?,"
			+ "SMGR_NAME=?, SMGR_PHONE=?, SMGR_GENDER=?, SMGR_ADDRESS=? WHERE SMGR_ID=?";
	public static final String DELETE_STMT = "DELETE FROM SERVERMANAGER WHERE SMGR_ID=?";
	public static final String FIND_BY_PRIMARY_KEY = "SELECT * FROM SERVERMANAGER WHERE SMGR_ID=?";
	public static final String GET_ALL = "SELECT * FROM SERVERMANAGER";

	static {
		try {
			Class.forName(SQLUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(ServerManagerVO serverManagerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, serverManagerVO.getSmgrEmail());
			pstmt.setString(2, serverManagerVO.getSmgrAccount());
			pstmt.setString(3, serverManagerVO.getSmgrPassword());
			pstmt.setString(4, serverManagerVO.getSmgrName());
			pstmt.setString(5, serverManagerVO.getSmgrPhone());
			pstmt.setInt(6, serverManagerVO.getSmgrGender());
			pstmt.setString(7, serverManagerVO.getSmgrAddress());

			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}
		
		return row;
	}

	@Override
	public int update(ServerManagerVO serverManagerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int row = 0;
		
		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, serverManagerVO.getSmgrEmail());
			pstmt.setString(2, serverManagerVO.getSmgrAccount());
			pstmt.setString(3, serverManagerVO.getSmgrPassword());
			pstmt.setString(4, serverManagerVO.getSmgrName());
			pstmt.setString(5, serverManagerVO.getSmgrPhone());
			pstmt.setInt(6, serverManagerVO.getSmgrGender());
			pstmt.setString(7, serverManagerVO.getSmgrAddress());
			pstmt.setInt(8, serverManagerVO.getSmgrId());

			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}
		
		return row;
	}

	@Override
	public int deleteById(Integer smgrId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int row = 0;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, smgrId);

			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}
		
		return row;
	}

	@Override
	public ServerManagerVO selectById(Integer smgrId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ServerManagerVO serverManagerVO = null;
		
		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PRIMARY_KEY);
			pstmt.setInt(1, smgrId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				serverManagerVO = new ServerManagerVO();
				serverManagerVO.setSmgrId(rs.getInt("SMGR_ID"));
				serverManagerVO.setSmgrEmail(rs.getString("SMGR_EMAIL"));
				serverManagerVO.setSmgrAccount(rs.getString("SMGR_ACCOUNT"));
				serverManagerVO.setSmgrPassword(rs.getString("SMGR_PASSWORD"));
				serverManagerVO.setSmgrName(rs.getString("SMGR_NAME"));
				serverManagerVO.setSmgrPhone(rs.getString("SMGR_PHONE"));
				serverManagerVO.setSmgrGender(rs.getInt("SMGR_GENDER"));
				serverManagerVO.setSmgrAddress(rs.getString("SMGR_ADDRESS"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return serverManagerVO;
	}

	@Override
	public List<ServerManagerVO> selectAll() {
		List<ServerManagerVO> list = new ArrayList<ServerManagerVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ServerManagerVO serverManagerVO = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				serverManagerVO = new ServerManagerVO();
				serverManagerVO.setSmgrId(rs.getInt("SMGR_ID"));
				serverManagerVO.setSmgrEmail(rs.getString("SMGR_EMAIL"));
				serverManagerVO.setSmgrAccount(rs.getString("SMGR_ACCOUNT"));
				serverManagerVO.setSmgrPassword(rs.getString("SMGR_PASSWORD"));
				serverManagerVO.setSmgrName(rs.getString("SMGR_NAME"));
				serverManagerVO.setSmgrPhone(rs.getString("SMGR_PHONE"));
				serverManagerVO.setSmgrGender(rs.getInt("SMGR_GENDER"));
				serverManagerVO.setSmgrAddress(rs.getString("SMGR_ADDRESS"));
				list.add(serverManagerVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return list;
	}
}
