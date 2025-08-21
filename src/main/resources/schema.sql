-- Tabela de produtos (PostgreSQL)
CREATE TABLE IF NOT EXISTS produto (
                                       co_produto INTEGER NOT NULL PRIMARY KEY,
                                       no_produto VARCHAR(200) NOT NULL,
                                       pc_taxa_juros NUMERIC(10,9) NOT NULL,
                                       nu_minimo_meses SMALLINT NOT NULL,
                                       nu_maximo_meses SMALLINT NULL,
                                       vr_minimo NUMERIC(18,2) NOT NULL,
                                       vr_maximo NUMERIC(18,2) NULL
);

-- Tabela de simulações (baseada na entidade Simulacao)
CREATE TABLE IF NOT EXISTS simulacao (
                                         nu_simulacao BIGSERIAL PRIMARY KEY,
                                         co_produto INTEGER NOT NULL,
                                         vr_simulacao NUMERIC(18,2),
                                         pr_simulacao INTEGER,
                                         vr_juros NUMERIC(18,2),
                                         vr_total NUMERIC(18,2),
                                         vr_parcela NUMERIC(18,2),
                                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         FOREIGN KEY (co_produto) REFERENCES produto(co_produto)
);

-- Trigger para atualizar updated_at automaticamente
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_simulacao_updated_at
    BEFORE UPDATE ON simulacao
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();