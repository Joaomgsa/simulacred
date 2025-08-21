-- Inserir produtos de crédito (PostgreSQL)
INSERT INTO produto (co_produto, no_produto, pc_taxa_juros, nu_minimo_meses, nu_maximo_meses, vr_minimo, vr_maximo)
VALUES (1, 'Produto 1', 0.017900000, 0, 24, 200.00, 10000.00);

INSERT INTO produto (co_produto, no_produto, pc_taxa_juros, nu_minimo_meses, nu_maximo_meses, vr_minimo, vr_maximo)
VALUES (2, 'Produto 2', 0.017500000, 25, 48, 10001.00, 100000.00);

INSERT INTO produto (co_produto, no_produto, pc_taxa_juros, nu_minimo_meses, nu_maximo_meses, vr_minimo, vr_maximo)
VALUES (3, 'Produto 3', 0.018200000, 49, 96, 100000.01, 1000000.00);

INSERT INTO produto (co_produto, no_produto, pc_taxa_juros, nu_minimo_meses, nu_maximo_meses, vr_minimo, vr_maximo)
VALUES (4, 'Produto 4', 0.015100000, 96, NULL, 1000000.01, NULL);