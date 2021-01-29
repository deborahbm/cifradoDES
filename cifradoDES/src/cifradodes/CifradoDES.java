/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cifradodes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 *
 * @author dealm
 */
public class CifradoDES {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
        // TODO code application logic here
        
        //declaración de variables
        int opcion;
        Scanner consola = new Scanner(System.in);
        String archivoEntrada;
        boolean salir= false;
        
        
        InputStreamReader leer_clave= new InputStreamReader (System.in);
        BufferedReader buff_clave= new BufferedReader (leer_clave);
        
        //lectura de clave del usuario para cifrar y descifrar archivo.
        System.out.print("Introduzca una clave (el método utilizado será el algoritmo DES):");
        String clave= buff_clave.readLine();
        
        //pasa la clave a la clase SecretKey
        SecretKeyFactory skf= SecretKeyFactory.getInstance("DES");
        DESKeySpec kspec = new DESKeySpec (clave.getBytes ());        
        SecretKey ks= skf.generateSecret(kspec);              
        
        while (!salir){
           //muestra menú con opciones de cifrado o descifrado
            System.out.println("Seleccione una opción:");
            System.out.println("1.Cifrar"); 
            System.out.println("2.Descifrar");
            System.out.println("3.Salir");
        
            opcion=consola.nextInt();
            consola.nextLine();
            
            //Opcion 1 Cifrar
            switch (opcion){
                case 1:
                    System.out.print("Introduzca nombre del archivo a cifrar:");                
                    archivoEntrada = consola.nextLine();
                    InputStream archivoLeer= new FileInputStream (archivoEntrada);
                               
                    byte[] buffer=new byte[1024];
                    int leidos=archivoLeer.read (buffer);

                    Cipher cifrado=Cipher.getInstance("DES");
                    cifrado.init (Cipher.ENCRYPT_MODE,ks);

                    byte[] bloque_cifrado;
                    String textoCifrado= new String();

                    while (leidos != -1){
                        bloque_cifrado=cifrado.update(buffer, 0, leidos);
                        textoCifrado= textoCifrado + new String(bloque_cifrado,"ISO-8859-1");

                        leidos= archivoLeer.read(buffer);
                    }
                
                    archivoLeer.close();
                
                    bloque_cifrado= cifrado.doFinal();
                    textoCifrado= textoCifrado+ new String(bloque_cifrado,"ISO-8859-1");
                
                    OutputStream file_out = new FileOutputStream("salida.txt");
                    file_out.write (textoCifrado.getBytes("ISO-8859-1"));
                    
                    System.out.println ("Se ha creado el fichero cifrado con el nombre: salida.txt");
                    
                    break;
                    
                //Opción 2 Descifrar   
                case 2:
                    System.out.print("Introduzca nombre del archivo a descifrar:");                
                    archivoEntrada = consola.nextLine();
                    archivoLeer= new FileInputStream (archivoEntrada);
                               
                    buffer=new byte[1024];
                    leidos=archivoLeer.read (buffer);

                    cifrado=Cipher.getInstance("DES");  
                    cifrado.init (Cipher.DECRYPT_MODE,ks);

                    byte[] bloque_descifrado;
                    String textoDescifrado= new String();

                    while (leidos != -1){
                        bloque_descifrado=cifrado.update(buffer, 0, leidos);
                        textoDescifrado= textoDescifrado + new String(bloque_descifrado,"ISO-8859-1");

                        leidos= archivoLeer.read(buffer);
                    }
                
                    archivoLeer.close();
                
                    bloque_descifrado= cifrado.doFinal();
                    textoCifrado= textoDescifrado+ new String(bloque_descifrado,"ISO-8859-1");
                
                    OutputStream fich_des = new FileOutputStream("salidaDescifrado.txt");
                    fich_des.write (textoCifrado.getBytes("ISO-8859-1"));
                    
                    System.out.println ("Se ha creado el fichero descifrado con el nombre: salidaDescifrado.txt");
                    
                    break;
                    
                //Salir    
                case 3:
                    salir = true;
                    break;
                    
                default:
                        System.out.println("Solo números entre 1 y 3");
                    
            
            }
      
        
        }
        
    }       
 
}
