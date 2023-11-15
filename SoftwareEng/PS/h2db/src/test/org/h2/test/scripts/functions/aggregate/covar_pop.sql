-- Copyright 2004-2023 H2 Group. Multiple-Licensed under the MPL 2.0,
-- and the EPL 1.0 (https://h2database.com/html/license.html).
-- Initial Developer: H2 Group
--

SELECT COVAR_POP(Y, X) OVER (ORDER BY R) FROM (VALUES
    (1, NULL, 1),
    (2, 1, NULL),
    (3, NULL, NULL),
    (4, -3, -2),
    (5, -3, -1),
    (6, 10, 9),
    (7, 10, 10),
    (8, 10, 11),
    (9, 11, 7)
) T(R, Y, X) ORDER BY R;
> COVAR_POP(Y, X) OVER (ORDER BY R)
> ---------------------------------
> null
> null
> null
> 0.0
> 0.0
> 30.333333333333332
> 35.75
> 35.88
> 31.277777777777775
> rows (ordered): 9
