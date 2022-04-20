package com.example.creativeproject.ui.shoppingcart;

import static android.content.Context.VIBRATOR_SERVICE;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativeproject.MainMenuActivity;
import com.example.creativeproject.MyDialogFragment;
import com.example.creativeproject.R;
import com.example.creativeproject.models.HistoryItem;
import com.example.creativeproject.models.Product;
import com.example.creativeproject.recyclers.recyclerForNeedToAddList.RecyclerAdapterNeedToAddList;
import com.example.creativeproject.recyclers.recyclerForNeedToAddList.SimpleItemTouchHelperCallbackNeedToAddList;
import com.example.creativeproject.databinding.FragmentShoppingCartBinding;
import com.example.creativeproject.models.User;
import com.example.creativeproject.recyclers.recyclerAlreadyInCartList.RecyclerAdapterAlreadyInCartList;
import com.example.creativeproject.recyclers.recyclerAlreadyInCartList.SimpleItemTouchHelperCallbackAlreadyInCartList;

import java.util.List;

public class ShoppingCartFragment extends Fragment {

    private ShoppingCartViewModel shoppingCartViewModel;
    private FragmentShoppingCartBinding binding;

    private List<Product> productListAlreadyInCart = User.getInstance().getAlreadyInCartProductList();
    private List<Product> productListNeedToAdd = User.getInstance().getNeedToAddProductList();


    private RecyclerView mRecyclerViewNeedList;
    private RecyclerAdapterNeedToAddList mAdapterNeedList;
    private SimpleItemTouchHelperCallbackNeedToAddList callbackNeedList;
    private ItemTouchHelper mItemTouchHelperNeedList;

    private RecyclerView mRecyclerViewAlreadyList;
    private RecyclerAdapterAlreadyInCartList mAdapterAlreadyList;
    private SimpleItemTouchHelperCallbackAlreadyInCartList callbackAlreadyList;
    private ItemTouchHelper mItemTouchHelperAlreadyList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shoppingCartViewModel =
                new ViewModelProvider(this).get(ShoppingCartViewModel.class);

        binding = FragmentShoppingCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.constraintLayoutRecyclers.setVisibility(ConstraintLayout.GONE);

        if (!productListNeedToAdd.isEmpty() || !productListAlreadyInCart.isEmpty()){
            binding.constraintLayoutRecyclers.setVisibility(ConstraintLayout.VISIBLE);
            binding.constraintLayoutForEmptyBag.setVisibility(ConstraintLayout.GONE);
            setRecyclerViewAlreadyInCartList();
            setRecyclerViewNeedToAddList();
            if (!productListAlreadyInCart.isEmpty()){
                binding.textAlreadyInCart.setText("Уже в корзине:");
            } else {
                binding.textAlreadyInCart.setText("Корзина пуста!");
            }
            if (!productListNeedToAdd.isEmpty()){
                binding.textNeedToAdd.setText("Необходимо добавить в корзину:");
            } else {
                binding.textNeedToAdd.setText("Вы уже всё добавили в корзину!");
            }
        } else {
            binding.constraintLayoutRecyclers.setVisibility(ConstraintLayout.GONE);
            binding.constraintLayoutForEmptyBag.setVisibility(ConstraintLayout.VISIBLE);
        }

        binding.buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getParentFragmentManager();
                MyDialogFragment myDialogFragment = new MyDialogFragment(mAdapterNeedList);
                myDialogFragment.show(manager,"myDialog");

            }
        });

        binding.buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!productListAlreadyInCart.isEmpty()) {
                    addToHistory();
                    clearAlreadyListRecycler();
                    ((MainMenuActivity)getActivity()).onNavigationItemSelected(binding.buttonFinish, R.id.from_cart_to_profile);
                    Log.i("tg",User.getInstance().getHistoryProductList().size()+"");
                } else {
                    ((Vibrator)getActivity().getSystemService(VIBRATOR_SERVICE))
                            .vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
        });
        return root;
    }

    private void setRecyclerViewNeedToAddList(){
        mRecyclerViewNeedList = binding.recyclerviewNeedToAdd;
        mAdapterNeedList = new RecyclerAdapterNeedToAddList(User.getInstance().getNeedToAddProductList(),
                User.getInstance().getAlreadyInCartProductList(),mAdapterAlreadyList,binding.textNeedToAdd);
        mRecyclerViewNeedList.setAdapter(mAdapterNeedList);
        callbackNeedList = new SimpleItemTouchHelperCallbackNeedToAddList(mAdapterNeedList);
        mItemTouchHelperNeedList = new ItemTouchHelper(callbackNeedList);
        mItemTouchHelperNeedList.attachToRecyclerView(mRecyclerViewNeedList);
    }

    private void setRecyclerViewAlreadyInCartList(){
        mRecyclerViewAlreadyList = binding.recyclerviewAlreadyInCart;
        mAdapterAlreadyList = new RecyclerAdapterAlreadyInCartList(User.getInstance().getAlreadyInCartProductList(),binding.textAlreadyInCart);
        mRecyclerViewAlreadyList.setAdapter(mAdapterAlreadyList);
        callbackAlreadyList = new SimpleItemTouchHelperCallbackAlreadyInCartList(mAdapterAlreadyList);
        mItemTouchHelperAlreadyList = new ItemTouchHelper(callbackAlreadyList);
        mItemTouchHelperAlreadyList.attachToRecyclerView(mRecyclerViewAlreadyList);
    }

    private void addToHistory() {
        HistoryItem item = new HistoryItem();
        item.setList(productListAlreadyInCart);
        item.setDate(User.getInstance().getCurrentListDate());

        User.getInstance().setCurrentListDate(null);
        productListAlreadyInCart.clear();

        User.getInstance().getHistoryProductList().add(0,item);
        Log.i("fd", item.getDate() + " " + item.getProductList().get(0));

    }

    private void clearAlreadyListRecycler(){
        int size = productListAlreadyInCart.size();
        productListAlreadyInCart.clear();
        mAdapterAlreadyList.notifyItemRangeRemoved(0,size);
    }

    /*public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}