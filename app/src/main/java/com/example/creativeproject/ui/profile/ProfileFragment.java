package com.example.creativeproject.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativeproject.databinding.FragmentProfileBinding;
import com.example.creativeproject.models.HistoryItem;
import com.example.creativeproject.models.User;
import com.example.creativeproject.recyclers.recyclerAlreadyInCartList.RecyclerAdapterAlreadyInCartList;
import com.example.creativeproject.recyclers.recyclerAlreadyInCartList.SimpleItemTouchHelperCallbackAlreadyInCartList;
import com.example.creativeproject.recyclers.recyclerHistory.RecyclerAdapterHistory;
import com.example.creativeproject.recyclers.recyclerHistory.SimpleItemTouchHelperCallbackHistory;

import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;

    private List<HistoryItem> listHistory = User.getInstance().getHistoryProductList();

    private RecyclerView mRecyclerViewHistoryList;
    private RecyclerAdapterHistory mAdapterHistoryList;
    private SimpleItemTouchHelperCallbackHistory callbackHistoryList;
    private ItemTouchHelper mItemTouchHelperHistoryList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (!listHistory.isEmpty()){
            setRecyclerViewHistoryList();
            binding.textYourHistory.setText("Ваша история покупок:");
        } else {
            binding.textYourHistory.setText("Ваша история покупок пуста!");
        }

        return root;
    }

    private void setRecyclerViewHistoryList(){
        mRecyclerViewHistoryList = binding.historyListRecycler;
        mAdapterHistoryList = new RecyclerAdapterHistory(listHistory,binding.textYourHistory,getActivity(),getParentFragmentManager());
        mRecyclerViewHistoryList.setAdapter(mAdapterHistoryList);
        callbackHistoryList = new SimpleItemTouchHelperCallbackHistory(mAdapterHistoryList);
        mItemTouchHelperHistoryList = new ItemTouchHelper(callbackHistoryList);
        mItemTouchHelperHistoryList.attachToRecyclerView(mRecyclerViewHistoryList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}