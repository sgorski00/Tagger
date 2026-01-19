[![codecov](https://codecov.io/gh/sgorski00/Tagger/graph/badge.svg?token=D8F3ILZU6X)](https://codecov.io/gh/sgorski00/Tagger)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=spring-boot&logoColor=white)
![OpenAI](https://img.shields.io/badge/OpenAI-412991?logo=openai&logoColor=white)

# Tagger - backend

This is backend part of Tagger application.

## Live Demo

The live demo is not available at the moment.

## API

Tagger provides both REST and GraphQL APIs for generating tags, titles, and descriptions for various items. The API is designed to be flexible and easy to use, allowing you to generate content for different platforms and audiences.

Tagger supports multilanguage generation. To change language, you can set the `Accept-Language` header in your request.
The default language is English (`en`), but you can use any other language that is supported by the OpenAI API.

Error messages are available only in english and polish.

---

## REST

Rest API documentation is available at `/docs`. Below is a simple example of how to use the REST API.

### Request
`POST /api/tags`

```json
{
  "item": "iPhone 14 Pro",
  "responseStyle": "casual",
  "targetAudience": "teenagers",
  "tagsQuantity": 5,
  "platform": "ebay"
}
```

### Response
```json
{
  "title": "iPhone 14 Pro - The Ultimate Smartphone for Teens"
  "description": "Discover the iPhone 14 Pro, a cutting-edge smartphone designed for teenagers. With its sleek design and advanced features, it's the perfect device for staying connected and entertained.",
  "tags": [
    "#iPhone14",
    "#iPhone14Pro",
    "#Smartphone",
    "#Apple",
    "#BestDeals"
  ],
}
```

---

## GraphQL

GraphQL API playground and documentation is available at `/graphiql`. Below is a simple example of how to use the GraphQL API.

### Request

```graphql
query {
  generateListingElectronics(input: {
    item: "smartphone"
    responseStyle: "detailed"
    targetAudience: "tech enthusiasts"
    tagsQuantity: 5
    platform: "eBay"
    brand: "Apple"
    model: "iPhone 14"
    specifications: "128GB storage, 6GB RAM"
    color: "Black"
    monthsOfWarranty: 12
  }) {
    title
    tags
  }
}
```

### Response

```json
{
  "data": {
    "generateListingElectronics": {
      "title": "Apple iPhone 14 - 128GB, Black - Unleash Your Tech Passion",
      "tags": [
        "#Apple",
        "#iPhone14",
        "#Smartphone",
        "#TechEnthusiasts",
        "#eBayDeals"
      ]
    }
  }
}
```

---

## Response history

Response history is available at `/history`. You can view the history of generated responses, including generated tags, titles, and descriptions.

To save and see history, you need to be logged in. You can log in via Google OAuth2 using the `/auth/login` endpoint.

After logging in, you can access the history of your generated responses at `/history`. Your history is visible only to you.

---

## Configuration

### AI model
To use the AI model, you need to set the `OPENAI_API_KEY` environment variable with your OpenAI API key. 

You also need to provide `OPENAI_API_BASE_URL` and `OPENAI_API_MODEL`.

#### Example .env file
```bash
OPENAI_API_KEY=your_openai_api_key
OPENAI_API_BASE_URL=https://api.groq.com/openai
OPENAI_API_MODEL=llama-3.3-70b-versatile
```

## Technologies used

- Java 25
- Spring Boot 4
- OpenAI API
- JUnit 5, Mockito
- Maven
- Lombok, Jakarta Validation, OpenAPI (`Swagger`)

## Author

- [sgorski00](https://github.com/sgorski00)
