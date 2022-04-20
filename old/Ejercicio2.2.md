
## Realice los siguientes ejercicios.

Ejercicio 1

Realizar la tabla de Análisis Sintáctico Descendente para la entrada
```
//si 
term : term exp
    | NUMERO
    ;

//s
exp : '+' term
    | '-' term
    |
    ;
```

7 - 2 + 9

```
$ term                     7 - 2 + 9 $ derivar
$ exp term                 7 - 2 + 9 $ match
$ exp                        - 2 + 9 $ derivarto
$ term '-'                   - 2 + 9 $ match
$ term                         2 + 9 $ derivar
$ exp term                     2 + 9 $ match
$ exp                            + 9 $ derivar
$ term '+'                       + 9 $ match
$ term                             9 $ match
$                                    $ OK
```
Ejercicio 2

5 + 3 6 - 4

```
$ term                    5 + 3 6 - 4 $ derivar
$ exp term                5 + 3 6 - 4 $ match
$ exp                       + 3 6 - 4 $ derivar
$ term '+'                  + 3 6 - 4 $ match
$ term                        3 6 - 4 $ derivar
$ exp term                    3 6 - 4 $ match
$ exp                           6 - 4 $ derivar
$ term '+'                      6 - 4 $ ERROR
```

Ejercicio 3

5 + - 3 + 4

```
$ term                    5 + - 3 + 4 $ derivar
$ exp term                5 + - 3 + 4 $ match
$ exp                       + - 3 + 4 $ derivar
$ term '+'                  + - 3 + 4 $ match
$ term                        - 3 + 4 $ derivar
$ exp term                    - 3 + 4 $ derivar
$ exp exp term                - 3 + 4 $ ERROR
```

Ejercicio 4

Realizar la tabla de Análisis Sintáctico Ascendente para la entrada

desplazar mover a la izquierda

derivar cambiar a regla

```
//si 
term : term exp
    | NUMERO
    ;

//s
exp : '+' term
    | '-' term
    |
    ;
```


7 - 2 + 9

```
$                          7 - 2 + 9 $ Desplazar
$ 7                          - 2 + 9 $ Reducir
$ term                       - 2 + 9 $ Desplazar
$ term -                       2 + 9 $ Desplazar
$ term - 2                       + 9 $ Reducir
$ term - term                    + 9 $ Reducir
$ term exp                       + 9 $ Reducir
$ term                           + 9 $ Desplazar
$ term +                           9 $ Desplazar
$ term + 9                           $ Reducir
$ term + term                        $ Reducir
$ term exp                           $ Reducir
$ term                               $ OK
```

Ejercicio 5

5 + 3 6 - 4
```
$                  5 + 3 6 - 4 $ Desplazar
$ 5                  + 3 6 - 4 $ Reducir
$ term               + 3 6 - 4 $ Desplazar
$ term +               3 6 - 4 $ Desplazar
$ term + 3               6 - 4 $ Reducir
$ term exp               6 - 4 $ Reducir
$ term                   6 - 4 $ Reducir
$ term                   6 - 4 $ Desplazar
$ term 6                   - 4 $ Reducir
$ term term                - 4 $ Desplazar
$ term term -                4 $ Desplazar
$ term term - 4                $ Reducir
$ term term exp                $ Reducir
$ term term                    $ Error
```

Ejercicio 6

5 + - 3 + 4
```
$                  5 + - 3 + 4 $ Desplazar
$ 5                  + - 3 + 4 $ Reducir
$ term               + - 3 + 4 $ Desplazar
$ term +               - 3 + 4 $ Desplazar
$ term + -               3 + 4 $ Desplazar
$ term + - 3               + 4 $ Reducir
$ term + exp               + 4 $ Desplazar
$ term + exp +               4 $ Desplazar
$ term + exp + 4               $ Reducir
$ term + exp + 4               $ Reducir
$ term + exp exp               $ Error
```