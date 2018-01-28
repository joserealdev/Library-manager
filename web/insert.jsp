<%-- 
    Document   : insert
    Created on : 27-ene-2018, 20:06:35
    Author     : casa
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="com.josereal.model.Book"%>
<%@page import="com.josereal.utilities.*"%>
<%@page import="com.josereal.controllers.BooksController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add book to DataBase</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="stylesheet.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <%
            Functions use=new Functions();
            String nIsbn=request.getParameter("isbn");
            if(request.getParameter("isbn")==null){
                out.println("<form action=\"insert.jsp\" method=\"post\">");
                out.println("Title: <input type=\"text\" name=\"title\" placeholder=\"Insert title\" required><br>");
                out.println("Author: <input type=\"text\" name=\"author\" placeholder=\"Insert author\" required><br>");
                out.println("Editorial: <input type=\"text\" name=\"editorial\" placeholder=\"Insert editorial\" required><br>");
                out.println("ISBN: <input type=\"text\" name=\"isbn\" placeholder=\"Insert a valid ISBN-13\" required><br>");
                out.println("<input type=\"submit\" value=\"Send\">");
                out.println("</form>");
            }else{
                try{
                    if(use.comprobarisbn(nIsbn)){
                        String nTitulo=request.getParameter("title");
                        String nAuthor=request.getParameter("author");
                        String nEditorial=request.getParameter("editorial");
                        Fecha today=new Fecha();
                        Book nuevo=null;
                        try {
                            nuevo=new Book(nTitulo, nAuthor, nEditorial, nIsbn, false, today, today);
                            if(use.insertBook(nuevo))out.print("<h1>Insert correctly</h1>");
                            else out.print("<h1>Error</h1>");
                        } catch (IsbnException e1) {
                            out.println("<h1>Error: "+e1.getMessage()+"</h1>");
                        }
                    }else {
                        out.println("<form action=\"insert.jsp\" method=\"post\">");
                        out.println("Title: <input type=\"text\" name=\"title\" placeholder=\"Insert title\" value=\""+request.getParameter("title")+"\" required><br>");
                        out.println("Author: <input type=\"text\" name=\"author\" placeholder=\"Insert author\" value=\""+request.getParameter("author")+"\" required><br>");
                        out.println("Editorial: <input type=\"text\" name=\"editorial\" placeholder=\"Insert editorial\" value=\""+request.getParameter("editorial")+"\" required><br>");
                        out.println("ISBN: <input type=\"text\" name=\"isbn\" placeholder=\"Insert a valid ISBN-13\" value=\""+request.getParameter("isbn")+"\" style=\"border-color:red\" required><br>");
                        out.println("<input type=\"submit\" value=\"Send\">");
                        out.println("</form>");
                        out.println("<script>alert(\"Wrong ISBN\")</script>");
                    }
                }catch(Exception e){
                    out.print(e);
                }
            }
            
        %>
    </body>
</html>
