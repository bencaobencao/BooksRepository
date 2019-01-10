package com.example.hp.booksrepository;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.booksrepository.DOMINIO.ENTIDADE.Book;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Book> books;


    public RecyclerViewAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v;
        LayoutInflater mInflater = LayoutInflater.from(context);
        v = mInflater.inflate(R.layout.cardview_item_book, viewGroup, false );
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Book singleBook = books.get(position);
        holder.tv_book_title.setText(books.get(position).title);

        //holder.img_book_thumbnail.setImageResource(Integer.parseInt(books.get(position).imagePath));

        //Picasso.get().load(new File(books.get(position).imagePath)).into(holder.img_book_thumbnail);

        //Set click Listener

        holder.cardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Book_Activity.class);

                //passing data to the new activicty
                intent.putExtra("Title", singleBook.title);
                intent.putExtra("imagePath", singleBook.imagePath);
                intent.putExtra("Category", singleBook.category);
                intent.putExtra("Description", singleBook.description);
                intent.putExtra("Codigo", singleBook.codigo);

                // Start the activicty
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_book_title = itemView.findViewById(R.id.book_title_id);
            img_book_thumbnail = itemView.findViewById(R.id.book_image_id);
            cardView = itemView.findViewById(R.id.cardview_id);

        }
    }
}
