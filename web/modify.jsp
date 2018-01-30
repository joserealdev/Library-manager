<%-- 
    Document   : modify
    Created on : 30-ene-2018, 18:59:37
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
        <title>Modify books</title>
    </head>
    <body onload="selFirst()">
        <%
            BooksController controlador = new BooksController();
            try {
                controlador.abrirConexion();
            } catch (ClassNotFoundException | SQLException e) {
                response.sendRedirect("errors/connection.html");
            }
            if(request.getParameter("isbn")==null){
                if(request.getParameter("ModifyBk")==null){

                    ArrayList<Book> libros = null;
                    String[] columnas = null;
                    try {
                        libros = controlador.getLibros();
                        columnas = controlador.getColumnas();
                    } catch (IsbnException | SQLException | ParseException e1) {
                        e1.printStackTrace();
                    }
                    out.print("<FORM ACTION=\"modify.jsp\" METHOD=\"post\">");
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
                                + "<td><input type=\"radio\" name=\"ModifyBk\" value=\""+sacar.getIsbn()+"\" onclick=\"selRow(this)\"></td>");
                        out.println("</tr>");
                    }

                    out.println("</table>");
                    out.println("<input id=\"delb\" type=\"submit\" value=\"Modify\" disabled>");
                    out.println("</form>");

                    try {
                        controlador.cerrarConexion();
                    } catch (SQLException e) {
                        // TODO Bloque catch generado automáticamente
                        e.printStackTrace();
                    }
                    out.println("<input type=\"submit\" value=\"Back\" onclick='location.href = \"index.html\"'/>");
                }else{
                    List aBuscar=controlador.buscarLibro("isbn", request.getParameter("ModifyBk"));
                    Book found=(Book) aBuscar.get(0);
                    out.println("<FORM ACTION=\"modify.jsp\" METHOD=\"post\">");
                    out.println("Title: <input type=\"text\" name=\"title\" placeholder=\"Insert title\" value=\""+found.getTitulo()+"\" required><br>");
                    out.println("Author: <input type=\"text\" name=\"author\" placeholder=\"Insert author\" value=\""+found.getAutor()+"\" required><br>");
                    out.println("Editorial: <input type=\"text\" name=\"editorial\" placeholder=\"Insert editorial\" value=\""+found.getEditorial()+"\" required><br>");
                    out.println("ISBN: <input type=\"text\" name=\"isbn\" placeholder=\"Insert a valid ISBN-13\" value=\""+request.getParameter("ModifyBk")+"\" disabled required><br>");
                    out.println("<input type=\"text\" name=\"loandate\" value=\""+found.getFechaPrestamo()+"\" hidden>");
                    out.println("<input type=\"text\" name=\"returndate\" value=\""+found.getFechaDevolucion()+"\" hidden>");
                    out.println("<input type=\"text\" name=\"borrowed\" value=\""+found.isPrestado()+"\" hidden>");
                    out.println("<input type=\"submit\" value=\"Send\">");
                    out.println("</form>");
                    out.println("<input type=\"submit\" value=\"Cancel\" onclick='location.href = \"modify.jsp\"'/>");
                    
                }
            }else{
                String title=request.getParameter("title");
                String author=request.getParameter("author");
                String editorial=request.getParameter("editorial");
                String isbn=request.getParameter("isbn");
                boolean borrowed=Boolean.parseBoolean(request.getParameter("borrowed"));
                Fecha loandate=new Fecha(request.getParameter("loandate"));
                Fecha returndate=new Fecha(request.getParameter("returndate"));
                Book modify=new Book(title, author, editorial, isbn, borrowed, loandate, returndate);
                controlador.modificarLibro(modify);
                out.println("<h1>The book \""+title+"\" has been modified correctly");
                out.println("<input type=\"submit\" value=\"Back\" onclick='location.href = \"modify.jsp\"'/>");
            
            }
            
            //Close connection
            try {
                controlador.cerrarConexion();
            } catch (SQLException e) {
                // TODO Bloque catch generado automáticamente
                e.printStackTrace();
            }
        %>
    </body>
</html>