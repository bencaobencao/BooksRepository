package com.example.hp.booksrepository.DOMINIO.ENTIDADE;

public class Book {
    public int codigo;
    public String title;
    public String category;
    public String description;
    public String imagePath;

    public Book() {
    }

    public Book(int codigo, String title, String category, String description , String imagePath) {
        this.codigo = codigo;
        this.title = title;
        this.category = category;
        this.description = description;
        this.imagePath = imagePath;
    }
/*
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }*/
}
