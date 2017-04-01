#include "stdafx.h"
#include "Ecc.h"
#include <stdio.h>
#include<string.h>
#include "EccArth.h"
#include <openssl/bn.h>
#include <openssl/ec.h>
#include <openssl/rand.h>
#include <openssl/err.h>
#include <openssl/ecdsa.h>
#include <openssl/ecdh.h>
#include <openssl/evp.h>
#include <openssl/sha.h>

#pragma comment(lib,"libeay32.lib")

#define V 256
#define VBYTE (V+7)/8
#define RSANUMLEN  64
#define PUBKEYLEN  64
#define PRIKEYLEN  64

unsigned char *hextochs ( unsigned char* ascii )
{
    int len = strlen((const char *)ascii) ;
	unsigned char *chs = NULL ;
	int  i = 0 ;
	char ch[2] = {0};
    if( len%2 != 0 )
        return NULL ;
    
    chs = (unsigned char*)OPENSSL_malloc( len / 2 + 1);                
	// calloc chs
	
	
    while( i < len )
    {
        ch[0] = ( (int)ascii[i] > 64 ) ? ( ascii[i]%16 + 9 ) : ascii[i]%16 ;
        ch[1] = ( (int)ascii[i + 1] > 64 ) ? ( ascii[i + 1]%16 + 9 ) : ascii[i + 1]%16 ;
		
        chs[i/2] = (unsigned char)( ch[0]*16 + ch[1] );
        i += 2;
    }
	memcpy(ascii, chs, len / 2 + 1);
	OPENSSL_free(chs);
	
    return ascii ;            // chs ∑µªÿ«∞Œ¥ Õ∑≈
}

int hex2asc(char *dest, const unsigned char *src, unsigned int srclen)
{
	if(!src)  return 0;
	if(!dest) return srclen*2;
	unsigned int npos=0;
	unsigned int loop=0;
	for(loop=0; loop<srclen; loop++)
		npos += sprintf(dest+npos,"%02X",(unsigned char)src[loop]);  
	return npos;
} 


bool AsciiToHex(unsigned char * pAscii, unsigned char * pHex, int nLen)
{
	if (nLen%2)
		return false;
	int nHexLen = nLen / 2;
	for (int i = 0; i < nHexLen; i ++)
	{
		unsigned char Nibble[2];
		Nibble[0] = *pAscii ++;
		Nibble[1] = *pAscii ++;
		for (int j = 0; j < 2; j ++)
		{
			if (Nibble[j] <= 'F' && Nibble[j] >= 'A')
				Nibble[j] = Nibble[j] - 'A' + 10;
			else if (Nibble[j] <= 'f' && Nibble[j] >= 'a')
				Nibble[j] = Nibble[j] - 'a' + 10;
			else if (Nibble[j] >= '0' && Nibble[j] <= '9')
				Nibble [j] = Nibble[j] - '0';
			else
				return false;
		} // for (int j = ...)
		pHex[i] = Nibble[0] << 4; // Set the high nibble
		pHex[i] |= Nibble[1]; //Set the low nibble
	} // for (int i = ...)
	return true;
}


