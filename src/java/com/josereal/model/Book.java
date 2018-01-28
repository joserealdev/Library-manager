
package com.josereal.model;

import com.josereal.utilities.*;

public class Book {
	
	private int idlibros;
	private String titulo, autor, editorial, isbn;
	private boolean prestado;
	private Fecha fechaPrestamo, fechaDevolucion;
	
	public Book(){
		super();	
	}

	public Book(String titulo, String autor, String editorial, String isbn, boolean prestado,
			Fecha fechaPrestamo, Fecha fechaDevolucion) throws IsbnException {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.setIsbn(isbn);
		this.prestado = prestado;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
	}
	
	

	public Book(int idlibros, String titulo, String autor, String editorial, String isbn, boolean prestado,
			Fecha fechaPrestamo, Fecha fechaDevolucion) throws IsbnException {
		super();
		this.idlibros = idlibros;
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.setIsbn(isbn);
		this.prestado = prestado;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
	}

	public int getIdlibros() {
		return idlibros;
	}

	public void setIdlibros(int idlibros) {
		this.idlibros = idlibros;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) throws IsbnException {
		if(compruebaIsbn(isbn)){
		this.isbn = isbn;}
	}

	private boolean compruebaIsbn(String isbn) throws IsbnException{
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



	public boolean isPrestado() {
		return prestado;
	}

	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}

	public Fecha getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Fecha fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public Fecha getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Fecha fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}



	@Override
	public String toString() {
		return "Libro [titulo=" + titulo + ", autor=" + autor + ", editorial=" + editorial
				+ ", isbn=" + isbn + ", prestado=" + prestado + ", fechaPrestamo=" + fechaPrestamo
				+ ", fechaDevolucion=" + fechaDevolucion + "]";
	}
	
	public String toFalso() {
		return "Libro [titulo=" + titulo + ", autor=" + autor + ", editorial=" + editorial
				+ ", isbn=" + isbn + ", prestado=" + prestado + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}	
}
