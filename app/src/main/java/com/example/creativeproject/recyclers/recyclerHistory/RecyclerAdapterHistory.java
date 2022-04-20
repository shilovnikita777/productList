package com.example.creativeproject.recyclers.recyclerHistory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativeproject.DialogFragmentHistoryList;
import com.example.creativeproject.MainMenuActivity;
import com.example.creativeproject.MyDialogFragment;
import com.example.creativeproject.R;
import com.example.creativeproject.models.HistoryItem;
import com.example.creativeproject.models.Product;
import com.example.creativeproject.models.User;
import com.example.creativeproject.ui.profile.ProfileFragment;

import java.util.List;

public class RecyclerAdapterHistory extends RecyclerView.Adapter<RecyclerAdapterHistory.MyViewHolder> {

    private List<HistoryItem> listOfHistory;
    TextView isListEmptyText;
    Button repeatButton;
    Button removeButton;

    private Context mContext;
    private FragmentManager manager;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView productsList;

        public MyViewHolder(View v) {
            super(v);

            date = v.findViewById(R.id.text_date);
            productsList = v.findViewById(R.id.text_products);
            repeatButton = v.findViewById(R.id.button_repeat);
            removeButton = v.findViewById(R.id.button_remove);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        HistoryItem clickedItem = listOfHistory.get(pos);
                        DialogFragmentHistoryList dialog = new DialogFragmentHistoryList(clickedItem.getProductList());
                        dialog.show(manager,"myDialog1");
                    }
                }
            });
        }
    }
    public RecyclerAdapterHistory(List<HistoryItem> productList, TextView isListEmptyText
            , Context mContext, FragmentManager manager) {
        this.listOfHistory = productList;
        this.isListEmptyText = isListEmptyText;
        this.mContext = mContext;
        this.manager = manager;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //holder.mTextView.setText(mDataset.get(position));
        //holder.mTextView1.setText(mDataset1.get(position));
        switch(listOfHistory.get(position).getProductList().size()) {
            case 1:
                holder.productsList.setText(listOfHistory.get(position).getProductList().get(0).getName());
                break;
            case 2:
                holder.productsList.setText(listOfHistory.get(position).getProductList().get(0).getName() + "\n"
                        + listOfHistory.get(position).getProductList().get(1).getName());
                break;
            case 3:
                holder.productsList.setText(listOfHistory.get(position).getProductList().get(0).getName() + "\n"
                        + listOfHistory.get(position).getProductList().get(1).getName() + "\n"
                        + listOfHistory.get(position).getProductList().get(2).getName());
                break;
            default:
                holder.productsList.setText(listOfHistory.get(position).getProductList().get(0).getName() + "\n"
                        + listOfHistory.get(position).getProductList().get(1).getName() + "\n"
                        + listOfHistory.get(position).getProductList().get(2).getName() + "\n" + "...");
                break;
        }

        holder.date.setText(listOfHistory.get(position).getDate());

        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContext instanceof MainMenuActivity) {
                    User.getInstance().getNeedToAddProductList().clear();
                    User.getInstance().copyToNeedProductList(listOfHistory.get(holder.getAdapterPosition()).getProductList());

                    ((MainMenuActivity)mContext).onNavigationItemSelected(repeatButton,R.id.from_profile_to_shopping);
                    User.getInstance().setCurrentListDate(((MainMenuActivity)mContext).getCurrentDate());
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemDismiss(holder.getAdapterPosition());
            }
        });

        if (listOfHistory.size() == 1) {
            isListEmptyText.setText("Ваша история покупок:");
        }
    }
    @Override
    public int getItemCount() {
        return listOfHistory.size();
    }
    //
    public void onItemDismiss(int position) {
        listOfHistory.remove(position);
        notifyItemRemoved(position);

        if (listOfHistory.isEmpty()) {
            isListEmptyText.setText("Ваша история покупок пуста!");
        }
    }

    public void onItemMove(int fromPosition, int toPosition) {
        HistoryItem prev = listOfHistory.remove(fromPosition);
        listOfHistory.add(toPosition > fromPosition ? toPosition : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }
    //
}