bool GeneratorECKeyPair(char* cp, char* ca, char* cb, char* cGx, char* cGy, char* cn, char* ch, EC_KEY* &eckey )
{
	BIGNUM *p,*a,*b,*Gx,*Gy,*n,*rGy,*DA, *RandNum;
	EC_GROUP *group;
	BN_CTX *ctx = NULL;
	p = BN_new();
	a = BN_new();
	b = BN_new();
	Gx = BN_new();
	Gy = BN_new();
	rGy = BN_new();
	n = BN_new();
	DA = BN_new();
	RandNum = BN_new();

	group = EC_GROUP_new(EC_GFp_mont_method());
	ctx = BN_CTX_new();
	if (!ctx) return false;
	
	if (!p || !a || !b|| !Gx|| !Gy|| !rGy|| !n|| !DA) return false;
	if (!BN_hex2bn(&p, cp)) return false;
	if (!BN_hex2bn(&a, ca)) return false;
	if (!BN_hex2bn(&b, cb)) return false;
	if (!BN_hex2bn(&Gx, cGx)) return false;
	if (!BN_hex2bn(&Gy, cGy)) return false;
	if (!BN_hex2bn(&n, cn)) return false;
	//if (!BN_hex2bn(&n, sn.GetBuffer(0))) return false;
	
	if ( !BN_rand_range( RandNum, n ) )
	{
		return false;
	}
	char *ptmp = BN_bn2hex( RandNum );

	if (!EC_GROUP_set_curve_GFp(group, p, a, b, ctx))
	{
		return false;
	}
	EC_POINT *P, *Q, *R;
	P = EC_POINT_new(group);
	Q = EC_POINT_new(group);
	R = EC_POINT_new(group);
	if (!P || !Q || !R)
	{
		 return false;
	}
	
 	if (!EC_POINT_set_affine_coordinates_GFp(group, P, Gx, Gy, ctx))
 	{
 		 return false;
 	}
 	if (!EC_POINT_is_on_curve(group, P, ctx)) 
 	{
 		 return false;
 	}
 	if (!EC_GROUP_set_generator(group, P, n, BN_value_one())) 
 	{
 		 return false;
 	}


	if (!EC_POINT_mul(group, Q, RandNum, NULL, NULL, ctx)) 
	{
		return false;
	}

	if (!EC_POINT_get_affine_coordinates_GFp(group, Q, Gx, rGy, ctx)) 
 	{
 		 return false;
 	}
	char *pQx = BN_bn2hex( Gx );
	char *pQy = BN_bn2hex( rGy );
	
	if ((eckey = EC_KEY_new()) == false)
	{
		EC_POINT_free(P);
		EC_POINT_free(Q);
		EC_POINT_free(R);
		EC_GROUP_free(group);
		BN_CTX_free(ctx);
		return false;
	}
	if (EC_KEY_set_group(eckey, group) == 0)
	{
		EC_POINT_free(P);
		EC_POINT_free(Q);
		EC_POINT_free(R);
		EC_GROUP_free(group);
		BN_CTX_free(ctx);
		return false;
	}
	/* create key */
	if (!EC_KEY_generate_key(eckey))
	{
		EC_POINT_free(P);
		EC_POINT_free(Q);
		EC_POINT_free(R);
		EC_GROUP_free(group);
		BN_CTX_free(ctx);
		return false;
	}

	EC_KEY_set_private_key( eckey, RandNum );
	EC_KEY_set_public_key( eckey, Q );

	return true;
}

char* CreateECCKeyPair(char* cp, char* ca, char* cb, char* cGx, char* cGy, char* cn, char* ch)
{
	char *presult = (char*)malloc(2048);
	memset(presult, 0, 2048);

	if( cp == NULL ||
		ca == NULL ||
		cb == NULL ||
	   cGx == NULL ||
	   cGy == NULL || 
	   cn == NULL ||
	   ch == NULL  )
	{
		strcpy(presult, "parameter error!");
		return presult;
	}
	
	const EC_GROUP *group;
	EC_KEY *eckey = NULL;
	BN_CTX *ctx = BN_CTX_new();

	GeneratorECKeyPair(cp, ca, cb, cGx, cGy, cn, ch, eckey);
	if(eckey == NULL)
	{
		strcpy(presult, "parameter error!");
		BN_CTX_free( ctx );
		return presult;
	}

	group = EC_KEY_get0_group(eckey);


	BIGNUM *Gx, *Gy, *ck;
	Gx = BN_new();
	Gy = BN_new();
	ck = BN_new();

	char *chgx = NULL;
	char *chgy = NULL;
	char *chDa = NULL;

	ck = (BIGNUM*)EC_KEY_get0_private_key( eckey );
	

	EC_POINT *PA;
	PA = EC_POINT_new(group);

	if (!EC_POINT_mul(group, PA, ck, NULL, NULL, ctx))
	{
		//var = "EC_POINT_mul º∆À„¥ÌŒÛ£°";
		strcpy(presult, "EC_POINT_mul calculate error!");
		BN_CTX_free( ctx );
		EC_POINT_clear_free( PA );
		return presult;
	}

	if( !EC_POINT_get_affine_coordinates_GFp(group, PA, Gx, Gy, ctx))
	{
		//var = "EC_POINT_get_affine_coordinates_GFp º∆À„¥ÌŒÛ£°";
		strcpy(presult, "EC_POINT_get_affine_coordinates_GFp calculate error!");
		BN_CTX_free( ctx );
		EC_POINT_clear_free( PA );
		return presult;
	}

	chgx = BN_bn2hex(Gx);

	int iCount = strlen(chgx);
	char chCHGX[PUBKEYLEN+1] = {0};

	if (iCount<PUBKEYLEN)
	{
        for(int i=0; i<PUBKEYLEN-iCount; i++)
		{
			strcat(chCHGX, "0");
		}

		memcpy(chCHGX+PUBKEYLEN-iCount, chgx, iCount);
	}
	else
	{
		memcpy(chCHGX, chgx, iCount);
	}

	chgy = BN_bn2hex(Gy);
	iCount = strlen(chgy);
	char chCHGY[PUBKEYLEN+1] = {0};

	if (iCount<PUBKEYLEN)
	{
        for(int i=0; i<PUBKEYLEN-iCount; i++)
		{
			strcat(chCHGY, "0");
		}

		memcpy(chCHGY+PUBKEYLEN-iCount, chgy, iCount);
	}
	else
	{
		memcpy(chCHGY, chgy, iCount);
	}


	chDa = BN_bn2hex(ck);
	iCount = strlen(chDa);
	char chCHDA[PRIKEYLEN+1] = {0};

	if (iCount<PRIKEYLEN)
	{
        for(int i=0; i<PRIKEYLEN-iCount; i++)
		{
			strcat(chCHDA, "0");
		}

		memcpy(chCHDA+PRIKEYLEN-iCount, chDa, iCount);
	}
	else
	{
		memcpy(chCHDA, chDa, iCount);
	}

	//char* result;
	//strcat(strcat(strcpy(result,chgx),chgy),chDa);
	//presult = result;
	sprintf( presult, "%s%s%s", chCHDA,chCHGX,chCHGY);

	return presult;
}

