grammar compiladores;

@header {
package compiladores;
}
// Dado el proyecto base de Java con ANTLR presentado en clases, realizar el archivo .g4 con las expresiones regulares y reglas sintácticas que contemple las siguientes instrucciones:

// declaracion -> int x;
//                double y;
//                int z = 0;
//                double w, q, t;
//                int a = 5, b, c = 10;

// asignacion -> x = 1;
//               z = y;

// iwhile -> while (x comp y) { instrucciones }

// NOTA: Entregar solamente el archivo *.g4

fragment DIGITO : [0-9] ;

// Caracteres especiales
PA : '(';
PC : ')';
LA : '{';
LC : '}';
PyC: ';';
ESP: ' ';
IGU: '=';
COM: ',';

// Variables
INT : 'int' ;
DOUBLE: 'double';

// Palabras reservadas
IWHILE: 'while';

// Nombre de variables
LETRAS: [a-zA-Z] ;

WS : [ \t\n\r] -> skip;

// Declaracion de entero y doble en numeros
ENTERO : DIGITO+ ;
DOBLE: DIGITO+ '.' DIGITO+;

programa : instrucciones EOF ;

instrucciones : instruccion instrucciones
              |
              ;

instruccion : declaracion
            | 
            ;

declaracion : (INT | DOUBLE) ESP declaracion2 PyC
            | asignacion PyC
            ;

declaracion2: LETRAS declaracion3
            | LETRAS ESP IGU ESP (ENTERO | DOBLE) declaracion3;

declaracion3: COM ESP declaracion2
            | ;

asignacion: LETRAS ESP IGU ESP (LETRAS | ENTERO | DOBLE);

bloque: LA instrucciones LC;

inst_simple: . ;

e: term exp;

exp: SUMA term exp
    | RESTA term exp
    |
    ;

term: factor t;

t: MULT factor t
 | DIV factor t
 | MOD factor t
 |
 ;

factor: NUMERO
      | PA e PC
      ;

4 + 5 - 3 * 4 


