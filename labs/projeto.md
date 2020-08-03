# CRdb: Classificações e Reviews de Disciplinas
Objetivo: organizar plataforma colaborativa para avaliações e informações sobre disciplinas de um curso

O CRdb é uma API para classificação e reviews de disciplinas de um curso. Os usuários dessa aplicação são alunos deste curso e irão construir conteúdo sobre as disciplinas de forma colaborativa através de comentários, notas e likes nas disciplinas. O sistema deve usar essa informação construída para rankear as disciplinas do curso. 

## Mais informaçãoes
* Equipe - O projeto pode ser desenvolvido em dupla ou individualmente. Para quem for trabalhar em dupla lembrem de trabalhar sem encontros presenciais, usando as ferramentas de sua preferência para a comunicação entre os membros da equipe de forma virtual.
* Data de entrega - 13/08/2020
* Repositório - os projetos devem estar em repositórios privados, compartilhados com os professores (raquelvl e marcuswac)


## Avaliação do projeto

### Dentre os aspectos a serem avaliados do produto final, os seguintes aspectos devem ser considerados:
* Funcionalidade (ter pelo menos um método POST e um método PUT em uso - e da forma correta)
* Uso apropriado de padrões de design e de arquitetura típicos da web. Uso de MVC e boas projeto adequado de API REST
* uso de boas práticas para nomes de rotas
  * escolha apropriada de métodos HTTP para cada operação
  * semântica apropriada nas implementações dos métodos HTTP
  * na API REST, uso devido de status codes de erro

### Requisitos não funcionais
* Todos os dados desta aplicação devem persistir em um banco de dados.
* Autenticação/autorização via JWT - JSON Web Token. O período de validade de um token deve ser definido pelo grupo e justificado no README.md.
* O backend deve produzir apenas strings UTF8, o frontend deve saber ler apenas strings UTF8.
* O projeto deve estar em um repositório privado no github compartilhado com os professores (raquelvl e marcuswac)

## Para os que vão implantar a aplicação no heroku
**Não é obrigatória a implantação da aplicação** como um serviço na Internet. Mas, para quem estiver com vontade de aprender um pouco sobre esse processo aqui vão dicas.

