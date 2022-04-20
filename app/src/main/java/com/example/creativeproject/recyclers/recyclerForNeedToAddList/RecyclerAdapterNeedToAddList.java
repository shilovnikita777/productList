package com.example.creativeproject.recyclers.recyclerForNeedToAddList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.creativeproject.R;
import com.example.creativeproject.models.Product;
import com.example.creativeproject.models.User;
import com.example.creativeproject.recyclers.recyclerAlreadyInCartList.RecyclerAdapterAlreadyInCartList;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerAdapterNeedToAddList extends RecyclerView.Adapter<RecyclerAdapterNeedToAddList.MyViewHolder> {

    private List<Product> listOfProductsNeedToAdd;
    private List<Product> listOfProductsAlreadyInCart;
    private RecyclerAdapterAlreadyInCartList alreadyListAdapter;
    private TextView isNeedEmptyText;
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
    public RecyclerAdapterNeedToAddList(List<Product> productList, List<Product> listOfProductsAlreadyInCart,
                                 RecyclerAdapterAlreadyInCartList alreadyListAdapter, TextView isNeedEmptyText) {
        this.listOfProductsNeedToAdd = productList;
        this.listOfProductsAlreadyInCart = listOfProductsAlreadyInCart;
        this.alreadyListAdapter = alreadyListAdapter;
        this.isNeedEmptyText = isNeedEmptyText;
    }
    @Override
    public RecyclerAdapterNeedToAddList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //holder.mTextView.setText(mDataset.get(position));
        //holder.mTextView1.setText(mDataset1.get(position));
        holder.productName.setText(listOfProductsNeedToAdd.get(position).getName());


        if (listOfProductsNeedToAdd.get(position).getCount() % 1 == 0)
        {
            holder.productCountMeasure.setText((int)listOfProductsNeedToAdd.get(position).getCount() + " "
                    + listOfProductsNeedToAdd.get(position).getMeasure());
        }
        else holder.productCountMeasure.setText(listOfProductsNeedToAdd.get(position).getCount() + " "
                + listOfProductsNeedToAdd.get(position).getMeasure());

        holder.productImage.setImageResource(listOfProductsNeedToAdd.get(position).getImageID());

        if (listOfProductsNeedToAdd.size() == 1) {
            isNeedEmptyText.setText("Необходимо добавить в корзину:");
        }
    }
    @Override
    public int getItemCount() {
        return listOfProductsNeedToAdd.size();
    }
    //
    public void onItemDismiss(int position) {
        listOfProductsAlreadyInCart.add(listOfProductsNeedToAdd.get(position));
        alreadyListAdapter.notifyItemInserted(User.getInstance().getAlreadyInCartProductList().size() - 1);

        listOfProductsNeedToAdd.remove(position);
        notifyItemRemoved(position);

        if (listOfProductsNeedToAdd.isEmpty()) {
            isNeedEmptyText.setText("Вы уже всё добавили в корзину!");
        }
    }

    public void onItemMove(int fromPosition, int toPosition) {
        Product prev = listOfProductsNeedToAdd.remove(fromPosition);
        listOfProductsNeedToAdd.add(toPosition > fromPosition ? toPosition : toPosition, prev);
        Log.i("tg",fromPosition + " " +toPosition);
        for (Product p : listOfProductsNeedToAdd) {
            Log.i("tg",p.getName() + " ");
        }
        notifyItemMoved(fromPosition, toPosition);
    }
    //
}