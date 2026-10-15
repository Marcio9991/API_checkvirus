package br.edu.br.checkvirus.controller;

import br.edu.br.checkvirus.api.VirusTotalApi;
import br.edu.br.checkvirus.model.Verificacao;
import br.edu.br.checkvirus.repository.VerificacaoRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VerificacaoController {

    private final VerificacaoRepository repository;
    private final VirusTotalApi virusTotalApi;

    public VerificacaoController(VerificacaoRepository repository, VirusTotalApi virusTotalApi) {
        this.repository = repository;
        this.virusTotalApi = virusTotalApi;
    }

    @GetMapping("/")
    public String listar(Model model) {
        model.addAttribute("verificacoes", repository.findAll());
        return "lista";
    }

    @PostMapping("/consultar")
    public String consultar(@RequestParam("url") String url, Model model) {
        try {
            Verificacao verificacao = virusTotalApi.verificarUrl(url);
            model.addAttribute("verificacao", verificacao);
            return "resultado";
        } catch (Exception e) {
            model.addAttribute("erro", "Não foi possível verificar a URL: " + e.getMessage());
            model.addAttribute("verificacoes", repository.findAll());
            return "lista";
        }
    }

    @PostMapping("/salvar")
    public String salvar(Verificacao verificacao) {
        verificacao.setDataVerificacao(LocalDateTime.now());
        repository.save(verificacao);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        model.addAttribute("verificacao", repository.findById(id).orElseThrow());
        return "editar";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable("id") Long id, @RequestParam("observacao") String observacao) {
        Verificacao verificacao = repository.findById(id).orElseThrow();
        verificacao.setObservacao(observacao);
        repository.save(verificacao);
        return "redirect:/";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}
