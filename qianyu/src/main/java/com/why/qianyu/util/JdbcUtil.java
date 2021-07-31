package com.why.qianyu.util;//STEP 1. Import required packages

import com.why.qianyu.entity.Test01;
import org.springframework.jdbc.support.JdbcUtils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Properties;

public class JdbcUtil {
    /**
     * 获取数据库连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {

        String url = "jdbc:mysql://127.0.0.1:3306/qianyu_study";
        String driver = "com.mysql.cj.jdbc.Driver";

        //加载dirver
        Class.forName(driver);

        //获取连接
        Connection conn = DriverManager.getConnection(url,"root","root");

        return conn;
    }

    /**
    * 关闭连接
     */
    public void closeResource(Connection conn, Statement ps){
        try {
            if (conn!=null){
                conn.close();
            }
            if (ps!=null){
                ps.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeResource(Connection conn,Statement ps,ResultSet rs){
        try {
            if (conn!=null){
                conn.close();
            }
            if (ps!=null) {
                ps.close();
            }
            if (rs!=null){
                rs.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 通用查询
     * @param sql SQL语句
     * @param args sql参数
     * @return 返回查询结构
     * @throws Exception
     */
    public static Test01 queryForUser(String sql,Object ...args) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object bj = new Object();
        try {
            conn = getConnection();

            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length ; i++) {
                ps.setObject(i+1,args[i]);
            }

            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取结果集列数,查的字段数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){
                Test01 test01 = new Test01();
                //处理结果集一行数据中的字段
                for (int i = 0; i < columnCount ; i++) {

                    //获取字段值，通过结果集
                    Object value = rs.getObject(i + 1);

                    //获取结果集中的字段名，通过元数据
                    //获取列的别名
                    String columnName = rsmd.getColumnLabel(i+1);

                    //给user对象指定的指定属性赋值为value,通过反射
                    //**注意属性名和字段名不同
                    Field field = Test01.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(test01,value);

                }
                return test01;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(conn,ps,rs);
        }
        return null;
    }
}

