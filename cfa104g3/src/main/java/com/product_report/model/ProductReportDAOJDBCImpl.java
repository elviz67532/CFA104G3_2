package com.product_report.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.DualKey;
import core.util.SQLUtil;

public class ProductReportDAOJDBCImpl implements ProductReportDAO {
	private static final String GET_ALL_STMT = "select * from PRODCUT_REPORT";
	private static final String GET_ONE_STMT = "select * from PRODCUT_REPORT where PRODRP_PROD_ID = ? and PRODRP_MEM_ID = ?";
	private static final String INSERT_STMT = "insert into PRODCUT_REPORT"
			+ "(PRODRP_PROD_ID, PRODRP_MEM_ID, PRODRP_CONTENT, PRODRP_DATE, PRODRP_PHOTO, PRODRP_STATUS) "
			+ "values (?, ?, ?, ?, ?, ?)";
	private static final String DELETE = "delete from PRODCUT_REPORT where PRODRP_PROD_ID = ? and PRODRP_MEM_ID = ?";
	private static final String UPDATE = "UPDATE PRODCUT_REPORT set "
			+ "PRODRP_CONTENT = ?, PRODRP_DATE = ?, PRODRP_PHOTO = ?, PRODRP_STATUS = ? "
			+ "where PRODRP_MEM_ID = ? and PRODRP_PROD_ID = ?";

	static {
		try {
			Class.forName(SQLUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(ProductReportVO productReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertedRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, productReportVO.getProductId());
			pstmt.setInt(2, productReportVO.getMemberId());
			pstmt.setString(3, productReportVO.getContent());
			pstmt.setTimestamp(4, productReportVO.getDate());
			pstmt.setBytes(5, productReportVO.getPhoto());
			pstmt.setInt(6, productReportVO.getStatus());

			insertedRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return insertedRow;
	}

	@Override
	public int update(ProductReportVO productReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, productReportVO.getContent());
			pstmt.setTimestamp(2, productReportVO.getDate());
			pstmt.setBytes(3, productReportVO.getPhoto());
			pstmt.setInt(4, productReportVO.getStatus());
			pstmt.setInt(5, productReportVO.getMemberId());
			pstmt.setInt(6, productReportVO.getProductId());
			
			updateRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return updateRow;
	}

	@Override
	public List<ProductReportVO> selectAll() {
		List<ProductReportVO> list = new ArrayList<ProductReportVO>();
		ProductReportVO productReportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				productReportVO = new ProductReportVO();
				productReportVO.setProductId(rs.getInt("PRODRP_PROD_ID"));
				productReportVO.setMemberId(rs.getInt("PRODRP_MEM_ID"));
				productReportVO.setContent(rs.getString("PRODRP_CONTENT"));
				productReportVO.setDate(rs.getTimestamp("PRODRP_DATE"));
				productReportVO.setPhoto(rs.getBytes("PRODRP_PHOTO"));
				productReportVO.setStatus(rs.getInt("PRODRP_STATUS"));
				list.add(productReportVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return list;
	}

	@Override
	public ProductReportVO selectById(DualKey<Integer, Integer> id) {
		ProductReportVO productReportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, id.getK1());
			pstmt.setInt(2, id.getK2());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				productReportVO = new ProductReportVO();
				productReportVO.setProductId(rs.getInt("PRODRP_PROD_ID"));
				productReportVO.setMemberId(rs.getInt("PRODRP_MEM_ID"));
				productReportVO.setContent(rs.getString("PRODRP_CONTENT"));
				productReportVO.setDate(rs.getTimestamp("PRODRP_DATE"));
				productReportVO.setPhoto(rs.getBytes("PRODRP_PHOTO"));
				productReportVO.setStatus(rs.getInt("PRODRP_STATUS"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return productReportVO;
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
}
