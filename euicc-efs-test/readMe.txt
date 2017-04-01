#by url
wsdl2java -autoNameResolution -p com.whty.efs.webservice.es.message -encoding utf-8 -all http://localhost:8080/euicc-efs-container/webservice/EnterFrontService?wsdl
#by file
wsdl2java -autoNameResolution -p com.whty.efs.webservice.es.message -encoding utf-8 -all WebServicesAPI-euicc/WebServicesAPI/ES2_MNO?wsdl
wsdl2java -autoNameResolution -p com.whty.efs.webservice.es.message -encoding utf-8 -all WebServicesAPI/ES2_MNO?wsdl