package com.product_order.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.util.SQLUtil;

public class ProductOrderDAOJDBCImpl implements ProductOrderDAO {
	private static final String GET_ALL_STMT = "select * from PRODUCT_ORDER order by PRODO_ID";
	private static final String GET_ONE_STMT = "select * from PRODUCT_ORDER where PRODO_ID = ?";
	private static final String INSERT_STMT = "insert into PRODUCT_ORDER"
			+ "(PRODO_ID, PRODO_PROD_ID, PRODO_MBUY_ID, PRODO_MSELL_ID, PRODO_NAME, PRODO_MOBILE, PRODO_ADDRESS, PRODO_DATE, PRODO_AMOUNT, PRODO_STATUS, PRODO_SUM) "
			+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String DELETE = "delete from PRODUCT_ORDER where PRODO_ID = ?";
	private static final String UPDATE = "update PRODUCT_ORDER set "
			+ "PRODO_NAME = ?, PRODO_MOBILE = ?, PRODO_ADDRESS = ?, PRODO_DATE = ?, PRODO_AMOUNT = ?, PRODO_STATUS = ?, PRODO_SUM = ? "
			+ "where PRODO_ID = ?";

	static {
		try {
			Class.forName(SQLUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(ProductOrderVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertedRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, vo.getId());
			pstmt.setInt(2, vo.getProductId());
			pstmt.setInt(3, vo.getCustomerMemberId());
			pstmt.setInt(4, vo.getSellerMemberId());
			pstmt.setString(5, vo.getProductName());
			pstmt.setString(6, vo.getPhone());
			pstmt.setString(7, vo.getAddress());
			pstmt.setTimestamp(8, vo.getDate());
			pstmt.setInt(9, vo.getAmountOfProduct());
			pstmt.setInt(10, vo.getStatus());
			pstmt.setInt(11, vo.getAmountOfPrice());

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
	public int update(ProductOrderVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, vo.getProductName());
			pstmt.setString(2, vo.getPhone());
			pstmt.setString(3, vo.getAddress());
			pstmt.setTimestamp(4, vo.getDate());
			pstmt.setInt(5, vo.getAmountOfProduct());
			pstmt.setInt(6, vo.getStatus());
			pstmt.setInt(7, vo.getAmountOfPrice());
			pstmt.setInt(8, vo.getId());

			updateRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return updateRow;
	}

	@Override
	public ProductOrderVO selectById(Integer id) {
		ProductOrderVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new ProductOrderVO();
				vo.setId(rs.getInt("PRODO_ID"));
				vo.setProductId(rs.getInt("PRODO_PROD_ID"));
				vo.setCustomerMemberId(rs.getInt("PRODO_MBUY_ID"));
				vo.setSellerMemberId(rs.getInt("PRODO_MSELL_ID"));
				vo.setProductName(rs.getString("PRODO_NAME"));
				vo.setPhone(rs.getString("PRODO_MOBILE"));
				vo.setAddress(rs.getString("PRODO_ADDRESS"));
				vo.setDate(rs.getTimestamp("PRODO_DATE"));
				vo.setAmountOfProduct(rs.getInt("PRODO_AMOUNT"));
				vo.setStatus(rs.getInt("PRODO_STATUS"));
				vo.setAmountOfPrice(rs.getInt("PRODO_SUM"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return vo;
	}

	@Override
	public List<ProductOrderVO> selectAll() {
		List<ProductOrderVO> list = new ArrayList<ProductOrderVO>();
		ProductOrderVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new ProductOrderVO();
				vo.setId(rs.getInt("PRODO_ID"));
				vo.setProductId(rs.getInt("PRODO_PROD_ID"));
				vo.setCustomerMemberId(rs.getInt("PRODO_MBUY_ID"));
				vo.setSellerMemberId(rs.getInt("PRODO_MSELL_ID"));
				vo.setProductName(rs.getString("PRODO_NAME"));
				vo.setPhone(rs.getString("PRODO_MOBILE"));
				vo.setAddress(rs.getString("PRODO_ADDRESS"));
				vo.setDate(rs.getTimestamp("PRODO_DATE"));
				vo.setAmountOfProduct(rs.getInt("PRODO_AMOUNT"));
				vo.setStatus(rs.getInt("PRODO_STATUS"));
				vo.setAmountOfPrice(rs.getInt("PRODO_SUM"));
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
