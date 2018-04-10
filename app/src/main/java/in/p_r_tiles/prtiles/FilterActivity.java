package in.p_r_tiles.prtiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import static in.p_r_tiles.prtiles.MainActivity.mainActivity;

public class FilterActivity extends AppCompatActivity {

    Spinner orderBy;
    Spinner category;


    public void filterNow(View view)
    {
        //"PRICE LOW-HIGH","PRICE HIGH_LOW","LATEST","VIEW COUNTS","OLDEST"
        switch (orderBy.getSelectedItem().toString())
        {
            case "PRICE LOW-HIGH": MainActivity.orderBy = " PRICE ASC";
                break;
            case "PRICE HIGH_LOW":MainActivity.orderBy = " PRICE DESC";
                break;
            case "LATEST": MainActivity.orderBy = " entryDate DESC";
                break;
            case "VIEW COUNTS":MainActivity.orderBy = " viewCount DESC";
                break;
            case "OLDEST": MainActivity.orderBy = " entryDate ASC";
                break;
        }
        //"Featured","Floor","Mosaic","Wall","All"
        switch (category.getSelectedItem().toString())
        {
            case "Featured": MainActivity.orderBy = "Featured";
                break;
            case "Floor":MainActivity.orderBy = "Floor";
                break;
            case "Mosaic": MainActivity.orderBy = "Mosaic";
                break;
            case "Wall":MainActivity.orderBy = "Wall";
                break;
            case "All": MainActivity.category = "NIL";
                break;
        }
        mainActivity.finish();
        Intent intent = new Intent(FilterActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_filter);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .4));

        //Initialization of variables
        {
            orderBy = (Spinner)findViewById(R.id.spinnerOrderBy);
            String orderByData[] ={"PRICE LOW-HIGH","PRICE HIGH_LOW","LATEST","VIEW COUNTS","OLDEST"};
            ArrayAdapter<String> adapterOrderBy = new ArrayAdapter<String>(FilterActivity.this,android.R.layout.simple_list_item_1, orderByData);
            orderBy.setAdapter(adapterOrderBy);

            category = (Spinner)findViewById(R.id.spinnerCategory);
            String categoryData[] ={"Featured","Floor","Mosaic","Wall","All"};
            ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(FilterActivity.this,android.R.layout.simple_list_item_1, categoryData);
            category.setAdapter(adapterCategory);
        }
    }
}
