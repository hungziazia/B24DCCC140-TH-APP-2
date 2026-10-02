package com.example.app2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.app2.databinding.FragmentAddTaskBinding;
import java.util.Calendar;

public class AddTaskFragment extends Fragment {

    private FragmentAddTaskBinding binding;
    private long diffDays = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.edtDueDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(requireContext(), (view1, selectedYear, selectedMonth, selectedDay) -> {
                binding.edtDueDate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);

                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(selectedYear, selectedMonth, selectedDay, 0, 0, 0);

                Calendar todayDate = Calendar.getInstance();
                todayDate.set(year, month, day, 0, 0, 0);

                long diffMillis = selectedDate.getTimeInMillis() - todayDate.getTimeInMillis();
                diffDays = diffMillis / (24 * 60 * 60 * 1000); // Đổi từ mili-giây ra ngày
            }, year, month, day);
            dialog.show();
        });

        binding.btnSave.setOnClickListener(v -> {
            String taskName = binding.edtTaskName.getText().toString();

            if (!taskName.isEmpty() && !binding.edtDueDate.getText().toString().isEmpty()) {
                Bundle result = new Bundle();
                result.putString("NEW_TASK", taskName);
                result.putInt("DAY_DIFF", (int) diffDays);
                getParentFragmentManager().setFragmentResult("requestKey", result);

                Toast.makeText(requireContext(), "Đã lưu!", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(AddTaskFragment.this).popBackStack();
            } else {
                Toast.makeText(requireContext(), "Vui lòng nhập tên và chọn ngày!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}