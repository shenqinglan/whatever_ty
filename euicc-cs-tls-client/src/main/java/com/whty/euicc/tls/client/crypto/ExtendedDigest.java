package com.whty.euicc.tls.client.crypto;

public abstract interface ExtendedDigest extends Digest
{
	public abstract int getByteLength();
}