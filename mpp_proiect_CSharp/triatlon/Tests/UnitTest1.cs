using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using triatlon.repository;
using System.Configuration;
namespace Tests
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void testRepoArbitru()
        {
            testRepoArbitruSave();
        }
        public void testRepoArbitruSave()
        {

            TestRepository tr = new TestRepository();
            tr.testAdauga();
            tr.testSterge();
        }
    }
}
