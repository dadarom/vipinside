
## cmd
mvn clean install -Pdev
mvn jetty:run -Pdev

## config files
1. profile -> conf/web_config.properties
2. ${user.home}/qs_web/config.properties

## database
1. h2(dev)
2. mysql(qa/staging/prod)
3. oracle