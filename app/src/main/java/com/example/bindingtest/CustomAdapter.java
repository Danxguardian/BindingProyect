package com.example.bindingtest;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.bindingtest.databinding.RowListviewBinding;

import java.util.List;

public class CustomAdapter extends BaseAdapter{
    private List<Task> listTasks;
    private Activity activity;

    public CustomAdapter(Activity activity, List<Task> listTasks){
        this.listTasks = listTasks;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listTasks.size();
    }

    @Override
    public Object getItem(int position) {
        return listTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RowListviewBinding binding;
        if(convertView == null){
            convertView = LayoutInflater.from(activity).inflate(R.layout.row_listview, null);
            binding = DataBindingUtil.bind(convertView);
            convertView.setTag(binding);
        }else{
            binding = (RowListviewBinding) convertView.getTag();
        }
        binding.setTask(listTasks.get(position));
        return binding.getRoot();
    }
}
