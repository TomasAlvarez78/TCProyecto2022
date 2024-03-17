
## Realice los siguientes ejercicios.
Dada la regla sintáctica 



```
operacion : term ;

term : term exp
     | NUMERO
     | '(' term ')'
     ;

exp : '+' term
    | '-' term
    |
    ;
```

Realizar la tabla de Análisis Sintáctico Descendente para la entrada 
`7 - ( 2 + 9 )`

```
$ operacion         7 - ( 2 + 9 ) $ Derivar
$ term              7 - ( 2 + 9 ) $ Derivar
$ exp term          7 - ( 2 + 9 ) $ Match
$ exp                 - ( 2 + 9 ) $ Derivar
$ term '-'            - ( 2 + 9 ) $ Match
$ term                  ( 2 + 9 ) $ Derivar
$ ')' term '('          ( 2 + 9 ) $ Match
$ ')' term                2 + 9 ) $ Derivar
$ ')' exp term            2 + 9 ) $ Match
$ ')' exp                   + 9 ) $ Derivar
$ ')' term '+'              + 9 ) $ Match
$ ')' term                    9 ) $ Match
$ ')'                           ) $ Match
$                                 $ OK
```

Realizar la tabla de Análisis Sintáctico Descendente para la entrada 
`5 + ( 6 - 4`

```
$ operacion         5 + ( 6 - 4 $ Derivar
$ term              5 + ( 6 - 4 $ Derivar
$ exp term          5 + ( 6 - 4 $ Match
$ exp                 + ( 6 - 4 $ Derivar
$ term '+'            + ( 6 - 4 $ Match
$ term                  ( 6 - 4 $ Derivar
$ ')' term '('          ( 6 - 4 $ Match
$ ')' term                6 - 4 $ Derivar
$ ')' exp term            6 - 4 $ Match
$ ')' exp                   - 4 $ Derivar
$ ')' term '-'              - 4 $ Match
$ ')' term                    4 $ Match
$ ')'                           $ ERROR
```

Realizar la tabla de Análisis Sintáctico Descendente para la entrada 
`5 + 6 ) - 4`

```
$ operacion         5 + 6 ) - 4 $ Derivar
$ term              5 + 6 ) - 4 $ Derivar
$ exp term          5 + 6 ) - 4 $ Match
$ exp                 + 6 ) - 4 $ Derivar
$ term '+'            + 6 ) - 4 $ Match
$ term                  6 ) - 4 $ Derivar
$ exp term              6 ) - 4 $ Match
$ exp                     ) - 4 $ ERROR
```

Realizar la tabla de Análisis Sintáctico Ascendente para la entrada 
`7 - ( 2 + 9 )`

```
$                       7 - ( 2 + 9 ) $ Desplazar
$ 7                       - ( 2 + 9 ) $ Reducir
$ term                    - ( 2 + 9 ) $ Desplazar
$ term '-'                  ( 2 + 9 ) $ Desplazar
$ term '-' (                  2 + 9 ) $ Desplazar
$ term '-' ( 2                  + 9 ) $ Reducir
$ term '-' ( term               + 9 ) $ Desplazar
$ term '-' ( term '+'             9 ) $ Desplazar
$ term '-' ( term '+' 9             ) $ Reducir
$ term '-' ( term '+' term          ) $ Reducir
$ term '-' ( term exp               ) $ Reducir
$ term '-' ( term                   ) $ Desplazar
$ term '-' ( term )                   $ Reducir
$ term '-' term                       $ Reducir
$ term exp                            $ Reducir
$ term                                $ Reducir
$ operacion                           $ Reducir
```

Realizar la tabla de Análisis Sintáctico Ascendente para la entrada 
`5 + ( 6 - 4`

```
$                       5 + ( 6 - 4 $ Desplazar
$ 5                       + ( 6 - 4 $ Reducir
$ term                    + ( 6 - 4 $ Desplazar
$ term '+'                  ( 6 - 4 $ Desplazar
$ term '+' (                  6 - 4 $ Desplazar
$ term '+' ( 6                  - 4 $ Reducir
$ term '+' ( term               - 4 $ Desplazar
$ term '+' ( term '-'             4 $ Desplazar
$ term '+' ( term '-' 4             $ Reducir
$ term '+' ( term '-' term          $ Reducir
$ term '+' ( term exp               $ Reducir
$ term '+' ( term                   $ ERROR
```

Realizar la tabla de Análisis Sintáctico Ascendente para la entrada 
`5 + 6 ) - 4`

```
$                       5 + 6 ) - 4 $ Desplazar
$ 5                       + 6 ) - 4 $ Reducir
$ term                    + 6 ) - 4 $ Desplazar
$ term '+'                  6 ) - 4 $ Desplazar
$ term '+' 6                  ) - 4 $ Reducir
$ term '+' term               ) - 4 $ Reducir
$ term exp                    ) - 4 $ Reducir
$ term                        ) - 4 $ Desplazar
$ term )                        - 4 $ Desplazar
$ term ) '-'                      4 $ Desplazar
$ term ) '-' 4                      $ Reducir
$ term ) '-' term                   $ Reducir
$ term ) exp                        $ Reducir
```