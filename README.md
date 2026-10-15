# CheckVirus

**Integrantes:** (colocar os nomes da dupla)

**Disciplina:** Programação Orientada a Objetos - 3º Informática - IFPR Campus Cascavel

## Tema
Verificador de links (URLs) suspeitos.

## Descrição do sistema
O usuário informa uma URL, o sistema consulta o VirusTotal e mostra quantos antivírus consideraram o link malicioso, suspeito ou inofensivo. O resultado pode ser salvo no banco de dados MySQL e depois listado, editado (observação) e excluído.

## API utilizada
VirusTotal API v3 - https://docs.virustotal.com/reference/overview

Funcionalidade: a aplicação envia a URL para `POST /urls`, consulta o resultado em `GET /analyses/{id}` e usa as estatísticas (malicious, suspicious, harmless, undetected) para calcular a situação do link (Segura, Suspeita ou Perigosa) e exibir na tela.

## Tecnologias
Java 21, Spring Boot, Spring Web, Spring Data JPA, MySQL, Thymeleaf, HTML e CSS.

## Como executar
1. Ter o MySQL rodando na porta 3306.
2. Copiar `src/main/resources/segredos.properties.example` para `src/main/resources/segredos.properties` e preencher a senha do MySQL e a chave do VirusTotal.
3. Rodar `./mvnw spring-boot:run` e abrir http://localhost:8080
