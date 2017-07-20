# synology-dsmclient
Synology DSM WebAPI - Java client lib

Small Java-Wrapper for calling Synology DSM WebAPI. My personal focus is on controlling users, groups and basic system functions - not so much the DownloadStation etc.

Implementing further APIs is more or less easy and will be improved later.

Compile & use it
======
### Status
[![Build Status](https://travis-ci.org/cljk/synology-dsmclient.png)](https://travis-ci.org/cljk/synology-dsmclient)


Requires Maven 3.x (perhaps 2.x will work too)

Checkout and compile it
```
git clone https://github.com/cljk/synology-dsmclient.git
cd synology-dsmclient
mvn install
```

Mini-Demo
----

Focus is on providing an API to be used in other projects - but contains a mini demo console - run it with (warning: console app acceppts ANY certificate - don´t use it over internet or untrusted networks):
```
mvn exec:java -Dexec.args="{dsmHostIp/dsmHostName} {dsmPort} {useSsl:true/false} {userName} {password}"
```
example:
```
mvn exec:java -Dexec.args="192.168.188.2 5001 true admin PASSWORD"
```

Basically the logic of the console is:
```
DsmConnection conn = new DsmConnection(host, Integer.parseInt(port), Boolean.parseBoolean(useSsl), true);

JsonObject apiInfo = conn.queryApis();
System.out.println("apiQuery: " + apiInfo);
		
String sid = conn.login(user, password);
System.out.println("sid: " + sid);
		
JsonObject utilize = conn.getCore().getSystem().getCurrentUtilization();
System.out.println("util: " + utilize);
		
conn.logout();
```
