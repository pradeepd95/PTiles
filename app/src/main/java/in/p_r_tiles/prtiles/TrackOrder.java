package in.p_r_tiles.prtiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TrackOrder extends AppCompatActivity {

    ConnectionClass connectionClass;
    EditText emailId;
    EditText pin;


    String getOrderId()
    {
        String email = emailId.getText().toString();
        String code = pin.getText().toString();
        String order = "";
        try {
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN();
            if (con == null) {
                Log.d("PRTilesError", "No internet connection.");
                //Toast.makeText(MainActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
            } else {
                String   query = "select id from newItemsOrders where eMailAddress ='"+ email +"' AND pin="+code;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (rs.next()) {

                    order = rs.getString(1);
                }
            }
        } catch (Exception ex) {
            Log.d("PRTilesError", ex.getMessage());
            //Toast.makeText(MainActivity.this, "Error occurred, contact support.", Toast.LENGTH_SHORT).show();
        }
        return  order;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);


        setTitle("Track Order");

        //Initialization of variables
        {
            emailId = (EditText)findViewById(R.id.editTextTrackEmailId);
            pin = (EditText)findViewById(R.id.editTextTrackOrderPin);
        }
        Button clickMe = (Button)findViewById(R.id.buttonOpenMyOrder);

        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackOrder.this,MyOrderActivity.class);
                intent.putExtra("ORDER_ID",getOrderId());
                startActivity(intent);
            }
        });





    }
}
