### Exercícios para exercitar Java OO
Imagine que estamos criando o embrião de um sistema que é uma rede social de alunos para avaliar disciplinas de seu curso específico, por exemplo, computação.
No contexto dessa aplicação, uma Disciplina é uma classe que tem pelo menos os seguintes atributos: id:int, nome:String, List<Comentario> comentarios, likes:int e nota:double.

Use Java para desenvolver as classes importantes para que esta aplicação tenha a seguinte funcionalidade: 
1.	CRUD de usuários (nome, e-mail e senha);
2.	Login de um usuário;
3.	Adiciona a disciplina no sistema. Deve ser possível adicionar uma nova disciplina nesse sistema.
4.	Recupera todas as disciplinas inseridas no sistema.
5.	Atualiza o nome da disciplina com um determinado identificador id no sistema. Essa funcionalidade retorna a disciplina que foi atualizada. 
6.	Atualiza a nota da disciplina com um determinado identificador id no sistema. Retorna a disciplina que foi atualizada
7.	Adiciona um comentário a uma disciplina com identificador id no sistema. Cada comentário deve estar associado a apenas uma disciplina e deve ser possível identificar o usuário que escreveu o comentário e a data em que foi escrito. Retorna a disciplina atualizada com o novo comentário.
8.	Remove um comentário com um determinado identificador. Na verdade, um comentário não deve ser de fato removido, ele deve ser marcado como removido, e não deve mais ser inserido na resposta para o cliente.
9.	Remove a disciplina de identificador id do sistema e retorna a disciplina que foi removida. Ao remover a disciplina, remova também seus comentários.
10.	Recupera uma disciplina com um determinado identificador id (se esta disciplina estiver cadastrada). Apenas os comentários não removidos (logicamente) devem ser retornados.
11.	Retorna todas as disciplinas inseridas no sistema ordenadas pela nota (da maior para a menor.
Pense em todas as possibilidades de erro em cada uma das funcionalidades e programe-se para elas. Use exceções sempre que necessário. 

**Dicas:**

* Use uma classe de fachada de controle para realizar cada uma das funcionalidades do sistema.
* Pode usar sua criatividade para adicionar mais atributos às classes comentadas;
* Você pode ter uma classe que funciona como serviço de cadastro de disciplinas, por exemplo. A fachada conhece esse serviço e solicita funcionalidades desse serviço. Assim, a fachada tem uma referência para um objeto desse serviço. Dentro do cadastro de disciplinas existe a coleção de disciplinas e esse serviço de cadastro realiza as operações de CRUD de disciplina dessa coleção. 
* Um material que pode ajudar:[Padrões de projeto para atribuição de responsabilidades](http://www.dsc.ufcg.edu.br/~jacques/cursos/apoo/html/proj1/proj5.htm) – é um texto beeem antigo, mas os padrões não mudam!
