# kong-client-spring-boot-starter
Spring boot client starter for kong api gateway


Sample configuration

	server:
	  port: 8086 # Optional
	  address: 192.168.80.74 # Optional
	
	kong:
	  server:
	    url: http://192.168.80.110:8001
	    user: test # Optional
	    password: tests # Optional
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
