package com.example.tugasprak7sqliteroom.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasprak7sqliteroom.R;
import com.example.tugasprak7sqliteroom.database.ContactEntity;

public class ContactAdapter extends ListAdapter<ContactEntity, ContactAdapter.ContactViewHolder> {
    private OnItemClickListener listener;

    protected ContactAdapter(ContactActivity contactActivity) {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ContactEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<ContactEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull ContactEntity oldItem, @NonNull ContactEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ContactEntity oldItem, @NonNull ContactEntity newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getNumber().equals(newItem.getNumber()) &&
                    oldItem.getGroup().equals(newItem.getGroup()) &&
                    oldItem.getInstagram().equals(newItem.getInstagram());
        }
    };

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactEntity currentContact = getItem(position);
        holder.tvName.setText(currentContact.getName());
        holder.tvNumber.setText(currentContact.getNumber());
        holder.tvGroup.setText(currentContact.getGroup());
        holder.tvInstagram.setText(currentContact.getInstagram());
    }

    public void setContacts(Object contactEntities) {
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvNumber;
        private TextView tvGroup;
        private TextView tvInstagram;

        public ContactViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvGroup = itemView.findViewById(R.id.tv_group);
            tvInstagram = itemView.findViewById(R.id.tv_instagram);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ContactEntity contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

