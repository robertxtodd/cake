
CHANGED
=======
URLS NOW:
	http://localhost:8080/
	http://localhost:8080/cakes

RUN WITH:
	mvn spring-boot:run

CODE CHANGES:
added spring boot as it bundled in a lot of other nice addons like REST annotations needed to supply requests
moved jsp under web-inf to allow no access from outside
added MvcConfiguration class to handle View resolution from return types of controller methods
HibernateUtil.buildSessionFactory() reworked as it was creating the config twice and causing an error
CakeServlet.init() now on a PostContruct to ensure loading of data only happens at startup, also removed lot of json creation code
CakeEntity Hibernate JPA amended to more suitable table names, fixed error on mapping, added json mappings to allow jackson to marshall, toString for logging
CakeServletTest class created to handle MockMvc tests

MANUAL TESTING DONE WITH CHROME ARC TO CHECK RESULTS WITH APP/JSON REQUESTS...

