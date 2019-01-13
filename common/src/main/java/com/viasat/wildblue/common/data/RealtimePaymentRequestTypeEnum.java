package com.viasat.wildblue.common.data;

/**
 */
public enum RealtimePaymentRequestTypeEnum
{
    ONE_TIME_PAYMENT(0, "one time payment"),  //
    RECURRING_PAYMENT(1, "recurring payment"),  //
    REFUND(2, "refund");  //

    private String m_desc;

    private int m_id;

    private RealtimePaymentRequestTypeEnum(int id, String desc)
    {
        m_id = id;
        m_desc = desc;
    }

    public String getDesc()
    {
        return m_desc;
    }

    public int getId()
    {
        return m_id;
    }

    public String getIdAsString()
    {
        return String.valueOf(m_id);
    }
}
