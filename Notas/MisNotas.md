## Clase 4 (06-04-2022)

## **HACER CODIGO LIMPIO**

Las siguientes 3 lineas son instrucciones debido a que el for solo contiene 1 linea de codigo, pero al poner llaves lo hacemos una instruccion compuesta

Ejemplos
```
int x = 0
int main()
{
    int y = 1;
    int z = x + y;
    return x;
}

for()
    x++;

for()
{
    x++;
    y++;
}
```

Para depurar hay que modificar el archivo launch.json
Modificar el `startRule` y tambien el `input`

---

```
LA : '{';
LC : '}';

programa : instrucciones EOF;

instrucciones: instruccion instrucciones
            |
            ;

instruccion: int_simple
            | bloque
            ;

bloque: LA instrucciones LC
        |
        ;

int_simple: . ;
```