package com.collabnet.ccf.core.rmdhandlers;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import com.collabnet.ccf.core.db.RMDConfigDBExtractor;

public class RMDFilterHandlerTest {

    private static final String rmdID = "1";

    @Test
    public void test11Values() {
        RMDFilterHandler filterHandler = getFilterHandler("values:artf1234");
        Assertions.assertTrue(filterHandler.containsId(rmdID, "artf1234"));
        Assertions.assertFalse(filterHandler.containsId(rmdID, "testValue"));

    }

    @Test
    public void test12CommaSeperatedForValues() {
        RMDFilterHandler filterHandler = getFilterHandler("values:artf111,artf1234");
        Assertions.assertTrue(filterHandler.containsId(rmdID, "artf1234"));
        Assertions.assertTrue(filterHandler.containsId(rmdID, "artf111"));
        Assertions.assertFalse(filterHandler.containsId(rmdID, "testValue"));

    }

    @Test
    public void test13EmptyQueryForValues() {
        RMDFilterHandler filterHandler = getFilterHandler("values:");
        validateAssert(filterHandler);
    }

    @Test
    public void test14EmptyForValues() {
        RMDFilterHandler filterHandler = getFilterHandler("");
        validateAssert(filterHandler);
    }

    @Test
    public void test15NullForValues() {
        RMDFilterHandler filterHandler = getFilterHandler(null);
        Assertions.assertTrue(filterHandler.containsId(rmdID, "artf1234")); //containsId returns true if filter is not enabled
        Assertions.assertTrue(filterHandler.containsId(rmdID, ""));//containsId returns true if filter is not enabled

    }

    @Test
    public void test20Regex() {
        RMDFilterHandler filterHandler = getFilterHandler("regex:^artf123.$");
        Assertions.assertTrue(filterHandler.containsId(rmdID, "artf1234"));
        Assertions.assertTrue(filterHandler.containsId(rmdID, "artf1235"));
        Assertions.assertFalse(filterHandler.containsId(rmdID, "artf12345"));
        Assertions.assertFalse(filterHandler.containsId(rmdID, "testValue"));

    }

    @Test
    public void test21RegexWithMistake() {
        RMDFilterHandler filterHandler = getFilterHandler("regex:^artf123.+*$");
        Assertions.assertFalse(filterHandler.containsId(rmdID, "artf1234"));
        Assertions.assertFalse(filterHandler.containsId(rmdID, "testValue"));

    }

    @Test
    public void test24EmptyQueryForRegex() {
        RMDFilterHandler filterHandler = getFilterHandler("regex:");
        validateAssert(filterHandler);
    }

    @Test
    public void test30Ranges() {
        RMDFilterHandler filterHandler = getFilterHandler("ranges:jira-1234$jira-1237");
        filterHandler.setRangeSeperator("$");
        Assertions.assertTrue(filterHandler.containsId(rmdID, "jira-1234"));
        Assertions.assertTrue(filterHandler.containsId(rmdID, "jira-1235"));
        Assertions.assertTrue(filterHandler.containsId(rmdID, "jira-1236"));
        Assertions.assertTrue(filterHandler.containsId(rmdID, "jira-1237"));
        Assertions.assertFalse(filterHandler.containsId(rmdID, "testValue"));

    }

    @Test
    public void test31Ranges() {
        RMDFilterHandler filterHandler = getFilterHandler("ranges:artf111-artf1234");
        Assertions.assertTrue(filterHandler.containsId(rmdID, "artf1234"));
        Assertions.assertTrue(filterHandler.containsId(rmdID, "artf112"));
        Assertions.assertTrue(filterHandler.containsId(rmdID, "artf1230"));
        Assertions.assertTrue(filterHandler.containsId(rmdID, "artf111"));
        Assertions.assertFalse(filterHandler.containsId(rmdID, "testValue"));

    }

    @Test
    public void test32EmptyQueryForRanges() {
        RMDFilterHandler filterHandler = getFilterHandler("ranges:");
        validateAssert(filterHandler);
    }

    @Test
    public void test33MisMatchQueryForRanges() {
        RMDFilterHandler filterHandler = getFilterHandler("ranges:artf1234-abc1235");
        Assertions.assertFalse(filterHandler.containsId(rmdID, "artf1234"));
        Assertions.assertFalse(filterHandler.containsId(rmdID, "abc1235"));
    }

    @Test
    public void test33RangeSeperatorAsQueryRanges() {
        RMDFilterHandler filterHandler = getFilterHandler("ranges: - ");
        Assertions.assertTrue(filterHandler.containsId(rmdID, "artf1234"));
    }

    @Test
    public void test40HospitalOnly() {
        RMDFilterHandler filterHandler = getFilterHandler("ignoreOrdinaryArtifactUpdates");
        Assertions.assertTrue(filterHandler.ignoreOrdinaryArtifactUpdates(rmdID));
        Assertions.assertTrue(filterHandler.containsId(rmdID, "artf1234"));
    }

    @Test
    public void test41NullForHospitalOnly() {
        RMDFilterHandler filterHandler = getFilterHandler(null);
        Assertions.assertFalse(filterHandler.ignoreOrdinaryArtifactUpdates(rmdID));
    }

    @Test
    public void test42EmptyForHospitalOnly() {
        RMDFilterHandler filterHandler = getFilterHandler("");
        Assertions.assertFalse(filterHandler.ignoreOrdinaryArtifactUpdates(rmdID));
    }

    @Test
    public void test50QueryWithoutFormat() {
        RMDFilterHandler filterHandler = getFilterHandler("abc");
        validateAssert(filterHandler);
    }

    @Test
    public void test51QueryWithSymbols() {
        RMDFilterHandler filterHandler = getFilterHandler("abc !@#!@#!@#$%%^%&^&**^**(");
        validateAssert(filterHandler);
    }

    @Test
    public void test54QueryWithAplhaNumeric() {
        RMDFilterHandler filterHandler = getFilterHandler("1234qwer");
        validateAssert(filterHandler);
    }

    @Test
    public void test55QueryWithColonAndAphen() {
        RMDFilterHandler filterHandler = getFilterHandler(" : - ");
        validateAssert(filterHandler);
    }

    private Map<String, String> getDummyRMDConfigMap(String filterValue) {
        Map<String, String> rmdconfig = new HashMap<String, String>();
        rmdconfig.put(FilterHandler.FILTER_KEY, filterValue);
        return rmdconfig;
    }

    private RMDConfigDBExtractor getDummyRMDExtractor(String rmdIdString,
            String filterValue) {
        Map<String, Map<String, String>> rmdAndRmdConfigMap = new HashMap<String, Map<String, String>>();
        rmdAndRmdConfigMap.put(rmdIdString, getDummyRMDConfigMap(filterValue));
        RMDConfigDBExtractor dummyExtractor = new RMDConfigDBExtractor();
        dummyExtractor.setRmdAndRMDConfigMap(rmdAndRmdConfigMap);
        return dummyExtractor;
    }

    private RMDFilterHandler getFilterHandler(String rmdConfigVal) {
        RMDFilterHandler filterHandler = new RMDFilterHandler();
        RMDConfigDBExtractor dummyExtractor = getDummyRMDExtractor(rmdID,
                rmdConfigVal);
        filterHandler.setRmdConfigExtractor(dummyExtractor);
        return filterHandler;
    }

    private void validateAssert(RMDFilterHandler filterHandler) {
        Assertions.assertTrue(filterHandler.containsId(rmdID, "artf1234"));
        Assertions.assertTrue(filterHandler.containsId(rmdID, "testValue"));
    }
}
