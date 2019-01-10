package com.example.hp.booksrepository;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hp.booksrepository.BD.DadosOpenHelper;
import com.example.hp.booksrepository.BD.Estrutura_BBDD;
import com.example.hp.booksrepository.DOMINIO.ENTIDADE.Book;
import com.example.hp.booksrepository.DOMINIO.REPOSITORIO.BookRepositorio;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Book_Add_Activity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;

    BookRepositorio bookRepositorio;
    private SQLiteDatabase conexao;
    private DadosOpenHelper dadosOpenHelper;
    private RelativeLayout relativeLayout;
    private Book book;

    private EditText textoTitle, textoCategory, textoDescription;
    private ImageView add_new_image;
    private Button button_salvar, button_buscar_imagem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

        textoTitle = findViewById(R.id.add_new_title_id);
        textoCategory = findViewById(R.id.add_new_category_id);
        textoDescription = findViewById(R.id.add_new_description_id);
        add_new_image = findViewById(R.id.add_new_image_id);
        button_salvar = findViewById(R.id.add_save_button_id);
        button_buscar_imagem = findViewById(R.id.buscar_imagem_id);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_id);

        // Pick image from device
        button_buscar_imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        criarconexao();

        // Save to data Base
        button_salvar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                book = new Book();

                if(validaCampos() == false){

                    bookRepositorio.inserir(book);
                    //finish();

                    /*try{
                        bookRepositorio.inserir(book);

                    }catch (SQLException ex){
                        Toast.makeText(getApplicationContext(), "ERRO!! Campos vazios : "+ex.getMessage(), Toast.LENGTH_LONG).show();
                    }*/
                }

                startActivity(new Intent(Book_Add_Activity.this, MainActivity.class));
            }
        });

    }

    private void criarconexao(){
        try{
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            Snackbar.make(relativeLayout, "ConexÃ£o criada com sucesso", Snackbar.LENGTH_LONG).setAction("OK", null).show();

            bookRepositorio = new BookRepositorio(conexao);

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("ERRO");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

    }

    //Validar Campos
    private boolean validaCampos(){

        boolean res = false;

        String titulo = textoTitle.getText().toString();
        String categoria = textoCategory.getText().toString();
        String descricao = textoDescription.getText().toString();

        book.title = titulo;
        book.category = categoria;
        book.description = descricao;

        if(res = isCampoVazio(titulo)){
            textoTitle.requestFocus();
        }
        else if(res = isCampoVazio(categoria)){
            textoCategory.requestFocus();
        }
        else if(res = isCampoVazio(descricao)){
            textoDescription.requestFocus();
        }
        if(res){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.titulo_aviso);
            dlg.setMessage(R.string.mensagem_aviso);
            dlg.setNeutralButton(R.string.ok_aviso, null);
            dlg.show();
        }

        return  res;
    }

    private  boolean isCampoVazio(String valor){
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;

    }
    //Open file
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            Picasso.get().load(mImageUri).into(add_new_image);
        }
    }
}
