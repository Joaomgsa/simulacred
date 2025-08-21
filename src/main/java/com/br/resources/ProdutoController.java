package com.br.resources;

import com.br.application.service.ProdutoService;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.DecimalMin;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.annotations.Parameter;

import java.math.BigDecimal;


@Path("/produtos")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoController {

    @Inject
    private ProdutoService produtoService;

    @GET
    public  Response getProdutos() {
        return Response.ok(produtoService.getProdutos()).build();
    }

    @GET
    @Path("/escolher")
    public Response escolherProduto(
    @QueryParam("valor")
    BigDecimal valor,
    @QueryParam("prazo")
    Integer prazo)
    {

        return Response.ok(produtoService.filtrarProduto(prazo, valor)).build();
    }


}
