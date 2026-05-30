package br.com.banco.cardservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.banco.cardservice.dto.CartaoRequest;
import br.com.banco.cardservice.entity.Cartao;
import br.com.banco.cardservice.repository.CartaoRepository;

@Service
public class CartaoService {

    private final CartaoRepository repository;

    public CartaoService(CartaoRepository repository) {
        this.repository = repository;
    }

    public String solicitarAumento() {
        return "Aumento solicitado com sucesso";
    }

    public String solicitarCartao(@RequestBody CartaoRequest request) {
    Cartao cartao = new Cartao();
    cartao.setNumero(request.numero);
    cartao.setCvv(request.cvv);
    cartao.setValidade(request.validade);
    cartao.setTitular(request.titular);
    cartao.setLimite(request.limite);

    repository.save(cartao);

    System.out.println("Salvou no banco");

    return "OK";
}

public List<Cartao> listarCartoes() {
    return repository.findAll();
    
}
}
