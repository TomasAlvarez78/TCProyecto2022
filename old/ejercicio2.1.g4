grammar compiladores;

@header {
package compiladores;
}

// Debe entregar un archivo ANTLR que contenga las expresiones regulares y
// reglas sintácticas necesarias para realizar la verificación del 
// balance de llaves, corchetes y paréntesis.

PA : '(' ;
PC : ')' ;
CA : '[' ;
CC : ']' ;
LA : '{' ;
LC : '}' ;

WS : [ \t\n\r] -> skip;
OTRO : . ;

si : s EOF ;

s : PA s PC s
  | CA s CC s
  | LA s LC s
  |
  ;
