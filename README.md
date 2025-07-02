[![codecov](https://codecov.io/gh/sgorski00/Tagger/graph/badge.svg?token=D8F3ILZU6X)](https://codecov.io/gh/sgorski00/Tagger)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=spring-boot&logoColor=white)
![OpenAI](https://img.shields.io/badge/OpenAI-412991?logo=openai&logoColor=white)

# Tagger

Tagger is a AI powered web application that allows users to create tags, descriptions and titles for their items.
App allows users to create tags for every product they own.

## Live Demo

Application demo is hosted on Render and can be accessed at the following link:
[Tagger](https://tagger-kohq.onrender.com)

At this link you can also find the documentation for the API.

## Example

### Request
`POST /api/tags`

```json
{
  "item": "iPhone 14 Pro",
  "responseStyle": "casual",
  "targetAudience": "teenagers",
  "item": "Smartphone",
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

- Java 21
- Spring Boot
- OpenAI API
- JUnit 5, Mockito
- Maven
- Lombok, Jakarta Validation, OpenAPI (`Swagger`)

## Author

- [sgorski00](https://github.com/sgorski00)
