package br.com.banco.cardservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.banco.cardservice.CardserviceApplication;
import br.com.banco.cardservice.dto.CartaoRequest;
import br.com.banco.cardservice.dto.ResultadoDTO;
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
        if (!contaExiste(request.idConta)) {
            return "Conta não existe. Cartão negado.";
        }
        if (request.limite < 0) {
            return "Limite inválido";
        }
        Cartao cartao = new Cartao();
        cartao.setNumero(request.numero);
        cartao.setCvv(request.cvv);
        cartao.setValidade(request.validade);
        cartao.setTitular(request.titular);
        cartao.setLimite(request.limite);
        cartao.setIdConta(request.idConta);

        repository.save(cartao);

        System.out.println("Salvou no banco");

        return "OK";
    }

    public Cartao buscarPorIdConta(String idConta) {
        return repository.findById(idConta).orElse(null);
    }

    public List<Cartao> listarCartoes() {
        return repository.findAll();

    }

    private boolean contaExiste(String idConta) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://contaprojvaporarquitetura-2.onrender.com/contas/"
                + idConta;

        try {
            restTemplate.getForObject(url, Object.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String solicitarAumento(String idConta) {

        Cartao cartao = repository.findById(idConta).orElse(null);

        if (cartao == null) {
            return "Cartão não encontrado para a conta: " + idConta;
        }

        if (!cartao.isAtivo()) {
            return "Cartão inativo.";
        }

        ResultadoDTO resultado = buscarSalario(idConta);

        if (!resultado.isSucesso()) {
            return "Aumento negado. Erro ao buscar salário: " + resultado.getErro();
        }

        double salario = resultado.getNovoValor();

        double limiteMaximo = salario * 0.4;

        if (cartao.getLimite() >= limiteMaximo) {
            return "Aumento negado. Limite já adequado à renda.";
        }

        cartao.setLimite(limiteMaximo);
        repository.save(cartao);

        return "Aumento concedido com sucesso. Novo limite: " + limiteMaximo;
    }

    private ResultadoDTO buscarSalario(String idConta) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://contaprojvaporarquitetura-2.onrender.com/contas/"
                + idConta
                + "/salario";

        return restTemplate.getForObject(
                url,
                ResultadoDTO.class);
    }

}
