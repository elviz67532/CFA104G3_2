package com.product_question.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.util.SQLUtil;

public class ProductQuestionDAOJDBCImpl implements ProductQuestionDAO {
	private static final String GET_ALL_STMT = "select * from PRODUCT_QUESTION order by PRODQ_ID";
	private static final String GET_ONE_STMT = "select * from PRODUCT_QUESTION where PRODQ_ID = ?";
	private static final String INSERT_STMT = "insert into PRODUCT_QUESTION"
			+ "(PRODQ_ID, PRODQ_MEM_ID, PRODQ_PROD_ID, PRODQ_PROMCONTENT, PRODQ_REPCONTENT, PRODQ_PROMDATE, PRODQ_REPDATE) "
			+ "values (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update PRODUCT_QUESTION set "
			+ "PRODQ_PROMCONTENT = ?, PRODQ_REPCONTENT = ?, PRODQ_PROMDATE = ?, PRODQ_REPDATE = ? "
			+ "where PRODQ_ID = ?";
	private static final String DELETE = "delete from PRODUCT_QUESTION where PRODQ_ID = ?";

	static {
		try {
			Class.forName(SQLUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(ProductQuestionVO vo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int insertedRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, vo.getId());
			pstmt.setInt(2, vo.getMemberId());
			pstmt.setInt(3, vo.getProductId());
			pstmt.setString(4, vo.getProblem());
			pstmt.setString(5, vo.getReply());
			pstmt.setTimestamp(6, vo.getProblemDate());
			pstmt.setTimestamp(7, vo.getReplyDate());

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
	public int update(ProductQuestionVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, vo.getProblem());
			pstmt.setString(2, vo.getReply());
			pstmt.setTimestamp(3, vo.getProblemDate());
			pstmt.setTimestamp(4, vo.getReplyDate());
			pstmt.setInt(5, vo.getId());

			updateRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return updateRow;
	}

	@Override
	public ProductQuestionVO selectById(Integer id) {
		ProductQuestionVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new ProductQuestionVO();
				vo.setId(rs.getInt("PRODQ_ID"));
				vo.setMemberId(rs.getInt("PRODQ_MEM_ID"));
				vo.setProductId(rs.getInt("PRODQ_PROD_ID"));
				vo.setProblem(rs.getString("PRODQ_PROMCONTENT"));
				vo.setReply(rs.getString("PRODQ_REPCONTENT"));
				vo.setProblemDate(rs.getTimestamp("PRODQ_PROMDATE"));
				vo.setReplyDate(rs.getTimestamp("PRODQ_REPDATE"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return vo;
	}

	@Override
	public List<ProductQuestionVO> selectAll() {
		List<ProductQuestionVO> list = new ArrayList<ProductQuestionVO>();
		ProductQuestionVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new ProductQuestionVO();
				vo.setId(rs.getInt("PRODQ_ID"));
				vo.setMemberId(rs.getInt("PRODQ_MEM_ID"));
				vo.setProductId(rs.getInt("PRODQ_PROD_ID"));
				vo.setProblem(rs.getString("PRODQ_PROMCONTENT"));
				vo.setReply(rs.getString("PRODQ_REPCONTENT"));
				vo.setProblemDate(rs.getTimestamp("PRODQ_PROMDATE"));
				vo.setReplyDate(rs.getTimestamp("PRODQ_REPDATE"));
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
