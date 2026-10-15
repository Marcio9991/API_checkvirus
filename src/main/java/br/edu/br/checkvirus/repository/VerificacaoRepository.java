package br.edu.br.checkvirus.repository;

import br.edu.br.checkvirus.model.Verificacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificacaoRepository extends JpaRepository<Verificacao, Long> {
}
