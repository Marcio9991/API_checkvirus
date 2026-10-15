package br.edu.br.checkvirus.api;

import br.edu.br.checkvirus.model.Verificacao;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
public class VirusTotalApi {

    private final RestClient restClient;

    public VirusTotalApi(@Value("${virustotal.api.key}") String chave) {
        this.restClient = RestClient.builder()
                .baseUrl("https://www.virustotal.com/api/v3")
                .defaultHeader("x-apikey", chave)
                .build();
    }

    public Verificacao verificarUrl(String url) throws InterruptedException {
        MultiValueMap<String, String> corpo = new LinkedMultiValueMap<>();
        corpo.add("url", url);

        Map<?, ?> envio = restClient.post()
                .uri("/urls")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(corpo)
                .retrieve()
                .body(Map.class);

        Map<?, ?> dadosEnvio = (Map<?, ?>) envio.get("data");
        String idAnalise = (String) dadosEnvio.get("id");

        for (int tentativa = 0; tentativa < 6; tentativa++) {
            Thread.sleep(3000);

            Map<?, ?> resposta = restClient.get()
                    .uri("/analyses/{id}", idAnalise)
                    .retrieve()
                    .body(Map.class);

            Map<?, ?> dados = (Map<?, ?>) resposta.get("data");
            Map<?, ?> atributos = (Map<?, ?>) dados.get("attributes");

            if ("completed".equals(atributos.get("status"))) {
                Map<?, ?> estatisticas = (Map<?, ?>) atributos.get("stats");

                Verificacao verificacao = new Verificacao();
                verificacao.setUrl(url);
                verificacao.setMalicioso(numero(estatisticas, "malicious"));
                verificacao.setSuspeito(numero(estatisticas, "suspicious"));
                verificacao.setInofensivo(numero(estatisticas, "harmless"));
                verificacao.setNaoDetectado(numero(estatisticas, "undetected"));
                return verificacao;
            }
        }

        throw new RuntimeException("A análise demorou demais. Tente novamente em instantes.");
    }

    private int numero(Map<?, ?> mapa, String chave) {
        Object valor = mapa.get(chave);
        if (valor instanceof Number) {
            return ((Number) valor).intValue();
        }
        return 0;
    }
}
