package com.cinnober.gradle.semver_git

import groovy.util.GroovyTestCase

class SemverGitPluginTest extends GroovyTestCase { 
    void testParseVersion(versionStr, expVersionArr) {
        assertArrayEquals((Object[])expVersionArr, (Object[])SemverGitPlugin.parseVersion(versionStr));
    }
    void testParseVersion100() {
        testParseVersion("1.0.0", [1,0,0,null]);
    }
    void testParseVersion123() {
        testParseVersion("1.2.3", [1,2,3,null]);
    }
    void testParseVersion123beta() {
        testParseVersion("1.2.3-beta", [1,2,3,"beta"]);
    }
    void testParseVersion123snapshot() {
        testParseVersion("1.2.3-SNAPSHOT", [1,2,3,"SNAPSHOT"]);
    }
    void testParseVersion12_34_56_rc78() {
        testParseVersion("12.34.56-rc78", [12,34,56,"rc78"]);
    }
    void testFailParseVersion_abc() {
        shouldFail({SemverGitPlugin.parseVersion("a.b.c")});
    }
    void testFailParseVersion_1() {
        shouldFail({SemverGitPlugin.parseVersion("1")});
    }
    void testFailParseVersion_a123() {
        shouldFail({SemverGitPlugin.parseVersion("a1.2.3")});
    }
    void testFailParseVersion_123a() {
        shouldFail({SemverGitPlugin.parseVersion("1.2.3a")});
    }

    void testFormatVersion(expVersionStr, versionArr) {
        assertEquals(expVersionStr, SemverGitPlugin.formatVersion(versionArr));
    }
    void testFormatVersion100() {
        testFormatVersion("1.0.0", [1,0,0,null]);
    }
    void testFormatVersion123() {
        testFormatVersion("1.2.3", [1,2,3,null]);
    }
    void testFormatVersion123beta() {
        testFormatVersion("1.2.3-beta", [1,2,3,"beta"]);
    }
    void testFormatVersion123snapshot() {
        testFormatVersion("1.2.3-SNAPSHOT", [1,2,3,"SNAPSHOT"]);
    }
    void testFormatVersion12_34_56_rc78() {
        testFormatVersion("12.34.56-rc78", [12,34,56,"rc78"]);
    }

    void testNextVersion(expVersion, version, nextVersion, snapshotSuffix) {
        assertEquals(expVersion, SemverGitPlugin.getNextVersion(version, nextVersion, snapshotSuffix));
    }
    void testNextPatchVersion123() {
        testNextVersion("1.2.4-SNAPSHOT", "1.2.3", "patch", "SNAPSHOT");
    }
    void testNextPatchVersion123beta() {
        testNextVersion("1.2.3-SNAPSHOT", "1.2.3-beta", "patch", "SNAPSHOT");
    }
    void testNextMinorVersion123() {
        testNextVersion("1.3.0-SNAPSHOT", "1.2.3", "minor", "SNAPSHOT");
    }
    void testNextMinorVersion123beta() {
        testNextVersion("1.2.3-SNAPSHOT", "1.2.3-beta", "minor", "SNAPSHOT");
    }
    void testNextMinorVersion120beta() {
        testNextVersion("1.2.0-SNAPSHOT", "1.2.0-beta", "minor", "SNAPSHOT");
    }
    void testNextMajorVersion123() {
        testNextVersion("2.0.0-SNAPSHOT", "1.2.3", "major", "SNAPSHOT");
    }
    void testNextMajorVersion123beta() {
        testNextVersion("1.2.3-SNAPSHOT", "1.2.3-beta", "major", "SNAPSHOT");
    }
    void testNextMajorVersion100beta() {
        testNextVersion("1.0.0-SNAPSHOT", "1.0.0-beta", "major", "SNAPSHOT");
    }

}