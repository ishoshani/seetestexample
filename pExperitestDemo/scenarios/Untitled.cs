using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using experitestClient;

namespace Experitest
{
    [TestClass]
    public class aditi
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
            client.SetReporter("xml", "reports", "aditi");
        }

        [TestMethod]
        public void Testaditi()
        {
            client.SetDevice("ios_app:iPhone 8plus A11");
            //int int0 = client.GetElementCount("NATIVE", "xpath=//*[@accessibilityIdentifier='privacy_terms']");
            //client.Click("NATIVE", "xpath=//*[@accessibilityIdentifier='privacy_terms']", 0, 1);
            string str1 = client.ElementSetProperty("NATIVE", "xpath=//*[@class='RCTPicker']", 0, "text", "01");
            string str2 = client.ElementSetProperty("NATIVE", "xpath=//*[@class='RCTPicker']", 1, "text", "2018");
            string str3 = client.ElementSetProperty("NATIVE", "xpath=//*[@class='RCTPicker']", 0, "text", "05");
            client.Click("NATIVE", "xpath=//*[@text='08']", 0, 1);
            client.ElementSwipe("NATIVE", "xpath=//*[@class='RCTPicker']", 1, "Down", 420, 2000);
            client.Click("NATIVE", "xpath=//*[@accessibilityLabel='Done' and @accessibilityIdentifier='DatePicker_Done_Button']", 0, 1);
            string str4 = client.SetPickerValues("NATIVE", "xpath=//*[@class='RCTPicker' and ./*[./*[./*[./*[./*[./*[./*[@text='01']]]]]]]]", 0, 0, "02");
            client.SendText("{ENTER}");
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