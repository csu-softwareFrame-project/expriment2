/**
 * Copyright (c) 2011, Tim Buktu
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.csu.mypetstore.ntru.demo;

import org.csu.mypetstore.ntru.encrypt.EncryptionKeyPair;
import org.csu.mypetstore.ntru.encrypt.EncryptionParameters;
import org.csu.mypetstore.ntru.encrypt.NtruEncrypt;
import org.csu.mypetstore.ntru.sign.NtruSign;
import org.csu.mypetstore.ntru.sign.SignatureKeyPair;
import org.csu.mypetstore.ntru.sign.SignatureParameters;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple program demonstrating the use of NtruEncrypt and NtruSign.
 */
public class SimpleExample {

    //demo
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //encrypt();
        //System.out.println();
        //sign();
        String password = "a";
        Map<String,Object> map = encrypt(password);
        String enc = (String)map.get("enc");
        byte[] kp = (byte[])map.get("kp");
        String dec = decode(enc,kp);
        System.out.println(dec);
    }

    //自己重写的加密方法，以便数据持久化
    public static Map<String,Object> encrypt(String password) throws IOException {
        Map<String,Object> map = new HashMap<>();

        // create an instance of NtruEncrypt with a standard parameter set
        NtruEncrypt ntru = new NtruEncrypt(EncryptionParameters.APR2011_439_FAST);

        //生成一对新的公私钥
        EncryptionKeyPair kp = ntru.generateKeyPair();
        System.out.println(" 加密前: " + password);

        //序列化
        byte[] ret = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(kp);
        out.close();
        ret = baos.toByteArray();
        baos.close();


        // encrypt the message with the public key created above
        byte[] enc = ntru.encrypt(password.getBytes(), kp.getPublic());//加密
        System.out.println();
        String str = new String(enc, Charset.forName("ISO-8859-1"));//密文，byte转string
        System.out.println("转string（加密后）：");
        System.out.println(str);

        map.put("enc",str);//密文字符串(String)
        map.put("kp",ret);//公私钥串(byte[])
        return map;

    }

    //自己重写的解密方法，以便数据持久化
    public static String decode(String encPassword,byte[] kp) throws IOException, ClassNotFoundException {
        NtruEncrypt ntru = new NtruEncrypt(EncryptionParameters.APR2011_439_FAST);
        //反序列化
        Object retO = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(kp);
        ObjectInputStream in = new ObjectInputStream(bais);
        retO = in.readObject();
        EncryptionKeyPair kp1= (EncryptionKeyPair)retO;
        //System.out.println("kp:"+kp);
        in.close();

        //System.out.println();
        byte[] after = encPassword.getBytes(Charset.forName("ISO-8859-1"));//密文，string转回byte
        System.out.println("再转回：");
        System.out.println(after.length);
        // decrypt the message with the private key created above
        byte[] dec = ntru.decrypt(after, kp1);
        return new String(dec);//密码明文
    }



    private static void encrypt() throws IOException, ClassNotFoundException {
        System.out.println("NTRU encryption");
        
        // create an instance of Nt
        //ruEncrypt with a standard parameter set
        NtruEncrypt ntru = new NtruEncrypt(EncryptionParameters.APR2011_439_FAST);

        // create an encryption key pair
        EncryptionKeyPair kp = ntru.generateKeyPair();

        String msg = "The quick brown fox";
        System.out.println(" 加密前: " + msg);

        byte[] ret = null;
        //序列化
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(kp);
        out.close();
        ret = baos.toByteArray();
        baos.close();

        //反序列化
        Object retO = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(ret);
        ObjectInputStream in = new ObjectInputStream(bais);
        retO = in.readObject();
        EncryptionKeyPair kp1= (EncryptionKeyPair)retO;
        System.out.println("kp:"+kp1);
        in.close();

        // encrypt the message with the public key created above
        byte[] enc = ntru.encrypt(msg.getBytes(), kp.getPublic());//加密

        System.out.println();
        String str = new String(enc, Charset.forName("ISO-8859-1"));//密文，byte转string
        System.out.println("转string（加密后）：");
        System.out.println(str);

        System.out.println();
        byte[] after = str.getBytes(Charset.forName("ISO-8859-1"));//密文，string转回byte
        System.out.println("再转回：");
        System.out.println(after.length);
        // decrypt the message with the private key created above
        byte[] dec = ntru.decrypt(after, kp1);

        // print the decrypted message
        System.out.println("  解密后:  " + new String(dec));
    }

    private static void sign() {
        System.out.println("NTRU signature");
        
        // create an instance of NtruSign with a test parameter set
        NtruSign ntru = new NtruSign(SignatureParameters.TEST157);
        
        // create an signature key pair
        SignatureKeyPair kp = ntru.generateKeyPair();

        String msg = "The quick brown fox";
        System.out.println("  Message: " + msg);
        
        // sign the message with the private key created above
        byte[] sig = ntru.sign(msg.getBytes(), kp);
        
        // verify the signature with the public key created above
        boolean valid = ntru.verify(msg.getBytes(), sig, kp.getPublic());
        
        System.out.println("  Signature valid? " + valid);
    }
}