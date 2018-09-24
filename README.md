# kong-client-spring-boot-starter
Spring boot client starter for kong api gateway




	server:
		port: 8086
		address: 192.168.80.99
	kong:
	  service:
	    url: http://192.168.80.83:8001
	    user: test
	    password: test
	  mapping:
	    host: 192.168.80.83