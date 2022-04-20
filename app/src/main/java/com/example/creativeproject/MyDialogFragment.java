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

import com.example.creativeproject.databinding.DialogLayoutBinding;
import com.example.creativeproject.databinding.FragmentShoppingCartBinding;
import com.example.creativeproject.models.Product;
import com.example.creativeproject.models.User;
import com.example.creativeproject.recyclers.recyclerForNeedToAddList.RecyclerAdapterNeedToAddList;

import java.util.List;

public class MyDialogFragment extends DialogFragment {

    private Spinner spinner;
    private DialogLayoutBinding binding;
    private RecyclerAdapterNeedToAddList mAdapter;
    private List<Product> listOfProductsNeedToAdd = User.getInstance().getNeedToAddProductList();

    public MyDialogFragment(RecyclerAdapterNeedToAddList mAdapterNeedList) {
        super();
        this.mAdapter = mAdapterNeedList;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Style_Dialog_Rounded_Corner);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //View view = inflater.inflate(R.layout.dialog_layout, null);
        //builder.setView(view);
        // Остальной код
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding = DialogLayoutBinding.inflate(inflater);
        builder.setView(binding.getRoot());

        setSpinner();
        setAddButtonClickListener();
        //
        return builder.create();
    }

    private void setAddButtonClickListener() {
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextNameOfProduct.getText().toString();
                String count = binding.editTextCountOfProduct.getText().toString();
                String measure = spinner.getSelectedItem().toString();

                if (checkOnLetters(name) && checkOnCount(count)) {
                    addToList(name,count,measure);
                    getDialog().dismiss();
                } else {
                    ((Vibrator)getActivity().getSystemService(VIBRATOR_SERVICE))
                            .vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
        });
    }

    private void addToList(String name, String count, String measure){
        if (measure.equals("Килограммы")) {
            listOfProductsNeedToAdd
                    .add(new Product(name, Double.parseDouble(count), "кг", R.drawable.meat));
        } else if (measure.equals("Литры")){
            listOfProductsNeedToAdd
                    .add(new Product(name, Double.parseDouble(count), "л", R.drawable.milk));
        } else if (measure.equals("Штуки")){
            listOfProductsNeedToAdd
                    .add(new Product(name, Double.parseDouble(count), "шт", R.drawable.bread));
        }
        binding.editTextNameOfProduct.getText().clear();
        binding.editTextCountOfProduct.getText().clear();

        mAdapter.notifyItemInserted(listOfProductsNeedToAdd.size() - 1);
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

    private void setSpinner(){
        spinner = binding.spinnerDialog;
        ArrayAdapter<String> adapter = new ArrayAdapter
                (getActivity(), R.layout.spinner_item, R.id.measure,getResources().getStringArray(R.array.measures));
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
