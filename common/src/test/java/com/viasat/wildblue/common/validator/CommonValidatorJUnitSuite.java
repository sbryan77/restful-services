package com.viasat.wildblue.common.validator;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses(
    {
        CommonValidatorToolTest.class,  //
        DateValidatorTest.class,  //
        PhoneValidatorTest.class,  //
        ValidatorConfigurationListenerTest.class,  //
        ValidatorConfigurationTest.class,  //
        ValidateRequiredOneOfManyTest.class,  //
        WildBlueHeaderValidatorTest.class,  //
        ZipCodeValidatorTest.class
    }
)
public class CommonValidatorJUnitSuite
{
}
