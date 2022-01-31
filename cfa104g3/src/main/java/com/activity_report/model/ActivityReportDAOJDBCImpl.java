package com.activity_report.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.dao.CoreDao;
import core.util.SQLUtil;

public class ActivityReportDAOJDBCImpl implements ActivityReportDao{
	private static final String GET_ALL_STMT = "select * from ACTIVITY_COMPLAINTS";
	private static final String GET_ONE_STMT = "select * from ACTIVITY_COMPLAINTS where ACTC_ID = ?";
	private static final String INSERT_STMT = "insert into ACTIVITY_COMPLAINTS"
			+ "(ACTC_ACT_ID, ACTC_MEM_ID, ACTC_CONTENT, ACTC_RESULT, ACTC_PHOTO)"
			+ "values (?, ?, ?, ?, ?)";
	private static final String DELETE = "delete from ACTIVITY_COMPLAINTS where ACTC_ID = ?";
	private static final String UPDATE = "update ACTIVITY_COMPLAINTS set ACTC_ACT_ID = ?, ACTC_MEM_ID = ?, ACTC_CONTENT = ?, ACTC_RESULT = ?, ACTC_PHOTO = ? where ACTC_ID = ? ";
	
	static {
		try {
			Class.forName(SQLUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(ActivityReportVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertedRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, vo.getActivityId());
			pstmt.setInt(2, vo.getMemberId());
			pstmt.setString(3, vo.getContent());
			pstmt.setInt(4, vo.getStatus());
			pstmt.setBytes(5, vo.getPhoto());

			insertedRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return insertedRow;
	}

	@Override
	public int deleteById(Integer id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int deleteRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, id);

			deleteRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return deleteRow;
	}

	@Override
	public int update(ActivityReportVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		int updateRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
			
		 	pstmt.setInt(1, vo.getActivityId());
			pstmt.setInt(2, vo.getMemberId());
			pstmt.setString(3, vo.getContent());
			pstmt.setInt(4, vo.getStatus());
			pstmt.setBytes(5, vo.getPhoto());
			pstmt.setInt(6, vo.getId());
			
			updateRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}
		return updateRow;
	}

	@Override
	public ActivityReportVO selectById(Integer id) {
		ActivityReportVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new ActivityReportVO();
				vo.setId(rs.getInt("ACTC_ID"));
				vo.setActivityId(rs.getInt("ACTC_ACT_ID"));
				vo.setMemberId(rs.getInt("ACTC_MEM_ID"));
				vo.setContent(rs.getString("ACTC_CONTENT"));
				vo.setStatus(rs.getInt("ACTC_RESULT"));
				vo.setPhoto(rs.getBytes("ACTC_PHOTO"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return vo;
	}

	@Override
	public List<ActivityReportVO> selectAll() {
		List<ActivityReportVO> list = new ArrayList<ActivityReportVO>();
		ActivityReportVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new ActivityReportVO();
				vo.setId(rs.getInt("ACTC_ID"));
				vo.setActivityId(rs.getInt("ACTC_ACT_ID"));
				vo.setMemberId(rs.getInt("ACTC_MEM_ID"));
				vo.setContent(rs.getString("ACTC_CONTENT"));
				vo.setStatus(rs.getInt("ACTC_RESULT"));
				vo.setPhoto(rs.getBytes("ACTC_PHOTO"));
				list.add(vo);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}
		return list;
	}
}
