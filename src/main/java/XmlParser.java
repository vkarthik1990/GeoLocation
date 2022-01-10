
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class XmlParser {

    private static final String FILENAME = "src/main/resources/hawker-centres-kml.kml";

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter Latitude");
        double input_Latitude = Double.valueOf(myObj.nextLine());
        System.out.println("Enter Longitude: ");
        double input_longitude =  Double.valueOf(myObj.nextLine());
        System.out.println("Enter closest_threshold: ");
        int closest_threshold = Integer.valueOf(myObj.nextLine());

        //double input_Latitude= 103.896283;
        //double input_longitude = 1.392407 ;
        //int closest_threshold = 5;

        List<Hawker> hawkerList=parseGeoLocFile(FILENAME);
        List<Hawker> result=getDistance(hawkerList,input_Latitude,input_longitude);

        Collections.sort(result, new Comparator<Hawker>() {

            @Override
                public int compare(Hawker c1, Hawker c2) {
                    return Double.compare(c1.getDistance(), c2.getDistance());
                }
            });

            
            for(Hawker h:result){
                if(h.getStatus().equalsIgnoreCase("Existing") && closest_threshold!=0) {
                    System.out.println("hawker name : " + h.getName());
                    System.out.println("distance : " + h.getDistance());
                    System.out.println("Photo URL : " + h.getPhotourl());
                    closest_threshold--;
                }
            }





    }

    private static List<Hawker> parseGeoLocFile(String filename) {
        List<Hawker> hawkerList=new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("Placemark");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    NodeList simpleData = element.getElementsByTagName("SimpleData");
                    String cordinates = element.getElementsByTagName("Point").item(0).getTextContent();
                    String hawkername = XmlParser.getValue(simpleData, "name");
                    String photourl =  XmlParser.getValue(simpleData, "PHOTOURL");
                    String status =  XmlParser.getValue(simpleData, "STATUS");
                    //System.out.println("Status : " + status);
                    //System.out.println("cordinates : " + cordinates);
                    //System.out.println("hawkername : " + hawkername);
                    //System.out.println("photourl : " + photourl);

                    hawkerList.add(new Hawker(hawkername,cordinates,photourl,status));
                }
            }


            }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return hawkerList;
    }

    private static List<Hawker> getDistance(List<Hawker> hawkerList, double input_latitude, double input_longitude) {
        for(Hawker hc: hawkerList){
            hc.setDistance(DistanceCalculator.distance(hc.getLatitude(),hc.getLongitude(),input_latitude,input_longitude,'K'));
        }


        return hawkerList;
    }

    static String  getValue(NodeList node, String attribue) {
        for(int i=0; i <node.getLength(); i++ ){
            if(node.item(i).getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase(attribue)) {
                return node.item(i).getFirstChild().getNodeValue();
            }
        }
        return "NA";
    }

}