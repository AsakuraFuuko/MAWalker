package walker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class SQLiteCRUD {

	private SQLiteConn connection1;
	private Connection connection;

	public SQLiteCRUD(SQLiteConn connection) {
		try {
			this.connection1 = connection;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Connection getConnection() {
		try {
			this.connection = connection1.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return connection;
	}

	/**
	 * 创建表。
	 * 
	 * @param sql
	 * @return boolean
	 */
	public boolean createTable(String sql) {
		getConnection();
		int start = sql.toLowerCase().indexOf("table");
		int end = sql.indexOf("(");
		String tablename = sql.substring(start + 6, end).trim();
		if (!tabbleIsExist(tablename)) {
			System.out.println(sql);
			Statement stmt = null;
			try {
				stmt = getConnection().createStatement();
				stmt.executeUpdate(sql);
				return true;
			} catch (Exception e) {
				System.out.println("创建指定表时异常 : " + e.getLocalizedMessage());
				connectionRollback(connection);
				return false;
			} finally {
				try {
					stmt.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 向指定表中插入一条数据。
	 * 
	 * @param table
	 *            表名
	 * @param params
	 *            参数数组
	 * @return boolean
	 */
	public boolean insert(String table, String[] params) {
		getConnection();
		Statement stmt = null;
		String sql = "insert into " + table + " values('";
		for (int i = 0; i < params.length; i++) {
			if (i == (params.length - 1)) {
				sql += (params[i] + "');");
			} else {
				sql += (params[i] + "', '");
			}
		}
		System.out.println(sql);
		try {
			stmt = getConnection().createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			System.out.println("向表插入" + table + "数据时异常 : "
					+ e.getLocalizedMessage());
			connectionRollback(connection);
			return false;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 向指定表中插入一条数据。
	 * 
	 * @param table
	 *            表名
	 * @param fields
	 *            列名
	 * @param params
	 *            参数数组
	 * @return boolean
	 */
	public boolean insert(String table, String[] fields, String[] params) {

		getConnection();
		String username = "";
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].equals("username")) {
				username = params[i];
			}
		}
		if (Config.sqlitecrud.getTableCount("config", "username", username) == 0) {
			Statement stmt = null;
			String sql = "insert into " + table + " ('";
			for (int i = 0; i < fields.length; i++) {
				if (i == (fields.length - 1)) {
					sql += (fields[i] + "')values('");
				} else {
					sql += (fields[i] + "', '");
				}
			}
			for (int i = 0; i < params.length; i++) {
				if (i == (params.length - 1)) {
					sql += (params[i] + "');");
				} else {
					sql += (params[i] + "', '");
				}
			}
			System.out.println(sql);
			try {
				stmt = getConnection().createStatement();
				stmt.executeUpdate(sql);
				return true;
			} catch (Exception e) {
				System.out.println("向表插入" + table + "数据时异常 : "
						+ e.getLocalizedMessage());
				connectionRollback(connection);
				return false;
			} finally {
				try {
					stmt.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else
			return update(table, username, "username", fields, params);
	}

	/**
	 * 向指定表中插入一条数据。
	 * 
	 * @param table
	 *            表名
	 * @param fields
	 *            列名
	 * @param params
	 *            参数数组
	 * @return boolean
	 */
	public boolean insertlast(String table, String[] fields, String[] params) {

		getConnection();
		Statement stmt = null;
		String sql = "insert into " + table + " ('";
		for (int i = 0; i < fields.length; i++) {
			if (i == (fields.length - 1)) {
				sql += (fields[i] + "')values('");
			} else {
				sql += (fields[i] + "', '");
			}
		}
		for (int i = 0; i < params.length; i++) {
			if (i == (params.length - 1)) {
				sql += (params[i] + "');");
			} else {
				sql += (params[i] + "', '");
			}
		}
		System.out.println(sql);
		try {
			stmt = getConnection().createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			System.out.println("向表插入" + table + "数据时异常 : "
					+ e.getLocalizedMessage());
			connectionRollback(connection);
			return false;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 修改表中一个元组的数据。
	 * 
	 * @param table
	 *            表名
	 * @param keyParam
	 *            要修改的元组的主键值
	 * @param keyField
	 *            要修改的元组的主键字段名称
	 * @param fields
	 *            要修改的元组的字段名称数组
	 * @param params
	 *            要修改的元组的值数组
	 * @return boolean
	 */
	public boolean update(String table, String keyParam, String keyField,
			String[] fields, String[] params) {

		getConnection();
		Statement stmt = null;
		String sql = "update " + table + " set ";
		for (int i = 0; i < fields.length; i++) {
			if (i == (fields.length - 1)) {
				sql += (fields[i] + "='" + params[i] + "' where " + keyField
						+ "='" + keyParam + "';");
			} else {
				sql += (fields[i] + "='" + params[i] + "', ");
			}
		}
		System.out.println(sql);
		try {
			stmt = getConnection().createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			System.out.println("修改表" + table + "数据时异常 : "
					+ e.getLocalizedMessage());
			connectionRollback(connection);
			return false;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 删除指定表中指定键值的元组。
	 * 
	 * @param table
	 * @param key
	 * @param keyValue
	 * @return boolean
	 */
	public boolean delete(String table, String key, String keyValue) {

		getConnection();
		Statement stmt = null;
		String sql = "delete from " + table + " where " + key + "='" + keyValue
				+ "';";
		System.out.println(sql);
		try {
			stmt = getConnection().createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			System.out.println("删除表" + table + "数据时异常 : "
					+ e.getLocalizedMessage());
			connectionRollback(connection);
			return false;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将一个表中满足指定条件的所有元组以Vector<Vector<Object>>的形式返回
	 * 
	 * @param table
	 * @param key
	 * @param keyValue
	 * @return Vector<Vector<Object>>
	 */
	public Vector<Vector<Object>> selectVector(String table, String key,
			String keyValue) {

		getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		Vector<Vector<Object>> value = new Vector<Vector<Object>>();

		String sql = "select * from " + table + " where " + key + "='"
				+ keyValue + "';";
		System.out.println(sql);
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			int columnCounts = getFieldsCounts(rs);
			while (rs.next()) {
				Vector<Object> valueVector = new Vector<Object>();
				for (int i = 1; i <= columnCounts; i++) {
					valueVector.addElement(rs.getObject(i));
				}
				value.addElement(valueVector);
			}
			return value;
		} catch (Exception e) {
			System.out.println("查询表" + table + "数据时异常 : "
					+ e.getLocalizedMessage());
			return value;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 返回制定sql语句查询的Vector<Vector<Object>>结果集
	 * 
	 * @param sql
	 *            sql语句
	 * @return Vector<Vector<Object>>
	 */
	public Vector<Vector<Object>> selectVector(String sql) {

		getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		Vector<Vector<Object>> value = new Vector<Vector<Object>>();

		System.out.println(sql);
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			int columnCounts = getFieldsCounts(rs);
			while (rs.next()) {
				Vector<Object> valueVector = new Vector<Object>();
				for (int i = 1; i <= columnCounts; i++) {
					valueVector.addElement(rs.getObject(i));
				}
				value.addElement(valueVector);
			}
			return value;
		} catch (Exception e) {
			System.out.println("查询表sql数据时异常 : " + e.getLocalizedMessage());
			return value;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将满足一定条件的指定表中所有元组数据以Object[][]形式返回
	 * 
	 * @param table
	 * @param key
	 * @param keyValue
	 * @return Object[][]
	 */
	public Object[][] selectObject(String table, String key, String keyValue) {

		getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		int columns = getFieldsCounts(table);
		int rows = getTableCount(table, key, keyValue);

		Object[][] tableObject = new Object[rows][columns];

		String sql = "select * from " + table + " where " + key + "='"
				+ keyValue + "';";
		System.out.println(sql);
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next()) {
				for (int j = 0; j < columns; j++) {
					tableObject[i][j] = rs.getObject(j + 1);
				}
				i++;
			}
			return tableObject;
		} catch (Exception e) {
			System.out.println("查询表" + table + "数据时异常 : "
					+ e.getLocalizedMessage());
			return tableObject;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将一个表中所有的元组以Vector<Vector<Object>>的形式返回
	 * 
	 * @param table
	 * @param key
	 * @param keyValue
	 * @return Vector<Vector<Object>>
	 */
	public Vector<Vector<Object>> select(String table) {

		getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		Vector<Vector<Object>> value = new Vector<Vector<Object>>();

		String sql = "select * from " + table + ";";
		System.out.println(sql);
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			int columnCounts = getFieldsCounts(rs);
			while (rs.next()) {
				Vector<Object> valueVector = new Vector<Object>();
				for (int i = 1; i <= columnCounts; i++) {
					valueVector.addElement(rs.getObject(i));
				}
				value.addElement(valueVector);
			}
			return value;
		} catch (Exception e) {
			System.out.println("查询表" + table + "数据时异常 : "
					+ e.getLocalizedMessage());
			return value;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将一个表中第一列以Hashtable<String,Object>的形式返回
	 * 
	 * @param table
	 * @param key
	 * @param keyValue
	 * @return Hashtable<String,Object>
	 */
	public Hashtable<String,Object> selectHashTable(String table, String key, String keyValue) {

		getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		Hashtable<String,Object> value = new Hashtable<String,Object>();
		String sql = "select * from " + table + " where " + key + "='"
				+ keyValue + "';";
		System.out.println(sql);
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCounts = getFieldsCounts(rs);
			while (rs.next()) {
				for (int i = 1; i <= columnCounts; i++) {
					value.put(rsmd.getColumnName(i),rs.getObject(i));
				}
			}
			return value;
		} catch (Exception e) {
			System.out.println("查询表" + table + "数据时异常 : "
					+ e.getLocalizedMessage());
			return value;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将一个表中所有的元组以ResultSet的形式返回
	 * 
	 * @param table
	 * @return ArrayList<String>
	 */
	public ArrayList<String> GetAllUserId(String table) {

		getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "select * from " + table + ";";
		ArrayList<String> value=new ArrayList<String>();
		System.out.println(sql);
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				value.add(rs.getString("username"));
			}
			return value;
		} catch (Exception e) {
			System.out.println("查询表" + table + "数据时异常 : "
					+ e.getLocalizedMessage());
			return value;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将一个表中所有的元组以Object[][]的形式返回
	 * 
	 * @param table
	 * @return Object[][]
	 */
	public Object[][] selectObject(String table) {

		getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		int columns = getFieldsCounts(table);
		int rows = getTableCount(table);

		Object[][] tableObject = new Object[rows][columns];

		String sql = "select * from " + table + ";";
		System.out.println(sql);
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next()) {
				for (int j = 0; j < columns; j++) {
					tableObject[i][j] = rs.getObject(j + 1);
				}
				i++;
			}
			return tableObject;
		} catch (Exception e) {
			System.out.println("查询表" + table + "数据时异常 : "
					+ e.getLocalizedMessage());
			return tableObject;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将一个ResultSet结果集中的所有字段以List形式返回
	 * 
	 * @param resultSet
	 * @return List<String>
	 */
	public List<String> getFields(ResultSet resultSet) {
		List<String> fieldsList = new ArrayList<String>();
		try {
			int columnCounts = resultSet.getMetaData().getColumnCount();
			for (int i = 1; i <= columnCounts; i++) {
				fieldsList.add(resultSet.getMetaData().getColumnName(i));
			}
		} catch (SQLException e) {
			System.out.println("加载表中字段异常 ：" + e.getLocalizedMessage());
			return null;
		}
		return fieldsList;
	}

	/**
	 * 将一个表中的所有字段以List形式返回
	 * 
	 * @param resultSet
	 * @return List<String>
	 */
	public List<String> getFields(String table) {

		getConnection();
		List<String> fieldsList = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from " + table + ";";
		System.out.println(sql);
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			fieldsList = getFields(rs);
		} catch (Exception e) {
			System.out.println("查询表" + table + "数据时异常 : "
					+ e.getLocalizedMessage());
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fieldsList;
	}

	/**
	 * 将一个ResultSet结果集中的所有字段的数目返回
	 * 
	 * @param resultSet
	 * @return int
	 */
	public int getFieldsCounts(ResultSet resultSet) {
		try {
			return resultSet.getMetaData().getColumnCount();
		} catch (SQLException e) {
			System.out.println("加载表中字段异常 ：" + e.getLocalizedMessage());
			return 0;
		}
	}

	/**
	 * 返回一个表的所有字段数目
	 * 
	 * @param table
	 * @return int
	 */
	public int getFieldsCounts(String table) {

		getConnection();
		int counts = 0;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from " + table + ";";
		System.out.println(sql);
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			counts = getFieldsCounts(rs);
		} catch (Exception e) {
			System.out.println("查询表" + table + "数据时异常 : "
					+ e.getLocalizedMessage());
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return counts;
	}

	/**
	 * 查询一个表中的所有元组数目
	 * 
	 * @param table
	 * @return int
	 */
	public int getTableCount(String table) {

		getConnection();
		String sql = "select count(*) from " + table + ";";
		Statement stmt = null;
		ResultSet rs = null;
		int counts = 0;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				counts = rs.getInt(1);
			}
			return counts;
		} catch (Exception e) {
			System.out.println("查询表" + table + "元组数时异常 : "
					+ e.getLocalizedMessage());
			return counts;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询一个表中的满足一定条件的所有元组数目
	 * 
	 * @param table
	 *            表名
	 * @param key
	 *            字段名称
	 * @param keyValue
	 *            字段值
	 * @return int
	 */
	public int getTableCount(String table, String key, String keyValue) {

		getConnection();
		String sql = "select count(*) from " + table + " where " + key + "='"
				+ keyValue + "';";
		Statement stmt = null;
		ResultSet rs = null;
		int counts = 0;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				counts = rs.getInt(1);
			}
			return counts;
		} catch (Exception e) {
			System.out.println("查询表" + table + "元组数时异常 : "
					+ e.getLocalizedMessage());
			return counts;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void connectionRollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println("异常时回滚错误 : " + e.getLocalizedMessage());
		}
	}

	/**
	 * 判断表是否存在
	 * 
	 * @param table
	 *            表名
	 * @return boolean
	 */
	public boolean tabbleIsExist(String table) {
		getConnection();
		boolean result = false;
		if (table == null) {
			return false;
		}
		String sql = "SELECT COUNT(*) FROM sqlite_master where type='table' and name='"
				+ table.replace("'", "").trim() + "'";
		Statement stmt = null;
		ResultSet rs = null;
		int counts = 0;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				counts = rs.getInt(1);
			}
			if (counts > 0) {
				result = true;
			}
		} catch (Exception e) {
			System.out.println("查询表" + table + "元组数时异常 : "
					+ e.getLocalizedMessage());
			result = false;
		} finally {
			try {
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}