package eu.veldsoft.share.with.me.model;

import java.util.Random;

/**
 * 
 * @author
 */
final public class Util {
	/**
	 * 
	 */
	private Util() {
	}

	/**
	 * 
	 */
	public static final Random PRNG = new Random();

	/**
	 * 
	 */
	public static final String SHARED_PREFERENCE_INSTNCE_HASH_CODE_KEY = "eu.veldsoft.share.with.me.instance.hash.code";

	/**
	 * 
	 */
	public static final String JSON_INSTNCE_HASH_CODE_KEY = "instance_hash";

	/**
	 * 
	 */
	public static final String JSON_PARENT_HASH_CODE_KEY = "parent_hash";

	/**
	 * 
	 */
	public static final String JSON_MESSAGE_HASH_CODE_KEY = "message_hash";

	/**
	 * 
	 */
	public static final String JSON_MESSAGE_KEY = "message";

	/**
	 * 
	 */
	public static final String JSON_REGISTERED_KEY = "registered";

	/**
	 * 
	 */
	public static final String JSON_FOUND_KEY = "found";

	/**
	 * 
	 */
	public static final String JSON_NAMES_KEY = "names";

	/**
	 * 
	 */
	public static final String JSON_EMAIL_KEY = "email";

	/**
	 * 
	 */
	public static final String JSON_PHONE_KEY = "phone";

	/**
	 * 
	 */
	public static final String JSON_LAST_MESSAGE_HASH_CODE_KEY = "last_message";

	/**
	 * Find better way for giving value of this constant.
	 */
	public static final int ALARM_REQUEST_CODE = 0;

	/**
	 * Parent message hash key.
	 */
	public static final String PARENT_MESSAGE_HASH_KEY = "eu.veldsoft.share.with.me.parent.message.hash";

	/**
	 * Registration time stamp key.
	 */
	public static final String REGISTERED_KEY = "eu.veldsoft.share.with.me.registered";
}
