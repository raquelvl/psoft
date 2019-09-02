# Acessando dados do banco de dados

Até agora só brincamos um pouco com uma API que não provê persistência aos dados. Em ciência da computação, [persistência](https://pt.wikipedia.org/wiki/Persist%C3%AAncia_(ci%C3%AAncia_da_computa%C3%A7%C3%A3o)) se refere à característica de um estado que sobrevive ao processo que o criou. 

Vamos entender esta frase. Um processo é um "programa em execução". Se durante a execução do programa, dados são criados, modificados e apagados, o que deve acontecer com esses dados depois que o programa é encerrado? Se os dados se mantém só na memória RAM (volátil), então eles irão se perder quando o processo "morrer"(o programa for encerrado). Para que os dados não se percam, eles precisam ser armazenados em uma memória não volátil (como em um disco rígido ou memória flash). Ou seja, pensando em java, devemos realizar a serialização dos dados para um formato armazenável e em seguida salvar os dados serializados em um arquivo.

Uma API pode ser vista como uma interface para gerenciar dados de forma controlada e segura. Toda API acessa/controla dados que devem persistir. Tipicamente, a API vai acessar uma camada de persistência que conta com um Sistema de Gerenciamento de Banco de Dados (SGBD) — ou do inglês Data Base Management System (DBMS). Um SGBD é o conjunto de pacotes de software responsáveis pelo gerenciamento de um banco de dados. O SGBD disponibiliza uma interface para que seus programas clientes possam incluir, alterar ou consultar dados previamente armazenados. No nosso curso usaremos bancos de dados relacionais. De forma simplificada, podemos dizer que um banco de dados relacional modela os dados na forma de tabelas. Nesses bancos de dados  a interface do banco com o usuário é constituída pelos drivers do SGBD, que executam comandos na linguagem SQL (Structured Query Language).

Então vamos entender: estaremos construindo APIs usando a linguagem Java com o suporte do framework spring boot. E deveremos acessar os dados que são armazenados na forma de tabelas (registros em tabelas) usando uma outra linguagem chamada SQL. Esse buraco entre esses dois paradigmas gera bastante trabalho: a todo momento devemos "transformar" objetos em registros e registros em objetos. Como caminhar entre esses dois mundos de forma simples e fácil de entender/manter?

Para facilitar esse processo vamos usar técnicas de mapeamento objeto-relacional (ou ORM, do inglês: Object-relational mapping). ORM é uma técnica de desenvolvimento utilizada para facilitar a programação dos bancos de dados relacionais usando a programação orientada a objetos. Ao usar uma ferramente ORM, as tabelas do banco de dados são representadas dentro da aplicação Java por de classes e os registros de cada tabela são representados por instâncias (objetos) das classes correspondentes. Com esta técnica, o programador não precisa se preocupar com os comandos em linguagem SQL (ou precisará se preocupar bem menos). O programador irá usar uma interface de programação simples que faz todo o trabalho de persistência.

O Hibernate é um framework mais popular para o mapeamento objeto-relacional na linguagem Java. Este framework facilita o mapeamento dos atributos entre uma base tradicional de dados relacionais e o modelo objeto de uma aplicação, mediante o uso de arquivos (XML) ou anotações Java (veja Annotation (java)). Mesmo com essa técnica de mapeamento, ainda não era simples persistir os dados... Assim, o próprio Hibernate foi usado como inspiração para a especificação Java Persistence API (JPA). 

A **JPA** The Java ORM standard for storing, accessing, and managing Java objects in a relational database




