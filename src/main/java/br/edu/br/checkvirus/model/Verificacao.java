package br.edu.br.checkvirus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Verificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2048)
    private String url;

    private int malicioso;
    private int suspeito;
    private int inofensivo;
    private int naoDetectado;
    private LocalDateTime dataVerificacao;

    @Column(length = 500)
    private String observacao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public int getMalicioso() { return malicioso; }
    public void setMalicioso(int malicioso) { this.malicioso = malicioso; }

    public int getSuspeito() { return suspeito; }
    public void setSuspeito(int suspeito) { this.suspeito = suspeito; }

    public int getInofensivo() { return inofensivo; }
    public void setInofensivo(int inofensivo) { this.inofensivo = inofensivo; }

    public int getNaoDetectado() { return naoDetectado; }
    public void setNaoDetectado(int naoDetectado) { this.naoDetectado = naoDetectado; }

    public LocalDateTime getDataVerificacao() { return dataVerificacao; }
    public void setDataVerificacao(LocalDateTime dataVerificacao) { this.dataVerificacao = dataVerificacao; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public String getStatus() {
        if (malicioso > 0) {
            return "Perigosa";
        }
        if (suspeito > 0) {
            return "Suspeita";
        }
        return "Segura";
    }
}
