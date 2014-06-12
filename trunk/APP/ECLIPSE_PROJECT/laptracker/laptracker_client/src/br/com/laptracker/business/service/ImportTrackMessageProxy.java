package br.com.laptracker.business.service;

public class ImportTrackMessageProxy implements br.com.laptracker.business.service.ImportTrackMessage {
  private String _endpoint = null;
  private br.com.laptracker.business.service.ImportTrackMessage importTrackMessage = null;
  
  public ImportTrackMessageProxy(String portAddress) {
    _initImportTrackMessageProxy(portAddress);
  }
  
  private void _initImportTrackMessageProxy(String portAddress) {
    try {
      importTrackMessage = (new br.com.laptracker.business.service.impl.ImportTrackMessageImplServiceLocator(portAddress)).getImportTrackMessageImplPort();
      if (importTrackMessage != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)importTrackMessage)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)importTrackMessage)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (importTrackMessage != null)
      ((javax.xml.rpc.Stub)importTrackMessage)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public br.com.laptracker.business.service.ImportTrackMessage getImportTrackMessage() {
//    if (importTrackMessage == null)
//      _initImportTrackMessageProxy();
    return importTrackMessage;
  }
  
  public br.com.laptracker.business.service.ImportTrackMessageResult importTrackMessage(java.lang.String nickname, java.lang.String password, java.lang.String xmlContent) throws java.rmi.RemoteException{
//    if (importTrackMessage == null)
//      _initImportTrackMessageProxy();
    return importTrackMessage.importTrackMessage(nickname, password, xmlContent);
  }
  
  
}