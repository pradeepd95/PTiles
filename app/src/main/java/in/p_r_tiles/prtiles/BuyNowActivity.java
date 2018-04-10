package in.p_r_tiles.prtiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

public class BuyNowActivity extends AppCompatActivity {

    String getDate() {
        //CONVERT(TIME,GETDATE()) 2017-03-24
        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        Log.d("Date", currentDateTimeString);
        return currentDateTimeString;
    }

    String getTime() {
        //CONVERT(TIME,GETDATE())
        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
        Log.d("Time", currentDateTimeString);
        return currentDateTimeString;
    }

    String getOrderId() {
        String retOrder="";
        //"select id from dbo.newItemsOrders WHERE eMailAddress=@eMailAddress AND pin=@pin
        try {
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN();
            if (con == null) {
                Log.d("PRTilesError", "No internet connection.");
                //Toast.makeText(MainActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
            } else {
                String query = "select id from dbo.newItemsOrders WHERE eMailAddress='" + emailAddress.getText().toString() + "' AND pin=" + pin;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (rs.next()) {
                    retOrder = rs.getString(1);
                }
            }
        } catch (Exception ex) {
            Log.d("PRTilesError", ex.getMessage());
            //Toast.makeText(MainActivity.this, "Error occurred, contact support.", Toast.LENGTH_SHORT).show();
        }
        return  retOrder;
    }

    public void orderNow(View view) {


        Random rand = new Random();
        pin = rand.nextInt(9999) + 1000;

        try {
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN();
            if (con == null) {
                Log.d("PRTilesError", "No internet connection.");
                //Toast.makeText(MainActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
            } else {
                String query = "INSERT INTO dbo.newItemsOrders (pin, name, promocode, cNumber, eMailAddress,itemId,priceToCollect,time,entryDate,shippingAddress,isDelivered)" +
                        "VALUES (" + pin + ",'" + name.getText().toString() + "','" + promocode.getText().toString() + "','" + contactNumber.getText().toString() + "','" + emailAddress.getText().toString() +
                        "',"+productId+ ",(select price from newItems where id=" + productId + "),'" + getTime() + "','" + getDate() + "','" + shippingAddress.getText().toString() + "','no')";
                Statement stmt = con.createStatement();
                stmt.execute(query);
            }
        } catch (Exception ex) {
            Log.d("PRTilesError", ex.getMessage());
            //Toast.makeText(MainActivity.this, "Error occurred, contact support.", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(BuyNowActivity.this, MyOrderActivity.class);
        intent.putExtra("ORDER_ID", getOrderId());
        startActivity(intent);
        finish();

    }

    Integer pin;
    String productId;
    EditText name;
    EditText contactNumber;
    EditText emailAddress;
    EditText shippingAddress;
    EditText promocode;
    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);

        setTitle("Shipping Details");

        //Initialization of variables
        {
            productId = getIntent().getStringExtra("PRODUCT_ID");
            name = (EditText) findViewById(R.id.editTextUserName);
            contactNumber = (EditText) findViewById(R.id.editTextUserContactNumber);
            emailAddress = (EditText) findViewById(R.id.editTextUserEmailId);
            shippingAddress = (EditText) findViewById(R.id.editTextUserShippingAddress);
            promocode = (EditText) findViewById(R.id.editTextUserPromocode);
        }
    }
}
