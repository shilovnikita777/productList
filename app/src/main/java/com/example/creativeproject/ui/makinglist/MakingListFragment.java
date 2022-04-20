package com.example.creativeproject.ui.makinglist;


import static android.content.Context.VIBRATOR_SERVICE;

import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativeproject.MainMenuActivity;
import com.example.creativeproject.R;
import com.example.creativeproject.recyclers.recyclerCreatingList.RecyclerAdapterCreatingList;
import com.example.creativeproject.databinding.FragmentMakingListBinding;
import com.example.creativeproject.models.Product;
import com.example.creativeproject.models.User;
import com.example.creativeproject.recyclers.recyclerCreatingList.SimpleItemTouchHelperCallbackCreatingList;
import com.example.creativeproject.ui.shoppingcart.ShoppingCartFragment;

import java.util.ArrayList;
import java.util.List;

public class MakingListFragment extends Fragment {

    private MakingListViewModel makingListViewModel;
    private FragmentMakingListBinding binding;
    private Spinner spinner;
    private List<Product> productListCurrentCreating = User.getInstance().getCurrentCreatingProductList();

    private RecyclerView mRecyclerView;
    private RecyclerAdapterCreatingList mAdapter;
    private SimpleItemTouchHelperCallbackCreatingList callback;
    private ItemTouchHelper mItemTouchHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        makingListViewModel = new ViewModelProvider(this).get(MakingListViewModel.class);

        binding = FragmentMakingListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        /*makingListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        setSpinner();

        if (User.getInstance().getCurrentCreatingProductList().size() > 0) {
            binding.textCurrentList.setText("Ваш текущий список:");
        }

        setRecyclerView();

        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextNameOfProduct.getText().toString();
                String count = binding.editTextCountOfProduct.getText().toString();
                String measure = spinner.getSelectedItem().toString();

                if (checkOnLetters(name) && checkOnCount(count)) {
                    if (measure.equals("Килограммы")) {
                        productListCurrentCreating
                                .add(new Product(name, Double.parseDouble(count), "кг", R.drawable.meat));
                    } else if (measure.equals("Литры")){
                        productListCurrentCreating
                                .add(new Product(name, Double.parseDouble(count), "л", R.drawable.milk));
                    } else if (measure.equals("Штуки")){
                        productListCurrentCreating
                                .add(new Product(name, Double.parseDouble(count), "шт", R.drawable.bread));
                    }
                    binding.editTextNameOfProduct.getText().clear();
                    binding.editTextCountOfProduct.getText().clear();

                    if (productListCurrentCreating.size() == 1){
                        binding.textCurrentList.setText("Ваш текущий список:");
                    }

                    mAdapter.notifyItemInserted(productListCurrentCreating.size() - 1);
                } else {
                    Vibrator vibrator = (Vibrator)getActivity().getSystemService(VIBRATOR_SERVICE);
                    ((Vibrator)getActivity().getSystemService(VIBRATOR_SERVICE))
                            .vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
        });


        binding.buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productListCurrentCreating.size() == 0) {
                    ((Vibrator)getActivity().getSystemService(VIBRATOR_SERVICE))
                            .vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));

                } else {
                    ((MainMenuActivity)getActivity()).onNavigationItemSelected(binding.buttonFinish, R.id.from_making_to_shopping);

                    User.getInstance().getNeedToAddProductList().clear();
                    User.getInstance().copyToNeedProductList(productListCurrentCreating);

                    int size = productListCurrentCreating.size();
                    productListCurrentCreating.clear();
                    mAdapter.notifyItemRangeRemoved(0,size);

                    String date;
                    if (checkDate(binding.editTextDate.getText().toString())){
                        date = binding.editTextDate.getText().toString();
                    } else {
                        date = ((MainMenuActivity) getActivity()).getCurrentDate();
                    }
                    User.getInstance().setCurrentListDate(date);
                }
            }
        });

        return root;
    }

    private void setSpinner(){
        spinner = binding.spinner;
        ArrayAdapter<String> adapter = new ArrayAdapter
                (getActivity(), R.layout.spinner_item, R.id.measure,getResources().getStringArray(R.array.measures));
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setRecyclerView(){
        mRecyclerView = binding.currentListRecyclerview;
        mAdapter = new RecyclerAdapterCreatingList(User.getInstance().getCurrentCreatingProductList(),binding.textCurrentList);
        mRecyclerView.setAdapter(mAdapter);
        callback = new SimpleItemTouchHelperCallbackCreatingList(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private boolean checkOnLetters(String name){
        return name.matches("^[a-zA-Zа-яА-Я\\s[-]]+$");
    }

    private boolean checkOnCount(String count){
        try {
            Double number = Double.parseDouble(count);
            if (number > 0)
                return true;
            else
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean checkDate(String date){
        return date.matches("\\d{2}[.]\\d{2}[.]\\d{4}");
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