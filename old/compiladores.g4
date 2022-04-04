grammar compiladores;

@header {
package compiladores;
}
// No ponemos de [A-z] porque en el codigo ascci hay algunos valores entre la Z y la a
fragment LETRA : [A-Za-z] ;
fragment DIGITO : [0-9] ;
SEQ : '3'[4-9] | '4'[0-5];
// SIGNOS : [-+*/];

// Si es un salto de linea, tabulacion, los salta, no le importa
WS : [\t\n\r] -> skip;

NUMERO : DIGITO+ ;
OTRO : . ;

ID : (LETRA | '_')(LETRA | DIGITO | '_')+ ;
// ID : [A-Za-z_][A-Za-z0-9_];

s : ID     { System.out.println("ID ->" + $ID.getText() + "<--"); }         s
  | NUMERO { System.out.println("NUMERO ->" + $NUMERO.getText() + "<--"); } s
  | OTRO   { System.out.println("Otro ->" + $OTRO.getText() + "<--"); }     s
  | EOF
  ;
