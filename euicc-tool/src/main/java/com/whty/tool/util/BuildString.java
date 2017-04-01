package com.whty.tool.util;

public class BuildString {
	//String类的构造方法详解
	//方法一：String();
	//方法二：String(byte[] bytes)
	//方法三：String (byte[] bytes,int index,int length)
	//方法四：String（char[] value）
	//方法五：String（char[] value,int index,int length）
	//方法六：String（String str）

	    public static void main(String[] args) {
	        //方法一：String();
	        String s1=new String();
	        //String这个类重写了Object父类的方法，return this；
	        System.out.println("s1:"+s1);
	        System.out.println("s1.length:"+s1.length());
	        System.out.println("---------------------");
	  
	        //方法二：String(byte[] bytes)
	        byte[] byts={97,98,99,100,101,102};
	        String s2=new String(byts);
	        System.out.println("s2:"+s2);
	        System.out.println("s2.length:"+s2.length());
	        System.out.println("---------------------");
	        
	        //方法三：String (byte[] bytes,int index,int length)
	        byte[] byts2={97,98,99,100,101,102};
	        String s3=new String(byts2,2,4);
	        System.out.println("s3:"+s3);
	        System.out.println("s3.length:"+s3.length());
	        System.out.println("---------------------");
	        
	        //方法四：String（char[] value）
	        char [] value={'a','b','c','d','e','f'};
	        String s4=new String(value);
	        System.out.println("s4:"+s4);
	        System.out.println("s4.length:"+s4.length());
	        System.out.println("---------------------");
	        
	        //方法五：String（char[] value,int index,int length）
	        char [] value2={'a','b','c','d','e','f'};
	        String s5=new String(value,2,4);
	        System.out.println("s5:"+s5);
	        System.out.println("s5.length:"+s5.length());
	        System.out.println("---------------------");
	        
	        //方法六：String（String str）
	        String s6=new String("abcdef");
	        System.out.println("s6:"+s6);
	        System.out.println("s6.length:"+s6.length());
	        System.out.println("---------------------");
	        
	    }


}
