function main:
    i = 1
    L1:
    T1 = i > 2
    T2 = i < 4
    T3 = T1 && T2
    i = T3
    if T3 == false goto L2
        i = i + 1
    goto L1
    L2:
end main
