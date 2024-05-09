package utils;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionServer {
    private static Connection myCon;
    public static Connection mysqlServerConnection() {
        try{
            myCon = DriverManager.getConnection(Constant.URL_MYSQL_SERVER, Constant.USER_MYSQL_SERVER, Constant.PASSWORD_MYSQL_SERVER);
            return myCon;
        }catch(Exception ex){
            Constant.MESSAGE = ex.getMessage();
            return null;
        }
    }
    
    public static Connection postgreServerConnection() {
        try{
            myCon = DriverManager.getConnection(Constant.URL_POSTGRE_SERVER, Constant.USER_POSTGRE_SERVER, Constant.PASSWORD_POSTGRE_SERVER);
            return myCon;
        }catch(Exception ex){
            Constant.MESSAGE = ex.getMessage();
            return null;
        }
    }
}
