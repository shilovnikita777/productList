package com.example.creativeproject;

import static android.content.Context.VIBRATOR_SERVICE;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativeproject.databinding.DialogLayoutBinding;
import com.example.creativeproject.databinding.DialogListLayoutBinding;
import com.example.creativeproject.databinding.FragmentShoppingCartBinding;
import com.example.creativeproject.models.Product;
import com.example.creativeproject.models.User;
import com.example.creativeproject.recyclers.recyclerCreatingList.RecyclerAdapterCreatingList;
import com.example.creativeproject.recyclers.recyclerForNeedToAddList.RecyclerAdapterNeedToAddList;
import com.example.creativeproject.recyclers.recyclerListInHistory.RecyclerAdapterListInHistory;

import java.util.List;

public class DialogFragmentHistoryList extends DialogFragment {

    private List<Product> listOfProductsInHistory;

    private DialogListLayoutBinding binding;

    private RecyclerAdapterListInHistory mAdapter;
    private RecyclerView mRecyclerView;

    public DialogFragmentHistoryList(List<Product> listOfProductsInHistory) {
        super();
        this.listOfProductsInHistory = listOfProductsInHistory;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Style_Dialog_Rounded_Corner);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //View view = inflater.inflate(R.layout.dialog_layout, null);
        //builder.setView(view);
        // Остальной код
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding = DialogListLayoutBinding.inflate(inflater);
        builder.setView(binding.getRoot());

        setRecyclerView();
        //setAddButtonClickListener();
        //
        return builder.create();
    }

    private void setRecyclerView(){
        mRecyclerView = binding.listInHistoryRecyclerview;
        mAdapter = new RecyclerAdapterListInHistory(listOfProductsInHistory);
        mRecyclerView.setAdapter(mAdapter);
    }
}
