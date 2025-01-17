i = 5
goto L1
L1:
T1 = i > 0
if T1 == false goto L2
i = i - 1
x = 1
goto L1
L2:
i = i + 1
i = 0
goto L3
L3:
T2 = i < 5
if T2 == false goto L4
x = 1
i = i + 1
goto L3
L4:
