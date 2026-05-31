package br.com.banco.cardservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.banco.cardservice.CardserviceApplication;
import br.com.banco.cardservice.dto.CartaoRequest;
import br.com.banco.cardservice.entity.Cartao;
import br.com.banco.cardservice.repository.CartaoRepository;

@Service
public class CartaoService {

    private final CardserviceApplication cardserviceApplication;
    private final CartaoRepository repository;

    public CartaoService(CartaoRepository repository, CardserviceApplication cardserviceApplication) {
        this.repository = repository;
        this.cardserviceApplication = cardserviceApplication;
    }

    public String solicitarCartao(CartaoRequest request) {
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
public String solicitarAumento(Long cartaoId) {
    Cartao cartao = repository.findById(cartaoId).orElse(null);
    if (cartao == null) {
        return "Aumento negado. Cartão não encontrado.";
    } 
    if (!cartao.isAtivo()) {
            return "Aumento negado. Cartão inativo.";
    } 
    double salarioAnterior = 5000.00; // Simulação de salário anterior
    double salarioAtual = 6000.00; // Simulação de salário atual
    double aumento;
    if (salarioAtual > salarioAnterior) {
        aumento = salarioAtual - salarioAnterior;
        cartao.setLimite(cartao.getLimite() + aumento);
        repository.save(cartao);
        return "Aumento concedido com sucesso";
    }   
    return "Aumento negado. Salário não aumentou.";

}

public Cartao buscarPorId(Long id) {
    return repository.findById(id).orElse(null);
}


    
}
