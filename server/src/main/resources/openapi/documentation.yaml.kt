openapi: "3.0.3"
info:
  title: "Application API"
  description: "Application API"
  version: "1.0.0"
servers:
  - url: "http://0.0.0.0:8081"
paths:
  /:
    get:
      description: "Hello World!"
      responses:
        "200":
          description: "OK"
          content:
            text/plain:
              schema:
                type: "string"
              examples:
                Example#1:
                  value: "Hello World!"
components:
  schemas: {}