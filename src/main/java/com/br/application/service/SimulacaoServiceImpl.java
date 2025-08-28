package com.br.application.service;

import com.br.domain.dto.request.SimulacaoRequestDTO;
import com.br.domain.dto.response.*;
import com.br.domain.model.ParcelaPrice;
import com.br.domain.model.ParcelaSac;
import com.br.domain.model.Produto;
import com.br.domain.model.Simulacao;
import com.br.domain.repository.SimulacaoRepository;
import com.br.exception.NegocioException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@ApplicationScoped
public class SimulacaoServiceImpl implements SimulacaoService {

    @Inject
    SimulacaoRepository simulacaoRepository;

    @Inject
    ParcelaSacService parcelaSacService;

    @Inject
    ParcelaPriceService parcelaPriceService;

    @Inject
    ProdutoService produtoService;



    @Override
    @Transactional
    public SimulacaoResponseDTO simularCredito(SimulacaoRequestDTO simulacaoRequest) {
        Produto prod = produtoService.findByPrazoAndValor(
                simulacaoRequest.prazo(),
                simulacaoRequest.valorDesejado()
        );

        NegocioException.throwIf(prod == null, "Produto não encontrado para o prazo e valor informados.");

        // registrar a simulação no banco de dados
        Simulacao simulacaoPersistida = persistirSimulacao(simulacaoRequest, prod);



        SimulacaoRequestDTO requestDtoComId = simulacaoRequest.withIdSimulacao(
                simulacaoPersistida.getNuSimulacao().toString()
        );
        // chamar metodo de calculo parcelas SAC

        List<ParcelaSac> parcelaSacs = parcelaSacService.calcularParcelas(requestDtoComId, prod);

        // chamar metodo de calculo parcelas PRICE

        List<ParcelaPrice> parcelasPrice = parcelaPriceService.calcularParcelas(requestDtoComId, prod);


        return montarResponseSimulacao(parcelaSacs,
                parcelasPrice,
                simulacaoPersistida,
                prod);
    }


    @Override
    public PaginatedResponseDTO<SimulacaoListaResponseDTO> listarSimulacoes(int pagina, int tamanho) {
        List<SimulacaoListaResponseDTO> simulacoes = simulacaoRepository.findSimulacoesResumo(pagina - 1, tamanho);
        long totalRegistros = simulacaoRepository.countSimulacoes();

        return new PaginatedResponseDTO<>(
                pagina,
                (int) totalRegistros,
                tamanho,
                simulacoes
        );
    }

    @Override
    public RelatorioSimulacaoResponseDTO obterRelatorioSimulacoes(LocalDate dataReferencia) {
        List<SimulacaoPorProdutoDTO> simulacoes = simulacaoRepository.findSimulacoesPorDataProduto(dataReferencia);
        return new RelatorioSimulacaoResponseDTO(dataReferencia, simulacoes);
    }

    private Simulacao persistirSimulacao(SimulacaoRequestDTO request, Produto produto) {
        Simulacao simulacao = new Simulacao();
        simulacao.setVrSimulacao(request.valorDesejado());
        simulacao.setPrSimulacao(request.prazo());
        simulacao.setProduto(produto);
        simulacao.setCreatedAt(LocalDateTime.now());
        simulacaoRepository.persistAndFlush(simulacao);

        return simulacao;
    }





    private String criarIdSimulacao(Simulacao simulacao){
        String ano = String.valueOf(simulacao.getCreatedAt().getYear());
        String mes = String.format("%02d", simulacao.getCreatedAt().getMonthValue());
        return ano + mes + simulacao.getNuSimulacao();
    }

    private SimulacaoResponseDTO montarResponseSimulacao(List<ParcelaSac> parcelaSacs,
                                                            List<ParcelaPrice> parcelasPrice,
                                                         Simulacao simulacao,
                                                         Produto produto) {

        // Converter parcelas SAC para DTO
        List<ParcelaDTO> parcelasSacDTO = parcelaSacs.stream()
                .map(this::converterParcelaSacParaDTO)
                .toList();

        // Converter parcelas PRICE para DTO
        List<ParcelaDTO> parcelasPriceDTO = parcelasPrice.stream()
                .map(this::converterParcelaPriceParaDTO)
                .toList();


        // Calcular valores totais SAC
        ResultadoSimulacaoDTO resultadoSac = calcularResultadoSac(parcelaSacs);

        // Calcular valores totais PRICE
        ResultadoSimulacaoDTO resultadoPrice = calcularResultadoPrice(parcelasPrice);

        // Criar lista com ambos os resultados
        List<ResultadoSimulacaoDTO> resultados = List.of(resultadoSac, resultadoPrice);

        String idSimulacao = criarIdSimulacao(simulacao);


        return new SimulacaoResponseDTO(
                idSimulacao,
                produto.getCoProduto().toString(),
                produto.getNoProduto(),
                produto.getPcTaxaJuros(),
                resultados
        );
    }

    private ParcelaDTO converterParcelaSacParaDTO(ParcelaSac parcela) {
        return new ParcelaDTO(
                parcela.getNuParcela(),
                calcularAmortizacao(parcela), // método auxiliar para calcular amortização
                parcela.getVrJuros(),
                parcela.getVrPrestacao()
        );
    }

    private ResultadoSimulacaoDTO calcularResultadoSac(List<ParcelaSac> parcelas) {

        List<ParcelaDTO> par = parcelas.stream()
                .map(this::converterParcelaSacParaDTO)
                .toList();

        return new ResultadoSimulacaoDTO(
                "SAC",
                par
        );
    }

    private BigDecimal calcularAmortizacao(ParcelaSac parcela) {
        // Amortização = Prestação - Juros
        return parcela.getVrPrestacao().subtract(parcela.getVrJuros());
    }

    private ParcelaDTO converterParcelaPriceParaDTO(Object parcela) {
        if (parcela instanceof ParcelaPrice) {
            ParcelaPrice p = (ParcelaPrice) parcela;
            return new ParcelaDTO(
                    p.getNuParcela(),
                    calcularAmortizacaoPrice(p), // método auxiliar para calcular amortização
                    p.getVrJuros(),
                    p.getVrPrestacao()
            );
        }
        throw new IllegalArgumentException("Tipo de parcela inválido para conversão.");
    }

    private ResultadoSimulacaoDTO calcularResultadoPrice(List<?> parcelas) {

        List<ParcelaDTO> par = parcelas.stream()
                .map(this::converterParcelaPriceParaDTO)
                .toList();

        return new ResultadoSimulacaoDTO(
                "PRICE",
                par
        );
    }
    private BigDecimal calcularAmortizacaoPrice(ParcelaPrice parcela) {
        // Amortização = Prestação - Juros
        return parcela.getVrPrestacao().subtract(parcela.getVrJuros());
    }

}
