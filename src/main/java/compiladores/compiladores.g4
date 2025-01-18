grammar compiladores;

@header {
package compiladores;
}

fragment DIGITO : [0-9] ;

// Bool useless for the moment,

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
BOOL: 'bool';

// Booleanos
ITRUE: 'true';
IFALSE: 'false';

// Palabras reservadas
IWHILE: 'while';
IIF: 'if';
IELSE: 'else';
IFOR: 'for';
IMAIN: 'main';
IRETURN: 'return';

// Nombre de variables
VAR: [a-zA-Z]+ ;

// Declaracion de entero, doble y bool en numeros
ENTERO : DIGITO+ ;
DOBLE: DIGITO+ '.' DIGITO+;
BOOLEANO: ITRUE | IFALSE;

// Skip
WS : [ \t\n\r] -> skip;

programa : instrucciones EOF ;

instrucciones : instruccion instrucciones
              |
              ;

instruccion : main_function
            | declaracion PyC
            | asignacion PyC
            | concatenacion PyC
            | incremento PyC
            | decremento PyC
            | bucle_while 
            | condicional_if
            | bucle_for
            | declaracion_funcion
            | asignacion_funcion
            | llamado_funcion PyC
            | return_tipo PyC
            ;

main_function: INT IMAIN PA PC bloque;

declaracion: tipo_var VAR declaracion_concat;

declaracion_concat: COM VAR declaracion_concat 
            |
            ;
//  a = 10;
// int a = 10;
asignacion: tipo_var VAR IGU e
            | VAR IGU e 
            | 
            ;
// int a;
// int a = 10, b = 10;

// int a = 1 + 2, b = 1;
concatenacion: tipo_var VAR concatenacion
            | tipo_var VAR IGU e concatenacion
            | COM VAR IGU e concatenacion
            | COM VAR IGU e
            | COM VAR
            ;
            

// concatenacion_concat: IGU e concatenacion_concat
//             | COM VAR IGU e concatenacion_concat
//             | COM VAR concatenacion_concat
//             |
//             ;
// concatenacion:  declaracion asignacion declaracion_concat;

bloque: LA instrucciones LC;

op_logicos: AND
          | OR
          ;

op_booleanas: ITRUE
            | IFALSE
            ;

bucle_while: IWHILE PA cond PC bloque;

condicional_if: IIF PA cond PC bloque condicional_else;
condicional_else: IELSE bloque
                | IELSE condicional_if
                |
                ;

bucle_for: IFOR PA asignacion PyC cond PyC (incremento | decremento) PC bloque;

declaracion_funcion: tipo_var VAR PA declaracion_argumentos PC PyC;

declaracion_argumentos: tipo_var VAR 
                        | tipo_var VAR COM declaracion_argumentos
                        ;

asignacion_funcion: tipo_var VAR PA asignacion_argumentos PC bloque;

asignacion_argumentos: INT VAR concatenacion_argumentos_asignacion 
                                 | DOUBLE VAR concatenacion_argumentos_asignacion 
                                 | INT asignacion concatenacion_argumentos_asignacion 
                                 | DOUBLE asignacion concatenacion_argumentos_asignacion 
                                 ;

concatenacion_argumentos_asignacion: COM asignacion_argumentos
              |
              ;

llamado_funcion: VAR PA VAR PC;

return_tipo: IRETURN VAR
           | IRETURN factor
           | IRETURN
           ;

tipo_var: INT
        | DOUBLE
        | BOOL
        ;

incremento: VAR SUMA SUMA
            | VAR RESTA RESTA
            ;

decremento: SUMA SUMA VAR
            | RESTA RESTA VAR
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


/// 7 + 6 * 3
e: term exp; // nivel 0

exp: SUMA e // nivel 1
    | RESTA e
    |
    ;

term: factor t; // nivel 1

t: MULT term // nivel 2
 | DIV term
 | MOD term
 |
 ;

factor: ENTERO // nivel 2
      | DOBLE
      | BOOLEANO
      | VAR
      | PA e PC
      ;