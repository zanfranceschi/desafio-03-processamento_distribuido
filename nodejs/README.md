## Como executar

Para execução do script deve-se primeiro ter uma instância redis rodando, isso acontece por causa do uso da biblioteca [bull](https://github.com/OptimalBits/bull) para gerenciamento de fila, caso não tenha um servidor funcionando, recomendo o uso do docker para subir um container com a [imagem oficial do redis](https://hub.docker.com/_/redis/)

### Instalando e subindo o container redis

Já tendo o docker na sua máquina, caso contrário [siga a documentação](https://docs.docker.com/engine/install/)

```
docker pull redis
docker run -p 6379:6379 --name teste -d redis
```

Usando a imagem docker, os seguintes comandos devem ser executados:

```
docker start {nome da sua imagem redis} // caso não esteja rodando
npm install
npm start
```
