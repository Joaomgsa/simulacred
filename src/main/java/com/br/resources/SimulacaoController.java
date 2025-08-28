package com.br.resources;


import com.br.application.service.SimulacaoService;
import com.br.domain.dto.request.SimulacaoRequestDTO;
import com.br.domain.dto.response.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Path("/")
@ApplicationScoped
public class SimulacaoController  {


    @Inject
    SimulacaoService simulacaoService;


    @POST
    @Path("/simulacao")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response simularCredito(@Valid SimulacaoRequestDTO request) {
        try {
            SimulacaoResponseDTO response = simulacaoService.simularCredito(request);
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro: " + e.getMessage())
                    .build();
        }
    }

    //Listar Simulacoes
    @GET
    @Path("/simulacoes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarSimulacoes() {
        // Implementar a lógica para listar simulações

        PaginatedResponseDTO simulacoes = simulacaoService.listarSimulacoes(1, 10);
        return Response.ok(simulacoes).build();

    }

    @GET
    @Path("/relatorio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterRelatorio(@QueryParam("dataReferencia") LocalDate dataReferencia) {
        RelatorioSimulacaoResponseDTO response = simulacaoService.obterRelatorioSimulacoes(dataReferencia);
                return Response.ok(response).build();
    }


}
