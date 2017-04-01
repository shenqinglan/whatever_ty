wsdl2java -p com.whty.efs.client -client http://localhost:8080/euicc-efs-container/webservice/EnterFrontService?wsdl
wsdl2java -p com.whty.efs.client -client -autoNameResolution http://localhost:8080/euicc-efs-container/webservice/ES2MnoService?wsdl
wsdl2java -autoNameResolution -p com.whty.efs.webservice.es.message -encoding utf-8 -all http://localhost:8080/euicc-efs-container/webservice/ES3SmDpService?wsdl
wsdl2java -autoNameResolution -p com.whty.efs.webservice.es.message -encoding utf-8 -all http://localhost:8080/euicc-efs-container/webservice/ES4MnoService?wsdl
