package com.example.creativeproject.recyclers.recyclerCreatingList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.creativeproject.R;
import com.example.creativeproject.models.Product;

import java.util.List;

public class RecyclerAdapterCreatingList extends RecyclerView.Adapter<RecyclerAdapterCreatingList.MyViewHolder> {

    private List<Product> listOfProducts;
    TextView isListEmptyText;
    //private MakingListFragment makingListFragment;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView productName;
        public TextView productCountMeasure;

        public MyViewHolder(View v) {
            super(v);

            productImage = v.findViewById(R.id.imageView_product);
            productName = v.findViewById(R.id.text_name_of_product);
            productCountMeasure = v.findViewById(R.id.text_count_measure);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
    public RecyclerAdapterCreatingList(List<Product> productList, TextView isListEmptyText) {
        this.listOfProducts = productList;
        this.isListEmptyText = isListEmptyText;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //holder.mTextView.setText(mDataset.get(position));
        //holder.mTextView1.setText(mDataset1.get(position));
        holder.productName.setText(listOfProducts.get(position).getName());


        if (listOfProducts.get(position).getCount() % 1 == 0)
        {
            holder.productCountMeasure.setText((int)listOfProducts.get(position).getCount() + " "
                    + listOfProducts.get(position).getMeasure());
        }
        else holder.productCountMeasure.setText(listOfProducts.get(position).getCount() + " "
                + listOfProducts.get(position).getMeasure());

        holder.productImage.setImageResource(listOfProducts.get(position).getImageID());
    }
    @Override
    public int getItemCount() {
        return listOfProducts.size();
    }
    //
    public void onItemDismiss(int position) {
        listOfProducts.remove(position);
        notifyItemRemoved(position);

        if (listOfProducts.isEmpty()) {
            isListEmptyText.setText("Ваш текущий список пуст!");
        }
    }

    public void onItemMove(int fromPosition, int toPosition) {
        Product prev = listOfProducts.remove(fromPosition);
        listOfProducts.add(toPosition > fromPosition ? toPosition : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }
    //
}