### Sobre habilitação de CORS
Por questões de segurança, precisamos de configurações específicas para permitir que um frontend em um domínio se comunique com um backend em outro domínio. Precisamos habilitar CORS - Cross Origin Resource Sharing. [Este artigo](https://spring.io/blog/2015/06/08/cors-support-in-spring-framework) é bem didático para quem quiser ler um pouco mais sobre isso. Uma forma de habilitar CORS é inserir um @Bean na aplicação com um filtro - [ver código exemplo aqui](https://drive.google.com/file/d/1ruk58z1qTtc07MCGmpwQFGOPNi3zTtec/view?usp=sharing). Outras formas de configuração no spring boot podem ser encontradas [aqui](https://spring.io/blog/2015/06/08/cors-support-in-spring-framework).

### Sobre deploy no heroku: 
* [Vídeo tutorial](https://drive.google.com/file/d/17cbWhwMhyM6aoQ8Oyd2pf0IB-uCp3aPQ/view?usp=sharing) feito pelo monitor Yuri da UFCG. 
* Se você seguir corretamente cada configuração do vídeo vai dar certo. Lembre de não usar palavras reservadas como USER, LIKE como nomes de entidades/tabelas. 

## Deliverable final
Data de entrega 13/08/2020, até as 23:59h (mas se quiser usar a madrugada e entregar até as 8h da manhã do dia 14/08 também aceitamos): 
* link para o README.md do repositório de backend. O README.md do backend deve explicar a API de backend desenvolvida; preferencialmente deve ter um link para a documentação da API gerada pelo swagger (que pode estar no proprio repositorio). [Sobre swagger](http://www.matera.com/blog/post/documentando-api-rest-com-swagger-contract-last).
* [opcional - mas ajuda muito os professores]link do youtube para o vídeo de demonstração da aplicação (de 3 minutos no máximo)

## Casos de uso da API

### Caso de uso 1: cadastrar/autenticar usuários
Cadastrar usuários no sistema com primeiro nome, ultimo nome, e-mail e senha. O e-mail deve ser o identificador único dos usuários, assim se já existe um usuário com um certo e-mail, outro usuário com o mesmo e-mail não poderá ser cadastrado. Se usuário com o e-mail informado não existir ainda, ele deverá ser criado no sistema. 
Usuários cadastrados podem se autenticar passando e-mail e senha. Um usuário que foi autenticado via email/senha deve receber um JWT. Esse token será usado posteriormente no header authorization HTTP, para interações futuras com a API. 

Dica para API: crie a base do seu projeto no https://start.spring.io/, já indicando dependências que com certeza você vai precisar, como por exemplo, web, jpa, h2 e lombok. Depois na sua IDE de preferência você faz o import como um projeto maven. Isso é menos propenso a dar erros.

### Caso de uso 2: pesquisar disciplinas a partir de uma (sub)string
Deve ser possível recuperar o nome completo de disciplinas a partir de substrings. Por exemplo, ao pesquisar "sistemas" um json com uma listagem de id e nomes de disciplinas deve ser enviada na resposta HTTP para o cliente.

Com esta funcionalidade o usuário da API pode identificar os nomes das disciplinas e seus respectivos ids cadastradas no CRdb. Esta funcionalidade é pública, pode ser acessada por qualquer usuário, mesmo que não esteja autenticado.

Todos os nomes das disciplinas do curso considerado devem estar em um arquivo json. Para quem preferir, pode usar estes dados deste [arquivo json](https://drive.google.com/file/d/1r3WFTRwqh8TrQ0f5DvNO6362hhkYnglz/view?usp=sharing). Apenas os nomes estão aí, IDs únicos devem ser gerados para cada disciplina. As disciplinas já devem ser inseridas no banco de dados antes do uso da aplicação.

### Caso de uso 3: Adicionar comentários de uma disciplina 
Usuários autenticados podem adicionar comentários aos perfis das disciplinas (para entender melhor sobre perfis de disciplinas veja o caso de uso 6). O sistema deve manter o usuário que escreveu o comentário e a data e hora em qeu o comentário foi escrito.

### Caso de uso 4: Apagar comentários de uma disciplina
Um usuário autenticado pode apagar comentários que tenha feito anteriormente. Claro que um usuário só pode deletar comentários escritos por ele próprio. A deleção é lógica. Isto significa que o texto do comentário não será mais fornecido pela API, porém, ele é mantido na base de dados. Assim, os comentários devem manter esta informação sobre se o comentário foi apagado ou não. Ao responder uma requisição o backend deve enviar o texto vazio para os comentários apagados. Os comentários apagados, contudo, não devem ser contabilizados para efeitos do ranking (ie. não devem contabilizar no número de comentários que uma disciplina tem).

### Caso de uso 5: Dar/retirar like em uma disciplina
Um usuário autenticado pode "favoritar" com um like suas disciplinas favoritas, uma por vez, claro. Significa que pode dar um like para a disciplina. Um usuário que já deu like em uma disciplina não pode dar um segundo like. O segundo like deve ser entendido como uma retirada ao like dado anteriormente (desistiu de dar like).

Esta rota deve atualizar o perfil da disciplina mantendo a informação dos usuários que deram like. 

### Caso de uso 6: Deve ser possível recuperar o perfil de uma disciplina a partir do seu código numérico
Como já deu pra entender, cada disciplina está associada a um perfil. Esta funcionalidade permite que as informações do perfil de uma disciplina com um dado id seja recuperado. Apenas usuários autenticados podem ter acesso a esta funcionalidade. O perfil de uma disciplina mantém informações que são definidas de forma colaborativa pelos usuários do CRdb. As seguintes informações fazem parte do perfil de uma disciplina: likes, coleção de notas dadas pelos alunos sobre a disciplina, coleção de comentários que os alunos escreveram sobre a disciplina. Ao recuperar o perfil da disciplina, deve ser possível ver a nota da disciplina, que deve ser a média aritmética de todas as notas dadas à disciplina, o número de likes e os comentários. 

Detalhes da API: para esta rota, a API deve retornar um json com o nome da disciplina, o número de likes, a média das notas dadas pelos alunos sobre a disciplina, coleção de comentários que os alunos escreveram sobre a disciplina, e informar se o usuário interagindo com o sistema (que chamou esta rota) deu like (true ou false) e também marcar de alguma forma que comentários que foram inseridos por esse usuário.

### Caso de uso 7: mostrar ranking das disciplinas
Um usuário autenticado pode ver o ranking das disciplinas de acordo com algum critério de livre escolha. Por exemplo, pelo número de likes, pelo número de comentários, ou pela nota. Os comentários apagados, contudo, não devem ser contabilizados para efeitos do ranking.

