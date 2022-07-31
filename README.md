# Desafio 03: Processamento Distribuído Básico

Esse repositório faz parte do desafio disponível [nessa thread do twitter](https://twitter.com/zanfranceschi/status/1550228591652519936) e também [nesse post de dev.to](https://dev.to/zanfranceschi/desafio-frontend-conectar-a-uma-api-para-sse-9ok).

Por favor, note que o código disponível aqui não tem qualidade de produção e não deveria ser usado para referência de um sistema produtivo.

### Executando a Solução
Em um ambiente virtual, execute:
~~~
$ pip install -r requirements.txt
~~~


Agora o coletor dos resultados:
~~~
$ python results_collector.py
~~~

Para os workers, execute o seguinte comando algumas vezes (ex.: 4x para que 4 workers processem em paralelo) em terminais diferentes:
~~~
$ python worker.py
~~~

E finalmente o módulo que produz o conteúdo a ser processado:
~~~
$ python sender.py
~~~

## Referência

https://learning-0mq-with-pyzmq.readthedocs.io/en/latest/pyzmq/patterns/pushpull.html


## Resoluções da Comunidade

Abaixo você encontra exemplos de resoluções criados pela comunidade:

**FAÇA SEU PULL REQUEST E INCLUA SUA SOLUÇÃO AQUI!!!**
