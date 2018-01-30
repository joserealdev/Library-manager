<%-- 
    Document   : delete
    Created on : 27-ene-2018, 23:24:10
    Author     : casa
--%>

<%@page import="java.util.List"%>
<%@page import="java.text.ParseException"%>
<%@page import="com.josereal.utilities.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.josereal.model.Book"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.josereal.controllers.BooksController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="stylesheet.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="functions.js"></script>
        <title>Delete books</title>
    </head>
    <body onload="selFirst()">
        <%
            BooksController controlador = new BooksController();

            try {
                controlador.abrirConexion();
            } catch (ClassNotFoundException | SQLException e) {
                response.sendRedirect("errors/connection.html");
            }
            if(request.getParameter("DeleteBk")!=null){
                List aBuscar=controlador.buscarLibro("isbn", request.getParameter("DeleteBk"));
                Book encontrado=(Book) aBuscar.get(0);

                controlador.borrarLibro(encontrado);
                out.println("<script>alert(\"The book has been deleted\")</script>");
            }
            ArrayList<Book> libros = null;
            String[] columnas = null;
            try {
                libros = controlador.getLibros();
                columnas = controlador.getColumnas();
            } catch (IsbnException | SQLException | ParseException e1) {
                e1.printStackTrace();
            }
            out.print("<FORM ACTION=\"delete.jsp\" METHOD=\"post\">");
            out.println("<table>");
            for (int x = 0; x < columnas.length; x++) {
                out.println("<th>" + columnas[x] + "</th>");
            }
            out.print("<th>Delete</th>");
            for (int x = 0; x < libros.size(); x++) {
                Book sacar = libros.get(x);
                out.println("<tr id=\""+sacar.getIdlibros()+"\">");
                out.println("<td>" + sacar.getIdlibros() + "</td><td>" + sacar.getTitulo() + "</td><td>" + sacar.getAutor() + "</td>"
                        + "<td>" + sacar.getEditorial() + "</td><td>" + sacar.isPrestado() + "</td><td>" + sacar.getFechaPrestamo() + "</td>"
                        + "<td>" + sacar.getFechaDevolucion() + "</td><td>" + sacar.getIsbn() + "</td>"
                        + "<td><input type=\"radio\" name=\"DeleteBk\" value=\""+sacar.getIsbn()+"\" onclick=\"selRow(this)\"></td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<input id=\"delb\" type=\"submit\" value=\"Delete\" disabled>");
            out.println("</form>");

            try {
                controlador.cerrarConexion();
            } catch (SQLException e) {
                // TODO Bloque catch generado automáticamente
                e.printStackTrace();
            }

        %>
        <input type="submit" value="Back" onclick='location.href = "index.html"'/>
    </body>
</html>
