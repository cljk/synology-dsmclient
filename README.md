[![Build Status](https://travis-ci.org/cljk/synology-dsmclient.png)](https://travis-ci.org/cljk/synology-dsmclient)
[![Dependency Status](https://www.versioneye.com/user/projects/59765f760fb24f003f48d061/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/59765f760fb24f003f48d061)

# synology-dsmclient
Synology DSM WebAPI - Java client lib

Small Java-Wrapper for calling Synology DSM WebAPI. My personal focus is on controlling users, groups and basic system functions - not so much the DownloadStation etc.

Implementing further APIs is more or less easy and will be improved later.

## Compile & use it
Requires Maven 3.x (perhaps 2.x will work too)

Checkout and compile it
```
git clone https://github.com/cljk/synology-dsmclient.git
cd synology-dsmclient
mvn install site:site
```

## Mini-Demo
Focus is on providing an API to be used in other projects - but contains a mini demo console - run it with (warning: console app acceppts ANY certificate - don´t use it over internet or untrusted networks):
```
mvn compile exec:java -Dexec.args="{dsmHostIp/dsmHostName} {dsmPort} {useSsl:true/false} {userName} {password}"
```
example:
```
mvn compile exec:java -Dexec.args="192.168.188.2 5001 true admin PASSWORD"
```

Basically the logic of the console is:
```java
// init DSM connection
DsmConnection conn = new DsmConnection(host, Integer.parseInt(port), Boolean.parseBoolean(useSsl), true);
WebApi webApi = conn.getWebApi();

// invoke some API calls
JsonObject apiInfo = webApi.queryApis();
System.out.print("apiQuery:");
jsonWriterFactory.createWriter(System.out).writeObject(apiInfo);

String sid = webApi.getAuth().login(user, password);
System.out.println("sid: " + sid);

JsonObject utilize = webApi.getCore().getSystem().getCurrentUtilization();
System.out.print("utilize:");
jsonWriterFactory.createWriter(System.out).writeObject(utilize);


webApi.getAuth().logout();
```

## Protocol reverse engineering snippets
* [blob/master/src/site/markdown/syno-office-file-listing-requests.md](https://github.com/cljk/synology-dsmclient/blob/master/src/site/markdown/syno-office-file-listing-requests.md)
