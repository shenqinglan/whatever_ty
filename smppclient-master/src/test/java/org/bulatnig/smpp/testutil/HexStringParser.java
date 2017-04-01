package org.bulatnig.smpp.testutil;

import org.bulatnig.smpp.pdu.Pdu;
import org.bulatnig.smpp.pdu.impl.DefaultPduParser;
import org.bulatnig.smpp.pdu.impl.DeliverSm;
import org.bulatnig.smpp.util.ByteBuffer;

/**
 * Useful class for parsing hex dumps.
 *
 * @author Bulat Nigmatullin
 */
public class HexStringParser {

    public static final void main(String[] args) throws Exception {
        String dump = "000000b300000005000000000fa79c1f000101373930363033373633323000000132353133233830343934393134000400000000000000006669643a31393038313937393334207375623a30303120646c7672643a303030207375626d697420646174653a3131303332313137343920646f6e6520646174653a3131303332313137353420737461743a45585049524544206572723a30303020746578743a0427000103001e000b3139303831393739333400";
        DeliverSm deliverSm = (DeliverSm) parse(dump);
        System.out.println(new String(deliverSm.getShortMessage()));
    }

    private static Pdu parse(String hexString) throws Exception {
        byte[] array = new byte[hexString.length()/2];
        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) Integer.parseInt(hexString.substring(i*2, (i+1)*2), 16);
        }
        ByteBuffer bb = new ByteBuffer(array);
        return new DefaultPduParser().parse(bb);
    }

}