char* CreateECCPubKeyByDa(char* cp, char* ca, char* cb, char* cGx, char* cGy, char* cn, char* ch, char* cDa)
{
	char presult[1024]={0};
	if( cp == NULL ||
		  ca == NULL ||
		  cb == NULL ||
			cGx == NULL ||
			cGy == NULL || 
			cn == NULL ||
			ch == NULL ||
			cDa == NULL )
	{
		strcpy(presult, "parameter error!");
		return presult;
	}
			
	const EC_GROUP *group;
	EC_KEY *eckey = NULL;
	BN_CTX *ctx = BN_CTX_new();

	GeneratorECKeyPair( cp, ca, cb, cGx, cGy, cn, ch, eckey);
	if(eckey == NULL)
	{
		strcpy(presult, "parameter error!");
		BN_CTX_free( ctx );
		return presult;
	}

	group = EC_KEY_get0_group(eckey);


	BIGNUM *Gx, *Gy, *ck;
	Gx = BN_new();
	Gy = BN_new();
	ck = BN_new();

	BN_hex2bn( &ck, cDa);

	char *chgx;
	char *chgy;

	EC_POINT *PA;
	PA = EC_POINT_new(group);

	if (!EC_POINT_mul(group, PA, ck, NULL, NULL, ctx))
	{
		//var = "EC_POINT_mul º∆À„¥ÌŒÛ£°";
		strcpy(presult, "EC_POINT_mul calculate error!");
		BN_CTX_free( ctx );
		EC_POINT_clear_free( PA );
		return presult;
	}

	if( !EC_POINT_get_affine_coordinates_GFp(group, PA, Gx, Gy, ctx))
	{
		//var = "EC_POINT_get_affine_coordinates_GFp º∆À„¥ÌŒÛ£°";
		strcpy(presult, "EC_POINT_get_affine_coordinates_GFp calculate error!");
		BN_CTX_free( ctx );
		EC_POINT_clear_free( PA );
		return presult;
	}

	chgx = BN_bn2hex(Gx);
	chgy = BN_bn2hex(Gy);
	
//	int iLen = strlen(chgx) + strlen(chgy);
//	char *result = new char[iLen+1];
	//memset(presult, 0, iLen+1);
	/*memcpy(presult, chgx, strlen(chgx));
	memcpy(presult + strlen(chgx), chgy, strlen(chgy));
	*/
//	memcpy(presult, result, iLen);
//	delete[] result;
	//strcat(strcpy(result,chgx),chgy);
	//presult = result;
	sprintf( presult, "%s%s", chgx,chgy);
	
	return presult;
}


