# Arquiteturas de aplicações Web

Em alto nível e falando de forma muito prática, existem duas organizações diferentes para as aplicações Web: aplicações **cliente/servidor** e aplicações **peer-to-peer**.

As aplicações cliente servidor são bem mais comuns e serão alvo deste curso. O servidor oferece serviços para múltiplos clientes. Em se tratando de aplicações Wen temos um Cliente universal que é o browser. Cliente e servidor conversam seguindo im protocolo que se chama HTTP.

É comum dizermos que o servidor roda/executa um serviço: um processo que ouve uma porta TCP (ou UDP) e atende os pedidos que chegam atraves dela.

As aplicações peer-to-peer (ou P2P ou entre pares) não dividem a tarefa de servidor e cliente. Todos os processos que participam da aplicação são servidor e cliente ao mesmo tempo. Essa arquitetura permite compartilhamentos de serviços e dados sem a necessidade de um servidor/nó central para coordenar o compartilhamento. Exemplos de aplicações que seguem esta organização são bittorrent e emule. Aplicações P2P não serão alvo de estuido neste curso.

## Onde executam os servidores

Na última década passamos por transformações na infraestrutura em que os servidores são executados. Atualmente a grande maioria dos novos serviços irão executar em hosts na nuvem.
