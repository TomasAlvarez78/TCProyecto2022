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
IGU: '=';
COM: ',';

// Comparadores
EQ: '==';
GT: '>';
GE: '>=';
LT: '<';
LE: '<=';
NEQ: '!=';

// Expresiones logicas
AND: '&&';
OR: '||';

// Aritmetica
SUMA: '+';
RESTA: '-';
MULT: '*'; 
DIV: '/'; 
MOD: '%'; 

// Variables
INT : 'int' ;
DOUBLE: 'double';

// Palabras reservadas
IWHILE: 'while';

// Nombre de variables
VAR: [a-zA-Z] ;

WS : [ \t\n\r] -> skip;

// Declaracion de entero y doble en numeros
ENTERO : DIGITO+ ;
DOBLE: DIGITO+ '.' DIGITO+;

programa : instrucciones EOF ;

instrucciones : instruccion instrucciones
              |
              ;

instruccion : declaracion PyC
            | asignacion PyC
            | bucleWhile 
            ;

declaracion: INT VAR concatenacion 
            | DOUBLE VAR concatenacion 
            | INT asignacion concatenacion 
            | DOUBLE asignacion concatenacion 
            ;

concatenacion: COM VAR concatenacion 
              | COM asignacion
              |
              ;

asignacion: VAR IGU VAR
            | VAR IGU ENTERO
            | VAR IGU DOBLE
            ;

bloque: LA instrucciones LC;

bucleWhile: IWHILE PA cond PC bloque;

cond: e comparadores e 
    | cond AND cond
    | cond OR cond
    ;


comparadores : GT
             | GE
             | LT
             | LE
             | EQ
             | NEQ
             ;

e: term exp;

exp: SUMA e
    | RESTA e
    |
    ;

term: factor t;

t: MULT term
 | DIV term
 | MOD term
 |
 ;

factor: ENTERO
      | DOBLE
      | VAR
      | PA e PC
      ;