char* ECC_ECKA_Sign(char* cp, char* ca, char* cb, char* cGx, char* cGy, char* cn, char* ch, char* cM, char* cDA)
{

	char* presult = (char*)malloc(2048);
	memset(presult, 0, 2048);

	if( cp == NULL ||
		  ca == NULL ||
		  cb == NULL ||
			cGx == NULL ||
			cGy == NULL || 
			cn == NULL ||
			ch == NULL ||
			cDA == NULL )
	{
		presult = "parameter error!";
		return presult; //presult;
	}
	
	EC_KEY	*eckey = NULL;
	BN_CTX *ctx = BN_CTX_new();

	GeneratorECKeyPair(cp, ca, cb, cGx, cGy, cn, ch, eckey);
	if( eckey == NULL )
	{
		presult = "parameter error!";
		BN_CTX_free( ctx );
		return presult;
	}
	
	const EC_GROUP *group;
	group = EC_KEY_get0_group(eckey);
	
	BIGNUM *n, *k, *Qx, *Qy, *r, *kr,*Me, *Da, *Gx, *Gy;
	n = BN_new();
	k = BN_new();
	Qx = BN_new();
	Qy = BN_new();
	kr = BN_new();
	Me = BN_new();
	Da = BN_new();
	Gx = BN_new();
	Gy = BN_new();
	r = BN_new();

	BN_hex2bn( &n, cn);
	BN_hex2bn( &Da, cDA);
	BN_hex2bn( &Gx, cGx);
	BN_hex2bn( &Gy, cGy);

	EC_POINT *Q;
	Q = EC_POINT_new(group);

	while ( true )
	{
		if( !BN_rand_range( k, n ))
		{
			//var = "BN_rand_range º∆À„¥ÌŒÛ";
			presult = "BN_rand_range calculate error!";
			BN_CTX_free( ctx );
			return presult;
		}

		
		if (!EC_POINT_mul(group, Q, k, NULL, NULL, ctx))
		{
			//var = "EC_POINT_mul1 º∆À„¥ÌŒÛ";
			presult = "EC_POINT_mul1 calculate error!";
			BN_CTX_free( ctx );
			EC_POINT_clear_free( Q );
			return presult;
		}
		
		if( !EC_POINT_get_affine_coordinates_GFp(group, Q, Qx, Qy, ctx))
		{
			//var = "EC_POINT_get_affine_coordinates_GFp º∆À„¥ÌŒÛ";
			presult = "EC_POINT_get_affine_coordinates_GFp calculate error!";
			BN_CTX_free( ctx );
			EC_POINT_clear_free( Q );
			return presult;
		}

		if( !BN_nnmod( r, Qx, n, ctx ))
		{
			//var = "BN_nnmod º∆À„¥ÌŒÛ";
			presult = "BN_nnmod calculate error!";
			BN_CTX_free( ctx );
			EC_POINT_clear_free( Q );
			return presult;
		}

		if ( BN_is_zero(r) )
		{
			continue;
		}
		//kinv = k?1 mod n
		if( !BN_mod_inverse( kr, k, n, ctx ))
		{
			//var = "BN_mod_inverse º∆À„¥ÌŒÛ";
			presult = "BN_mod_inverse calculate error!";
			BN_CTX_free( ctx );
			EC_POINT_clear_free( Q );
			return presult;
		}

		int imlen = strlen(cM);
		unsigned char *pTmp = (unsigned char*)malloc((imlen/2+1)*sizeof(char));
		memset( pTmp, 0, imlen/2+1);
		AsciiToHex( (unsigned char*)cM, pTmp, imlen );
		unsigned char md[33]={0};
		SHA256( pTmp, imlen/2, md );
		free( pTmp );
		pTmp = NULL;
		
		unsigned char pHex[65]={0};
		hex2asc( (char*)pHex, md, 32 );
		BN_hex2bn( &Me, (const char*)pHex );

		//√ª”–øº¬«µΩKƒ⁄¥ÊŒ Ã‚
		//r °§ dA ===> k
		if(!BN_mul( k, r, Da, ctx ))
		{
			//var = "BN_mul º∆À„¥ÌŒÛ";
			presult = "BN_mul calculate error!";
			BN_CTX_free( ctx );
			EC_POINT_clear_free( Q );
			return presult; //presult;
		}

		char *pK = BN_bn2hex( k );
		
		//r °§ dA + OS2I(H¶”(M)) ==> k
		if( !BN_uadd( k, k, Me ))
		{
			//var = "BN_uadd º∆À„¥ÌŒÛ";
			presult = "BN_uadd calculate error!";
			BN_CTX_free( ctx );
			EC_POINT_clear_free( Q );
			return presult;//presult;
		}
		
		//s = kinv °§ (r °§ dA + OS2I(H¶”(M))) mod n
		if( !BN_mod_mul( k, kr,k, n, ctx ))
		{
			//var = "BN_mod_mul º∆À„¥ÌŒÛ";
			presult = "BN_mod_mul calculate error!";
			BN_CTX_free( ctx );
			EC_POINT_clear_free( Q );
			return presult;//presult;
		}

		if ( !BN_is_zero(k) )
		{
			break;
		}
	}


	BN_CTX_free( ctx );
	EC_POINT_clear_free( Q );
	char * chr;
	char * chs;

	chr = BN_bn2hex( r );
	int iCount = strlen(chr);
	char chCHR[RSANUMLEN+1] = {0};

	if (iCount<RSANUMLEN)
	{
        for(int i=0; i<RSANUMLEN-iCount; i++)
		{
			strcat(chCHR, "0");
		}
		
		memcpy(chCHR+RSANUMLEN-iCount, chr, iCount);
	}
	else
	{
		memcpy(chCHR, chr, iCount);
	}

	chs = BN_bn2hex( k );
	iCount = strlen(chs);
	char chCHS[RSANUMLEN+1] = {0};

	if (iCount<RSANUMLEN)
	{
        for(int i=0; i<RSANUMLEN-iCount; i++)
		{
			strcat(chCHS, "0");
		}

		memcpy(chCHS+RSANUMLEN-iCount, chs, iCount);
	}
	else
	{
		memcpy(chCHS, chs, iCount);
	}
	 
	//presult.append(chr, strlen(chr)); 
	//memcpy(&presult[0], chr, strlen(chr));
	//strcpy(presult, chCHR);
	memcpy(presult, chCHR, strlen(chCHR));
	//presult.append(chs) ;
	//memcpy(presultstrlen(chr), chs, strlen(chs));
	//strcat(presult, chCHS);
	memcpy(presult+strlen(chCHR), chCHS, strlen(chCHS));
	return presult;
}

