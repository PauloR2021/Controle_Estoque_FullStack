# 📦 API Controle de Estoque + Frontend JavaFX

Sistema completo para gestão de estoque, com API REST em Spring Boot, autenticação JWT, validação de usuário por e-mail, recuperação de senha, e frontend em JavaFX.

Projeto desenvolvido com foco em boas práticas, segurança, arquitetura limpa e integração backend + frontend.

---

## 🚀 Funcionalidades
### 🔐 Autenticação e Usuários
- Cadastro de usuário
- Criptografia de senha com BCrypt
- Validação de conta via código enviado por e-mail
- Login com JWT
- Bloqueio de login para usuários não ativados
- Recuperação de senha com:
- Envio de código por e-mail
- Validação de código
- Definição de nova senha

### 📦 Controle de Estoque
- Cadastro de produtos
- Consulta de produtos
- Validações de dados
- Integração segura com autenticação JWT]

## 🖥️ Frontend (JavaFX)

### Telas de:
- Login
- Cadastro de usuário

### 🛠Validação de código
- Recuperação de senha
- Campos com validação (senha, e-mail, números)
- Botão para mostrar/ocultar senha
- Alertas padronizados com CSS reutilizável
- Estilo visual customizado (TableView, Buttons, Labels, Alerts)

## 🛠️ Tecnologias Utilizadas
### Backend
- Java 21
- Spring Boot 3
- Spring Security
- JWT
- Spring Data JPA
- Flyway
- PostgreSQL
- Java Mail Sender
- Swagger / OpenAPI
### Frontend
- JavaFX 21
- FXML
- CSS
- HttpClient (consumo da API)


## 📐 Arquitetura
api-controle-estoque

```
├── backend (Spring Boot)
│   ├── controller
│   ├── service
│   ├── repository
│   ├── model
│   ├── dto
│   ├── infra/security
│   └── db/migration (Flyway)
│
└── frontend (JavaFX)
├── controller
├── view (FXML)
├── styles (CSS)
└── util (Alertas reutilizáveis)
```
## 🔐 Segurança
- Autenticação baseada em JWT
- Filtros de segurança personalizados
- Rotas públicas e privadas configuradas
- Usuário só acessa o sistema após validação por e-mail

## 📧 Validação por E-mail
- Código numérico gerado automaticamente
- Tempo de expiração configurado
- Reutilizado tanto para:
  - Ativação de conta
  - Recuperação de senha

## ⚙️ Configuração (application.properties)
```
spring.datasource.url=jdbc:postgresql://localhost:5432/estoque
spring.datasource.username=postgres
spring.datasource.password=senha

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu_email@gmail.com
spring.mail.password=sua_senha_app
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

api.security.token.secret=chave-secreta

```
## ▶️ Como Executar
```
mvn spring-boot:run
http://localhost:8085
http://localhost:8085

```
- Frontend
```
MainApp.java

```

## 🧪 Testes
- Testes manuais via Swagger
- Validação completa de fluxos:
  - Cadastro → Validação → Login  
  - Esqueci a senha → Código → Nova senha
  
## 👨‍💻 Autor
```
Paulo Ricardo Soares
Tecnólogo em Gestão da Tecnologia da Informação
Desenvolvedor Java | Spring Boot | Backend
```





