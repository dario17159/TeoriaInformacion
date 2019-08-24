package com.dario.carrizo.teoriainformacion.Commons;

/**
 *  LeveshteinDistance nos permite comparar dos cadenas, pero no de la forma tradicional.
 *  Sino mas bien calculado la distancia entre cadenas la cual es número mínimo de operaciones
 *  (bien una inserción, eliminación o la sustitución de un carácter) requeridas para transformar
 *  una cadena de caracteres en otra.
 *  fuentes de informacion:
 *      https://sites.google.com/site/algoritmossimilaridaddistancia/distancia-de-levenshtein
 *      https://es.wikipedia.org/wiki/Distancia_de_Levenshtein#Aplicaciones
 **/

public class LevenshteinDistance {

    private static int minimum(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    /**
     * Recibe las dos cadenas y las manda por parametro como arreglos de caracteres
     * */
    public static int computeLevenshteinDistance(String str1, String str2) {
        return computeLevenshteinDistance(str1.toCharArray(),
                str2.toCharArray());
    }
    /**
     * Recbile los dos arreglos de caractesres y conforma una matriz distance
     * */
    private static int computeLevenshteinDistance(char [] str1, char [] str2) {
        int [][]distance = new int[str1.length+1][str2.length+1];

        // en los dos primeros for rellena la primer fila y columna
        for(int i=0;i<=str1.length;i++){
            distance[i][0]=i;
        }
        for(int j=0;j<=str2.length;j++){
            distance[0][j]=j;
        }
        for(int i=1;i<=str1.length;i++){
            for(int j=1;j<=str2.length;j++){
                distance[i][j]= minimum(distance[i-1][j]+1,//eliminacion
                        distance[i][j-1]+1,// insersion
                        distance[i-1][j-1]+((str1[i-1]==str2[j-1])?0:1));//sustitucion
            }
        }
        // Se devuelve la esquina final de la matriz donde se almacena la distancia
        return distance[str1.length][str2.length];

    }
}
