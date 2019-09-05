# Introdução à autorização com JSON Web Tokens

Como já comentamos antes, uma API backend expõe dados de forma coordenada e segura. Isto significa que expõe-se apenas o que pode e deve ser exposto e com o cuidado de garantir que apenas usuários autorizados terão acesso aos dados de forma segura.

Neste módulo estudaremos sobre autenticação e autorização usando JSON Web Tokens ([JWT](https://jwt.io/)). Antes de entrarmos na tecnologia, vamos conversar um pouco sobre o que é autenticação e autorização.

*Autenticação* é o ato de atestar a identidade de um usuário. Quando entramos no nosso app de email e colocamos email e senha estamos passando informações sigilosas que só nós sabemos para atestar nossa identidade. Mas essa parte é só uma parte do processo. Por trás, é gerado um token (no nosso caso, um token JWT) seguro que permite a autorização. 

*Autorização* é o cenário mais comum para o uso do token. Após o login do usuário (autenticação), cada solicitação subsequente incluirá o token, permitindo que o usuário acesse rotas, serviços e recursos permitidos com esse token. Enquanto o token não expirar o usuário pode utilizá-lo sem precisar ficar fazendo login várias vezes. O Logon único é um recurso amplamente usado devido à pequena sobrecarga e à capacidade de ser facilmente usado em diferentes APIs. O token vai no _authorization header_ das requisições HTTP do cliente. Ao ler o token, a API consegue identificar se a rota solicitada pode ou não ser acessada pelo usuário em questão (pois o JWT identifica o usuário que está enviando requisições de forma segura).

## Entendendo o JSON Web Token

O JWT define uma maneira compacta e independente de transmitir informações com segurança entre as partes como um objeto JSON. Essas informações podem ser verificadas e confiáveis porque são assinadas digitalmente. Os JWTs podem ser assinados usando um segredo (com o algoritmo HMAC) ou um par de chaves pública/privada usando RSA ou ECDSA.

Em sua forma compacta, os JSON Web Tokens consistem em três partes separadas por pontos (.), que são:

* Cabeçalho
* Carga útil
* Assinatura

Portanto, um JWT normalmente se parece com uma string como essa string: 
````
xxxxx.yyyyy.zzzzz
````

[Diagrama de sequência usando token JWT](imagens/sequenciaJWT.png)
