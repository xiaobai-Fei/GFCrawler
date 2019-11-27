package xiao.fei.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author fei
 */
public class MySqlUtils {

    private static DruidDataSource druidDataSource = new DruidDataSource();

    private static final String drive = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://S128:3306/zhihu?useUnicode=true&characterEncoding=UTF8&useServerPrepStmts=true&prepStmtCacheSqlLimit=256&cachePrepStmts=true&prepStmtCacheSize=256&rewriteBatchedStatements=true";

    static {
        druidDataSource.setDriverClassName(drive);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
    }

    public static Connection getConnection() {
        try {
            return  druidDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
