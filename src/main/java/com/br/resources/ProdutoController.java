package com.br.resources;

import com.br.application.service.ProdutoService;
import com.br.domain.model.Produto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


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
}
