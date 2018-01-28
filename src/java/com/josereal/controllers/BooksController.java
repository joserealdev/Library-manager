package com.josereal.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

import com.josereal.model.Book;
import com.josereal.utilities.*;

public class BooksController {
	
	//Driver para poder establecer conexi�n para la bbdd
	private final static String drv = "com.mysql.jdbc.Driver";
	//Cadena de conexi�n
	private final static String db = "jdbc:mysql://localhost:3306/DATABASE HERE";
	private final static String user = "USER HERE";
	private final static String pass = "PASSWORD HERE";
	
	Connection cn; //Se importa la librer�a de java.sql
	PreparedStatement pst;
	ResultSetMetaData rsmd;

	public ResultSet rs;
	private ArrayList<Book> libros;
	public BooksController() {
            super();
            libros=new ArrayList<Book>();
	}
	
	public void abrirConexion() throws ClassNotFoundException, SQLException{
		
            Class.forName(drv);
            cn = DriverManager.getConnection(db, user, pass);
		
	}
	
	public void cerrarConexion() throws SQLException{
		
            if(rs!=null) rs.close();

            if(pst!=null) pst.close();

            if(cn!=null) cn.close();
	}
	
	public String[] getColumnas() throws SQLException{	
            PreparedStatement preparedStatement=cn.prepareStatement("SELECT * FROM books");
            rs=preparedStatement.executeQuery();
            rsmd = rs.getMetaData();

            int numcolumnas=rsmd.getColumnCount();
            String []columnas =new String[numcolumnas];
            int cont=0; //UTILIZO UN CONTADOR PORQUE EL RESULSET DEBE EMPEZAR EN 1
            for(int x=0;x<numcolumnas;x++){
                cont++;
                columnas[x]=rsmd.getColumnName(cont);
            }

            rs=null;preparedStatement=null; rsmd=null;
            return columnas;
	}
	
	public ArrayList<Book> getLibros() throws IsbnException, SQLException, ParseException {
		
            PreparedStatement preparedStatement=cn.prepareStatement("SELECT * FROM books");
            //El objeto preparedStatement solo lee y lo hace en una direccion
            rs=preparedStatement.executeQuery();
            while(rs.next()){

                int id=rs.getInt("booksid");
                String titulo=rs.getString("title");
                String autor=rs.getString("author");
                String editorial=rs.getString("editorial");
                String isbn=rs.getString("isbn");
                boolean prestado=rs.getBoolean("borrowed");
                Fecha fechaPrestamo=new Fecha(rs.getDate("loanDate"));
                Fecha fechaDevolucion=new Fecha(rs.getDate("returnDate"));
                Book libro=new Book(id, titulo, autor, editorial, isbn, prestado, fechaPrestamo, fechaDevolucion);
                libros.add(libro);

                libro=null;
            }
            rs=null;preparedStatement=null;

            return libros;
	}
	
	public boolean agregarLibro(Book libro) throws SQLException{
		
            boolean agregado=false;
            String sql="insert into books values (?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement=cn.prepareStatement(sql);

            String titulo=libro.getTitulo();
            String autor=libro.getAutor();
            String editorial=libro.getEditorial();
            String isbn=libro.getIsbn();
            boolean prestado=libro.isPrestado();

            java.sql.Date fechaPrestamo=new java.sql.Date(libro.getFechaPrestamo().getDate().getTime());
            java.sql.Date fechaDevolucion=new java.sql.Date(libro.getFechaDevolucion().getDate().getTime());

            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, titulo);
            preparedStatement.setString(3, autor);
            preparedStatement.setString(4, editorial);
            preparedStatement.setString(8, isbn);
            preparedStatement.setBoolean(5, prestado);
            preparedStatement.setDate(6, fechaPrestamo);
            preparedStatement.setDate(7, fechaDevolucion);
            preparedStatement.executeUpdate();
            preparedStatement=null;
            agregado=true;

