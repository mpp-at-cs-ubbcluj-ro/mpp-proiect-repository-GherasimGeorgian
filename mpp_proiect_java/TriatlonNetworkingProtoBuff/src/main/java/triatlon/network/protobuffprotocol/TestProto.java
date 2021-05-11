package triatlon.network.protobuffprotocol;

import domain.Arbitru;

import java.io.IOException;

public class TestProto {
    public static void main(String[] args) {
        System.out.println("TriatlonRequest: ");
        try {
            ProtoUtils.createLoginRequest(new Arbitru((long)1,"ceva1","ceva2","pe.casaro10@gmail.com","esggs","rgdhd",(long)2)).writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}