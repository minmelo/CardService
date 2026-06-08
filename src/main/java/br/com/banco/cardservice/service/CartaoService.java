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
        ResultadoDTO resultado = buscarSalario(cartao.getIdConta());
        if (!resultado.isSucesso()) {
            return "Aumento negado. Erro ao buscar salário: " + resultado.getErro();
        }
        double salario = resultado.getNovoValor();
        double aumento;
        if (salario > 0) {
            aumento = salario * 0.3;
            cartao.setLimite(cartao.getLimite() + aumento);
            repository.save(cartao);
            return "Aumento concedido com sucesso";
        }
        return "Aumento negado. Salário não aumentou.";

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

    public Cartao buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

}