            return agregado;
	}
	
	public Book buscarLibrolista(String isbn){
            Book libro=new Book();

            for(int x=0;x<libros.size();x++){

                libro=libros.get(x);

                if(isbn.equals(libro.getIsbn())) break;
                else libro=null;

            }

            return libro;
	}
	
	public ArrayList<Book> buscarLibro(String campo, String cadenaBusqueda) throws SQLException, ParseException, IsbnException{
		
            ArrayList<Book> lista=null;
            String sql="SELECT * FROM books WHERE "+campo+" = ?";
            PreparedStatement preparedStatement= cn.prepareStatement(sql);
            preparedStatement.setString(1, cadenaBusqueda);
            rs= preparedStatement.executeQuery();
            rs.last();
            int tam=rs.getRow();
            rs.beforeFirst();
            if(tam>0){
                lista=new ArrayList<Book>();
                while(rs.next()){

                    String titulo=rs.getString("title");
                    String autor=rs.getString("author");
                    String editorial=rs.getString("editorial");
                    String isbn=rs.getString("isbn");
                    boolean prestado=rs.getBoolean("borrowed");
                    Fecha fechaPrestamo=new Fecha(rs.getDate("loanDate"));
                    Fecha fechaDevolucion=new Fecha(rs.getDate("returnDate"));
                    Book libro=new Book(titulo, autor, editorial, isbn, prestado, fechaPrestamo, fechaDevolucion);
                    lista.add(libro);

                    libro=null;
                }
            }
            rs=null;preparedStatement=null;
            return lista;
	}
	
	public ArrayList<Book> librosDisponibles() throws SQLException, ParseException, IsbnException{
            ArrayList<Book> lista=new ArrayList<>();

            PreparedStatement preparedStatement=cn.prepareStatement("select * from books");
            rs=preparedStatement.executeQuery();

            while(rs.next()){
                boolean prestado=rs.getBoolean("prestado");

                if(!prestado){
                    String titulo=rs.getString("title");
                    String autor=rs.getString("author");
                    String editorial=rs.getString("editorial");
                    String isbn=rs.getString("isbn");
                    Fecha fechaPrestamo=new Fecha(rs.getDate("loanDate"));

                    Book libro=new Book(titulo, autor, editorial, isbn, prestado, fechaPrestamo, fechaPrestamo);
                    lista.add(libro);
                    libro=null;
                }


            }
            rs=null;preparedStatement=null;

            return lista;
	}
	
	public ArrayList<Book> librosPrestados() throws SQLException, ParseException, IsbnException{
            ArrayList<Book> lista=new ArrayList<>();

            PreparedStatement preparedStatement=cn.prepareStatement("select * from books");
            rs=preparedStatement.executeQuery();

            while(rs.next()){
                boolean prestado=rs.getBoolean("borrowed");

                if(prestado){
                    String titulo=rs.getString("title");
                    String autor=rs.getString("author");
                    String editorial=rs.getString("editorial");
                    String isbn=rs.getString("isbn");
                    Fecha fechaPrestamo=new Fecha(rs.getDate("loanDate"));
                    Fecha fechaDevolucion=new Fecha(rs.getDate("returnDate"));

                    Book libro=new Book(titulo, autor, editorial, isbn, prestado, fechaPrestamo, fechaDevolucion);
                    lista.add(libro);
                    libro=null;
                }


            }
            rs=null;preparedStatement=null;

            return lista;
	}
	
	public int borrarLibro(Book libro) throws SQLException, ParseException, IsbnException{
            int borrados=0;
            String campo="isbn";
            String sql="delete from books where "+campo+" =?";
            PreparedStatement preparedStatement=cn.prepareStatement(sql);
            preparedStatement.setString(1, libro.getIsbn());
            borrados=preparedStatement.executeUpdate();
            rs=null;preparedStatement=null;
            
            return borrados;
	}
	
	public ArrayList<Book> comprobarDevolucion() throws SQLException, ParseException, IsbnException{
            ArrayList<Book> lista=new ArrayList<>();
            PreparedStatement preparedStatement=cn.prepareStatement("select * from books");
            rs=preparedStatement.executeQuery();

            Fecha hoy=new Fecha();

            while(rs.next()) {
                Fecha fechaDevolucion=new Fecha(rs.getDate("returnDate"));
                boolean prestado=rs.getBoolean("borrowed");

                if(hoy.compareTo(fechaDevolucion)>0 && prestado){

                    int id=rs.getInt("booksid");
                    String titulo=rs.getString("title");
                    String autor=rs.getString("author");
                    String editorial=rs.getString("editorial");
                    String isbn=rs.getString("isbn");
                    Fecha fechaPrestamo=new Fecha(rs.getDate("loanDate"));

                    Book libro=new Book(id, titulo, autor, editorial, isbn, prestado, fechaPrestamo, fechaDevolucion);
                    lista.add(libro);
                    libro=null;
                }

            }

            rs=null;preparedStatement=null;

            return lista;
	}
	
	public boolean prestarLibro(Book libro) throws SQLException{
            boolean prestado=false;
            String sql="Update books Set borrowed=?, loanDate=?, returnDate=? Where isbn=?";
            PreparedStatement preparedStatement=cn.prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            Fecha hoy=new Fecha();
            Fecha devolucion=new Fecha();
            devolucion.agregarDias(15);

            java.sql.Date fechaPrestamo= new java.sql.Date(hoy.getDate().getTime());
            java.sql.Date fechaDevolucion= new java.sql.Date(devolucion.getDate().getTime());

            preparedStatement.setDate(2, fechaPrestamo);
            preparedStatement.setDate(3, fechaDevolucion);
            preparedStatement.setString(4, libro.getIsbn());

            preparedStatement.executeUpdate();
            prestado=true;

            return prestado;
	}
	
	public boolean devolverLibro(Book libro) throws SQLException{
            boolean prestado=false;
            String sql="Update books Set borrowed=?, loanDate=?, returnDate=? Where isbn=?";
            PreparedStatement preparedStatement=cn.prepareStatement(sql);
            preparedStatement.setBoolean(1, false);
            Fecha hoy=new Fecha();

            java.sql.Date today= new java.sql.Date(hoy.getDate().getTime());

            preparedStatement.setDate(2, today);
            preparedStatement.setDate(3, today);
            preparedStatement.setString(4, libro.getIsbn());

            preparedStatement.executeUpdate();
            prestado=true;

            return prestado;
	}
	
	
	
	public int modificarLibro(Book libro) throws SQLException{
            int afectadas=0;

            String sql="Update books Set title=?, author=?, editorial=?, borrowed=?, loanDate=?, returnDate=? Where isbn=?";

            PreparedStatement preparedStatement=cn.prepareStatement(sql);
            preparedStatement.setString(1, libro.getTitulo());
            preparedStatement.setString(2, libro.getAutor());
            preparedStatement.setString(3, libro.getEditorial());
            preparedStatement.setBoolean(4, libro.isPrestado());

            java.sql.Date fechaPrestamo=new java.sql.Date(libro.getFechaPrestamo().getDate().getTime());
            java.sql.Date fechaDevolucion=new java.sql.Date(libro.getFechaDevolucion().getDate().getTime());

            preparedStatement.setDate(5, fechaPrestamo);
            preparedStatement.setDate(6, fechaDevolucion);
            preparedStatement.setString(7, libro.getIsbn());


            afectadas=preparedStatement.executeUpdate();
            return afectadas;
	}
	
}
