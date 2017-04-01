package com.whty.euicc.constant;

public class ConstantParam {

	public static final int MAX_TIMER = 8;

	public static final String COUNT_CMD = "2";

	public static final String COUNT_RSP = "2";

	public static final String[] CHANNEL_OK_ = { "8100", "8200", "8300",
			"8400", "8500", "8600", "8700" };

	public static final String[] CHANNEL_DROP_ = { "0105", "0205", "0305",
			"0405", "0505", "0605", "0705" };

	public static final String TERMRESP00 = "830100";

	public static final String TEST_BUFFER_SIZE = "0200";

	public static final String SENDDATA_STORE_MODE = "00";

	public static final String SENDDATA_IMMEDIATE_MODE = "01";

	public static final String UICC_CHANNEL_LENGTH = "ED";

	public static final String[] UICC_TO_CHANNEL_ = { "82028121", "82028122",
			"82028123", "82028124", "82028125", "82028126", "82028127" };

	public static final String UICC_TO_TERMINAL = "82028182";

	public static final String TERMINAL_TO_UICC = "82028281";

	public static final String UICC_TO_NETWORK = "82028183";

	public static final String NETWORK_to_UICC = "82028381";
	
	//'01' or '81'
	public static final String COMMAND_DETAILS_TAG = "01";

	//'02' or '82'
	public static final String DEVICE_IDENTITIES_TAG = "02";

	//'05' or '85'
	public static final String ALPHA_IDENTITIES_TAG = "05";

	//'1E' or '9E'
	public static final String ICON_IDENTITIES_TAG = "1E";

	//'35' or 'B5'
	public static final String BEARER_DESCRIPTION_TAG = "35";

	//'36' or 'B6'
	public static final String CHANNEL_DATA_TAG = "36";

	//'37' or 'B7'
	public static final String CHANNEL_DATA_LENGTH_TAG = "37";

	//'38' or 'B8'
	public static final String CHANNEL_STATUS_TAG = "38";

	//'39' or 'B9'
	public static final String BUFFER_SIZE_TAG = "39";

	//'47' or 'C7'
	public static final String NETWORK_ACCESS_NAME_TAG = "47";

	//'3E' or 'BE'
	public static final String OTHER_ADDRESS_TAG = "3E";

	//'0D' or '8D'
	public static final String TEXT_STRING_TAG = "0D";

	//'3C' or 'BC'
	public static final String UICC_TERMINAL_INTERFACE_TRANSPORT_LEVEL_TAG = "3C";

	//'50' or 'D0'
	public static final String TEXT_ATTRIBUTE_TAG = "50";

	//'68' or 'E8'
	public static final String FTAME_IDENTIFIER_TAG = "68";
	
	public static final String CLIENT_FINISH = "client finished";
	
	public static final String SERVER_FINISH = "server finished";
	
	public static final String APDU_MODE = "Expanded";

	public static final String OPENTERMRESP = "00";
	
	public static final String CLA80 = "80";
	
	public static final String DEFAULTCLEAREVENTLIST = "090A";
	
	public static final String UICC_Channel_length = "ED";
	
}
