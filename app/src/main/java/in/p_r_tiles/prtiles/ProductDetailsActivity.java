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

public class ProductDetailsActivity extends AppCompatActivity {

    ProgressBar detailsLoading;
    TextView itemTitle;
    TextView itemDescription;
    TextView itemInStock;
    TextView itemPrice;
    ImageView itemImage;
    ConnectionClass connectionClass;
    String productId;
    String sellerId;

    public void buyNow(View view)
    {
        Intent intent = new Intent(ProductDetailsActivity.this,BuyNowActivity.class);
        intent.putExtra("PRODUCT_ID",productId);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        setTitle("Product Details");
        //Initialization of variables
        {
            detailsLoading = (ProgressBar)findViewById(R.id.progressBarItemLoading);
            itemTitle = (TextView)findViewById(R.id.textViewItemTitle);
            itemDescription = (TextView)findViewById(R.id.textViewItemDescription);
            itemInStock = (TextView)findViewById(R.id.textViewItemInStock);
            itemPrice = (TextView)findViewById(R.id.textViewItemPrice);
            itemImage = (ImageView) findViewById(R.id.imageViewItemImage);
            connectionClass = new ConnectionClass();
            productId = getIntent().getStringExtra("PRODUCT_ID");
        }

        GetProductDetails getProductDetails = new GetProductDetails();
        getProductDetails.execute("");

    }

    class GetProductDetails extends AsyncTask<String, String, String> {
        String imageUrl="";
        String itemNName="";
        String itemDDescription="";
        String itemIInStock="";
        String itemPPrice="";
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            detailsLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            if (isSuccess) {
                Toast.makeText(ProductDetailsActivity.this, r, Toast.LENGTH_SHORT).show();
                detailsLoading.setVisibility(View.GONE);
                itemTitle.setText(itemNName);
                itemDescription.setText(itemDDescription);
                itemInStock.setText(itemIInStock);
                itemPrice.setText(itemPPrice);
                Picasso.with(ProductDetailsActivity.this).load(imageUrl).into(itemImage);
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
                        String   query = "select top 1 * from newItems where id ="+productId;
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs.next()) {

                            itemNName = rs.getString("title");
                            itemDDescription = rs.getString("description");
                            itemIInStock = rs.getString("itemsLeft");
                            itemPPrice = rs.getString("price");
                            sellerId = rs.getString("sellerId");
                            imageUrl = rs.getString("img1");
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

