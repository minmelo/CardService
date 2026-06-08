package br.com.banco.cardservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.cardservice.dto.CartaoRequest;
import br.com.banco.cardservice.entity.Cartao;
import br.com.banco.cardservice.service.CartaoService;

@RestController
@RequestMapping("/card")
public class CartaoController {
    private final CartaoService service;

    public CartaoController(CartaoService service) {
        this.service = service;
    }

    @PostMapping("/solicitar")
    public String solicitarCartao(@RequestBody CartaoRequest request) {
        return service.solicitarCartao(request);
    }

    @PostMapping("/aumento/{id}")
    public String solicitarAumento(@PathVariable String id) {
        return service.solicitarAumento(id);
    }

    @GetMapping("/{idConta}")
    public Cartao buscar(@PathVariable String idConta) {
        return service.buscarPorIdConta(idConta);
    }
}