bool ECC_ECKA_Verify(char* cp,char* ca,char* cb,char* cGx,char* cGy,char* cn,char* ch,char* cM,char* cpax,char* cpay,char* cR,char* cS)
{
	//char presult[1024]={0};
	if( cp == NULL ||
		  ca == NULL ||
		  cb == NULL ||
			cGx == NULL ||
			cGy == NULL || 
			cn == NULL ||
			ch == NULL ||
			cpax == NULL ||
			cpay == NULL ||
			cR == NULL ||
			cS == NULL )
	{
		//presult = "parameter error!";
		return false;
	}

	EC_KEY	*eckey = NULL;
	BN_CTX *ctx = BN_CTX_new();

	GeneratorECKeyPair( cp, ca, cb, cGx, cGy, cn, ch, eckey );
	if( eckey == NULL )
	{
		//presult = "parameter error!";
		BN_CTX_free( ctx );
		return false;
	}
	
	const EC_GROUP *group;
	group = EC_KEY_get0_group(eckey);
	
	BIGNUM *n, *k, *u1, *u2, *rR, *kr, *Qx, *Qy, *Pax, *Pay, *bS,*v;
	n = BN_new();
	k = BN_new();
	u1 = BN_new();
	u2 = BN_new();
	kr = BN_new();
	Qx = BN_new();
	Qy = BN_new();
	Pax = BN_new();
	Pay = BN_new();
	rR = BN_new();
	bS = BN_new();
	v  = BN_new();

	BN_hex2bn( &n, cn);
	BN_hex2bn( &rR, cR);
	BN_hex2bn( &Pax, cpax);
	BN_hex2bn( &Pay, cpay);
	BN_hex2bn( &bS, cS);
	
	//Verify that r, s °  {1, 2, . . . , n ? 1}
	if ( ( BN_ucmp( rR, n ) >= 0 ) || ( BN_ucmp( bS, n ) >= 0 ) )
	{
		//var = "«©√˚ ˝æ›”–ŒÛ£°";
		//presult = "The signature data is wrong!";
		BN_CTX_free( ctx );
		return false;
	}
	
	//sinv = s?1 mod n
	if( !BN_mod_inverse( k, bS, n, ctx ) )
	{
		//var = "BN_mod_inverseº∆À„¥ÌŒÛ!";
		//presult = "BN_mod_inverse calculate error!";
		BN_CTX_free( ctx );
		return false;
	}
	char *ptemp = cM;
	int iLen = strlen(cM);

	unsigned char *pTmp = (unsigned char*) malloc((iLen/2+1)*sizeof(char*));
	memset( pTmp, 0, iLen/2+1 );

	AsciiToHex( (unsigned char*)ptemp, pTmp, iLen );
	unsigned char md[33]={0};
	//H¶”(M)
	SHA256( pTmp, iLen/2, md );
	unsigned char pHex[65]={0};
	hex2asc( (char*)pHex, md, 32 );
	BN_hex2bn( &kr, (char*)pHex );
	free(pTmp);

	//u1 = sinv °§ OS2I(H¶”(M)) mod n
	if( !BN_mod_mul( u1, k, kr, n, ctx ))
	{
		//var = "BN_mod_mulº∆À„¥ÌŒÛ!";
		//presult = "BN_mod_mul calculate error!";
		BN_CTX_free( ctx );
		return false;	
	}

	//u2 = sinv °§ r mod n
	if( !BN_mod_mul( u2, k, rR, n, ctx ))
	{
		//var = "BN_mod_mulº∆À„¥ÌŒÛ!";
		//presult = "BN_mod_mul calculate error!";
		BN_CTX_free( ctx );
		return false;	
	}


	EC_POINT *Pa,*Q, *Y, *X;
	Pa = EC_POINT_new(group);
	Q  = EC_POINT_new(group);
	Y  = EC_POINT_new(group);
	X  = EC_POINT_new(group);

	if( !EC_POINT_set_affine_coordinates_GFp(group, Pa, Pax, Pay, ctx ))
	{
		//var = "EC_POINT_set_affine_coordinates_GFpº∆À„¥ÌŒÛ!";
		//presult = "EC_POINT_set_affine_coordinates_GFp calculate error!";
		BN_CTX_free( ctx );
		EC_POINT_clear_free( Pa );
		EC_POINT_clear_free( Q );
		EC_POINT_clear_free( X );
		EC_POINT_clear_free( Y );
		return false;	
	}

	//[u2]PA
	if( !EC_POINT_mul( group, Y, NULL, Pa, u2, ctx ))
	{
		//var = "EC_POINT_mul 1º∆À„¥ÌŒÛ!";
		//presult = "EC_POINT_mul1 calculate error!";
		BN_CTX_free( ctx );
		EC_POINT_clear_free( Pa );
		EC_POINT_clear_free( Q );
		EC_POINT_clear_free( X );
		EC_POINT_clear_free( Y );
		return false;	
	}
	

	//[u1]G 
	if( !EC_POINT_mul( group, X, u1, NULL, NULL, ctx ))
	{
		//var = "EC_POINT_mul 2º∆À„¥ÌŒÛ!";
		//presult = "EC_POINT_mul2 calculate error!";
		BN_CTX_free( ctx );
		EC_POINT_clear_free( Pa );
		EC_POINT_clear_free( Q );
		EC_POINT_clear_free( X );
		EC_POINT_clear_free( Y );
		return false;	
	}
	//Q = [u1]G + [u2]PA
	if( !EC_POINT_add( group, Q, X, Y, ctx ))
	{
		//var = "EC_POINT_add º∆À„¥ÌŒÛ!";
		//presult = "EC_POINT_add calculate error!";
		BN_CTX_free( ctx );
		EC_POINT_clear_free( Pa );
		EC_POINT_clear_free( Q );
		EC_POINT_clear_free( X );
		EC_POINT_clear_free( Y );
		return false;	
	}

	

	if( !EC_POINT_get_affine_coordinates_GFp( group, Q, Qx, Qy, ctx ))
	{
		//var = "EC_POINT_get_affine_coordinates_GFp º∆À„¥ÌŒÛ!";
		//presult = "EC_POINT_get_affine_coordinates_GFp calculate error!";
		BN_CTX_free( ctx );
		EC_POINT_clear_free( Pa );
		EC_POINT_clear_free( Q );
		EC_POINT_clear_free( X );
		EC_POINT_clear_free( Y );
		return false;
	}

	//v = OS2I(FE2OS(xQ)) mod n
	if( !BN_nnmod( v, Qx, n, ctx ))
	{
		//var = "BN_nnmod º∆À„¥ÌŒÛ!";
		//presult = "BN_nnmod calculate error!";
		BN_CTX_free( ctx );
		EC_POINT_clear_free( Pa );
		EC_POINT_clear_free( Q );
		EC_POINT_clear_free( X );
		EC_POINT_clear_free( Y );
		return false;
	}

	char * pp = BN_bn2hex( v );

	if ( 0 == BN_ucmp( v, rR) )
	{
		//presult = "0";
		BN_CTX_free( ctx );
		EC_POINT_clear_free( Pa );
		EC_POINT_clear_free( Q );
		EC_POINT_clear_free( X );
		EC_POINT_clear_free( Y );
		return true;	
	}
	else
	{
		//var = "1";
		//presult = "Contrast error!";
		BN_CTX_free( ctx );
		EC_POINT_clear_free( Pa );
		EC_POINT_clear_free( Q );
		EC_POINT_clear_free( X );
		EC_POINT_clear_free( Y );
		return false;
	}
}

