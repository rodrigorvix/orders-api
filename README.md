<h1 align="center">
    Orders-API-Ecommerce
</h1>


# ℹ️ Sobre

<p>
Projeto de construção de um e-commerce utilizando o conceito de microserviços. 
</p>
  <br>

# 🛠 Tecnologias

As seguintes tecnologias foram utilizadas na construção do projeto:
  
- Maven
- Java 11
- Spring Boot
- Spring Data
- Spring JPA
- Lombok
- MongoDB
- Docker
  <br><br>

# 🚀 Como executar

Clone o projeto e acesse a pasta.

```
$ git clone https://github.com/rodrigorvix/orders-api.git
$ cd orders-api
```

# ℹ️ Rotas

Segue abaixo os endpoints disponíveis no projeto.


<details><summary><b>Rotas de pedidos (Clique aqui)</b></summary>

1 - POST - http://localhost:8082/v1/{user_id}/orders

```
{

}
```
2- GET - http://localhost:8082/v1/{user_id}/orders

```

```

3 - PATCH Alterar status(open) - http://localhost:8082/v1/{user_id}/orders/{order_id}/open

```

```

4 - PATCH Alterar status(closed) - http://localhost:8082/v1/{user_id}/orders/{order_id}/closed

```

```

5 - DELETE - http://localhost:8082/v1/{user_id}/orders/{order_id}

```

```
</details>

<details><summary><b>Rotas de items do pedido (Clique aqui)</b></summary>

1 - POST - http://localhost:8082/v1/{user_id}/orders/{order_id}/order_items/{product_id}

```
{
	"quantity": 2	
}

```
2- GET - http://localhost:8082/v1/{user_id}/orders/{order_id}/order_items/

```

```

3 - PATCH  - http://localhost:8082/v1/{user_id}/orders/{order_id}/order_items/{order_item_id}

```
{
	"quantity": 15,
	"productId": 1,
	"orderId": "62991b4b70692041da36a0a1",
	"createdAt": "2022-04-11T19:37:15.231704-03:00",
	"updatedAt": "2022-04-11T19:37:15.23172-03:00"
	}

}
```

5 - DELETE - http://localhost:8082/v1/{user_id}/orders/{order_id}/order_items/{order_item_id}

```

```
</details>
