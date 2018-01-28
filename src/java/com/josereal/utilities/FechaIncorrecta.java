/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josereal.utilities;

/**
 *
 * @author Jose
 */
public class FechaIncorrecta extends Exception{
    public FechaIncorrecta(String fecha){
        super(fecha);
    } 
}
