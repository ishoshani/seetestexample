using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using experitestClient;

namespace Experitest
{
    [TestClass]
    public class download
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
            client.SetReporter("xml", "reports", "download");
        }

        [TestMethod]
        public void Testdownload()
        {
            client.SetDevice("adb:samsung GalaxyS8Plus S5");
            client.DeviceAction("Home");
            client.Click("default", "Store", 0, 1);
            client.Click("default", "search_button", 0, 1);
            client.ElementSendText("default", "Searchstore", 0, "seller profit calculator");
            client.SendText("{ENTER}");
            client.Click("NATIVE", "xpath=//*[@id='play_card' and ./*[@text='eBay Seller Profit Calculator']]", 0, 1);
            client.Click("NATIVE", "xpath=//*[@text='INSTALL']", 0, 1);
            client.Click("NATIVE", "xpath=//*[@text='OPEN']", 0, 1);
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