package com.viasat.wildblue.common.util;

import org.junit.Test;

/*
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.viasat.facade.voip.alianza.data.account.AccountTypeListInput;
import com.viasat.facade.voip.alianza.data.account.AccountTypeListOutput;
import com.viasat.facade.voip.alianza.data.account.AccountTypeListOutputListItem;
import com.viasat.facade.voip.alianza.webservice.account.AccountSoap;
import com.viasat.wildblue.common.header.InvokedBy;
import com.viasat.wildblue.common.header.WildBlueHeader;
import com.viasat.wildblue.internalwebservice.businesstransaction.BusinessTransaction;
import com.viasat.wildblue.internalwebservice.businesstransaction.data.GetServiceAgreementByInternalReference;
import com.viasat.wildblue.internalwebservice.businesstransaction.data.GetServiceAgreementByInternalReferenceResponse;
import com.viasat.wildblue.internalwebservice.businesstransaction.data.ServiceAgreement;
*/
/**
 * For compilation add the following dependencies to the pom.xml file:
 *      <dependency>
 *          <groupId>com.viasat.facade.voip</groupId>
 *          <artifactId>Facade-VoIp-alianza-api</artifactId>
 *          <version>2.0.0-016</version>
 *          <scope>test</scope>
 *      </dependency>
 *      <dependency>
 *          <groupId>com.viasat.wildblue.internalwebservice.businesstransaction</groupId>
 *          <artifactId>InternalWebService-BusinessTransaction-API</artifactId>
 *          <version>3.2.0-003</version>
 *          <scope>test</scope>
 *      </dependency>
 *
 * For test with a proxy server to which the local machine can't connect, use the SSH
 * local port forwarding to connect to a remote machine that is capable to connect the
 * proxy server. See details in the {@link #testProxyServer()} method.
 *
 * @author Xiao-Li Yang
 */
public class EndpointWrapperTest
{
    @Test
    public void dummy() {}
/*
    private EndpointWrapper<BusinessTransaction> btWrapper;
    private EndpointWrapper<AccountSoap> accountWrapper;
    private MyBTS myBTS;

    private static class MyBTS extends EndpointWrapper<BusinessTransaction> {
    }

    @Before
    public void setup() {
        btWrapper = new EndpointWrapper<BusinessTransaction>() {};
        assertNotNull(btWrapper);

        myBTS = new MyBTS();
        assertNotNull(myBTS);

        accountWrapper = new EndpointWrapper<AccountSoap>() {};
        assertNotNull(accountWrapper);
    }

    //@Test
    public void testGetEndpoint() throws Exception {
        String url1 = "http://apipri01.dev.wdc1.wildblue.net:11251/BusinessTransactionWebService/v3/services/BusinessTransactionService?wsdl";
        String url2 = "httpXX://apipri02.dev.wdc1.wildblue.net:11251/BusinessTransactionWebService/v3/services/BusinessTransactionService?wsdl";

        BusinessTransaction btep = btWrapper.getEndpoint(url2);
        assertNotNull(btep);
        btWrapper.setTimeouts(300000L, 300000L);

        try {
            getServiceAgreementByInternalRef(btep, "400774574");
            fail("Should not reach this line.");
        } catch (Exception e) {
            // expected, so ignored.
            System.out.print("Got the expected exception.");
        }

        btep = btWrapper.getEndpoint(url1);
        assertNotNull(btep);
        getServiceAgreementByInternalRef(btep, "400774574");

        // use explicit subclass MyBTS
        btep = myBTS.getEndpoint(url1);
        getServiceAgreementByInternalRef(btep, "400774574");
    }

    //@Test
    public void testProxyServer() throws Exception {
        // use ssh to forward through apipri01:
        // ssh -vL 9090:proxy.test.wdc1.wildblue.net:3128 weblogic@apipri01.dev.wdc1.wildblue.net
        String url1 = "https://services.beta.alianza.com:1701/4.0/Account.asmx?wsdl";
        String proxyUrl = "localhost", proxyType = "HTTP";
        int proxyPort = 9090;
        // String proxyUrl = "proxy.test.wdc1.wildblue.net", proxyType = "HTTP";
        // int proxyPort = 3128;

        AccountSoap accountep = accountWrapper.getEndpoint(url1);
        assertNotNull(accountep);
        accountWrapper.setProxyServer(proxyUrl, proxyPort, proxyType);
        //accountWrapper.setTimeouts(300000L, 300000L);

        getAccountTypes(accountep);
    }

    private void getAccountTypes(AccountSoap accountep) {
        AccountTypeListInput request = new AccountTypeListInput();
        request.setB64SecurityKey("ZXN3aHN3dmFodHN4eXd5d2t0dGNodW");
        request.setClientCode("VIA062");
        AccountTypeListOutput output = accountep.accountTypeList(request);
        List<AccountTypeListOutputListItem> itemList = (output.getReturnValue() == 0 && output
                .getOutputList() != null) ? output.getOutputList()
                .getAccountTypeListOutputListItem() : null;
        assertNotNull(itemList);
        assertTrue(itemList.size() > 0);
    }

    private void getServiceAgreementByInternalRef(BusinessTransaction btep, String serviceAgreementRef) throws Exception {
        GetServiceAgreementByInternalReference request = new GetServiceAgreementByInternalReference();
        request.setServiceAgreementReference(serviceAgreementRef);

        GetServiceAgreementByInternalReferenceResponse response = btep.getServiceAgreementByInternalReference(request, createWildBlueHeader());

        ServiceAgreement serviceAgreement = response.getServiceAgreement();
        assertNotNull(serviceAgreement);
    }

    protected WildBlueHeader createWildBlueHeader()
    {
        WildBlueHeader answer = new WildBlueHeader();
        InvokedBy invokedBy = new InvokedBy();

        invokedBy.setApplication("WebServiceClietn");
        invokedBy.setUsername("XLY");

        answer.setInvokedBy(invokedBy);
        return answer;
    }
*/
}
