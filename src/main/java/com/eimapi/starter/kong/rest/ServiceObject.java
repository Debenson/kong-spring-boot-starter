package com.eimapi.starter.kong.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Rest template for kong Service
 * 
 * @author Denys G. Santos
 * @since 0.0.2
 * @version 0.0.1
 */
/**
 * @author dengonca
 *
 */
@JsonIgnoreProperties(value = "routeObjects")
public class ServiceObject {
	private Logger logger = LoggerFactory.getLogger(ServiceObject.class);
	
	@JsonInclude(content = Include.NON_NULL)
	private String name;
	
	private String protocol;
	private String host;
	private Integer port;
	private String path;

	@JsonInclude(content = Include.NON_NULL)
	private String id;
	
	private Integer connect_timeout;
	private Integer read_timeout;
	private Integer retries;
	private Integer write_timeout;
	
	private RouteObject[] routeObjects;
	
	public ServiceObject() {}
	
	public ServiceObject(String name, String protocol, String host, Integer port, String path) {
		super();
		this.name = name;
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.path = path;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getConnect_timeout() {
		return connect_timeout;
	}

	public void setConnect_timeout(Integer connect_timeout) {
		this.connect_timeout = connect_timeout;
	}

	public Integer getRead_timeout() {
		return read_timeout;
	}

	public void setRead_timeout(Integer read_timeout) {
		this.read_timeout = read_timeout;
	}

	public Integer getRetries() {
		return retries;
	}

	public void setRetries(Integer retries) {
		this.retries = retries;
	}

	public Integer getWrite_timeout() {
		return write_timeout;
	}

	public void setWrite_timeout(Integer write_timeout) {
		this.write_timeout = write_timeout;
	}

	public RouteObject[] getRouteObjects() {
		return routeObjects;
	}

	public void setRouteObjects(RouteObject[] routeObjects) {
		this.routeObjects = routeObjects;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceObject other = (ServiceObject) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (protocol == null) {
			if (other.protocol != null)
				return false;
		} else if (!protocol.equals(other.protocol))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		String value = new String();
		
		try {
			value = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			logger.error("JSON Convertion Error: \n" + e.getMessage());
		}
		
		return value;
	}
}