bool ECC_ECKA_EG(char* cp,char* ca,char* cb,char* cGx,char* cGy,char* cn,char* ch,char* cDA,char* cpbx,char* cpby, char* presult )
{
		if( cp == NULL ||
		  ca == NULL ||
		  cb == NULL ||
			cGx == NULL ||
			cGy == NULL || 
			cn == NULL ||
			ch == NULL ||
			cDA == NULL ||
			cpbx == NULL ||
			cpby == NULL  )
	{
		strcpy(presult , "parameter error!");
		return false;
	}
	
	EC_KEY	*eckey = NULL;
	BN_CTX *ctx = BN_CTX_new();
	
	GeneratorECKeyPair(cp, ca, cb, cGx, cGy, cn, ch, eckey);
	if( eckey == NULL )
	{
		//var = "≤Œ ˝¥ÌŒÛ£°";
		strcpy(presult , "parameter error!");
		BN_CTX_free( ctx );
		return false;
	}
	
	const EC_GROUP *group;
	group = EC_KEY_get0_group(eckey);

	//Õ®π˝group£¨«ÛP
	EC_POINT *Pb, *Sab;
	Pb = EC_POINT_new( group );
	Sab = EC_POINT_new( group );

	BIGNUM *n, *k, *Pbx, *Pby, *r, *kr,*Me, *Da, *h;
	n = BN_new();
	k = BN_new();
	Pbx = BN_new();
	Pby = BN_new();
	kr = BN_new();
	Me = BN_new();
	Da = BN_new();
	r = BN_new();
	h = BN_new();
	
	BN_hex2bn( &n, cn );
	BN_hex2bn( &Da, cDA );
	BN_hex2bn( &Pbx, cpbx );
	BN_hex2bn( &Pby, cpby );
	BN_hex2bn( &h, ch );

	if( !EC_POINT_set_affine_coordinates_GFp( group, Pb, Pbx, Pby, ctx ) )
	{
		//var = "EC_POINT_set_affine_coordinates_GFp ¥ÌŒÛ";
		strcpy(presult, "EC_POINT_set_affine_coordinates_GFp error!");
		BN_CTX_free( ctx );
		return false;
	}
	
	//«Ûƒ™ƒÊ K = h?1 mod n
	if( !BN_mod_inverse( k, h, n, ctx ))
	{
		//var = "BN_mod_inverse ¥ÌŒÛ";
		strcpy(presult , "BN_mod_inverse error!");
		BN_CTX_free( ctx );
		return false;
	}


	EC_POINT *Q;
	Q = EC_POINT_new(group);

	//Q = [h]Pb
	if(!EC_POINT_mul( group, Q, false, Pb, h, ctx ))
	{
		//var = "EC_POINT_mul ¥ÌŒÛ";
		strcpy(presult, "EC_POINT_mul error!");
		BN_CTX_free( ctx );
		EC_POINT_clear_free(Q);
		return false;
	}
	
	//d b°§ l mod n
	if( !BN_mod_mul( k, k, Da, n, ctx ))
	{
		//var = "BN_mod_mul ¥ÌŒÛ";
		strcpy(presult, "BN_mod_mul error!");
		BN_CTX_free( ctx );
		EC_POINT_clear_free(Q);
		return false;
	}

	//SAB = [d b°§ l mod n]Q
	
	if( !EC_POINT_mul( group, Sab, false, Q, k, ctx ))
	{
		//var = "EC_POINT_mul ¥ÌŒÛ";
		strcpy(presult, "EC_POINT_mul error!");
		BN_CTX_free( ctx );
		EC_POINT_clear_free(Q);
		return false;
	}
	
	//≈–∂œ «∑ÒŒ™Œﬁ«Ó‘∂µ„
	if( EC_POINT_is_at_infinity( group, Sab ) )
	{
		//var = "EC_POINT_is_at_infinity ¥ÌŒÛ";
		strcpy(presult, "EC_POINT_is_at_infinity error!");
		BN_CTX_free( ctx );
		EC_POINT_clear_free(Q);
		return false;
	}

	BIGNUM *Sax, *Say;
	Sax = BN_new();
	Say = BN_new();

	if( !EC_POINT_get_affine_coordinates_GFp( group, Sab, Sax, Say, ctx ))
	{
		//var = "EC_POINT_get_affine_coordinates_GFp ¥ÌŒÛ";
		strcpy(presult, "EC_POINT_get_affine_coordinates_GFp error!");
		BN_CTX_free( ctx );
		EC_POINT_clear_free(Q);
		return false;
	}
	
	char * pZa, *pZb;

	pZa = BN_bn2hex( Sax );
	pZb = BN_bn2hex( Say );

//	int len = 2*strlen(pZa) + strlen(pZb);
//	char *result = new char[len + 1];
	
//	memset(result, 0, len + 1);

	//memcpy(presult, pZa, strlen(pZa));
	//memcpy(presult + strlen(pZa), pZb, strlen(pZb));
	//memcpy(presult + strlen(pZa) + strlen(pZb), pZa, strlen(pZa));

	//strcat(strcat(strcpy(result,pZa),pZb),pZa);
	//presult = result;
	sprintf( presult, "%s%s%s", pZa,pZb,pZa);
	//memcpy(presult, result, len);
	//presult[len] = '\0';
	//delete[] result;
	return true;
}


