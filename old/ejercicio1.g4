grammar compiladores;

@header {
package compiladores;
}
/*
    Dado el proyecto base de Java con ANTLR presentado en clases, realizar el archivo .g4 que contemple lo siguiente:

    Definir una Expresión Regular para capturar fechas correspondientes a los meses pares (formato DD/MM/YYYY).
    Definir una Expresión Regular para capturar horas correspondientes a las horas entre las 08:00 y las 12:59 (formato HH:MM).
    Definir una Expresión Regular para capturar horas correspondientes a las horas entre las 18:30 y las 21:30 (formato HH:MM).

    Al ejecutar el programa, deberá imprimir en pantalla el número de línea, el tipo de token y el token encontrado (ver archivo ejemplo adjunto). 
*/

// // 1er Ejercicio
// // Definir una Expresión Regular para capturar fechas correspondientes a 
// // los meses pares (formato DD/MM/YYYY).

// // Agarra todas las posiblidades de Dia
// fragment DIA : ('0'[1-9]|'1'[0-9]|'2'[0-9]|'3'[0-1]) ;

// // Agarra las posiblididades de mes que queremos (Pares)
// fragment MES : '/'('0'[2468] | '1'[02]) ;

// // Agarra todas las posiblidades de anio (entre 1000 a 2999)
// fragment ANO : '/'([1-2])([0-9])([0-9])([0-9]);

// FECHA : (DIA)(MES)(ANO);

// ID: (FECHA)([ \t\r\n]+);

// OTRO: . ;

// s : ID   { System.out.println("ID ->" + $ID.getText() + "<--"); }     s
//  | FECHA   { ; }     s
//  | OTRO  { ; }   s
//  | EOF
//  ;

// // 2do Ejercicio
// // Definir una Expresión Regular para capturar horas correspondientes a 
// // las horas entre las 08:00 y las 12:59 (formato HH:MM).

// // Agarra todas las posiblidades de Dia
// fragment DIA : ('0'[1-9]|'1'[0-9]|'2'[0-9]|'3'[0-1]) ;

// // Agarra las posiblididades de mes que queremos (Pares)
// fragment MES : '/'('0'[1-9] | '1'[0-2]) ;

// // Agarra todas las posiblidades de anio (entre 1000 a 2999)
// fragment ANO : '/'([1-2])([0-9])([0-9])([0-9]);

// fragment HORA1 : ( ('0'[89]) | ([1][0-2]) )':'([0-5][0-9]);
// HORA_1 : HORA1 ;

// FECHA : (DIA)(MES)(ANO);

// ID: (FECHA)([ \t\r\n]+)(HORA_1);

// OTRO: . ;

// s : ID   { System.out.println("ID ->" + $ID.getText() + "<--"); }     s
//  | FECHA   { ; }     s
//  | OTRO  { ; }   s
//  | EOF
//  ;

// 3er Ejercicio
// Definir una Expresión Regular para capturar horas correspondientes a las horas 
// entre las 18:30 y las 21:30 (formato HH:MM).

// Agarra todas las posiblidades de Dia
fragment DIA : ('0'[1-9]|'1'[0-9]|'2'[0-9]|'3'[0-1]) ;

// Agarra las posiblididades de mes que queremos (Pares)
fragment MES : '/'('0'[1-9] | '1'[0-2]) ;

// Agarra todas las posiblidades de anio (entre 1000 a 2999)
fragment ANO : '/'([1-2])([0-9])([0-9])([0-9]);


// NO HACE FALTA
// fragment MIN1 : ':'(([3-5][0-9]));
// fragment MIN2 : ':'(([3-5][0-9]));

fragment HORA1 : ( ('1'[8]) )':'(([3-5][0-9]));
fragment HORA2 : ( ('1'[9]) )':'(([0-5][0-9])) | ( ('2'[0]) )':'(([0-5][0-9]));
fragment HORA3 : ( ('2'[1]) )':'(([0-2][0-9]) | '30');

HORA_1 : HORA1 ;
HORA_2 : HORA2 ;
HORA_3 : HORA3 ;

//Se puede poner sin parentesis
FECHA : (DIA)(MES)(ANO);

ID: (FECHA)([ \t\r\n]+)(HORA_1) | (FECHA)([ \t\r\n]+)(HORA_2) | (FECHA)([ \t\r\n]+)(HORA_3);

OTRO: . ;

// Si FECHA y OTRO no hacen nada, no hacen falta ponerlos.
s : ID   { System.out.println("ID ->" + $ID.getText() + "<--"); }     s
 | FECHA   { ; }     s
 | OTRO  { ; }   s
 | EOF
 ;

// Si FECHA, OTRO no hacen nada, no hace falta ponerlas