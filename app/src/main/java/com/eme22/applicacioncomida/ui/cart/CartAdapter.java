package com.eme22.applicacioncomida.ui.cart;

import static com.eme22.applicacioncomida.data.api.WebApiAdapter.API_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eme22.applicacioncomida.R;
import com.eme22.applicacioncomida.data.model.CartItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder> {

    private final OnItemClicked listener;
    protected ArrayList<CartItem> mItemList;

    public void addAll(List< CartItem > mcList) {
        for (CartItem mc: mcList) {
            add(mc);
        }
    }

    public void add(CartItem mc) {
        mItemList.add(mc);
        notifyItemInserted(mItemList.size() - 1);
    }

    public void remove(CartItem city) {
        int position = mItemList.indexOf(city);
        if (position > -1) {
            mItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public CartItem getItem(int position) {
        return mItemList.get(position);
    }


    public CartAdapter(OnItemClicked listener) {
        this.listener = listener;
        this.mItemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {

        CartItem item = getItem(position);

        Picasso.get().load(getItemImage(API_URL, (int) item.getItemId())).into(holder.image);

    }
    @Override
    public int getItemCount() {
        return (null != mItemList ? mItemList.size() : 0);
    }

    private String getItemImage(String apiUrl, Integer itemId){
        return "";
    }

    protected class CartItemViewHolder extends RecyclerView.ViewHolder {

        ImageView image = itemView.findViewById(R.id.cartItemImage);
        TextView title = itemView.findViewById(R.id.cartItemName);
        TextView description  = itemView.findViewById(R.id.cartItemDescription);
        TextView price   = itemView.findViewById(R.id.cartItemDescription);
        TextView count  = itemView.findViewById(R.id.cartItemCount);
        Button delete =  itemView.findViewById(R.id.cartItemDelete);


        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            delete.setOnClickListener(v -> listener.onItemClick(getItem(getAdapterPosition())));
        }
    }

    public interface OnItemClicked {
        void onItemClick(CartItem cartItem);
    }
}
