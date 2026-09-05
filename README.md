# ExamenASC
Servicio de consulta, creación y actualización para clientes, con información de pedidos

#### Iniciar proyecto ####

Conectarse a la base de datos mongo de atlas con la siguiente cadena de conexión
mongodb+srv://vistaBase:tZtn0aDyBenIiG4r@cluster0.r0r7x8m.mongodb.net/?appName=Cluster0

El proyecto esta creado con arquitectura hexagonal

Este proyecto usa Spring Boot 4.x y Java 21 por lo que la configuración de la uri de mongo cambia 
spring.data.mongodb.uri a spring.mongodb.uri

Modelo Dominio (java puro) base del negocio
Pedido
Cliente
Item

Puertos 
ClienteRepository 
PedidosApi 
ItemsApi

Casos de uso
RegistrarCliente (Inserta un nuevo cliente a la base de datos)
ConsultarCliente (Devuelve un cliente si coincide algun id de la bd)
ActualizarCliente (Actualiza los datos del cliente)
SincronizarPedidosCliente (Agrega los pedidos al cliente)

Servicio de dominio
Para validación de textos permitiendo una tolerancia en la comparación
Conciliacion de pedidos con items

Adapatador Mongo
Con mapper mapstruct

Se concultan dos APIS con RestClient
GET https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/pedidos
reponse
[
    {
        "orderRef": "3010091676",
        "userId": "75c97531-abf5-4524-8107-90aa48d08efc",
        "canal": "online",
        "orderStatus": "2025-12-06",
        "marketPlace": false,
        "giftRegistry": false,
        "items": [
            "3010091676-1132351437",
            "3010091676-1179743767"
        ],
        "storeName": "L  SANTA FE",
        "id": "1"
    }
]
GET https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/items
response
[
    {
        "itemId": "3010091676-1132351437",
        "skuId": "1132351437",
        "quantity": 3,
        "displayName": "Pantalón Levi´s",
        "deliveryStatus": "Compra en línea",
        "id": "1"
    },
    {
        "itemId": "3010091676-1179743767",
        "skuId": "1179743767",
        "quantity": 4,
        "displayName": "Vasos cristal 250ml",
        "deliveryStatus": "Compra en tienda",
        "id": "2"
    }
]

Para despues hacer la union de pedidos e items en una clase conciliadora en Domain 

Consultar contrato al correr aplicacion
http://localhost:8080/swagger-ui/index.html
  
