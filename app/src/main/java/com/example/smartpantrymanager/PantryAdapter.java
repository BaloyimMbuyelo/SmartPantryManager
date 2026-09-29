package com.example.smartpantrymanager;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private Cursor cursor;

    public PantryAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {

        if (cursor != null && cursor.moveToPosition(position)) {

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME)
            );

            double quantity = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUANTITY)
            );

            String unit = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.COL_UNIT)
            );

            String expiryDate = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EXPIRY_DATE)
            );

            holder.tvPantryItemName.setText(name);

            holder.tvPantryItemDetails.setText(
                    "Quantity: " + quantity + " " + unit
            );

            if (expiryDate == null || expiryDate.isEmpty()) {
                holder.tvPantryItemExpiry.setText("Expiry: Not provided");
            } else {
                holder.tvPantryItemExpiry.setText(
                        "Expiry: " + expiryDate
                );
            }
        }
    }

    @Override
    public int getItemCount() {

        if (cursor == null) {
            return 0;
        }

        return cursor.getCount();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        TextView tvPantryItemName;
        TextView tvPantryItemDetails;
        TextView tvPantryItemExpiry;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPantryItemName =
                    itemView.findViewById(R.id.tvPantryItemName);

            tvPantryItemDetails =
                    itemView.findViewById(R.id.tvPantryItemDetails);

            tvPantryItemExpiry =
                    itemView.findViewById(R.id.tvPantryItemExpiry);
        }
    }
}