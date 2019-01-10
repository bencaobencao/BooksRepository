package com.example.hp.booksrepository;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.booksrepository.BD.DadosOpenHelper;
import com.example.hp.booksrepository.BD.Estrutura_BBDD;
import com.example.hp.booksrepository.DOMINIO.ENTIDADE.Book;
import com.example.hp.booksrepository.DOMINIO.REPOSITORIO.BookRepositorio;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Book_Activity extends AppCompatActivity {

    private TextView textoTitle, textoDescription, textoCategory;
    private ImageView img;
    private Button button_eliminar;
    private SQLiteDatabase conexao;
    private DadosOpenHelper dadosOpenHelper;
    BookRepositorio bookRepositorio;
    private Book book;

    private RecyclerView mrecyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        criarconexao();
        bookRepositorio = new BookRepositorio(conexao);
        textoTitle = (TextView) findViewById(R.id.tv_title_id);
        textoDescription = (TextView) findViewById(R.id.tv_description_id);
        textoCategory = (TextView) findViewById(R.id.tv_category_id);
        img = (ImageView) findViewById(R.id.img_bookthumbnail_id);
        button_eliminar = findViewById(R.id.button_eliminar_id);

        // Recieve data
        Intent intent = getIntent();
        String title = intent.getExtras().getString("Title");
        String category = intent.getExtras().getString("Category");
        String description = intent.getExtras().getString("Description");
        String image = intent.getExtras().getString("imagePath");
        int codigo = intent.getExtras().getInt("Codigo");
        book = new Book(codigo, title, category, description, image);

        //Setting values
        textoTitle.setText(title);
        textoCategory.setText(category);
        textoDescription.setText(description);
        // Picasso.get().load(new File(image)).into(img);
        //img.setImageResource(image);

        // Delete book from Data Base
        button_eliminar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                bookRepositorio.excluir(book.codigo);
                //finish();
                openMethod();
                startActivity(new Intent(Book_Activity.this, MainActivity.class));
            }
        });

    }

    private void criarconexao(){
        try{
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            //Snackbar.make(layoutcontentmain, "ConexÃ£o criada com sucesso", Snackbar.LENGTH_LONG).setAction("OK", null).show();

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("ERRO");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

    }

    private void  openMethod(){
        startActivityForResult(new Intent(Book_Activity.this, FragmentBookList.class), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 0){

            List<Book> books = bookRepositorio.buscarTodos();
            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, books);
            mrecyclerview.setAdapter(recyclerViewAdapter);
        }
    }
}
