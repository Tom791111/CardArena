package util;

import exception.AppException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {

    /**
     * 讀取資料庫設定。
     * 讀取順序：
     * 1. JAR 同一層的 db.properties
     * 2. 專案根目錄的 db.properties
     * 3. src/main/resources/db.properties 打包後的 classpath
     */
    private static Properties loadProperties() {
        Properties prop = new Properties();

        try {
            File external = new File("db.properties");
            if (external.exists()) {
                try (InputStream in = new FileInputStream(external)) {
                    prop.load(in);
                    return prop;
                }
            }

            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            if (in == null) {
                throw new AppException("找不到 db.properties，請確認 src/main/resources/db.properties 或 JAR 同層有 db.properties");
            }
            try (InputStream autoClose = in) {
                prop.load(autoClose);
            }
            return prop;
        } catch (Exception e) {
            if (e instanceof AppException) {
                throw (AppException) e;
            }
            throw new AppException("讀取 db.properties 失敗", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties prop = loadProperties();

            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");

            if (isBlank(driver) || isBlank(url) || isBlank(username)) {
                throw new AppException("db.properties 設定不完整，請確認 driver / url / username / password");
            }

            Class.forName(driver);
            return DriverManager.getConnection(url, username, password == null ? "" : password);
        } catch (Exception e) {
            if (e instanceof AppException) {
                throw (AppException) e;
            }
            throw new AppException("MySQL 連線失敗，請確認 MySQL 已啟動、帳密正確、資料庫 player_card_db 已建立", e);
        }
    }

    public static boolean test() {
        try (Connection c = getConnection()) {
            return c != null && !c.isClosed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getDbInfo() {
        Properties prop = loadProperties();
        return prop.getProperty("url", "未設定 url");
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
