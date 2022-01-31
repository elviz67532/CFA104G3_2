package com.activity_question.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.dao.CoreDao;
import core.util.SQLUtil;

public class ActivityQuestionDAOJDBCImpl implements ActivityQuestionDAO{
	private static final String GET_ALL_STMT = "select * from ACTIVITY_QUESTION";
	private static final String GET_ONE_STMT = "select * from ACTIVITY_QUESTION where ACTQ_ID = ?";
	private static final String INSERT_STMT = "insert into ACTIVITY_QUESTION"
			+ "(ACTQ_ACT_ID, ACTQ_MEM_ID, ACTQ_PROMCONTENT, ACTQ_REPCONTENT, ACTQ_PROMDATE, ACTQ_REPDATE)"
			+ "values (?, ?, ?, ?, ?, ?)";
	private static final String DELETE = "delete FROM ACTIVITY_QUESTION where ACTQ_ID = ?";
	private static final String UPDATE = "update ACTIVITY_QUESTION set "
			+ "ACTQ_ACT_ID = ?, ACTQ_MEM_ID = ?, ACTQ_PROMCONTENT = ?, ACTQ_REPCONTENT = ?, ACTQ_PROMDATE = ?, ACTQ_REPDATE = ? "
			+ "where ACTQ_ID = ?";
	
	static {
		try {
			Class.forName(SQLUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int insert(ActivityQuestionVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertedRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, vo.getActivityId());
			pstmt.setInt(2, vo.getMemberId());
			pstmt.setString(3, vo.getProblem());
			pstmt.setString(4, vo.getReply());
			pstmt.setTimestamp(5, vo.getProblemDate());
			pstmt.setTimestamp(6, vo.getReplyDate());
			
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
	public int update(ActivityQuestionVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		int updateRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, vo.getActivityId());
			pstmt.setInt(2, vo.getMemberId());
			pstmt.setString(3, vo.getProblem());
			pstmt.setString(4, vo.getReply());
			pstmt.setTimestamp(5, vo.getProblemDate());
			pstmt.setTimestamp(6, vo.getReplyDate());
			pstmt.setInt(7, vo.getId());
			
			updateRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}
		
		return updateRow;
	}
	
	@Override
	public ActivityQuestionVO selectById(Integer id) {
		ActivityQuestionVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new ActivityQuestionVO();
				vo.setId(rs.getInt("ACTQ_ID"));
				vo.setActivityId(rs.getInt("ACTQ_ACT_ID"));
				vo.setMemberId(rs.getInt("ACTQ_MEM_ID"));
				vo.setProblem(rs.getString("ACTQ_PROMCONTENT"));
				vo.setReply(rs.getString("ACTQ_REPCONTENT"));
				vo.setProblemDate(rs.getTimestamp("ACTQ_PROMDATE"));
				vo.setReplyDate(rs.getTimestamp("ACTQ_REPDATE"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return vo;
	}
	
	@Override
	public List<ActivityQuestionVO> selectAll() {
		List<ActivityQuestionVO> list = new ArrayList<ActivityQuestionVO>();
		ActivityQuestionVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new ActivityQuestionVO();
				vo.setId(rs.getInt("ACTQ_ID"));
				vo.setActivityId(rs.getInt("ACTQ_ACT_ID"));
				vo.setMemberId(rs.getInt("ACTQ_MEM_ID"));
				vo.setProblem(rs.getString("ACTQ_PROMCONTENT"));
				vo.setReply(rs.getString("ACTQ_REPCONTENT"));
				vo.setProblemDate(rs.getTimestamp("ACTQ_PROMDATE"));
				vo.setReplyDate(rs.getTimestamp("ACTQ_REPDATE"));
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
