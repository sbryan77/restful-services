/**
 *
 */
package com.viasat.wildblue.common.performance;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class MethodInvocationStatistics
{
	private static final String PRINT_MILLISECONDS = " ms";
	private static final String PRINT_LINE_SEPARATOR = "\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++";
	private static final String PRINT_LOCAL = "\n     -----local  : ";

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodInvocationStatistics.class);

	private static final ThreadLocal<List<MethodInvocationStatistics>> INVOCATION_STATS = new ThreadLocal<List<MethodInvocationStatistics>>()
	{
		@Override
		protected List<MethodInvocationStatistics> initialValue()
		{
			return new ArrayList<MethodInvocationStatistics>();
		}
	};

	public static List<MethodInvocationStatistics> getMethodStatistics()
	{
		return INVOCATION_STATS.get();
	}

	public static void printStatistics()
	{
		try
		{
			long currentTimeMillis = System.currentTimeMillis();
			List<MethodInvocationStatistics> stats = getMethodStatistics();

			MethodInvocationStatistics outerCallStat = getMethodStatistics().remove(0);

			StringBuilder sb = new StringBuilder(PRINT_LINE_SEPARATOR);
			sb.append("\n     Method Name: ").append(outerCallStat.getMethodName());

			if (stats != null)
			{
				sb.append("\n     Number of remote calls: " + (stats.size() - 1));
				sb.append("\n     Remote calls:");
				long previousEndTime = 0;
				for (int i = 1; i < stats.size(); i++)
				{
					MethodInvocationStatistics stat = stats.get(i);
					if (i == 1)
					{
						sb.append(PRINT_LOCAL);
						sb.append(stat.getStartTime() - outerCallStat.getStartTime());
						sb.append(PRINT_MILLISECONDS);
					}
					else
					{
						sb.append(PRINT_LOCAL);
						sb.append(stat.getStartTime() - previousEndTime).append(PRINT_MILLISECONDS);
					}
					sb.append("\n     ---");
					sb.append(stat.getServiceName());
					sb.append(".");
					sb.append(stat.getMethodName());
					sb.append(" : ");
					if (stat.getEndTime() != null)
					{
						sb.append(stat.getEndTime() - stat.getStartTime() + PRINT_MILLISECONDS);
						previousEndTime = stat.getEndTime();
					}
					else
					{
						sb.append(currentTimeMillis - stat.getStartTime()
								+ PRINT_MILLISECONDS);
						previousEndTime = currentTimeMillis;
					}
				}
			}

			sb.append("\n     Total Method Execution Time: ");
			if (outerCallStat.getEndTime() != null)
			{
				sb.append(outerCallStat.getEndTime() - outerCallStat.getStartTime());
			}
			else
			{
				sb.append(currentTimeMillis - outerCallStat.getStartTime());
			}
			sb.append(PRINT_MILLISECONDS);
			sb.append(PRINT_LINE_SEPARATOR);

			LOGGER.debug(sb.toString());
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
		}
	}

	public static void reset()
	{
		INVOCATION_STATS.get().clear();
	}

	private String serviceName;
	private String methodName;
	private Long startTime;

	private Long endTime;

	/**
	 * @param serviceName
	 * @param methodName
	 * @param startTime
	 * @param endTime
	 */
	public MethodInvocationStatistics(String serviceName, String methodName, Long startTime,
			Long endTime)
	{
		super();
		this.serviceName = serviceName;
		this.methodName = methodName;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		MethodInvocationStatistics other = (MethodInvocationStatistics) obj;
		if (endTime == null)
		{
			if (other.endTime != null)
			{
				return false;
			}
		}
		else if (!endTime.equals(other.endTime))
		{
			return false;
		}

		if (methodName == null)
		{
			if (other.methodName != null)
			{
				return false;
			}
		}
		else if (!methodName.equals(other.methodName))
		{
			return false;
		}
		if (serviceName == null)
		{
			if (other.serviceName != null)
			{
				return false;
			}
		}
		else if (!serviceName.equals(other.serviceName))
		{
			return false;
		}
		if (startTime == null)
		{
			if (other.startTime != null)
			{
				return false;
			}
		}
		else if (!startTime.equals(other.startTime))
		{
			return false;
		}
		return true;
	}

	public Long getEndTime()
	{
		return endTime;
	}

	public String getMethodName()
	{
		return methodName;
	}

	public String getServiceName()
	{
		return serviceName;
	}

	public Long getStartTime()
	{
		return startTime;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (endTime == null ? 0 : endTime.hashCode());
		result = prime * result + (methodName == null ? 0 : methodName.hashCode());
		result = prime * result + (serviceName == null ? 0 : serviceName.hashCode());
		result = prime * result + (startTime == null ? 0 : startTime.hashCode());
		return result;
	}

	public void setEndTime(Long endTime)
	{
		this.endTime = endTime;
	}

	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}

	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	public void setStartTime(Long startTime)
	{
		this.startTime = startTime;
	}

	@Override
	public String toString()
	{
		return "MethodInvocationStatistics [serviceName=" + serviceName + ", methodName="
				+ methodName + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
}
