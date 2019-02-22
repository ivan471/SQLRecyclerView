package com.example.sqlrecyclerview;

import android.content.Context;
import android.content.Entity;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

public class GroveryAdapter extends RecyclerView.Adapter <GroveryAdapter.GroveryViewHolder>{

    private Context mContext;
    private Cursor mCursor;

    public GroveryAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;

    }

    @Override
    public GroveryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.grocery_item,parent,false);
        return new GroveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroveryViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_NAME));
        int amount = mCursor.getInt(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_AMOUNT));
        holder.nameText.setText(name);
        holder.countText.setText(String.valueOf(amount));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
    public void swapcursor(Cursor newCursor){
        if (mCursor != null){
            mCursor.close();
        }
        mCursor= newCursor;
        if (newCursor != null){
            notifyDataSetChanged();
        }
    }
    public class GroveryViewHolder extends RecyclerView.ViewHolder{
        public TextView nameText;
        public TextView countText;
        public GroveryViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.tv_name_item);
            countText = itemView.findViewById(R.id.tv_amount_item);
        }
    }

}