char* ECC_Key_Agreement(char* cp,char* ca,char* cb,char* cGx,char* cGy,char* cn,char* ch,char* cDA,char* cpbx,char* cpby,char* cShareInfo,int keyLen)
{
	char *presult = (char*)malloc(2048);
	if( cp == NULL ||
		  ca == NULL ||
		  cb == NULL ||
			cGx == NULL ||
			cGy == NULL || 
			cn == NULL ||
			ch == NULL ||
			cDA == NULL ||
			cpbx == NULL ||
			cpby == NULL  )
	{
		strcpy(presult, "parameter error!");
		return presult;
	}
	
	if ( keyLen%16 != 0 )
	{
		//var = "Keyµƒ≥§∂»≤Œ ˝≤ª’˝»∑.";
		strcpy(presult , "The length of the Key parameters is not correct!");
		return presult;
	}
	
	//char* tmp = (char*)&presult[0];
	if( !ECC_ECKA_EG( cp, ca, cb, cGx, cGy, cn, ch, cDA, cpbx, cpby, presult ))
	{
		return presult;
	}

	unsigned char *poutkey = (unsigned char*)malloc((keyLen+1)*sizeof(char));
	memset( poutkey, 0, keyLen+1 );

	//CString szZab;
	//szZab = var.Right( 64 );
	
	int ilen = strlen(presult);
	char pszZab[65]={0};
	//char *pszZab = presult + ilen - 64;
	memcpy( pszZab, presult + ilen - 64, 64 );
 
	//char* ctmp = (char*)&presult[0];
	if( !ECC_ECKA_X963Kdf( pszZab, keyLen, cShareInfo, poutkey, presult))
	{
		free(poutkey);
		poutkey = NULL;
		return presult;
	}

	free(poutkey);
	poutkey = NULL;
	return presult;
}


