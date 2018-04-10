package in.p_r_tiles.prtiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProductAdapter extends ArrayAdapter<ProductClass> {
    Context context;
    int layoutResourceId;
    ArrayList<ProductClass> data = null;


    public ProductAdapter(Context context, int layoutResourceId, ArrayList<ProductClass> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public void openProductDetailsActivity(Integer i) {
        Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
        intent.putExtra("PRODUCT_ID", i.toString());
        getContext().startActivity(intent);
        Context xx = getContext();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ProductHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ProductHolder();
            holder.image = (ImageView) row.findViewById(R.id.imageViewProduct);
            holder.name = (TextView) row.findViewById(R.id.textViewProductName);
            holder.description = (TextView) row.findViewById(R.id.textViewProductDescription);
            holder.stock = (TextView) row.findViewById(R.id.textViewStock);
            holder.price = (TextView) row.findViewById(R.id.textViewProductPrice);
            holder.viewButton = (Button) row.findViewById(R.id.buttonProductView);

            ProductClass item = data.get(position);
            holder.id = item.productId;
            Picasso.with(context).load(item.imageUrl).into(holder.image);
            //holder.image.setImageResource(R.drawable.image_my);

            holder.name.setText(item.name);
            holder.description.setText(item.description);
            holder.stock.setText("Items in stock: " + item.stock);
            holder.price.setText(item.price + ".00 INR");
            holder.viewButton.setText("Views "+item.views+"+");

            final Integer productId = holder.id;
            final String productName = holder.name.getText().toString();

            holder.viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer i = position;
                    Toast.makeText(getContext(), "Details of "+productName, Toast.LENGTH_SHORT).show();
                    openProductDetailsActivity(productId);
                }
            });

            row.setTag(holder);
        } else {
            holder = (ProductHolder) row.getTag();
            ProductClass item = data.get(position);
            holder.id = item.productId;

            Picasso.with(context).load(item.imageUrl).into(holder.image);
            //holder.image.setImageResource(R.drawable.image_my);

            holder.name.setText(item.name);
            holder.description.setText(item.description);
            holder.stock.setText("Items in stock: " + item.stock);
            holder.price.setText(item.price + ".00 INR");
            holder.viewButton.setText("Views "+item.views+"+");
        }
        return row;
    }

    static class ProductHolder {
        Integer id;
        ImageView image;
        TextView name;
        TextView description;
        TextView stock;
        TextView price;
        Button viewButton;
    }
}

