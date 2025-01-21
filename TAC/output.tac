function resta:
param y
    a = 1
    return y
end resta
function sumar:
param x
    a = 1
    call resta
    return x
end sumar
function main:
    a = 1
    call sumar
    return a
end main
