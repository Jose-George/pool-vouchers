# pool-vouchers

Micro serviço de pool de vouchers baseado em micro serviços. Um pool de vouchers é uma coleção de códigos (vouchers) que podem ser usados pelos clientes
(destinatários) para obter descontos em uma loja virtual. Cada código só pode ser usado uma vez. 

### Arquitetura 
A aplicação foi implementada utilizando a Clean Architecture. 
consulte mais informações sobre [Clean Architecture](https://medium.com/luizalabs/descomplicando-a-clean-architecture-cf4dfc4a1ac6).

### Principais tecnologias utilizadas

1. Java
2. Spring Boot 
3. Spring Security
4. Mongo DB
5. Spring Data 
6. Mockito
7. Junit
8. Lombok 
9. Sonar

# Requisitos para executar o projeto


* [Git](https://git-scm.com/)
* [Docker](https://www.docker.com/)
* [JDK 17+](https://www.oracle.com/br/java/)

# Instalação

1.Clone o projeto

    https://github.com/Jose-George/pool-vouchers.git

2.Abra um terminal na raiz do projeto 

    docker compose -f docker-compose.yaml up -d

3.Abra a IDE de sua preferência e importe o projeto clonado e aguarde o download de todas dependências do projeto

4.Execute a classe **PoolVouchersApplication.java**

### Documentação 

1. Acesse a interface dos recursos do backend através do swagger usando o endereço local: http://localhost:8080/vouchers/swagger-ui/index.html#/Voucher
2. Dentro da raiz do projeto contém um arquivo chamado _Desafio pool vouchers.postman_collection.json_ que pode ser importado no postman para usar as rotas da aplicação.

# Rotas

Aqui está o conteúdo reescrito em formato de tabela:

| Número | Descrição | Método HTTP | Endpoint | Autenticação | Parâmetros |
| ------ | --------- | ----------- | -------- |--------------| ---------- |
| 1      | Autorização utilizando Authorization Server com Access Token opaco | POST | http://localhost:8080/vouchers/oauth2/token | Basic Auth   | username: **voucher-backend**<br>password: **maria**<br>Form-data: grant_type=client_credentials<br>scope=READ,WRITE |
| 2      | Verifica se o token está válido | POST | http://localhost:8080/vouchers/oauth2/introspect | Bearer Token            | - |
| 3      | Criar um voucher | POST | http://localhost:8080/vouchers/v1/vouchers | Bearer Token | - |
| 4      | Consulta se um voucher vinculado a um e-mail está ativo | GET | http://localhost:8080/vouchers/v1/vouchers/cefd-c1-/joao@email.com | Bearer Token            | - |
| 5      | Resgate de voucher (desativação) | POST | http://localhost:8080/vouchers/v1/vouchers/redeem/cefd-c1-/joao@email.com | Bearer Token           | - |
| 6      | Listar todas as ofertas especiais para um e-mail específico | GET | http://localhost:8080/vouchers/v1/vouchers/specialoffer/joao@email.com | Bearer Token            | - |

Essa tabela resume as rotas e os endpoints da aplicação, juntamente com as informações sobre o método HTTP, autenticação e parâmetros (se aplicável) necessários para cada requisição. Por favor, note que algumas rotas não requerem autenticação (endpoint 2, 3, 4 e 5), enquanto outras exigem autenticação com credenciais do tipo Basic Auth (endpoint 1).
### Observação

Para facilitar de ficar solicitando token a cada requisição, embora a aplicação esteja com segurança
os endpoints podem ser acessíveis sem o token, contudo para mudar isso basta alterar a classe
ResourceServerConfig.resourceServerFilterChain:

       .authorizeRequests()
            .anyRequest().authenticated() 
