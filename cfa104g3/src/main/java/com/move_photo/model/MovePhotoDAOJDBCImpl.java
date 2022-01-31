package com.move_photo.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.dao.CoreDao;
import core.util.SQLUtil;

public class MovePhotoDAOJDBCImpl implements MovePhotoDAO {
	private static final String GET_ALL_STMT = "select * from MOVE_PHOTO order by MOP_ID";
	private static final String GET_ONE_STMT = "select * from MOVE_PHOTO where MOP_ID = ?";
	private static final String INSERT_STMT = "insert into MOVE_PHOTO(MOP_MS_ID, MS_PHOTO) values(?, ?)";
	private static final String DELETE_STMT = "delete from MOVE_PHOTO where MOP_ID = ?";
	private static final String UPDATE_STMT = "update MOVE_PHOTO set MOP_MS_ID = ?, MS_PHOTO = ? where MOP_ID = ?";

	static {
		try {
			Class.forName(SQLUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(MovePhotoVO movePhotoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertedRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, movePhotoVO.getMoveRequestId());
			pstmt.setBytes(2, movePhotoVO.getPhoto());

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
			pstmt = con.prepareStatement(DELETE_STMT);

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
	public int update(MovePhotoVO movePhotoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setInt(1, movePhotoVO.getMoveRequestId());
			pstmt.setBytes(2, movePhotoVO.getPhoto());
			pstmt.setInt(3, movePhotoVO.getId());

			updateRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return updateRow;
	}

	@Override
	public MovePhotoVO selectById(Integer id) {
		MovePhotoVO movePhotoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				movePhotoVO = new MovePhotoVO();
				movePhotoVO.setId(rs.getInt("MOP_ID"));
				movePhotoVO.setMoveRequestId(rs.getInt("MOP_MS_ID"));
				movePhotoVO.setPhoto(rs.getBytes("MS_PHOTO"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return movePhotoVO;
	}

	@Override
	public List<MovePhotoVO> selectAll() {
		List<MovePhotoVO> list = new ArrayList<MovePhotoVO>();
		MovePhotoVO movePhotoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				movePhotoVO = new MovePhotoVO();
				movePhotoVO.setId(rs.getInt("MOP_ID"));
				movePhotoVO.setMoveRequestId(rs.getInt("MOP_MS_ID"));
				movePhotoVO.setPhoto(rs.getBytes("MS_PHOTO"));
				list.add(movePhotoVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}
		return list;
	}
}