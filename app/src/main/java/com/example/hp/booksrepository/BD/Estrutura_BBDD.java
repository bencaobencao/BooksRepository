package com.example.hp.booksrepository.BD;

public class Estrutura_BBDD {

    public  static String getCreateTableBook(){

        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS BOOKS (");
        sql.append("        CODIGO     INTEGER         PRIMARY KEY AUTOINCREMENT   NOT NULL,   ");
        sql.append("        IMAGEM     VARCHAR (250)   DEFAULT (''), " );
        sql.append("        TITULO     VARCHAR (250)   NOT NULL DEFAULT (''), " );
        sql.append("        CATEGORIA  VARCHAR (250)   NOT NULL DEFAULT (''), " );
        sql.append("        DESCRICAO  VARCHAR (250)   NOT NULL DEFAULT ('') ) ");

        return sql.toString();
    }
}
