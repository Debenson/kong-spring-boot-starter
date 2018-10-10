# Kong Spring Boot Starter

This is a spring boot starter for [kong](https://konghq.com/) API gateway. When using this starter you can auto configure
[kong](https://konghq.com/) _services_ and _routes_ to expose the configured Java APIs. 

Follow you can see the Sample configuration


<details>
    <sumary>application.yml</sumary>
    <p>

```yaml
server:
  port: 8086 # Optional
  address: 192.168.80.74 # Optional


kong:
  server:
    url: http://192.168.80.110:8001
  model:
    service:
      connect_timeout: 60000 # Optional
      read_timeout: 60000 # Optional
      write_timeout: 60000 # Optional
      protocol: "http" # Optional
      retries: 5 # Optional
    route:
      strip_path: true # Optional
      preserve_host: false # Optional
      regex_priority: 0 # Optional
  build:
    mode: REBUILD # Optional
    addressmode: IP # Optional (IP | HOST)
```
</p>
</datails>
<details>
    <sumary>application.properties</sumary>
    <p>

```properties
server.port=8086 # Optional
server.address=192.168.80.74 # Optional

kong.server.url=http://192.168.80.110:8001
kong.model.service.connect_timeout=60000 # Optional
kong.model.service.read_timeout=60000 # Optional
kong.model.service.write_timeout=60000 # Optional
kong.model.service.protocol="http" # Optional
kong.model.service.retries=5 # Optional
kong.model.route.strip_path=true # Optional
kong.model.route.preserve_host=false # Optional
kong.model.route.regex_priority=0 # Optional
kong.model.build.mode=REBUILD # Optional
kong.model.build.addressmode=IP # Optional (IP | HOST)
```
</p>
</datails>





Alternatively you also suppress the APIs that you cannot expose using the filter 