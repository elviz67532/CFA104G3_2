package com.move_order.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.util.SQLUtil;

public class MoveOrderDAOJDBCImpl implements MoveOrderDAO {
	private static final String GET_ALL_STMT = "select * from MOVE_ORDER ORDER by OS_ID";
	private static final String GET_ONE_STMT = "select * from MOVE_ORDER WHERE OS_ID = ?";
	private static final String INSERT_STMT = "insert into MOVE_ORDER"
			+ "(OS_MEM_ID, OS_NAME, OS_PHONE, OS_FROMADDRESS, OS_TOADDRESS, OS_MOVETIME, OS_AMOUNTFIRST, OS_DEPOSIT, OS_AMOUNTTOTAL, OS_COMMENT, OS_DATE, OS_STATUS) "
			+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update MOVE_ORDER set "
			+ "OS_MEM_ID = ?, OS_NAME = ?, OS_PHONE = ?, OS_FROMADDRESS = ?, OS_TOADDRESS = ?, OS_MOVETIME = ?, OS_AMOUNTFIRST = ?, OS_DEPOSIT = ?, OS_AMOUNTTOTAL = ?, OS_COMMENT = ?, OS_DATE = ?, OS_STATUS = ? "
			+ "where OS_ID = ?";
	private static final String DELETE = "delete from MOVE_ORDER where OS_ID = ?";

	static {
		try {
			Class.forName(SQLUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(MoveOrderVO moveOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertedRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, moveOrderVO.getMemberId());
			pstmt.setString(2, moveOrderVO.getCustomer());
			pstmt.setString(3, moveOrderVO.getPhone());
			pstmt.setString(4, moveOrderVO.getFromAddress());
			pstmt.setString(5, moveOrderVO.getToAddress());
			pstmt.setTimestamp(6, moveOrderVO.getMoveDate());
			pstmt.setInt(7, moveOrderVO.getAmountFirst());
			pstmt.setInt(8, moveOrderVO.getDeposit());
			pstmt.setInt(9, moveOrderVO.getAmountTotal());
			pstmt.setString(10, moveOrderVO.getComment());
			pstmt.setTimestamp(11, moveOrderVO.getOrderDate());
			pstmt.setInt(12, moveOrderVO.getStatus());

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
	public int update(MoveOrderVO moveOrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, moveOrderVO.getMemberId());
			pstmt.setString(2, moveOrderVO.getCustomer());
			pstmt.setString(3, moveOrderVO.getPhone());
			pstmt.setString(4, moveOrderVO.getFromAddress());
			pstmt.setString(5, moveOrderVO.getToAddress());
			pstmt.setTimestamp(6, moveOrderVO.getMoveDate());
			pstmt.setInt(7, moveOrderVO.getAmountFirst());
			pstmt.setInt(8, moveOrderVO.getDeposit());
			pstmt.setInt(9, moveOrderVO.getAmountTotal());
			pstmt.setString(10, moveOrderVO.getComment());
			pstmt.setTimestamp(11, moveOrderVO.getOrderDate());
			pstmt.setInt(12, moveOrderVO.getStatus());
			pstmt.setInt(13, moveOrderVO.getId());
					
			updateRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return updateRow;
	}

	@Override
	public MoveOrderVO selectById(Integer id) {
		MoveOrderVO moveOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				moveOrderVO = new MoveOrderVO();
				moveOrderVO.setId(rs.getInt("OS_ID"));
				moveOrderVO.setMemberId(rs.getInt("OS_MEM_ID"));
				moveOrderVO.setFromAddress(rs.getString("OS_FROMADDRESS"));
				moveOrderVO.setToAddress(rs.getString("OS_TOADDRESS"));
				moveOrderVO.setCustomer(rs.getString("OS_NAME"));
				moveOrderVO.setPhone(rs.getString("OS_PHONE"));
				moveOrderVO.setMoveDate(rs.getTimestamp("OS_MOVETIME"));
				moveOrderVO.setAmountFirst(rs.getInt("OS_AMOUNTFIRST"));
				moveOrderVO.setDeposit(rs.getInt("OS_DEPOSIT"));
				moveOrderVO.setAmountTotal(rs.getInt("OS_AMOUNTTOTAL"));
				moveOrderVO.setComment(rs.getString("OS_COMMENT"));
				moveOrderVO.setOrderDate(rs.getTimestamp("OS_DATE"));
				moveOrderVO.setStatus(rs.getInt("OS_STATUS"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}
		
		return moveOrderVO;
	}

	@Override
	public List<MoveOrderVO> selectAll() {
		List<MoveOrderVO> list = new ArrayList<MoveOrderVO>();
		MoveOrderVO moveOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				moveOrderVO = new MoveOrderVO();
				moveOrderVO.setId(rs.getInt("OS_ID"));
				moveOrderVO.setMemberId(rs.getInt("OS_MEM_ID"));
				moveOrderVO.setFromAddress(rs.getString("OS_FROMADDRESS"));
				moveOrderVO.setToAddress(rs.getString("OS_TOADDRESS"));
				moveOrderVO.setCustomer(rs.getString("OS_NAME"));
				moveOrderVO.setPhone(rs.getString("OS_PHONE"));
				moveOrderVO.setMoveDate(rs.getTimestamp("OS_MOVETIME"));
				moveOrderVO.setAmountFirst(rs.getInt("OS_AMOUNTFIRST"));
				moveOrderVO.setDeposit(rs.getInt("OS_DEPOSIT"));
				moveOrderVO.setAmountTotal(rs.getInt("OS_AMOUNTTOTAL"));
				moveOrderVO.setComment(rs.getString("OS_COMMENT"));
				moveOrderVO.setOrderDate(rs.getTimestamp("OS_DATE"));
				moveOrderVO.setStatus(rs.getInt("OS_STATUS"));
				list.add(moveOrderVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}
		return list;
	}
}
