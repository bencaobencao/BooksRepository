package com.example.hp.booksrepository;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.hp.booksrepository.BD.DadosOpenHelper;
import com.example.hp.booksrepository.DOMINIO.ENTIDADE.Book;
import com.example.hp.booksrepository.DOMINIO.REPOSITORIO.BookRepositorio;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase conexao;
    private DadosOpenHelper dadosOpenHelper;
    private CoordinatorLayout layoutcontentmain;
    BookRepositorio bookRepositorio;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private android.app.AlertDialog.Builder mrecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Book_Add_Activity.class));

            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tablelayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        bookRepositorio = new BookRepositorio();


        // Add fragment here
        FragmentBookList booksFragment = new FragmentBookList();

        adapter.addFragment(booksFragment, "");
        adapter.addFragment(new FragmentAboutAuthor(), "");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_library);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_person);

        // remove Shadow from the action bar

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        layoutcontentmain = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_id);

        criarconexao();
    }

    private void criarconexao(){
        try{
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            Snackbar.make(layoutcontentmain, "ConexÃ£o criada com sucesso", Snackbar.LENGTH_LONG).setAction("OK", null).show();

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("ERRO");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

    }

     /*
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }*/
}
