grammar compiladores;

@header {
package compiladores;
}

// Dada la regla sintáctica

fragment NUMERO_F : [0-9];
NUMERO: NUMERO_F;

term : term exp
     | NUMERO
     ;

exp : '+' term
    | '-' term
    |
    ;

// exp : '+' term
//     | '-' term
//     |
//     ;

// Realice los siguientes ejercicios.
// Realizar la tabla de Análisis Sintáctico Descendente para la entrada
// 7 - 2 + 9

WS : [ \t\n\r] -> skip;
OTRO : . ;

si : s EOF ;

s : term s
  |
  ;

// $ s                        7 - 2 + 9 $ derivar
// $ s term                   7 - 2 + 9 $ match
// $ s                        2 + 9     $ derivar
// $ s term                   2 + 9     $ match
// $ s                        9         $ derivar
// $ s term                   9         $ match
// $ s                                  $ derivar
// $                                    $ OK