package com.viasat.common.validator;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses(
    {
        CommonValidatorToolTest.class,  //
        PhoneValidatorTest.class,  //
        DateValidatorTest.class,  //
        ValidatorConfigurationTest.class,  //
        ValidateRequiredOneOfManyTest.class,  //
        ZipCodeValidatorTest.class,  //
        ValidationOfNestedObjectsTest.class
    }
)
public class CommonValidatorJUnitSuite
{
}
