package jav.app.myapplication.Data;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import jav.app.myapplication.Model.Blog;
import jav.app.myapplication.R;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Blog> blogList;

    public BlogRecyclerAdapter() {
    }

    public BlogRecyclerAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_post_row,parent,false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Blog blog = blogList.get(position);
        String imageUrl = null;

        holder.title.setText(blog.getTitle());
        holder.desc.setText(blog.getDesc());

        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(blog.getTimestamp())).getTime());
        holder.timestamp.setText(formattedDate);

        imageUrl = blog.getImage();
        Log.d("taggg",""+blog.getImage());

       // TODO: Use Picasso library to load image

        Picasso.get().load(""+imageUrl).into(holder.image);

//        Picasso.with(context).load(imageUrl).into(holder.image);  // used in old dependencies

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView desc;
        public TextView timestamp;
        public ImageView image;
        public String userId;
        public ViewHolder(@NonNull View view, Context ctx) {
            super(view);

            context = ctx;
            title = view.findViewById(R.id.postTitleList);
            desc = view.findViewById(R.id.postTextList);
            image = view.findViewById(R.id.postImageList);
            timestamp = view.findViewById(R.id.timeStamp);
            userId = null;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // we can go to the next activity...
                }
            });
        }
    }

}
