# Kong Spring Boot Starter

This is a spring boot starter for [kong](https://konghq.com/) API gateway. When using this starter you can auto 
configure [kong](https://konghq.com/) _services_ and _routes_ to expose the configured Java APIs. 


## Usage

To use Kong Spring boot Starter you just need to import this starter in your spring boot application and configure it on 
*application.yml* or *application.properties* file.



### Import Starter Library

As this starter is released on Maven Central Repository, you can use [Maven](http://maven.apache.org/) or 
[Gradle](https://gradle.org/) easily to import the library. The examples are follow.

**Maven**
```xml
<dependency>
    <groupId>com.eimapi</groupId>
    <artifactId>kong-spring-boot-starter</artifactId>
    <version>0.3.0</version>
</dependency>
```

**Gradle**
```groovy
compile group: 'com.eimapi', name: 'kong-spring-boot-starter', version: '0.3.0'
```


### Starter Configuration

The kong starter configurations is very easy, you just need to inform the __kong server URL__. This is the only required
 field.

The starter configuration use the default spring boot configuration policy,this way it's possible to configure it
 through the application file configuration (**application.yml** or **application.properties**).

Bellow has both configuration example with their explanation. 

**application.yml**
```yaml
server:
  port: 8086 #Optional - if not informed the starter will use as default 8080
  address: "192.168.80.74" #Optional - once informed the starter will ignore the build address model

kong:
  server:
    url: "http://192.168.80.110:8001" #REQUIRED - This is the Kong Management API address
  model:
    service:
      connect_timeout: 60000 #Optional - if not informed the starter will use as default 60000
      read_timeout: 60000 #Optional - if not informed the starter will use as default 60000
      write_timeout: 60000 #Optional - if not informed the starter will use as default 60000
      protocol: "http" #Optional - if not informed the starter will use as default 'http'
      retries: 5 #Optional - if not informed the starter will use as default 5
    route:
      strip_path: true #Optional - if not informed the starter will use as default true
      preserve_host: false #Optional - if not informed the starter will use as default false
      regex_priority: 0 #Optional - if not informed the starter will use as default 0
  build:
    mode: REBUILD #Optional - if not informed the starter will use as default CREATE.
                  #Exists 2 different properties to this field (REBUILD | CREATE). If the informed 
                  #option is different, the starter will assume the CREATE option
    addressmode: IP #Optional - if not informed the starter will use as default IP.
                    #Exists 2 different properties to this field (IP | HOST). If the informed 
                    #option is different, the starter will assume the IP option.
                    #NOTE: If the server address is informed this field will be ignored!
```


**application.properties**
```properties
server.port=8086 #Optional - if not informed the starter will use as default 8080
server.address=192.168.80.74 #Optional - once informed the starter will ignore the build address model

kong.server.url=http://192.168.80.110:8001 #REQUIRED - This is the Kong Management API address
kong.model.service.connect_timeout=60000 #Optional - if not informed the starter will use as 
                                         #default 60000
kong.model.service.read_timeout=60000 #Optional - if not informed the starter will use as default 60000
kong.model.service.write_timeout=60000 #Optional - if not informed the starter will use as default 60000
kong.model.service.protocol=http #Optional - if not informed the starter will use as default 'http'
kong.model.service.retries=5 #Optional - if not informed the starter will use as default 5
kong.model.route.strip_path=true #Optional - if not informed the starter will use as default true
kong.model.route.preserve_host=false #Optional - if not informed the starter will use as default false
kong.model.route.regex_priority=0 #Optional - if not informed the starter will use as default 0
kong.model.build.mode=REBUILD #Optional - if not informed the starter will use as default CREATE.
                              #Exists 2 different properties to this field (REBUILD | CREATE). 
                              #If the informed option is different, the starter will assume the
                              #CREATE option
kong.model.build.addressmode=IP #Optional (IP | HOST) #Optional - if not informed the starter will use
                                #as default IP. Exists 2 different properties to this field (IP | HOST).
                                #If the informed option is different, the starter will assume 
                                #the IP option.
                                #NOTE: If the server address is informed this field will be ignored!
```

### Package and Class Filter

when necessary, is possible to use filter to suppress one API, group of APIs or package of APIs groups.
 You can do that adding a property to the configuration file as  shown in the following code parts.
 
When just a set of method inside the class need 

**application.yml**
```yaml
kong:
  filter: "com.eimapi.donothow,com.eimapi.show.DoNotShowThisClass,com.eimapi.show.ShowMethods#doNotShow"
``` 
 
 
### Project Example 

A simple utilization example can be found at [this GitHub repository](https://github.com/gsdenys/kong-starter-example).


