# Search Ingestion API

Ingestion system that consumes product and offer
update events and produces updates to be sent into a search engine.
The search engine that the system talks to is product-centric and therefore it
must receive documents representing products with all offers related to them
grouped together. Search engine should never contain products with no offers.


## Executing

### From source code
Clone the repo and run the following command:
```
mvnw spring-boot:run
```

### From docker 
```
docker run -p 8080:8080 necais/search-ingestion-api:0.0.1 
```


The application will be available at http://localhost:8080
The database H2 console will be available at http://localhost:8080/h2-console
The swagger will be available at http://localhost:8080/swagger-ui/index.html


## Building

### Building jar file
```
mvnw package
```

### Building docker
```
docker build -t necais/search-ingestion-api:0.0.1 .
dokcer push necais/search-ingestion-api:0.0.1
```

### Example requests:

Create an offer:

```
curl --request PUT \
  --url http://localhost:8080/api/v1/offers \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: Insomnia/2023.5.7' \
  --data '{
	
	"offerId": "o3",
	"offerName":  "P3 offer",
	"relatedProductId": "p24"
}'
```

Create a product
```
curl --request PUT \
  --url http://localhost:8080/api/v1/products \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: Insomnia/2023.5.7' \
  --data '{
	"productId":"p23",
	"productName": "222"
}'
```