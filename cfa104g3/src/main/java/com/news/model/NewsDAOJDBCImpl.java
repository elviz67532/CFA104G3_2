package com.news.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.dao.CoreDao;
import core.util.SQLUtil;

public class NewsDAOJDBCImpl implements NewsDAO {
	private static final String GET_ALL_STMT = "select * from NEWS order by NEWS_ID";
	private static final String GET_ONE_STMT = "select * from NEWS where NEWS_ID = ?";
	private static final String INSERT_STMT = "insert into NEWS(NEWS_CONTENT, NEWS_IMG, NEWS_TIME, NEWS_TYPE) values(?, ?, ?, ?)";
	private static final String DELETE = "delete from NEWS where NEWS_ID = ?";
	private static final String UPDATE = "update NEWS set NEWS_CONTENT = ?, NEWS_IMG = ?, NEWS_TIME = ?, NEWS_TYPE = ? where NEWS_ID = ?";

	static {
		try {
			Class.forName(SQLUtil.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(NewsVO pojo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertedRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, pojo.getContent());
			pstmt.setBytes(2, pojo.getImage());
			pstmt.setTimestamp(3, pojo.getDate());
			pstmt.setInt(4, pojo.getType());

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
	public int update(NewsVO pojo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateRow;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, pojo.getContent());
			pstmt.setBytes(2, pojo.getImage());
			pstmt.setTimestamp(3, pojo.getDate());
			pstmt.setInt(4, pojo.getType());
			pstmt.setInt(5, pojo.getId());

			updateRow = pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, null);
		}

		return updateRow;
	}

	@Override
	public NewsVO selectById(Integer id) {
		NewsVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new NewsVO();
				vo.setId(rs.getInt("NEWS_ID"));
				vo.setContent(rs.getString("NEWS_CONTENT"));
				vo.setImage(rs.getBytes("NEWS_IMG"));
				vo.setDate(rs.getTimestamp("NEWS_TIME"));
				vo.setType(rs.getInt("NEWS_TYPE"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			SQLUtil.closeResource(con, pstmt, rs);
		}

		return vo;
	}

	@Override
	public List<NewsVO> selectAll() {
		List<NewsVO> list = new ArrayList<NewsVO>();
		NewsVO vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(SQLUtil.URL, SQLUtil.USER, SQLUtil.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new NewsVO();
				vo.setId(rs.getInt("NEWS_ID"));
				vo.setContent(rs.getString("NEWS_CONTENT"));
				vo.setImage(rs.getBytes("NEWS_IMG"));
				vo.setDate(rs.getTimestamp("NEWS_TIME"));
				vo.setType(rs.getInt("NEWS_TYPE"));
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
