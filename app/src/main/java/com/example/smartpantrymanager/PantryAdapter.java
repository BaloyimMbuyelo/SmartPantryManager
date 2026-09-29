package com.example.smartpantrymanager;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class PantryAdapter
        extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private Cursor cursor;
    private final DatabaseHelper databaseHelper;
    private final Context context;

    public PantryAdapter(Context context, Cursor cursor) {

        this.context = context;
        this.cursor = cursor;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_pantry,
                        parent,
                        false
                );

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder holder,
            int position
    ) {

        if (cursor == null ||
                !cursor.moveToPosition(position)) {

            return;
        }

        int id = cursor.getInt(
                cursor.getColumnIndexOrThrow(
                        DatabaseHelper.COL_ID
                )
        );

        String name = cursor.getString(
                cursor.getColumnIndexOrThrow(
                        DatabaseHelper.COL_NAME
                )
        );

        double quantity = cursor.getDouble(
                cursor.getColumnIndexOrThrow(
                        DatabaseHelper.COL_QUANTITY
                )
        );

        String unit = cursor.getString(
                cursor.getColumnIndexOrThrow(
                        DatabaseHelper.COL_UNIT
                )
        );

        String expiryDate = cursor.getString(
                cursor.getColumnIndexOrThrow(
                        DatabaseHelper.COL_EXPIRY_DATE
                )
        );

        // Display ingredient name
        holder.tvPantryItemName.setText(name);

        // Display quantity and unit
        holder.tvPantryItemDetails.setText(
                "Quantity: " + quantity + " " + unit
        );

        // Display expiry date
        if (expiryDate == null ||
                expiryDate.isEmpty()) {

            holder.tvPantryItemExpiry.setText(
                    "Expiry: Not provided"
            );

        } else {

            holder.tvPantryItemExpiry.setText(
                    "Expiry: " + expiryDate
            );
        }

        // EDIT BUTTON
        holder.btnEdit.setOnClickListener(v -> {

            if (context instanceof MainActivity) {

                ((MainActivity) context).openEditIngredient(
                        id,
                        name,
                        quantity,
                        unit,
                        expiryDate
                );
            }
        });

        // DELETE BUTTON
        holder.btnDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Delete Ingredient")
                    .setMessage(
                            "Are you sure you want to delete "
                                    + name
                                    + "?"
                    )
                    .setPositiveButton(
                            "Delete",
                            (dialog, which) -> {

                                databaseHelper.deletePantryItem(id);

                                if (context instanceof MainActivity) {

                                    ((MainActivity) context)
                                            .loadPantryItems();
                                }
                            }
                    )
                    .setNegativeButton(
                            "Cancel",
                            null
                    )
                    .show();
        });
    }

    @Override
    public int getItemCount() {

        if (cursor == null) {
            return 0;
        }

        return cursor.getCount();
    }

    public static class PantryViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvPantryItemName;
        TextView tvPantryItemDetails;
        TextView tvPantryItemExpiry;

        Button btnEdit;
        Button btnDelete;

        public PantryViewHolder(
                @NonNull View itemView
        ) {

            super(itemView);

            tvPantryItemName =
                    itemView.findViewById(
                            R.id.tvPantryItemName
                    );

            tvPantryItemDetails =
                    itemView.findViewById(
                            R.id.tvPantryItemDetails
                    );

            tvPantryItemExpiry =
                    itemView.findViewById(
                            R.id.tvPantryItemExpiry
                    );

            btnEdit =
                    itemView.findViewById(
                            R.id.btnEdit
                    );

            btnDelete =
                    itemView.findViewById(
                            R.id.btnDelete
                    );
        }
    }
}