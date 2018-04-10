package in.p_r_tiles.prtiles;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Variable Declaration
    ConnectionClass connectionClass;
    public static Activity mainActivity;
    public static String orderBy = "viewCount";
    public static String category ="NIL";
    public static String searchTitle = "";
    ListView productList;
    ProgressBar loadingProducts;

    //Manually Creating the menus
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Action of menu links
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemFilter:
                Intent intentFilter = new Intent(MainActivity.this,FilterActivity.class);
                startActivity(intentFilter);
                //finish();
                break;
            case R.id.menuItemSearch:
                Intent intentSearch = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intentSearch);
                //finish();
                break;
            case R.id.menuItemTrack:
                Intent intentTrack = new Intent(MainActivity.this,TrackOrder.class);
                startActivity(intentTrack);
                //finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("P R Tiles Tiles");

        //Variable Initialization
        {
            connectionClass = new ConnectionClass();
            productList = (ListView) findViewById(R.id.listViewProductList);
            loadingProducts = (ProgressBar) findViewById(R.id.progressBarLoadingProducts);

            mainActivity = this;
        }

        //Getting product data in the background
        GetProductData getProductData = new GetProductData();
        getProductData.execute("");
    }

    ArrayList<ProductClass> getProductData() {
        ArrayList<ProductClass> retValue = new ArrayList<ProductClass>();

        try {
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN();
            if (con == null) {
                Log.d("PRTilesError", "No internet connection.");
                //Toast.makeText(MainActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
            } else {
                String query ="";
                if(category.equals("NIL"))
                query = "select * from newItems where title LIKE '%"+searchTitle +"%' ORDER BY " + orderBy;
                else
                    query = "select * from newItems where title LIKE '%"+searchTitle +"%' AND category ='"+category +"' ORDER BY " + orderBy;
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    retValue.add(
                            new ProductClass(
                                    Integer.parseInt(rs.getString("id")),
                                    rs.getString("img1"),
                                    rs.getString("title"),
                                    rs.getString("description"),
                                    rs.getString("itemsLeft"),
                                    rs.getString("price"),
                                    rs.getString("viewCount")
                            )
                    );
                }

            }
        } catch (Exception ex) {
            Log.d("PRTilesError", ex.getMessage());
            //Toast.makeText(MainActivity.this, "Error occurred, contact support.", Toast.LENGTH_SHORT).show();
        }
        return retValue;
    }

    class GetProductData extends AsyncTask<String, String, String> {
        ArrayList<ProductClass> data;
        String z = "";
        Boolean isSuccess = false;
        ProductAdapter adapter;

        @Override
        protected void onPreExecute() {
            loadingProducts.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            if (isSuccess) {
                Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
                loadingProducts.setVisibility(View.GONE);
                adapter = new ProductAdapter(MainActivity.this, R.layout.list_product_item, data);
                productList.setAdapter(adapter);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                data = getProductData();
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

