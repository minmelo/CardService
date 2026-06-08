package br.com.banco.cardservice.dto;

public class ResultadoDTO {

    private boolean sucesso;
    private String id;
    private Double novoValor;
    private String erro;

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getNovoValor() {
        return novoValor;
    }

    public void setNovoValor(Double novoValor) {
        this.novoValor = novoValor;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
