package com.move_request.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.dao.CoreDao;
import core.util.SQLUtil;

/**
 * If you can't see content below, it means your encoding isn't utf-8.
 * ------------------------------------------------------------------ CFA104G3
 * DAO範例 本份類別會有大量註解，撰寫自己的類別時請減少使用註解
 * 
 * @author Elviz
 */
// 實現CoreDao介面，泛型填入對應的VO類別及Integer
public class MoveRequestDAOJDBCImpl implements MoveRequestDAO {
	// 定義基本操作方式，注意內容是否符合自己的表格
	// 為方便檢視, 將欄位、where換行
	private static final String GET_ALL_STMT = "select * from MOVE_REQUEST order by MS_ID";
	private static final String GET_ONE_STMT = "select * from MOVE_REQUEST where MS_ID = ?";
	private static final String INSERT_STMT = "insert into MOVE_REQUEST"
			+ "(MS_MEM_ID, MS_FROMADDRESS, MS_TOADDRESS, MS_EVATIME, MS_OBJECT, MS_EVAPRICE, MS_MOVETIME, MS_EVALUATE, MS_DATE, MS_STATUS, MS_HANDLE) "
			+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String DELETE = "delete from MOVE_REQUEST where MS_ID = ?";
	private static final String UPDATE = "update MOVE_REQUEST set "
			+ "MS_FROMADDRESS = ?, MS_TOADDRESS = ?, MS_EVATIME = ?, MS_OBJECT = ?, MS_EVAPRICE = ?, MS_MOVETIME = ?, MS_EVALUATE = ?, MS_DATE = ?, MS_STATUS = ?, MS_HANDLE = ? "
			+ "where MS_ID = ?";

	// 將class載入動作統一至static內
	static {
		try {
			Class.forName(SQLUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 回傳insert的row
	 */
	@Override
	public int insert(MoveRequestVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertedRow;

		try {
			// SQL參數一律使用SqlUtil處理
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			// 注意參數擺放是否正確
			pstmt.setInt(1, vo.getMemberId());
			pstmt.setString(2, vo.getFromAddress());
			pstmt.setString(3, vo.getToAddress());
			pstmt.setTimestamp(4, vo.getEvauateDate());
			pstmt.setString(5, vo.getItem());
			pstmt.setInt(6, vo.getEvauatePrice());
			pstmt.setTimestamp(7, vo.getMoveDate());
			pstmt.setInt(8, vo.getEvaluateType());
			pstmt.setTimestamp(9, vo.getRequestDate());
			pstmt.setString(10, vo.getStatus());
			pstmt.setBoolean(11, vo.getHandled());

			insertedRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			// 使用SQL工具處理
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
	public int update(MoveRequestVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, vo.getFromAddress());
			pstmt.setString(2, vo.getToAddress());
			pstmt.setTimestamp(3, vo.getEvauateDate());
			pstmt.setString(4, vo.getItem());
			pstmt.setInt(5, vo.getEvauatePrice());
			pstmt.setTimestamp(6, vo.getMoveDate());
			pstmt.setInt(7, vo.getEvaluateType());
			pstmt.setTimestamp(8, vo.getRequestDate());
			pstmt.setString(9, vo.getStatus());
			pstmt.setBoolean(10, vo.getHandled());
			pstmt.setInt(11, vo.getId());

			updateRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return updateRow;
	}

	@Override
	public MoveRequestVO selectById(Integer id) {
		MoveRequestVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new MoveRequestVO();
				vo.setId(rs.getInt("MS_ID"));
				vo.setMemberId(rs.getInt("MS_MEM_ID"));
				vo.setFromAddress(rs.getString("MS_FROMADDRESS"));
				vo.setToAddress(rs.getString("MS_TOADDRESS"));
				vo.setEvauateDate(rs.getTimestamp("MS_EVATIME"));
				vo.setItem(rs.getString("MS_OBJECT"));
				vo.setEvauatePrice(rs.getInt("MS_EVAPRICE"));
				vo.setMoveDate(rs.getTimestamp("MS_MOVETIME"));
				vo.setEvaluateType(rs.getInt("MS_EVALUATE"));
				vo.setRequestDate(rs.getTimestamp("MS_DATE"));
				vo.setStatus(rs.getString("MS_STATUS"));
				vo.setHandled(rs.getBoolean("MS_HANDLE"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return vo;
	}

	@Override
	public List<MoveRequestVO> selectAll() {
		List<MoveRequestVO> list = new ArrayList<MoveRequestVO>();
		MoveRequestVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new MoveRequestVO();
				vo.setId(rs.getInt("MS_ID"));
				vo.setMemberId(rs.getInt("MS_MEM_ID"));
				vo.setFromAddress(rs.getString("MS_FROMADDRESS"));
				vo.setToAddress(rs.getString("MS_TOADDRESS"));
				vo.setEvauateDate(rs.getTimestamp("MS_EVATIME"));
				vo.setItem(rs.getString("MS_OBJECT"));
				vo.setEvauatePrice(rs.getInt("MS_EVAPRICE"));
				vo.setMoveDate(rs.getTimestamp("MS_MOVETIME"));
				vo.setEvaluateType(rs.getInt("MS_EVALUATE"));
				vo.setRequestDate(rs.getTimestamp("MS_DATE"));
				vo.setStatus(rs.getString("MS_STATUS"));
				vo.setHandled(rs.getBoolean("MS_HANDLE"));
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
