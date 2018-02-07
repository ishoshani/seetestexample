using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using experitestClient;

namespace Experitest
{
    [TestClass]
    public class thisthing
    {
        private string host = "localhost";
        private int port = 8889;
        private string projectBaseDirectory = "C:\\Users\\ido.shoshani\\workspace\\pExperitestDemo";
        protected Client client = null;
        
        [TestInitialize()]
        public void SetupTest()
        {
            client = new Client(host, port, true);
            client.SetProjectBaseDirectory(projectBaseDirectory);
            client.SetReporter("xml", "reports", "thisthing");
        }

        [TestMethod]
        public void Testthisthing()
        {
            client.SetDevice("ios_app:iPhone 8plus A11");
            client.Launch("com.discoverfinancialenterprise.mobile", true, true);
            client.Click("default", "element 3", 0, 1);
            client.Click("default", "element 4", 0, 1);
            client.Click("default", "element 5", 0, 1);
            client.Click("default", "element 6", 0, 1);
            string str0 = client.ElementSetProperty("NATIVE", "xpath=//*[@class='RCTPicker']", 0, "", "03");
        }

        [TestCleanup()]
        public void TearDown()
        {
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        client.GenerateReport(false);
        // Releases the client so that other clients can approach the agent in the near future. 
        client.ReleaseClient();
        }
    }
}