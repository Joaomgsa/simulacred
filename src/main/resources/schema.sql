-- Tabela de produtos (PostgreSQL)
CREATE TABLE IF NOT EXISTS produto (
                                       co_produto BIGINT NOT NULL PRIMARY KEY,
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
                                         co_produto BIGINT NOT NULL,
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

CREATE TABLE IF NOT EXISTS parcela_sac (
                             id BIGSERIAL PRIMARY KEY,
                             nu_simulacao BIGINT NOT NULL,
                             nu_parcela INTEGER NOT NULL,
                             vr_saldodevedor DECIMAL(15,2) NOT NULL,
                             vr_juros DECIMAL(15,2) NOT NULL,
                             vr_prestacao DECIMAL(15,2) NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                             CONSTRAINT chk_nu_parcela_positivo CHECK (nu_parcela >= 0),
                             CONSTRAINT chk_vr_saldodevedor_positivo CHECK (vr_saldodevedor >= 0),
                             CONSTRAINT chk_vr_juros_positivo CHECK (vr_juros >= 0),
                             CONSTRAINT chk_vr_prestacao_positivo CHECK (vr_prestacao >= 0)
);

-- Índices para melhorar performance
CREATE INDEX idx_parcela_sac_nu_simulacao ON parcela_sac(nu_simulacao);
CREATE INDEX idx_parcela_sac_nu_parcela ON parcela_sac(nu_parcela);
CREATE INDEX idx_parcela_sac_simulacao_parcela ON parcela_sac(nu_simulacao, nu_parcela);

-- Trigger para atualizar updated_at automaticamente
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_parcela_sac_updated_at
    BEFORE UPDATE ON parcela_sac
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- Comentários na tabela
COMMENT ON TABLE parcela_sac IS 'Tabela para armazenar as parcelas calculadas pelo sistema SAC';
COMMENT ON COLUMN parcela_sac.nu_simulacao IS 'Número/ID da simulação';
COMMENT ON COLUMN parcela_sac.nu_parcela IS 'Número sequencial da parcela (iniciando em 0)';
COMMENT ON COLUMN parcela_sac.vr_saldodevedor IS 'Valor do saldo devedor no momento da parcela';
COMMENT ON COLUMN parcela_sac.vr_juros IS 'Valor dos juros da parcela';
COMMENT ON COLUMN parcela_sac.vr_prestacao IS 'Valor total da prestação (amortização + juros)';
-- Adicionar foreign key se existir tabela de simulações
ALTER TABLE parcela_sac
    ADD CONSTRAINT fk_parcela_sac_simulacao
        FOREIGN KEY (nu_simulacao) REFERENCES simulacao(nu_simulacao);



CREATE TABLE parcela_price (
                               id BIGSERIAL PRIMARY KEY,
                               nu_simulacao BIGINT NOT NULL,
                               nu_parcela INTEGER NOT NULL,
                               vr_saldodevedor NUMERIC(15,2) NOT NULL,
                               vr_juros NUMERIC(15,2) NOT NULL,
                               vr_prestacao NUMERIC(15,2) NOT NULL,
                               created_at TIMESTAMP,
                               updated_at TIMESTAMP
);

CREATE INDEX idx_nu_simulacao ON parcela_price(nu_simulacao);
CREATE INDEX idx_simulacao_parcela ON parcela_price(nu_simulacao, nu_parcela);