package in.p_r_tiles.prtiles;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyOrderActivity extends AppCompatActivity {


    String orderId;
    ProgressBar orderLoading;
    TextView orderMessage;
    TextView orderTitle;
    TextView orderDescription;
    ImageView orderImage;
    TextView orderNumber;
    TextView orderPin;
    TextView orderPrice;
    TextView orderDateAndTime;
    TextView orderShippingAddress;
    ConnectionClass connectionClass;

    //delete  string s = "delete from dbo.newItemsOrders WHERE id=@id";
    //select *,(select title from newItems where id=newItemsOrders.itemId) AS Title ,(select img1 from newItems where id=newItemsOrders.itemId) as Image ,(select description  from newItems where id=newItemsOrders.itemId) AS Description   from newItemsOrders

    public void cancelOrder(View view)
    {
        try {
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN();
            if (con == null) {
                Log.d("PRTilesError", "No internet connection.");
                //Toast.makeText(MainActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
            } else {
                String   query = "delete from newItemsOrders where id ="+ orderId;
                Statement stmt = con.createStatement();
                stmt.execute(query);
            }
        } catch (Exception ex) {
            Log.d("PRTilesError", ex.getMessage());
        }

        Toast.makeText(MyOrderActivity.this, "your order is cancelled.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MyOrderActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        setTitle("My Order");
        orderId = getIntent().getStringExtra("ORDER_ID");

        //Initialization of variables
        {
            orderLoading = (ProgressBar)findViewById(R.id.progressBarOrderLoading);
            orderMessage = (TextView)findViewById(R.id.textViewOrderMessage);
            orderTitle = (TextView)findViewById(R.id.textViewOrderTitle);
            orderDescription = (TextView)findViewById(R.id.textViewOrderDescription);
            orderNumber = (TextView)findViewById(R.id.textViewOrderNumber);
            orderPin = (TextView)findViewById(R.id.textViewOrderPin);
            orderPrice = (TextView)findViewById(R.id.textViewOrderPrice);
            orderDateAndTime = (TextView)findViewById(R.id.textViewOrderDateAndTime);
            orderShippingAddress = (TextView)findViewById(R.id.textViewOrderShippingAddress);
            connectionClass = new ConnectionClass();
            orderImage = (ImageView)findViewById(R.id.imageViewOrderImage);
        }

        GetOrderDetails getOrderDetails = new GetOrderDetails();
        getOrderDetails.execute("");
    }
    class GetOrderDetails extends AsyncTask<String, String, String> {
        String message;
        String title;
        String description;
        String number;
        String imageUrl="";
        String pin;
        String price;
        String dateAndTime;
        String shippingAddress;
        String z="";
        Boolean isSuccess=false;

        @Override
        protected void onPreExecute() {
            orderLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            if (isSuccess) {
                Toast.makeText(MyOrderActivity.this, r, Toast.LENGTH_SHORT).show();
                orderLoading.setVisibility(View.GONE);
                orderMessage.setText(message);
                orderTitle.setText(title);
                orderDescription.setText(description);
                orderNumber.setText(number);
                orderPin.setText(pin);
                orderPrice.setText(price);
                orderDateAndTime.setText(dateAndTime);
                orderShippingAddress.setText(shippingAddress);
                try
                {
                Picasso.with(MyOrderActivity.this).load(imageUrl).into(orderImage);
                }
                catch (Exception ee)
                {
                    ;
                }
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                try {
                    connectionClass = new ConnectionClass();
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        Log.d("PRTilesError", "No internet connection.");
                        //Toast.makeText(MainActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
                    } else {
                        String   query = "select *,(select title from newItems where id=newItemsOrders.itemId) AS Title ,(select img1 from newItems where id=newItemsOrders.itemId) as Image ,(select description  from newItems where id=newItemsOrders.itemId) AS Description  from newItemsOrders where id ="+ orderId;
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs.next()) {

                            title = rs.getString("Title");
                            description = rs.getString("Description");
                            imageUrl = rs.getString("Image");
                            number = "Order Id: "+ rs.getString("id");
                            pin = "Order Pin: "+rs.getString("pin");
                            message = "Hi, "+ rs.getString("name");
                            price ="Keep Change Ready: "+ rs.getString("priceToCollect") +".00 INR ";
                            dateAndTime = "Order Time & Date: "+ rs.getString("time") + " "+rs.getString("entryDate").substring(0,10) ;
                            shippingAddress = "Shipping Address: "+ rs.getString("shippingAddress");

                        }

                    }
                } catch (Exception ex) {
                    Log.d("PRTilesError", ex.getMessage());
                    //Toast.makeText(MainActivity.this, "Error occurred, contact support.", Toast.LENGTH_SHORT).show();
                }
                z = "Data loading completed.";
                isSuccess = true;
            } catch (Exception ex) {
                isSuccess = false;
                z = "Error occurred, contact support.";
                Log.d("PRTilesError", ex.getMessage());
            }
            return z;
        }
    }
}

