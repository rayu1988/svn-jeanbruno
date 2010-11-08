THIS ECLIPSE PROJECT TAKES REFERENCE TO NAKOV'S SOURCE CODE PROJECT AND HAS BEEN CHANGED PROJECT AND CONSEQUENTELY THIS README.
THE ORIGN TEXT FROM README WAS;
	To generate self-signed certificate for signing the applet, run generate-certificate.bat.
	
	To build the applet, run build-script.bat. Be sure to stop the Web browser (all its windows) and Java Plug-in before. Otherwise the file SmartCardSignerApplet.jar will be locked and will not be replaced (access denied).
	
	You should use JDK 1.5 or later in order to compile and use this applet (PKCS#11 and smart cards support is available since Java 1.5).


...RIGHT NOW IS;
 
	To generate self-signed certificate and the signed jar applet enough run build.xml in the build target.

BY NOW, JUST CHANGED THE WAY OF GENERATE CERTIFICATE JKS AND THE SIGNED JAR APPLET.
THE DIRECTORY'S STRUCTURE ALSO HAS BEEN CHANGED.

TO EXPORT THE CERTIFICATE TOGETHER SIGNED JAR APPLET, IT'S CAN BE FOUND IN 'certificate' AND 'deploy' DIRECTORY RESPECTIVELY.

NOTE:
THE FILES SmartCardSignerApplet.jar AND SmartCardSignerApplet.jks INSIDE THE 'xhtml' directory, MORE THE DIRECTORIES 'certificate', 'deploy' AND THE CONTENTS INSIDE THEY CANNOT BE COMMITED TO THE SERVER SVN.