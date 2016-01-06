package com.sunzxy.androiduitl;

import com.sunzxy.androiduitl.Util.Hex;

/**
 * Created by Sunzxyong on 16/1/6.
 */
public class Test {

    public static void main(String[] args) {
        String name = "zhengxiaoyong";
        String encodeName = Hex.encodeHex(name.getBytes(),false);
        System.out.println("encode:"+encodeName);
        byte[] decodeName = Hex.decodeHex(encodeName);
        System.out.println("decode:"+new String(decodeName));
    }
}
