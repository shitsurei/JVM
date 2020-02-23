package classLoader;

import java.sql.*;

/**
 * 20
 * DriverManager类在初始化过程化会执行loadInitialDrivers方法，该方法会【借助ServiceLoader类将classPath目录下的jar包中的驱动进行加载和初始化】
 * JDBC的驱动实现在初始化的过程中，会调用驱动管理器的registerDriver方法，将自己新创建的实例注册进去
 * 最终驱动管理器将所有注册好的驱动保存在内部静态常量列表registeredDrivers中
 * 建立数据库连接时列表中的驱动还会通过isDriverAllowed方法的验证，确保类加载器和驱动的一致性
 */
public class JDBCsample {
    public static void main(String[] args) {
        try {
//            通过反射对mysql连接驱动进行加载和初始化，默认使用调用者的类加载器加载（Reflection.getCallerClass()方法获取），即系统类加载器
//            初始化过程中会将新注册的实例注册到DriverManager中
//            {早期版本需要手动加载驱动类，现在JDBC的设计已经符合SPI规范，加载和初始化DriverManager类时会加载注册相关驱动}
//            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/liuyan?useSSL=false&serverTimezone=UTC", "root", "mysql");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select `department` from directory limit 20");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("department"));
            }
            statement.close();
            connection.close();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
