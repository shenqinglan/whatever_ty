package com.whty.euicc.common.caputils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CapHeader {

	// private int tag;// u1
	// private int size; // u2
	// private byte[] magic = new byte[4]; // u4 必须为0xDECAFFE
	private int minor_version; // u1
	private int major_version; // u1
	private int flags; // u1 ACC_INT - 0x01; ACC_EXPORT - 0x02; ACC_APPLET - 0x04
	
	/**
	 * package_info
	 */
	private int minor_package_version; // u1
	private int major_package_version; // u1
	// private int AID_length;//u1
	private byte[] AID;// ux
	/**
	 * package_name_info
	 */
	// private int name_length; //u1 当包内没有定义任何remote interfaces或者remote classes的时候值可以为0
	private byte[] name;// ux [name_length]

	public CapHeader(InputStream in) throws CapException, IOException {
		BufferedInputStream bis = new BufferedInputStream(in);
		// 1.tag
		int tag = bis.read();
		if (tag != 1) {
			throw new CapException("Illegal tag: CAP_HEADER");
		}
		// 2.size
		byte[] size = new byte[2];
		int len = bis.read(size);
		if (len == 2) {
			// int size0 = (short) ((size[0] << 8) + size[1]);
			//
		} else {
			throw new CapException("Error reading: size");
		}

		// 3.magic
		byte[] magic = new byte[4];
		len = bis.read(magic);
		if (len == 4) {
			if (magic[0] != -34 || magic[1] != -54 || magic[2] != -1 || magic[3] != -19) {
				throw new CapException("Wrong CAP magic number. ");
			}
		} else {
			throw new CapException("Error reading: magic");
		}

		// 4.minor_version
		this.minor_version = bis.read();
		if (this.minor_version == -1) {
			throw new CapException("Error reading: minor_version");
		} else if (this.minor_version > 2) {
			throw new CapException("Wrong CAP minor version number");
		}
		// 5.major_version
		this.major_version = bis.read();
		if (this.major_version == -1) {
			throw new CapException("Error reading: major_version");
		} else if (this.major_version > 2) {
			throw new CapException("Wrong CAP major version number");
		}
		// 6.flags
		this.flags = bis.read();
		if (this.flags == -1) {
			throw new CapException("Error reading: flags");
		}

		
		// 4.1.minor_package_version
		this.minor_package_version = bis.read();
		if (this.minor_package_version == -1) {
			throw new CapException("Error reading: minor_package_version");
		} else if (this.minor_package_version > 2) {
			throw new CapException("Wrong CAP minor package version number");
		}
		// 5.1.major_package_version
		this.major_package_version = bis.read();
		if (this.major_package_version == -1) {
			throw new CapException("Error reading: major_package_version");
		} else if (this.major_package_version > 2) {
			throw new CapException("Wrong CAP major package version number");
		}
		
		
		// 7.AID_length
		int AID_length = bis.read();
		if (AID_length == -1) {
			throw new CapException("Error reading: AID_length");
		} else if (AID_length > 16 || AID_length < 5) {
			throw new CapException("Illegal package AID length");
		}
		// 8.AID
		AID = new byte[AID_length];
		len = bis.read(AID);
		if (len == -1) {
			throw new CapException("Error reading: AID");
		}
		// 9.
		int name_length = bis.read();
		if (name_length == -1) {
			throw new CapException("Error reading: name_length");
		} else if (name_length > 16 || AID_length < 5) {
			throw new CapException("Illegal package name_length");
		}
		// 10.
		name = new byte[name_length];
		len = bis.read(name);
		if (len == -1) {
			throw new CapException("Error reading: name");
		}
	}

	public int getMinor_version() {
		return minor_version;
	}

	public int getMajor_version() {
		return major_version;
	}

	public int getFlags() {
		return flags;
	}

	public byte[] getAID() {
		return AID;
	}

	public byte[] getName() {
		return name;
	}
}
