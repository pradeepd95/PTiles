package in.p_r_tiles.prtiles;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionClass {
    String ip = "182.50.133.109";
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    String db = "002SuproData";
    String un = "suproRS";
    String password = "L@ve#123";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ViartTilesError", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ViartTilesError", e.getMessage());
        } catch (Exception e) {
            Log.e("ViartTilesError", e.getMessage());
        }
        return conn;
    }
}

