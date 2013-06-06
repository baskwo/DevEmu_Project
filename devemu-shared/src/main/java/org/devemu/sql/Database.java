<<<<<<< HEAD:DevemuBase/src/org/devemu/sql/Database.java
package org.devemu.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbcp.BasicDataSource;

public class Database {
    
    private static final Map<String, Database> databases = new HashMap<>(8);
    
    private final BasicDataSource source = new BasicDataSource();

    public Database(String driver, String url, String user, String password, int size, int maxSize, boolean readOnly) {
        source.setDriverClassName(driver);
        source.setUrl(url);
        source.setUsername(user);
        source.setPassword(password);
        source.setInitialSize(size);
        source.setMaxActive(maxSize);
        source.setDefaultReadOnly(readOnly);
        
        source.setDefaultAutoCommit(false);
        source.setMaxOpenPreparedStatements(maxSize * 2);
        source.setPoolPreparedStatements(true);
    }
    
    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }
    
    public static boolean dbExists(String name) {
        return databases.containsKey(name);
    }
    
    public static Map<String, Database> getAllDatabases() {
        return Collections.unmodifiableMap(databases);
    }
    
    public static Database getDatabase(String name) {
        return databases.get(name);
    }
    
    public static Database newDatabase(String name, String driver, String url, String user, String password, int size, int maxSize, boolean readOnly) {
        Database db = new Database(driver, url, user, password, size, maxSize, readOnly);
        databases.put(name, db);
        return db;
    }
    
    public static Connection getConnection(String name) throws SQLException {
        return databases.get(name).source.getConnection();
    }
    
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
            	resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (statement != null) {
            try {
            	statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (connection != null) {
            try {
            	connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void close(Statement statement, Connection connection) {
        if (statement != null) {
            try {
                if (!statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
=======
package org.devemu.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbcp.BasicDataSource;

public class Database {
    
    private static final Map<String, Database> databases = new HashMap<String, Database>(8);
    
    private final BasicDataSource source = new BasicDataSource();

    public Database(String driver, String url, String user, String password, int size, int maxSize, boolean readOnly) {
        source.setDriverClassName(driver);
        source.setUrl(url);
        source.setUsername(user);
        source.setPassword(password);
        source.setInitialSize(size);
        source.setMaxActive(maxSize);
        source.setDefaultReadOnly(readOnly);
        
        source.setDefaultAutoCommit(false);
        source.setMaxOpenPreparedStatements(maxSize * 2);
        source.setPoolPreparedStatements(true);
    }
    
    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }
    
    public static boolean dbExists(String name) {
        return databases.containsKey(name);
    }
    
    public static Map<String, Database> getAllDatabases() {
        return Collections.unmodifiableMap(databases);
    }
    
    public static Database getDatabase(String name) {
        return databases.get(name);
    }
    
    public static Database newDatabase(String name, String driver, String url, String user, String password, int size, int maxSize, boolean readOnly) {
        Database db = new Database(driver, url, user, password, size, maxSize, readOnly);
        databases.put(name, db);
        return db;
    }
    
    public static Connection getConnection(String name) throws SQLException {
        return databases.get(name).source.getConnection();
    }
    
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
            	resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (statement != null) {
            try {
            	statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (connection != null) {
            try {
            	connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void close(Statement statement, Connection connection) {
        if (statement != null) {
            try {
                if (!statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
>>>>>>> 800500b6eda7a3b433414a80b9c5ef278d93b04e:devemu-shared/src/main/java/org/devemu/sql/Database.java
