/**
 * ImportTrackMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.laptracker.business.service;

public interface ImportTrackMessage extends java.rmi.Remote {
    public br.com.laptracker.business.service.ImportTrackMessageResult importTrackMessage(java.lang.String nickname, java.lang.String password, java.lang.String xmlContent) throws java.rmi.RemoteException;
}
