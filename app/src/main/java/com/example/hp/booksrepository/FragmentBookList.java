package com.example.hp.booksrepository;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.hp.booksrepository.BD.DadosOpenHelper;
import com.example.hp.booksrepository.DOMINIO.ENTIDADE.Book;
import com.example.hp.booksrepository.DOMINIO.REPOSITORIO.BookRepositorio;

import java.util.ArrayList;
import java.util.List;

public class FragmentBookList extends Fragment {

    private SQLiteDatabase conexao;
    private DadosOpenHelper dadosOpenHelper;
    private LinearLayout linearLayout_id;
    private BookRepositorio bookRepositorio;

    View v;
    private RecyclerView mrecyclerview;
    private List<Book> books;

    public FragmentBookList() {
    }

    private void criarconexao(){
        try{
            dadosOpenHelper = new DadosOpenHelper(getActivity());
            conexao = dadosOpenHelper.getWritableDatabase();
            //Snackbar.make(linearLayout_id, "ConexÃ£o criada com sucesso", Snackbar.LENGTH_LONG).setAction("OK", null).show();

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
            dlg.setTitle("ERRO");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.book_list_fragment, container, false);
        criarconexao();
        mrecyclerview = (RecyclerView) v.findViewById(R.id.book_recyclerview_id);
        mrecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        linearLayout_id = (LinearLayout) v.findViewById(R.id.linearLayout_id);

        bookRepositorio = new BookRepositorio(conexao);

        books = new ArrayList<>();
        books = bookRepositorio.buscarTodos();
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), books);
        mrecyclerview.setAdapter(recyclerViewAdapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
