package com.whty.euicc.tls.client.crypto.digests;

public class SHA1Digest extends GeneralDigest
{
  private static final int DIGEST_LENGTH = 20;
  
  private int H1;
  
  private int H2;
  
  private int H3;
  
  private int H4;
  
  private int H5;
  
  private int[] X = new int[80];
  private int xOff;
  private static final int Y1 = 1518500249;
  private static final int Y2 = 1859775393;
  private static final int Y3 = -1894007588;
  private static final int Y4 = -899497514;
  
  public SHA1Digest() {
    reset();
  }
  




  public SHA1Digest(SHA1Digest t)
  {
    super(t);
    
    H1 = H1;
    H2 = H2;
    H3 = H3;
    H4 = H4;
    H5 = H5;
    
    System.arraycopy(X, 0, X, 0, X.length);
    xOff = xOff;
  }
  
  public String getAlgorithmName()
  {
    return "SHA-1";
  }
  
  public int getDigestSize()
  {
    return 20;
  }
  


  protected void processWord(byte[] in, int inOff)
  {
    X[(xOff++)] = 
      ((in[inOff] & 0xFF) << 24 | (in[(inOff + 1)] & 0xFF) << 16 | (in[(inOff + 2)] & 0xFF) << 8 | in[(inOff + 3)] & 0xFF);
    
    if (xOff == 16)
    {
      processBlock();
    }
  }
  



  private void unpackWord(int word, byte[] out, int outOff)
  {
    out[(outOff++)] = ((byte)(word >>> 24));
    out[(outOff++)] = ((byte)(word >>> 16));
    out[(outOff++)] = ((byte)(word >>> 8));
    out[(outOff++)] = ((byte)word);
  }
  

  protected void processLength(long bitLength)
  {
    if (xOff > 14)
    {
      processBlock();
    }
    
    X[14] = ((int)(bitLength >>> 32));
    X[15] = ((int)(bitLength & 0xFFFFFFFFFFFFFFFFl));
  }
  


  public int doFinal(byte[] out, int outOff)
  {
    finish();
    
    unpackWord(H1, out, outOff);
    unpackWord(H2, out, outOff + 4);
    unpackWord(H3, out, outOff + 8);
    unpackWord(H4, out, outOff + 12);
    unpackWord(H5, out, outOff + 16);
    
    reset();
    
    return 20;
  }
  



  public void reset()
  {
    super.reset();
    
    H1 = 1732584193;
    H2 = -271733879;
    H3 = -1732584194;
    H4 = 271733878;
    H5 = -1009589776;
    
    xOff = 0;
    for (int i = 0; i != X.length; i++)
    {
      X[i] = 0;
    }
  }
  











  private int f(int u, int v, int w)
  {
    return u & v | (u ^ 0xFFFFFFFF) & w;
  }
  



  private int h(int u, int v, int w)
  {
    return u ^ v ^ w;
  }
  



  private int g(int u, int v, int w)
  {
    return u & v | u & w | v & w;
  }
  



  protected void processBlock()
  {
    for (int i = 16; i < 80; i++)
    {
      int t = X[(i - 3)] ^ X[(i - 8)] ^ X[(i - 14)] ^ X[(i - 16)];
      X[i] = (t << 1 | t >>> 31);
    }
    



    int A = H1;
    int B = H2;
    int C = H3;
    int D = H4;
    int E = H5;
    



    int idx = 0;
    
    for (int j = 0; j < 4; j++)
    {


      E += (A << 5 | A >>> 27) + f(B, C, D) + X[(idx++)] + 1518500249;
      B = B << 30 | B >>> 2;
      
      D += (E << 5 | E >>> 27) + f(A, B, C) + X[(idx++)] + 1518500249;
      A = A << 30 | A >>> 2;
      
      C += (D << 5 | D >>> 27) + f(E, A, B) + X[(idx++)] + 1518500249;
      E = E << 30 | E >>> 2;
      
      B += (C << 5 | C >>> 27) + f(D, E, A) + X[(idx++)] + 1518500249;
      D = D << 30 | D >>> 2;
      
      A += (B << 5 | B >>> 27) + f(C, D, E) + X[(idx++)] + 1518500249;
      C = C << 30 | C >>> 2;
    }
    



    for (int j = 0; j < 4; j++)
    {


      E += (A << 5 | A >>> 27) + h(B, C, D) + X[(idx++)] + 1859775393;
      B = B << 30 | B >>> 2;
      
      D += (E << 5 | E >>> 27) + h(A, B, C) + X[(idx++)] + 1859775393;
      A = A << 30 | A >>> 2;
      
      C += (D << 5 | D >>> 27) + h(E, A, B) + X[(idx++)] + 1859775393;
      E = E << 30 | E >>> 2;
      
      B += (C << 5 | C >>> 27) + h(D, E, A) + X[(idx++)] + 1859775393;
      D = D << 30 | D >>> 2;
      
      A += (B << 5 | B >>> 27) + h(C, D, E) + X[(idx++)] + 1859775393;
      C = C << 30 | C >>> 2;
    }
    



    for (int j = 0; j < 4; j++)
    {


      E += (A << 5 | A >>> 27) + g(B, C, D) + X[(idx++)] + -1894007588;
      B = B << 30 | B >>> 2;
      
      D += (E << 5 | E >>> 27) + g(A, B, C) + X[(idx++)] + -1894007588;
      A = A << 30 | A >>> 2;
      
      C += (D << 5 | D >>> 27) + g(E, A, B) + X[(idx++)] + -1894007588;
      E = E << 30 | E >>> 2;
      
      B += (C << 5 | C >>> 27) + g(D, E, A) + X[(idx++)] + -1894007588;
      D = D << 30 | D >>> 2;
      
      A += (B << 5 | B >>> 27) + g(C, D, E) + X[(idx++)] + -1894007588;
      C = C << 30 | C >>> 2;
    }
    



    for (int j = 0; j <= 3; j++)
    {


      E += (A << 5 | A >>> 27) + h(B, C, D) + X[(idx++)] + -899497514;
      B = B << 30 | B >>> 2;
      
      D += (E << 5 | E >>> 27) + h(A, B, C) + X[(idx++)] + -899497514;
      A = A << 30 | A >>> 2;
      
      C += (D << 5 | D >>> 27) + h(E, A, B) + X[(idx++)] + -899497514;
      E = E << 30 | E >>> 2;
      
      B += (C << 5 | C >>> 27) + h(D, E, A) + X[(idx++)] + -899497514;
      D = D << 30 | D >>> 2;
      
      A += (B << 5 | B >>> 27) + h(C, D, E) + X[(idx++)] + -899497514;
      C = C << 30 | C >>> 2;
    }
    

    H1 += A;
    H2 += B;
    H3 += C;
    H4 += D;
    H5 += E;
    



    xOff = 0;
    for (int i = 0; i < 16; i++)
    {
      X[i] = 0;
    }
  }
}

