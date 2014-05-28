///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tool.snmp;
//
//import java.io.IOException;
//import org.snmp4j.PDU;
//import org.snmp4j.SNMP4JSettings;
//
//import org.snmp4j.ScopedPDU;
//import org.snmp4j.Snmp;
//import org.snmp4j.TransportMapping;
//import org.snmp4j.UserTarget;
//import org.snmp4j.mp.SnmpConstants;
//import org.snmp4j.security.AuthMD5;
//import org.snmp4j.security.Priv3DES;
//import org.snmp4j.security.SecurityLevel;
//import org.snmp4j.security.SecurityModels;
//import org.snmp4j.security.SecurityProtocols;
//import org.snmp4j.security.USM;
//import org.snmp4j.security.UsmUser;
//import org.snmp4j.smi.Address;
//import org.snmp4j.smi.GenericAddress;
//import org.snmp4j.smi.OID;
//import org.snmp4j.smi.OctetString;
//import org.snmp4j.smi.VariableBinding;
//import org.snmp4j.transport.DefaultUdpTransportMapping;
//
///**
// *
// * @author ryan_zhu
// */
//public class SnmpUtilSendTrap {
// private static final int _500 = 500;
//    private static final String LOCAL_USER = "user4example";
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//
//        SNMP4JSettings.setExtensibilityEnabled(true);
//        SecurityProtocols.getInstance().addDefaultProtocols();
//
//        UserTarget snmpTarget = new UserTarget();
//
//        snmpTarget.setSecurityName(new OctetString(LOCAL_USER));
//        snmpTarget.setVersion(SnmpConstants.version3);
//
//
//        int portNumber =162;
//        TransportMapping transport = null;
//        try {
//            transport = new DefaultUdpTransportMapping();
//        } catch (IOException e1) {
//           e1.printStackTrace();
//        }
//
//        Snmp snmp = new Snmp(transport);
//        byte[] enginId = "TEO_ID".getBytes();
//        USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(enginId), _500);
//        SecurityModels secModels = SecurityModels.getInstance();
//        synchronized (secModels) {
//            if (snmp.getUSM() == null) {
//              secModels.addSecurityModel(usm);
//            }
//
//            snmp.getUSM().addUser(
//                    new OctetString(LOCAL_USER), new OctetString(enginId),
//                        new UsmUser(new OctetString(LOCAL_USER), AuthMD5.ID, new OctetString("12345678"), Priv3DES.ID, new OctetString("123456789") ));
//
//           snmpTarget.setSecurityLevel(SecurityLevel.AUTH_PRIV);
//
//            try {
//                transport.listen();
//            } catch (IOException e1) {
//               e1.printStackTrace();
//            }
//            StringBuffer stringBuffer = new StringBuffer();
//            stringBuffer.append("udp:").append("127.0.0.1").append("/").append(portNumber);
//
//            Address targetAddress =
//                 GenericAddress.parse(stringBuffer.toString());
//            snmpTarget.setAddress(targetAddress);
//
//            targetAddress =
//                GenericAddress.parse(stringBuffer.toString());
//           snmpTarget.setAddress(targetAddress);
//
//            ScopedPDU pdu = new ScopedPDU();
//            pdu.setType(PDU.TRAP);
//            pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID, new OID("1.3.6.2")));
//            pdu.add(new VariableBinding(new OID("1.3.6.2"), new OctetString("2323") ));
//
//            snmp.setLocalEngine(enginId, _500, 1);
//            try {
//
//                snmp.send(pdu, snmpTarget);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            snmp.getUSM().removeUser(new OctetString(enginId), new OctetString(LOCAL_USER));
//            System.out.println("end");
//
//       }
//    }
//}
