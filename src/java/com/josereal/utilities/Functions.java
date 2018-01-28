/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josereal.utilities;

import com.josereal.controllers.BooksController;
import com.josereal.model.Book;
import java.sql.SQLException;

/**
 *
 * @author casa
 */
public class Functions {
    
    public boolean comprobarisbn(String isbn) {
        boolean isbnCorrecto=false;
        String[]campos;
        campos=isbn.split("-");
        isbn=campos[0]+campos[1]+campos[2]+campos[3];
        int DC=Integer.parseInt(campos[4]);
        boolean impar=true;
        int acu=0, num=0;
        for(int x=0;x<isbn.length();x++){
            if(impar){
                num=Integer.parseInt(Character.toString(isbn.charAt(x)));
                num=num*1;
                acu+=num;
            }else{
                num=Integer.parseInt(Character.toString(isbn.charAt(x)));
                num=num*3;
                acu+=num;
            }
            impar=!impar;
        }
        int dc=10-(acu%10);
        if(dc==10)dc=0;
        if(dc==DC) isbnCorrecto=true;

        return isbnCorrecto;
    }
    
    public boolean insertBook(Book book){
        boolean insert=false;
        BooksController controlador=new BooksController();
        try {
            controlador.abrirConexion();
        } catch (ClassNotFoundException | SQLException e) {
            return insert;
        }

        try {
            controlador.agregarLibro(book);
            insert=true;
        } catch (SQLException e) {
            System.out.println("<h1 style=\"Color:red;\">Error: "+e.getMessage()+"</h1>");
        }

        try {
            controlador.cerrarConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    
        return insert;
    }
    
}
