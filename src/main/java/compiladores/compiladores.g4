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
BOOL: 'boolean';

// Booleanos
TRUE: 'true';
FALSE: 'false';

// Palabras reservadas
IWHILE: 'while';
IIF: 'if';
IFOR: 'for';
IRETURN: 'return';

// Nombre de variables
VAR: [a-zA-Z]+ ;

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
            | bucle_while 
            | condicional_if
            | bucle_for
            | declaracion_funcion
            | asignacion_funcion
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

op_logicos: AND
          | OR
          ;

op_booleanas: TRUE
            | FALSE
            ;

bucle_while: IWHILE PA cond PC bloque;

condicional_if: IIF PA cond PC bloque;

bucle_for: IFOR PA (declaracion PyC cond PyC post_pre_incremento ) PC bloque;

// Declaracion Funcion
// int nombre (int,float,bool);

declaracion_funcion: tipo_var VAR PA declaracion_argumentos PC PyC;

declaracion_argumentos: tipo_var concatenacion_argumentos_declaracion;

concatenacion_argumentos_declaracion: COM declaracion_argumentos
                                    | 
                                    ;

// Asignacion funcion
// int nombre (int i,float x,bool z){   bloque   }

asignacion_funcion: tipo_var VAR PA asignacion_argumentos PC (LA bloque_funcion LC);

asignacion_argumentos: INT VAR concatenacion_argumentos_asignacion 
                                 | DOUBLE VAR concatenacion_argumentos_asignacion 
                                 | INT asignacion concatenacion_argumentos_asignacion 
                                 | DOUBLE asignacion concatenacion_argumentos_asignacion 
                                 ;

concatenacion_argumentos_asignacion: COM asignacion_argumentos
              |
              ;

bloque_funcion: instrucciones_funcion
          | return_tipo
          ;

instrucciones_funcion: instruccion_funcion instrucciones_funcion;

instruccion_funcion : declaracion PyC
            | asignacion PyC
            | bucle_while 
            | condicional_if
            | bucle_for
            ;

return_tipo: IRETURN VAR PyC
           | IRETURN factor PyC
           | IRETURN PyC
           ;

tipo_var: INT
        | DOUBLE
        | BOOL
        ;

post_pre_incremento: VAR SUMA SUMA
                   | VAR RESTA RESTA
                   | SUMA SUMA VAR
                   | RESTA RESTA VAR
                   | VAR IGU VAR SUMA factor 
                   | VAR IGU VAR RESTA factor     
                   ;

cond: e comparadores e 
    | cond op_logicos cond
    | op_booleanas op_logicos op_booleanas
    | op_booleanas
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
