# Arquiteturas de aplicações Web

Em alto nível e falando de forma muito prática, existem duas organizações diferentes para as aplicações Web: aplicações **cliente/servidor** e aplicações **peer-to-peer**.

As aplicações cliente servidor são bem mais comuns e serão alvo deste curso. O servidor oferece serviços para múltiplos clientes. Em se tratando de aplicações Wen temos um Cliente universal que é o browser. Cliente e servidor conversam seguindo im protocolo que se chama HTTP.

É comum dizermos que o servidor roda/executa um serviço: um processo que ouve uma porta TCP (ou UDP) e atende os pedidos que chegam atraves dela.

As aplicações peer-to-peer (ou P2P ou entre pares) não dividem a tarefa de servidor e cliente. Todos os processos que participam da aplicação são servidor e cliente ao mesmo tempo. Essa arquitetura permite compartilhamentos de serviços e dados sem a necessidade de um servidor/nó central para coordenar o compartilhamento. Exemplos de aplicações que seguem esta organização são bittorrent e emule. Aplicações P2P não serão alvo de estuido neste curso e portanto todas as informações que seguem são referentes a aplicações cliente/servidor.

## Onde estão os servidores

Na última década passamos por transformações na infraestrutura em que os servidores são executados. Atualmente a grande maioria dos novos serviços irá executar em hosts na nuvem. Provedores de nuvem públicos (ex. [Amazon](https://aws.amazon.com/), [Microsoft Azure](https://azure.microsoft.com/en-us/) e [Google cloud](https://cloud.google.com/)). Isso reduz custos com manutenção de infraestrutura de serviços (exemplos de custos: pessoal para manter a infraestrutura, energia, resfriamento, local, segurança).

Parte do trabalho do desenvolvedor do backend é implantar (deploy) o serviço (ou os serviços) do backend no servidor (ou servidores) e configurar tudo de forma que o serviço execute corretamente. 

A partir de agora entraremos em um assunto 

## Frontend e backend

O backend de uma aplicação Web é o atuador da experiência de frontend. O frontend da aplicação Web pode ser a página da Web mais bem trabalhada, mas se o backend não funcionar, a aplicação será falha. O backend é responsável por realizar ações de acordo com o que foi solicitado pelo usuário via frontend. No backend são realizados cálculos, lógica de negócios, interações com banco de dados e em geral é lá que ha bastante preocupação com desempenho (dependendo do que precisa ser feito). A maior parte do código necessário para que uma aplicação funcione será executada no backend. O código de backend é executado no servidor, em oposição ao cliente. Isso significa que os desenvolvedores de backend não precisam apenas entender linguagens de programação e bancos de dados, mas também devem ter uma compreensão da arquitetura do servidor. Se uma aplicação estiver lenta, travar com frequência ou gerar erros constantemente, provavelmente é devido a problemas de backend.

Como a maior parte do código de uma aplicação é escrito no backend, ele deve ser fácil de entender e manter/atualizar. Neste curso escreveremos backend em Java, que é uma linguagem orientada a objetos. Assim, é importante que boas práticas de design OO (como [padrões para atribuição de responsabilidade](https://www.devmedia.com.br/desenvolvimento-com-qualidade-com-grasp/28704) e outros [padrões de projeto](https://refactoring.guru/design-patterns/catalog)) sejam seguidos. Baixo acoplamento entre entidades do software, alta coesão dentro uma classe (ou método) e manter responsabilidade com quem tem os dados continuam sendo regras fundamentais para a atribuição de responsabilidades.

## Aplicações de arquitetura monolíticas e microserviços

O backend pode ser organizado basicamenet de 2 formas: seguindo uma arquitetura monolítica ou seguindo uma arquitetura de microsserviços. 

Em uma **aplicação monolítica** temos apenas um único sistema, com todos os módulos da aplicação dentro dele. De forma bem prática e já considerando java como a linguagem de programação, teremos apenas uma runtime rodando e um .jar. Já a aplicação pautada em **microsserviços** terá módulos (.jar) independentes, executando e se comunicando via algum protocolo de comunicação (muito provavelmente usam o protocolo HTTP e trocam informações no formato JSON).

Exemplos bastante didáticos que diferenciam aplicações monolíticas e de microsserviços pode ser visto [nesse blog da caelum](https://blog.caelum.com.br/arquitetura-de-microservicos-ou-monolitica/). Esta leitura deste artigo do blog caelum é fundamental para solidificar o conhecimento e diferenciação sobre arquiteturas monolíticas e de microsserviços.

A arquitetura de microsserviços é bem mais moderna, mas não significa que sempre será a melhor arquitetura para qualquer backend. Como todas as decisões arquiteturais, cada opção tem vantagens e desvantagens (ver tabela abaixo). 

Monolítico | Microserviços
------------ | -------------
Ponto único de falha | Falha de serviços independentes
Base de código extensa/complexa | Sistemas com código base menor e mais simples
Escolher uma tecnologia | Permite trabalhar com diferentes tecnologias
Mais simples de implantar | implantação distribuída
Não há duplicidade de código | Repetição de código e de dados

Aplicações monilíticas são muito mais simples de implantar e não há duplicidade de código; o que são caraterísticas positivas. Porém, é preciso escolher uma única tecnologia (linguagem de programação) para implementar a aplicação, a base de código é extensa porque os programadores precisam entender toda a aplicação de uma vez (em vez de entender módulos idolados). Além disso, a aplicação monolítica é um ponto único de falha (o que claro, pode ser resolvido com um cluster de _failover_).

Como devidir entre estas duas opções arquiteturais? Deve ser levada em conta a experiência do arquiteto de software (se é inexperiente provavelmente melhor começar com algo monolítico que é mais simples). Também deve-se pesar vantagens e desvantagens de cada arquitetura considerando características da aplicação. Se é natural desacoplar módulos independentes ou se seria muito difícil dividir em módulos. Na dúvida, a dica é começar com monolítico e migrar microserviços que façam sentindo depois.
