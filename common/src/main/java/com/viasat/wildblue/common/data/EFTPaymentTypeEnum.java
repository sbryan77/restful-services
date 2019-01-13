package com.viasat.wildblue.common.data;

public enum EFTPaymentTypeEnum
{
    C("CHECKING"),  //
    S("SAVINGS");  //

    private String m_wildBlueName;

    private EFTPaymentTypeEnum(String wbName)
    {
        this.m_wildBlueName = wbName;
    }

    public static EFTPaymentTypeEnum getByWildBlueName(String wbName)
    {
        EFTPaymentTypeEnum[] arr = values();

        for (int i = 0; i < arr.length; i++)
        {
            EFTPaymentTypeEnum e = arr[i];

            if (e.getWildBlueName().equals(wbName))
            {
                return e;
            }
        }

        return null;
    }

    public String getWildBlueName()
    {
        return m_wildBlueName;
    }
}