bool ECC_ECKA_X963Kdf( char *pszZab, int keylen, char *pszShareInfo, unsigned char *outkey, char* presult )
{
	unsigned char *hextochs ( unsigned char* ascii );

	unsigned long  klen_bit = keylen;
	unsigned int hlen1, bitlen, i, zlen;
	//unsigned char *ha, *Zn;
	unsigned char hai[VBYTE]={0};

	klen_bit *= 8;
	
	if(klen_bit%V==0)
		hlen1=klen_bit/V;
	else
		hlen1=klen_bit/V+1;

	//char *ss;
	//CString strCt;
	char pchCt[32]={0};
	//CString temp;
	char pTmp[1024]={0};
	//CString strOut;
	for(i=1;i<=hlen1;i++)
	{
		//strCt.Format("%08X",i);
		//chCt = strCt.GetBuffer(0);
		memset(pTmp, 0, 1024 );
		sprintf( pchCt, "%08X", i );
		//temp = szZab;
		//temp += strCt;
		//temp += szShareInfo;
		//zlen = temp.GetLength();
		//unsigned char *pTmp = (unsigned char *)temp.GetBuffer(0);
		sprintf( pTmp, "%s%s%s", pszZab, pchCt, pszShareInfo );
		zlen = strlen((char*)pTmp);
		hextochs((unsigned char*)pTmp);
		
		SHA256((unsigned char*) pTmp, zlen/2, hai );
		unsigned char pHex[65] ={0};

		hex2asc( (char*)pHex, hai, 32 );
		//strValue.Format( "%s", pHex );
		
		//strOut +=strValue;	
		memcpy( presult+(i-1)*64, pHex, 64 );
	}

	if((klen_bit%V)==0)
		bitlen=VBYTE*hlen1;
	else
		bitlen=VBYTE*(hlen1-1)+(klen_bit-V*(klen_bit/V))/8;
	
	//strValue=strOut.Left(keylen*2);
	int iretlen = strlen(presult);
	memset( presult+keylen*2, 0, iretlen -keylen*2  );
	
	
	return true;
}




