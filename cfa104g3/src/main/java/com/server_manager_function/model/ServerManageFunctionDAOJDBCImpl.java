package com.server_manager_function.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.util.SQLUtil;

public class ServerManageFunctionDAOJDBCImpl implements ServerManageFunctionDAO {
	private static final String GET_ALL_STMT = "select * from SERVERMANAGEFUNCTION";
	private static final String GET_FROM_ID_STMT = "select * from SERVERMANAGEFUNCTION where SMGEFUNC_ID = ?";
	private static final String INSERT_STMT = "insert into SERVERMANAGEFUNCTION (SMGEFUNC_ID, SMGEFUNC) VALUES (?, ?)";
	private static final String DELETE_STMT = "delete from SERVERMANAGEFUNCTION where SMGEFUNC_ID = ?";
	private static final String UPDATE_STMT = "update SERVERMANAGEFUNCTION set SMGEFUNC = ? where SMGEFUNC_ID = ?";

	static {
		try {
			Class.forName(SQLUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(ServerManageFunctionVO serverManageFunctionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int row = 0;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, serverManageFunctionVO.getSmgeFuncId());
			pstmt.setString(2, serverManageFunctionVO.getSmgeFunc());

			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return row;
	}

	@Override
	public int deleteById(Integer smgeFuncId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int row = 0;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, smgeFuncId);

			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return row;
	}

	@Override
	public int update(ServerManageFunctionVO serverManageFunctionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int row = 0;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);

			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, serverManageFunctionVO.getSmgeFunc());
			pstmt.setInt(2, serverManageFunctionVO.getSmgeFuncId());

			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return row;
	}

	@Override
	public ServerManageFunctionVO selectById(Integer smgeFuncId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ServerManageFunctionVO serverManageFunctionVO = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_FROM_ID_STMT);
			pstmt.setInt(1, smgeFuncId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				serverManageFunctionVO = new ServerManageFunctionVO();
				serverManageFunctionVO.setSmgeFuncId(rs.getInt("SMGEFUNC_ID"));
				serverManageFunctionVO.setSmgeFunc(rs.getString("SMGEFUNC"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return serverManageFunctionVO;
	}

	@Override
	public List<ServerManageFunctionVO> selectAll() {
		List<ServerManageFunctionVO> list = new ArrayList<ServerManageFunctionVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ServerManageFunctionVO serverManageFunctionVO = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				serverManageFunctionVO = new ServerManageFunctionVO();
				serverManageFunctionVO.setSmgeFuncId(rs.getInt(1));
				serverManageFunctionVO.setSmgeFunc(rs.getString(2));
				list.add(serverManageFunctionVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return list;
	}
}
