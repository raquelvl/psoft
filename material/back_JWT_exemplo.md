# Exemplo de autorização usando JWT

Para usar o JWT teremos que ter uma aplicação preparada para cadastrar usuários e realizar a autenticação dos mesmos. Neste momento, por questão de simplicidade, não vamos nos preocupar em criptografar nada. 

Suponha que já temos a gerência de usuários em nossa API:

* POST /api/v1/usuarios (adiciona um usuario com email, nome e senha - o email é o login do usuario e deve ser um identificador único do sistema);
* GET /api/v1/usuarios/{email} - recupera um usuário com determinado login (email);
* POST /api/v1/login - realiza o login de um usuário cujas credenciais (email, senha) são passados no JSON no corpo da requisição HTTP.

O que precisamos fazer agora é:

1. gerar o JWT do usuário quando ele se autenticar e enviar o token na resposta HTTP do login do usuário (se o login for bem sucedido, claro!);
2. configurar as rotas privadas da aplicação (consideraremos a aplicação de produtos que desenvolvemos quando estávamos aprendendo sobre DAO+JPA). O cadastro e a recuperação de novos produtos só pode ser realizado por usuários autenticados;
3. quando as requisições para rotas seguras chegarem, é preciso recuperar e avaliar o token recebido (se é um token válido e não expirado) para então passar a requisição para um controlador;
4. no controlador (e nos serviços associados), é possível que chegem requisições a rotas que requerem uma identificação mais fina do usuário. Por exemplo, só quem deveria poder deletar uma conta de usuário deveria ser o próprio usuário dono da conta. Em casos como este é preciso recuperar a identidade do usuário através do token passado para poder deletar a conta apropriada.

https://www.baeldung.com/spring-boot-add-filter
