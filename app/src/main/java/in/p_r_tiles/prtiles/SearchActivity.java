package in.p_r_tiles.prtiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import static in.p_r_tiles.prtiles.MainActivity.mainActivity;

public class SearchActivity extends AppCompatActivity {

    EditText searchString;
    public void searchNow(View view)
    {
        MainActivity.searchTitle = searchString.getText().toString();
        mainActivity.finish();
        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .2));

        //Variable Initialization
        {
            searchString = (EditText)findViewById(R.id.editTextSearchProduct);
        }
    }
}
