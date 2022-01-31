package com.activity_attend.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.DualKey;
import core.util.SQLUtil;

public class ActivityAttendDAOJDBCImpl implements ActivityAttendDAO {
	private static final String GET_ALL_STMT = "select * from ACTIVITY_ATTEND";
	private static final String GET_ONE_STMT = "select * from ACTIVITY_ATTEND where ACTA_MEM_ID = ? and ACTA_ACT_ID = ?";
	private static final String INSERT_STMT = "insert into ACTIVITY_ATTEND"
			+ "(ACTA_MEM_ID, ACTA_ACT_ID, ACTA_RELPY_CONTENT, ACTA_CONTENT_NOTE, ACTA__PAY_STATUS) "
			+ "values (?, ?, ?, ?, ?)";
	private static final String DELETE = "delete from ACTIVITY_ATTEND where ACTA_MEM_ID = ? and ACTA_ACT_ID = ? ";
	private static final String UPDATE = "update ACTIVITY_ATTEND set "
			+ "ACTA_RELPY_CONTENT = ?, ACTA_CONTENT_NOTE = ?, ACTA__PAY_STATUS = ? "
			+ "WHERE ACTA_MEM_ID = ? and ACTA_ACT_ID = ?";

	static {
		try {
			Class.forName(SQLUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(ActivityAttendVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertedRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, vo.getMemberId());
			pstmt.setInt(2, vo.getActivityId());
			pstmt.setString(3, vo.getComment());
			pstmt.setString(4, vo.getNote());
			pstmt.setInt(5, vo.getStatus());

			insertedRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return insertedRow;
	}

	@Override
	public int update(ActivityAttendVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, vo.getComment());
			pstmt.setString(2, vo.getNote());
			pstmt.setInt(3, vo.getStatus());
			pstmt.setInt(4, vo.getMemberId());
			pstmt.setInt(5, vo.getActivityId());

			updateRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return updateRow;
	}

	@Override
	public List<ActivityAttendVO> selectAll() {
		List<ActivityAttendVO> list = new ArrayList<ActivityAttendVO>();
		ActivityAttendVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new ActivityAttendVO();
				vo.setMemberId(rs.getInt("ACTA_MEM_ID"));
				vo.setActivityId(rs.getInt("ACTA_ACT_ID"));
				vo.setComment(rs.getString("ACTA_RELPY_CONTENT"));
				vo.setNote(rs.getString("ACTA_CONTENT_NOTE"));
				vo.setStatus(rs.getInt("ACTA__PAY_STATUS"));
				list.add(vo);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return list;
	}

	@Override
	public int deleteById(DualKey<Integer, Integer> id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int deleteRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, id.getK1());
			pstmt.setInt(2, id.getK2());

			deleteRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return deleteRow;
	}

	@Override
	public ActivityAttendVO selectById(DualKey<Integer, Integer> id) {
		ActivityAttendVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, id.getK1());
			pstmt.setInt(2, id.getK2());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new ActivityAttendVO();
				vo.setMemberId(rs.getInt("ACTA_MEM_ID"));
				vo.setActivityId(rs.getInt("ACTA_ACT_ID"));
				vo.setComment(rs.getString("ACTA_RELPY_CONTENT"));
				vo.setNote(rs.getString("ACTA_CONTENT_NOTE"));
				vo.setStatus(rs.getInt("ACTA_PAY_STATUS"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return vo;
	}
}
