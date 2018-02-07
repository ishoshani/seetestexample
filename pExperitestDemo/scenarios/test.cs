using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using experitestClient;

namespace Experitest
{
    [TestClass]
    public class test
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
            client.SetReporter("xml", "reports", "test");
        }

        [TestMethod]
        public void Testtest()
        {
            // it is recommended to start your script with SetDevice command:
            // client.SetDevice( <Device Name>);
            string str0 = client.ElementSetProperty("NATIVE", "xpath=//*[@class='RCTPicker']", 0, "text", "05,0");
            string str1 = client.ElementSetProperty("NATIVE", "xpath=//*[@class='RCTPicker']", 0, "text", "05,0");
            string str2 = client.ElementSetProperty("NATIVE", "xpath=//*[@class='RCTPicker']", 1, "text", "2025,0");
            string str3 = client.ElementSetProperty("NATIVE", "xpath=//*[@class='RCTPicker']", 1, "text", "2025,0");
            client.Drag("NATIVE", "xpath=//*[@class='RCTPicker']", 0, 0, 100);
            client.Drag("NATIVE", "xpath=//*[@class='RCTPicker']", 0, 0, -100);
            client.Drag("NATIVE", "xpath=//*[@class='RCTPicker']", 1, 0, 100);
            client.Drag("NATIVE", "xpath=//*[@class='RCTPicker']", 1, 0, -100);
            client.Click("NATIVE", "xpath=//*[@accessibilityLabel='Done' and @class='RCTText']", 0, 1);
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