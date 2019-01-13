package com.viasat.common.fault;

public enum FaultKeyEnum
{
	DUPLICATE("validationerror.duplicate", "DUPLICATE"), //
	FORBIDDEN("validationerror.forbidden", "FORBIDDEN"), //
	TRANSACTION_ERROR("validationerror.transactionerror", "TRANSACTION_ERROR"), //
	INVALID_COMBINATION("validationerror.invalidcombination", "INVALID_COMBINATION"), //
	INVALID_FORMAT("validationerror.invalidformat", "INVALID_FORMAT"), //
	INVALID_LIST_SIZE("validationerror.listsizeexceeded", "INVALID_LIST_SIZE"), //
	INVALID_LENGTH("validationerror.maxlengthexceeded", "INVALID_LENGTH"), //
	INVALID_VALUE("validationerror.notvalid", "INVALID_VALUE"), //
	INVALID_STATE("validationerror.invalidstate", "INVALID_STATE"), //
	OUT_OF_RANGE("validationerror.outofrange", "OUT_OF_RANGE"), //
	REQUIRED("validationerror.required", "REQUIRED"), //
	REQUIRED_ONE_OF_MANY("validationerror.requiredoneofmany", "INVALID_COMBINATION"), //
	MIN_LENGTH_INSUFFICIENT("validationerror.minlengthinsufficient", "INVALID_LENGTH"), //
	AUTHENTICATION_FAILED("validationerror.authenticationfailed", "AUTHENTICATION_FAILED"), //
	BEAM_CONFLICT("validationerror.beamconflict", "BEAM_CONFLICT"), //
	SYSTEM_FAULT("validationerror.systemfault", "SYSTEM_FAULT"), //
	NOT_FOUND("validationerror.notfound", "NOT_FOUND");

	private String key;
	private String code;

	private FaultKeyEnum(String key, String code)
	{
		this.key = key;
		this.code = code;
	}

	public static String findCodeByKey(String key)
	{
		FaultKeyEnum[] values = values();

		for (int faultKey = 0; faultKey < values.length; faultKey++)
		{
			FaultKeyEnum faultKeyEnum = values[faultKey];
			if (faultKeyEnum.key.equals(key))
			{
				return faultKeyEnum.code;
			}
		}
		return null;
	}

	public String getKey()
	{
		return key;
	}

	public String getCode()
	{
		return code;
	}
}
