package com.lobomarket.volleyjson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    List<Users> users;
    LayoutInflater inflater;
    Context context;

    public CustomAdapter(Context context, List<Users> users) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custom_users_layout, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fullname;
        public TextView age;
        public ImageView userPhoto;

        public ViewHolder(final View itemView){
            super(itemView);
            fullname = itemView.findViewById(R.id.txtName);
            age = itemView.findViewById(R.id.txtAge);
            userPhoto = itemView.findViewById(R.id.coverImage);


        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Users u = users.get(position);

            String fullName = u.getFname() + " " + u.getLname();
            holder.fullname.setText(fullName);
            holder.age.setText(String.valueOf(u.getAge()));
            Picasso.get().load(u.getUserImg()).into(holder.userPhoto);

    }
}
