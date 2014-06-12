/**
 * ImportTrackMessageResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.laptracker.business.service;

public class ImportTrackMessageResult implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ImportTrackMessageResult(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _LOGIN_FAILED = "LOGIN_FAILED";
    public static final java.lang.String _SUCCESS = "SUCCESS";
    public static final java.lang.String _FAIL = "FAIL";
    public static final ImportTrackMessageResult LOGIN_FAILED = new ImportTrackMessageResult(_LOGIN_FAILED);
    public static final ImportTrackMessageResult SUCCESS = new ImportTrackMessageResult(_SUCCESS);
    public static final ImportTrackMessageResult FAIL = new ImportTrackMessageResult(_FAIL);
    public java.lang.String getValue() { return _value_;}
    public static ImportTrackMessageResult fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ImportTrackMessageResult enumeration = (ImportTrackMessageResult)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ImportTrackMessageResult fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ImportTrackMessageResult.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.business.laptracker.com.br/", "importTrackMessageResult"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
