package com.example.hp.booksrepository.DOMINIO.REPOSITORIO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hp.booksrepository.BD.Estrutura_BBDD;
import com.example.hp.booksrepository.DOMINIO.ENTIDADE.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepositorio {

    private SQLiteDatabase conexao;

    public BookRepositorio(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public BookRepositorio() {

    }

    public void inserir(Book book){

        //create a new map of value, where column names are the keys
        ContentValues values = new ContentValues();

        values.put("TITULO", book.title);
        values.put("CATEGORIA", book.category);
        values.put("DESCRICAO", book.description);
        values.put("IMAGEM", book.imagePath);

        conexao.insertOrThrow("BOOKS",null, values );

    }

    public void excluir(int codigo){
        String [] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);

        conexao.delete("BOOKS","CODIGO = ?", parametros );

    }

    public void alterar(Book book){

        //create a new map of value, where column names are the keys
        ContentValues values = new ContentValues();

        values.put("TITULO", book.title);
        values.put("CATEGORIA", book.category);
        values.put("DESCRICAO", book.description);
        values.put("IMAGEM", book.imagePath);

        String [] parametros = new String[1];
        parametros[0] = String.valueOf(book.codigo);

        conexao.update("BOOKS", values, "CODIGO = ?", parametros );

    }

    public List<Book> buscarTodos(){
        List<Book> books = new ArrayList<Book>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT CODIGO , TITULO, CATEGORIA, DESCRICAO, IMAGEM ");
        sql.append("    FROM BOOKS ");

        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0){
            resultado.moveToFirst();

            do{
                Book book = new Book();

                book.codigo = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                book.title = resultado.getString(resultado.getColumnIndexOrThrow("TITULO"));
                book.category = resultado.getString(resultado.getColumnIndexOrThrow("CATEGORIA"));
                book.description = resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));
                book.imagePath = resultado.getString(resultado.getColumnIndexOrThrow("IMAGEM"));

                books.add(book);

            }while (resultado.moveToNext());
        }

        return books;
    }

    public Book buscarLivro(int codigo){
        Book book = new Book();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT CODIGO , TITULO, CATEGORIA, DESCRICAO, IMAGEM ");
        sql.append("    FROM BOOKS ");
        sql.append("    WHERE CODIGO = ? ");


        String [] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0){

            resultado.moveToFirst();

            book.codigo = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
            book.title = resultado.getString(resultado.getColumnIndexOrThrow("TITULO"));
            book.category = resultado.getString(resultado.getColumnIndexOrThrow("CATEGORIA"));
            book.description = resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));
            book.imagePath = resultado.getString(resultado.getColumnIndexOrThrow("IMAGEM"));

            return book;
        }
        return null;
    }
}
