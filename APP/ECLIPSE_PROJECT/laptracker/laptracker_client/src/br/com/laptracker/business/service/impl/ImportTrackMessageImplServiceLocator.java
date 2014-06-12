/**
 * ImportTrackMessageImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.laptracker.business.service.impl;

public class ImportTrackMessageImplServiceLocator extends org.apache.axis.client.Service implements br.com.laptracker.business.service.impl.ImportTrackMessageImplService {

    public ImportTrackMessageImplServiceLocator(String portAddress) {
    	this.setImportTrackMessageImplPortEndpointAddress(portAddress);
    }


    public ImportTrackMessageImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ImportTrackMessageImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ImportTrackMessageImplPort
    private java.lang.String ImportTrackMessageImplPort_address = null;

    public java.lang.String getImportTrackMessageImplPortAddress() {
        return ImportTrackMessageImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ImportTrackMessageImplPortWSDDServiceName = "ImportTrackMessageImplPort";

    public java.lang.String getImportTrackMessageImplPortWSDDServiceName() {
        return ImportTrackMessageImplPortWSDDServiceName;
    }

    public void setImportTrackMessageImplPortWSDDServiceName(java.lang.String name) {
        ImportTrackMessageImplPortWSDDServiceName = name;
    }

    public br.com.laptracker.business.service.ImportTrackMessage getImportTrackMessageImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ImportTrackMessageImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getImportTrackMessageImplPort(endpoint);
    }

    public br.com.laptracker.business.service.ImportTrackMessage getImportTrackMessageImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            br.com.laptracker.business.service.impl.ImportTrackMessageImplServiceSoapBindingStub _stub = new br.com.laptracker.business.service.impl.ImportTrackMessageImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getImportTrackMessageImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setImportTrackMessageImplPortEndpointAddress(java.lang.String address) {
        ImportTrackMessageImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (br.com.laptracker.business.service.ImportTrackMessage.class.isAssignableFrom(serviceEndpointInterface)) {
                br.com.laptracker.business.service.impl.ImportTrackMessageImplServiceSoapBindingStub _stub = new br.com.laptracker.business.service.impl.ImportTrackMessageImplServiceSoapBindingStub(new java.net.URL(ImportTrackMessageImplPort_address), this);
                _stub.setPortName(getImportTrackMessageImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ImportTrackMessageImplPort".equals(inputPortName)) {
            return getImportTrackMessageImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://impl.service.business.laptracker.com.br/", "ImportTrackMessageImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://impl.service.business.laptracker.com.br/", "ImportTrackMessageImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ImportTrackMessageImplPort".equals(portName)) {
            setImportTrackMessageImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
