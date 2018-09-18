package com.eimapi.starter.kong.bean;

import com.eimapi.starter.kong.exception.KongStarterException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Abstract class that helps to implements {@link ApiCandidateSearch} interface
 *
 * @author Denys G. Santos
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractApiCandidateSearch {

    /**
     * method to get the {@link InetAddress} object
     *
     * @return InetAddress the {@link InetAddress} object with localhost definition
     * @throws KongStarterException if any {@link UnknownHostException} occur
     */
    private InetAddress getInetAddress() throws KongStarterException {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new KongStarterException("Error when try to get the local host object", e.getCause());
        }
    }

    /**
     * Method to get the Host IP
     *
     * @return String the host IP
     */
    protected String getIP() throws KongStarterException {
        return this.getInetAddress().getHostAddress();
    }

    /**
     *  method to get the Hostname
     *
     * @return String the hostname
     */
    protected String getHostName() throws KongStarterException {
        return this.getInetAddress().getHostName();
    }
}
