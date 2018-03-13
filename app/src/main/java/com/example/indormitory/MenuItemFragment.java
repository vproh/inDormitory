package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.indormitory.models.Dish;
import com.example.indormitory.models.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeckk on 12.03.2018.
 */

public class MenuItemFragment extends Fragment {
    static final String PAGE_ITEM = "page_item";
    private static String[] items = {"Алкоголь", "Булочки", "М'ясні страви", "Більше алкоголю"};
    private String mItem;
    private RecyclerView mMenuRecyclerView;
    private MenuAdapter mAdapter;
    private List<Dish> mDishesList = new ArrayList<>();

    static MenuItemFragment newInstance(int position) {
        MenuItemFragment menuItemFragment = new MenuItemFragment();
        Bundle arguments = new Bundle();
        arguments.putString(PAGE_ITEM, items[position]);
        menuItemFragment.setArguments(arguments);
        return menuItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = getArguments().getString(PAGE_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_item, null);
        TextView textView = view.findViewById(R.id.fragment_item);
        textView.setText(mItem);
        mMenuRecyclerView = view.findViewById(R.id.menu_recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDishesList.clear();
        for(int i = 0; i < 5; i ++)
            mDishesList.add(new Dish("Olive", 50 + i, null, null, null));
        configureAdapter();
        return view;
    }

    private void configureAdapter() {
        /*if (mAdapter == null) {
            mAdapter = new MenuAdapter(mDishesList);
            mMenuRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setNews(mDishesList);
            mAdapter.notifyDataSetChanged();
        }*/
        mAdapter = new MenuAdapter(mDishesList);
        mMenuRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private class DishHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mPriceTextView;
        private Dish mDish;

        DishHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_food, parent, false));

            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.dish_title);
            mPriceTextView = itemView.findViewById(R.id.dish_price);
        }

        void bind(Dish dish) {
            mDish = dish;
            mTitleTextView.setText(mDish.getTitle());
            mPriceTextView.setText(String.valueOf(mDish.getPrice()));
        }

        @Override
        public void onClick(View v) {
            startActivity(new Intent(getContext(), ItemMenuActivity.class));
        }
    }

    private class MenuAdapter extends RecyclerView.Adapter<DishHolder> {
        private List<Dish> mDishesList;

        MenuAdapter(List<Dish> dishesList) {
            mDishesList = dishesList;
        }

        @NonNull
        @Override
        public DishHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new DishHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DishHolder holder, int position) {
            Dish dish = mDishesList.get(position);
            holder.bind(dish);
        }

        @Override
        public int getItemCount() {
            return mDishesList.size();
        }

        void setNews(List<Dish> dishesList) {
            mDishesList.clear();
            mDishesList.addAll(dishesList);
            this.notifyItemRangeInserted(0, mDishesList.size() - 1);
        }

    }
}