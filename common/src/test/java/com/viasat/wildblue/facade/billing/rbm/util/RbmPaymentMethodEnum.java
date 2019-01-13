package com.viasat.wildblue.facade.billing.rbm.util;

import com.viasat.wildblue.common.exception.fault.WildBlueFaultException;

import java.util.HashMap;
import java.util.Map;


/**
 * Enum/LookUp library of values for the RBM Payment Method id.
 */
public enum RbmPaymentMethodEnum
{
    ACH_ONE_TIME(1, false, false, "ACH one time", "EFT_ONE_TIME_PAYMENT"),
    CREDIT_CARD_RECURRING(2, true, true, "Credit Card recurring",
        "CREDIT_CARD_RECURRING_PAYMENT"),
    CREDIT_CARD_ONE_TIME(4, false, true, "Credit Card one time",
        "CREDIT_CARD_ONE_TIME_PAYMENT"),
    ACH_RECURRING(5, true, false, "ACH recurring", "EFT_RECURRING_PAYMENT");

    // For id lookup.
    /** The id map. */
    private static Map<Integer, RbmPaymentMethodEnum> idMap;

    static
    {
        idMap = new HashMap<Integer, RbmPaymentMethodEnum>(values().length);

        for (RbmPaymentMethodEnum rbmAS : values())
        {
            int id = rbmAS.getRbmId();
            idMap.put(id, rbmAS);
        }
    }

    /** The m_is credit card. */
    private boolean m_isCreditCard;

    /** The m_is recurring. */
    private boolean m_isRecurring;

    /** The payment method name. */
    private String m_paymentMethodName;

    /** The m_rbm id. */
    private int m_rbmId;
    private String m_wbPaymentMethodName;

    /**
     * Instantiates a new payment methods enum.
     *
     * @param  rbmId         the rbm id
     * @param  isRecurring   the is recurring
     * @param  isCreditCard  the is credit card
     * @param  longDesc      the long desc
     */
    private RbmPaymentMethodEnum(int rbmId, boolean isRecurring,
        boolean isCreditCard, String paymentMethodName,
        String wbPaymentMethodName)
    {
        m_rbmId = rbmId;
        m_isRecurring = isRecurring;
        m_isCreditCard = isCreditCard;
        m_paymentMethodName = paymentMethodName;
        m_wbPaymentMethodName = wbPaymentMethodName;
    }

    public static RbmPaymentMethodEnum findByName(String typeName)
    {
        for (int i = 0; i < values().length; i++)
        {
            RbmPaymentMethodEnum paymentMethod = values()[i];

            if (paymentMethod.getPaymentMethodName().equals(typeName))
            {
                return paymentMethod;
            }
        }

        throw new WildBlueFaultException("Unknown paymentMethod: " + typeName);
    }

    public static RbmPaymentMethodEnum findByWbName(String wbName)
    {
        for (int i = 0; i < values().length; i++)
        {
            RbmPaymentMethodEnum paymentMethod = values()[i];

            if (paymentMethod.getWbPaymentMethodName().equals(wbName))
            {
                return paymentMethod;
            }
        }

        throw new WildBlueFaultException("Unknown paymentMethod: " + wbName);
    }

    /**
     * Look up the Enum using RBM's id value.
     *
     * @param   id  the id
     *
     * @return  the rbm payment method enum
     */
    public static RbmPaymentMethodEnum lookup(int id)
    {
        return idMap.get(id);
    }

    /**
     * Gets the corresponding one time payment method.
     *
     * @return  the corresponding one time payment method
     */
    public RbmPaymentMethodEnum getCorrespondingOneTimePaymentMethod()
    {
        RbmPaymentMethodEnum oneTimePaymentMethod = null;

        if (this.isRecurring())
        {
            if (this.isCreditCard())
            {
                oneTimePaymentMethod =
                    RbmPaymentMethodEnum.CREDIT_CARD_ONE_TIME;
            }
            else
            {
                oneTimePaymentMethod = RbmPaymentMethodEnum.ACH_ONE_TIME;
            }
        }
        else
        {
            oneTimePaymentMethod = this;
        }

        return oneTimePaymentMethod;
    }

    /**
     * Gets the corresponding recurring payment method.
     *
     * @return  the corresponding recurring payment method
     */
    public RbmPaymentMethodEnum getCorrespondingRecurringPaymentMethod()
    {
        RbmPaymentMethodEnum recurringPaymentMethod = null;

        if (!this.isRecurring())
        {
            if (this.isCreditCard())
            {
                recurringPaymentMethod =
                    RbmPaymentMethodEnum.CREDIT_CARD_RECURRING;
            }
            else
            {
                recurringPaymentMethod = RbmPaymentMethodEnum.ACH_RECURRING;
            }
        }
        else
        {
            recurringPaymentMethod = this;
        }

        return recurringPaymentMethod;
    }

    /**
     * @return  the paymentMethodName
     */
    public String getPaymentMethodName()
    {
        return m_paymentMethodName;
    }

    /**
     * RBM integer ID value.
     *
     * @return  the rbm id
     */
    public int getRbmId()
    {
        return m_rbmId;
    }

    public String getWbPaymentMethodName()
    {
        return m_wbPaymentMethodName;
    }

    /**
     * Checks if is credit card.
     *
     * @return  true, if is credit card
     */
    public boolean isCreditCard()
    {
        return m_isCreditCard;
    }

    /**
     * Checks if is recurring.
     *
     * @return  true, if is recurring
     */
    public boolean isRecurring()
    {
        return m_isRecurring;
    }
}
