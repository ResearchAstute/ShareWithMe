package eu.veldsoft.share.with.me;

import java.util.Random;

/**
 * 
 * @author
 */
class Util {
	/**
	 * 
	 */
	protected static final Random PRNG = new Random();

	/**
	 * 
	 */
	protected static final String SHARED_PREFERENCE_INSTNCE_HASH_CODE_KEY = "eu.veldsoft.share.with.me.instance.hash.code";

	/**
	 * 
	 */
	protected static final String JSON_INSTNCE_HASH_CODE_KEY = "instance_hash";

	/**
	 * 
	 */
	protected static final String JSON_PARENT_HASH_CODE_KEY = "parent_hash";

	/**
	 * 
	 */
	protected static final String JSON_MESSAGE_HASH_CODE_KEY = "message_hash";

	/**
	 * 
	 */
	protected static final String JSON_MESSAGE_KEY = "message";

	/**
	 * 
	 */
	protected static final String JSON_NAMES_KEY = "names";

	/**
	 * 
	 */
	protected static final String JSON_EMAIL_KEY = "email";

	/**
	 * 
	 */
	protected static final String JSON_PHONE_KEY = "phone";

	/**
	 * 
	 */
	protected static final String JSON_LAST_MESSAGE_HASH_CODE_KEY = "last_message";

	/**
	 * Find better way for giving value of this constant.
	 */
	protected static final int ALARM_REQUEST_CODE = 0;

	/**
	 * Parent message hash key.
	 */
	protected static final String PARENT_MESSAGE_HASH_KEY = "eu.veldsoft.share.with.me.parent.message.hash";
}
