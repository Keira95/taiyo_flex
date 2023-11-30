package com.erp.Taiyo.adapter;

import com.erp.Taiyo.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MenuButtonAdapter extends RecyclerView.Adapter<MenuButtonAdapter.ViewHolder> {

    private List<String> menuButtons;

    public MenuButtonAdapter(List<String> menuButtons) {
        this.menuButtons = menuButtons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String buttonText = menuButtons.get(position);
        holder.button.setText(buttonText);
    }

    @Override
    public int getItemCount() {
        return menuButtons.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView button;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.menuButton);
        }
    }
}