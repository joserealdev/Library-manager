/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josereal.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Jose
 */
public class Fecha implements Comparable<Fecha>{
    
    private Calendar fecha;
    private int dia,mes,ano;

    public Fecha() {
        fecha=GregorianCalendar.getInstance(Locale.getDefault());
    }
    
    public Fecha(Date date) throws ParseException{
    	
    	fecha=Calendar.getInstance();
    	fecha.setTime(date);
    	
    	
    }

    public Fecha(String date) throws FechaIncorrecta{
        if (!isFechaValida(date)){
            throw new FechaIncorrecta("Introduce una fecha válida");
        }
        this.setFecha(date);
        
    }


    private void setFecha(String date) {
        //Rompe la cadena y la almacena en el calendario gregoriano
        String [] arrayFecha=date.split("/");
        this.fecha=new GregorianCalendar(Integer.parseInt(arrayFecha[2]),Integer.parseInt(arrayFecha[1])-1,Integer.parseInt(arrayFecha[0]));
        this.setAno(Integer.parseInt(arrayFecha[0]));
        this.setMes(Integer.parseInt(arrayFecha[1])-1);
        this.setDia(Integer.parseInt(arrayFecha[2]));
    }

    public int getDia() {
        return dia;
    }

    private void setDia(int dia) {
        this.dia=this.fecha.get(Calendar.DAY_OF_MONTH);
    }

    public int getMes() {
        return mes+1;
    }

    public void setMes(int mes) {
        this.mes =this.fecha.get((Calendar.MONTH));
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = fecha.get(Calendar.YEAR);
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }
    
    public Date getDate(){
        Date date=null;
        if (fecha!=null){
            date=fecha.getTime();
            
        }
        return date;
    }
    
    public void agregarDias (int dias){
        this.fecha.add(Calendar.DAY_OF_MONTH,dias);
    }
    

    //Método que comprueba si la fecha es correcta.
    private static boolean isFechaValida(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            formatoFecha.setLenient(false); //Evita que se corrija la fecha
            formatoFecha.parse(fecha);
        }
        catch (ParseException e) {
            return false;
        }
        return true;
    }
    
    public String diadehoy(){
    
        Date fechahoy=new Date();
        String[] hoy=fechahoy.toString().split(" ");
        String[] meses={"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        int meshoy=0;
        int x=0;
        for(x=1;x<meses.length;x++){
        
            if(hoy[1].equalsIgnoreCase(meses[x])) {meshoy=x; break;}
        
        }
        String fechaActual=hoy[2]+"/"+meshoy+"/"+hoy[5];
        
        return fechaActual;
    
    
    }
    
//    public String calcularEdas(String fecha){
//        Date fechahoy=new Date();
//        String[] hoy=fechahoy.toString().split(" ");
//        String[] meses={"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
//        int meshoy=0;
//        for(int x=1;x<meses.length;x++){
//        
//            if(hoy[1].equalsIgnoreCase(meses[x])) {meshoy=x; break;}
//        
//        }
//
//        int dia=Integer.parseInt(hoy[2]);
//        int anoactual=Integer.parseInt(hoy[5]);
//        int edad=anoactual-fecha.getAño();
//        
//        if(fecha.getMes()<=meshoy){
//            if(fecha.getDia()<=dia){
//                System.out.println("Tienes "+edad+" años");}
//            else{
//                edad--;
//                System.out.println("Tienes "+edad+" años");
//            }
//        } else {       
//            edad--;
//            System.out.println("Tienes "+edad+" años");
//        }
//        String fin="Tienes "+edad+" años";
//        
//        return fin;
//        
//    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(fecha.getTime());
    }


    public String toFile() {
        mes++;
        return dia + "/" + mes + "/" + ano;
    }
    
    

    @Override
    public int compareTo(Fecha f) {
        Fecha fecha=(Fecha) f;
        return this.fecha.compareTo(f.fecha);
    } 
}
