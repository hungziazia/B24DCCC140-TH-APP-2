package com.example.app2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.app2.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String username = getArguments().getString("USERNAME");
            if (username != null && !username.isEmpty()) {
                binding.tvUserInfo.setText(username + "\ndoc123");
            }
        }

        binding.btnAdd.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_AddTaskFragment)
        );

        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
            String newTask = bundle.getString("NEW_TASK");
            int dayDiff = bundle.getInt("DAY_DIFF");

            if (newTask != null) {
                CheckBox newCb = new CheckBox(requireContext());
                newCb.setText(newTask);

                if (dayDiff == 0) {
                    binding.layoutToday.addView(newCb);
                } else if (dayDiff == 1) {
                    binding.layoutTomorrow.addView(newCb);
                } else if (dayDiff >= 2) {
                    binding.tvDayAfterTitle.setVisibility(View.VISIBLE);
                    binding.layoutDayAfter.setVisibility(View.VISIBLE);

                    binding.layoutDayAfter.addView(newCb);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}