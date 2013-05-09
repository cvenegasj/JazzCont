/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.util;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Venegas
 */
public class ConversorPCGE {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BufferedReader br = null;

        try {

            long inicio = System.currentTimeMillis();
            
            String sCurrentLine;

            File fileIn = new File("C:\\Users\\Venegas\\Desktop\\entrada.txt");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn), "CP1252"));


            File fileOut = new File("C:\\Users\\Venegas\\Desktop\\salida.txt");

            // if file doesnt exists, then create it
            if (!fileOut.exists()) {
                fileOut.createNewFile();
            }

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut), "UTF-8"));

            Pattern p1 = Pattern.compile("^\\d+");

            // lee línea por línea
            while ((sCurrentLine = br.readLine()) != null) {

                Matcher m1 = p1.matcher(sCurrentLine);

                if (m1.find()) {
                    String s1 = m1.group();
                    String s2 = sCurrentLine.substring(m1.end() + 1);

                    if (s1.length() == 3) {
                        out.append(String.format("insert into cuentacontable values ('%s', '%s', %s, %s);\r\n", s1, s2, "null", s1.substring(0, 2)));
                    }

                    if (s1.length() == 4) {                        
                        out.append(String.format("insert into cuentacontable values ('%s', '%s', '%s', %s);\r\n", s1, s2, s1.substring(0, s1.length() - 1), s1.substring(0, 2)));
                    }

                    if (s1.length() == 5) {
                        out.append(String.format("insert into cuentacontable values ('%s', '%s', '%s', %s);\r\n", s1, s2, s1.substring(0, s1.length() - 1), s1.substring(0, 2)));
                    }
                }
            }

            //out.flush();
            out.close();
            
            long fin = System.currentTimeMillis();
            
            System.out.println("Tiempo de proceso: " + (fin - inicio) + "ms");
            System.out.println("Done :)");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
