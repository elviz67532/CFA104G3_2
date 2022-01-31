package com.product_collection.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.DualKey;
import core.util.SQLUtil;

public class ProductCollectionDAOJDBCImpl implements ProductCollectionDAO {
	private static final String GET_ALL_STMT = "select * from PRODUCT_COLLECTION";
	private static final String GET_ONE_STMT = "select * from PRODUCT_COLLECTION where PRODC_MEM_ID = ? and PRODC_PROD_ID = ?";
	private static final String INSERT_STMT = "insert into PRODUCT_COLLECTION(PRODC_MEM_ID, PRODC_PROD_ID) values(?,?)";
	private static final String DELETE = "delete from PRODUCT_COLLECTION where PRODC_MEM_ID = ? and PRODC_PROD_ID = ?";

	static {
		try {
			Class.forName(SQLUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(ProductCollectionVO productCollectionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertedRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, productCollectionVO.getMemberId());
			pstmt.setInt(2, productCollectionVO.getProductId());

			insertedRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return insertedRow;
	}

	@Override
	public int update(ProductCollectionVO productCollectionVO) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ProductCollectionVO> selectAll() {
		List<ProductCollectionVO> list = new ArrayList<ProductCollectionVO>();
		ProductCollectionVO productCollectionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				productCollectionVO = new ProductCollectionVO();
				productCollectionVO.setMemberId(rs.getInt("PRODC_MEM_ID"));
				productCollectionVO.setProductId(rs.getInt("PRODC_PROD_ID"));
				list.add(productCollectionVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return list;
	}

	@Override
	public ProductCollectionVO selectById(DualKey<Integer, Integer> id) {
		ProductCollectionVO productCollectionVO = null;
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
				productCollectionVO = new ProductCollectionVO();
				productCollectionVO.setMemberId(rs.getInt("PRODC_MEM_ID"));
				productCollectionVO.setProductId(rs.getInt("PRODC_PROD_ID"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return productCollectionVO;
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
