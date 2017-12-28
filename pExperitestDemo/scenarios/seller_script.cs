using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using experitestClient;

namespace Experitest
{
    [TestClass]
    public class sellerscript
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
            client.SetReporter("xml", "reports", "sellerscript");
        }

        [TestMethod]
        public void Testsellerscript()
        {
            string str0 = client.WaitForDevice("", 300000);
            client.DeviceAction("Home");
            client.Sleep(1000);
            if(client.SwipeWhileNotFound("Right", 100, 208, "NATIVE", "xpath=//*[@text='Seller Profit Calculator']", 0, 0, 7, false))
            {
                  // If statement
            }
            client.Click("NATIVE", "xpath=//*[@text='Seller Profit Calculator']", 0, 1);
            if(client.WaitForElement("NATIVE", "xpath=//*[@id='item_cost_edit_text']", 0, 10000))
            {
                  // If statement
            }
            client.ElementSendText("NATIVE", "xpath=//*[@id='item_cost_edit_text']", 0, "+-()*&^%$#@!akjdfhaueru;fha");
            if(client.WaitForElement("NATIVE", "xpath=//*[@id='shipping_cost_edit_text']", 0, 30000))
            {
                  // If statement
            }
            client.ElementSendText("NATIVE", "xpath=//*[@id='shipping_cost_edit_text']", 0, "+_{})(*&^gjhgkjh");
            if(client.WaitForElement("NATIVE", "xpath=//*[@class='android.widget.LinearLayout' and @height>0 and ./*[@class='android.widget.FrameLayout' and ./*[@id='decor_content_parent']]]", 0, 30000))
            {
                  // If statement
            }
            client.ElementSendText("NATIVE", "xpath=//*[@class='android.widget.LinearLayout' and @height>0 and ./*[@class='android.widget.FrameLayout' and ./*[@id='decor_content_parent']]]", 0, "+_=-!`dfake");
            client.SendText("{ENTER}");
            client.SendText("+_=-hasdfas23");
            client.SendText("{ENTER}");
            client.SendText("go+_=-)(*&^%$#@!{}@#$%kjh)(*&*");
            client.Sleep(1000);
            if(client.SwipeWhileNotFound("Down", 761, 602, "NATIVE", "xpath=//*[@id='arrow_icon']", 0, 0, 2, false))
            {
                  // If statement
            }
            client.Click("NATIVE", "xpath=//*[@id='arrow_icon']", 0, 1);
            client.Click("NATIVE", "xpath=//*[@text='Reserve price']", 0, 1);
            client.ElementSendText("NATIVE", "xpath=//*[@id='reserve_price_edit_text']", 0, "2234526+_=-)(*&^%$#@!");
            client.SendText("{ENTER}");
            client.Click("NATIVE", "xpath=//*[@text='Calculate']", 0, 1);
            client.SetAuthenticationReply("Success", 0);
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