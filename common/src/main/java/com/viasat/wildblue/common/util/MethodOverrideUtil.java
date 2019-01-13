package com.viasat.wildblue.common.util;

public class MethodOverrideUtil
{
    /**
     * Pure static implementation, private constructor.
     */
    private MethodOverrideUtil()
    {
    }

    /**
     * Checks if is method overriden.
     *
     * @param   methodName  the method name
     * @param   base        the base
     * @param   o           the o
     *
     * @return  true, if is method overriden
     */
    public static boolean isMethodOverriden(String methodName, Class<?> base,
        Object o)
    {
        boolean isImplemented = false;

        Class<?> c = o.getClass();

        if (base.isAssignableFrom(c))
        {
            while (!c.equals(base) && !isImplemented)
            {
                isImplemented = isMethodImplemented(methodName, c);

                if (!isImplemented)
                {
                    c = c.getSuperclass();
                }
            }
        }

        return isImplemented;
    }

    /**
     * Is the method implemented/declared by the concrete class?
     *
     * @param   methodName
     * @param   c
     *
     * @return
     */
    private static boolean isMethodImplemented(String methodName, Class<?> c)
    {
        boolean isImplemented = false;

        try
        {
            if (c.getDeclaredMethod(methodName, String.class) != null)
            {
                isImplemented = true;
            }
        }
        catch (SecurityException e)
        {
            // gulp...
        }
        catch (NoSuchMethodException e)
        {
            // gulp...
        }

        return isImplemented;
    }
}
