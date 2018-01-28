<%-- 
    Document   : show
    Created on : 25-ene-2018, 10:14:20
    Author     : daw2r
--%>

<%@page import="java.text.ParseException"%>
<%@page import="com.josereal.utilities.IsbnException"%>
<%@page import="com.josereal.model.Book"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.josereal.controllers.BooksController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="stylesheet.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Show boooks</title>
    </head>
    <body>
        <%
            BooksController controlador = new BooksController();

            try {
                controlador.abrirConexion();
            } catch (ClassNotFoundException | SQLException e) {
                out.print("<h1>No ha sido posible conectarse a la base de datos</h1>");
            }
            ArrayList<Book> libros = null;
            String[] columnas = null;
            try {
                libros = controlador.getLibros();
                columnas = controlador.getColumnas();
            } catch (IsbnException | SQLException | ParseException e1) {
                e1.printStackTrace();
            }

            out.println("<table>");
            for (int x = 0; x < columnas.length; x++) {
                out.println("<th>" + columnas[x] + "</th>");
            }
            for (int x = 0; x < libros.size(); x++) {
                Book sacar = libros.get(x);
                out.println("<tr>");
                out.println("<td>" + sacar.getIdlibros() + "</td><td>" + sacar.getTitulo() + "</td><td>" + sacar.getAutor() + "</td><td>" + sacar.getEditorial() + "</td><td>" + sacar.isPrestado() + "</td><td>" + sacar.getFechaPrestamo() + "</td><td>" + sacar.getFechaDevolucion() + "</td><td>" + sacar.getIsbn() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            try {
                controlador.cerrarConexion();
            } catch (SQLException e) {
                // TODO Bloque catch generado automáticamente
                e.printStackTrace();
            }

        %>

        <input type="submit" value="Back" onclick='location.href = "index.html"' />
    </body>
</html>
